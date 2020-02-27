package com.wp.Think;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.*;

/**
 * @author: wp
 * @Title: Producer_Comsumer
 * @Description: 模拟日志
 * @date 2020/2/27 15:23
 */
@Slf4j
public class Producer_Comsumer {

    enum Level{
        ERROR,INFO
    }

    @Data
    @NoArgsConstructor
    static
    class Log{
        private String msg;
        private Level level;

        public Log( String msg ) {
            this.msg = msg;
        }
    }

    static class Logger{
        private boolean isStart = false;
        private boolean isEnd = false;
        private int count = 5;
        private BlockingQueue<Log> logs = new LinkedBlockingQueue( 32 );
        private ExecutorService pool = Executors.newSingleThreadExecutor();

        public synchronized void start() throws Exception {
            if(isStart){
                return ;
            }
            isStart = true;
            File file = File.createTempFile("foo", ".log");
            System.out.println(file.getAbsolutePath());
            final FileWriter writer = new FileWriter(file);
            pool.execute( new Runnable() {
                @Override
                public void run() {
                    try{
                        Long cts = System.currentTimeMillis();
                        int x = 0;
                        while(!isEnd){
                            Log poll = logs.poll( 3, TimeUnit.SECONDS );
                            if(null!=poll){
                                writer.write( poll.getMsg() );
                                writer.write( "\n" );
                                x++;
                            }
                           if(x==0){
                               continue;
                           }
                            if((null!=poll&&poll.getLevel()== Level.ERROR)
                                    ||x==count||System.currentTimeMillis()-cts>=5000){
                                log.info( "开始写入文件..." );
                                writer.flush();
                                x = 0;
                                cts = System.currentTimeMillis();
                            }
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }finally{
                        try {
                            writer.flush();
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                }
            } );

        }

        public synchronized  void end(){
            isEnd = true;
            pool.shutdown();
        }

        public void info(Log log){
            try {
                log.setLevel( Level.INFO );
                logs.put( log );
                start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void error(Log log){
            try {
                log.setLevel( Level.ERROR );
                logs.put( log );
                start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    public static void main( String[] args ) throws InterruptedException {
        Logger logger = new Logger();
        for(int i=0;i<10;i++){
            if(i%2==0){
                logger.error( new Log( "错误级别日志, i="+i ) );

            }else{
                logger.info( new Log( "info级别的日志,i="+i ) );
            }
        }

        TimeUnit.SECONDS.sleep( 20 );
        logger.end();
        System.out.println(logger.pool.isShutdown());
    }
}
