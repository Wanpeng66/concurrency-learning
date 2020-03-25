package com.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: wp
 * @Title: data18_hashtable
 * @Description: 两个课后思考题
 * @date 2020/3/25 20:12
 */
public class data18_hashtable {
    @Data
    @AllArgsConstructor
    static class Url{
        String url;
        int count;

    }
    /**
    * 假设我们有 10 万条 URL 访问日志，如何按照访问次数给 URL 排序
    */
    public static Url[] question1(String[] urls){
        Url[] res = new Url[urls.length];
        long count = 0;
        for (String url : urls) {
            int index = hash(url);
            Url re = res[index];
            if(null==re){
                res[index] = new Url(url,0);
            }else if(re.getUrl().equals( url )){
                re.setCount( re.getCount()+ 1 );
                if(re.getCount()>count){
                    count = re.getCount();
                }
            }else{
                int length = urls.length;
                for (int k = index+1;k<length;k++){
                    re = res[k];
                    if(null==re){
                        res[k] = new Url(url,0);
                    }else if(re.getUrl().equals( url )){
                        re.setCount( re.getCount()+1 );
                        if(re.getCount()>count){
                            count = re.getCount();
                        }
                    }
                    if(k==length-1){
                        length = index;
                        k = -1;
                    }
                }
            }
        }
        if(count<10000){
            //对res进行桶排序
        }else{
            //对res进行快排
        }
        return res;
    }

    /**
    * 有两个字符串数组，每个数组大约有 10 万条字符串，
     * 如何快速找出两个数组中相同的字符串
    */
    public static List<String> question2( String[] str1, String[] str2){
        //存放重复字符串的集合
        List<String> res = new ArrayList<>(  );
        String[] tmp = new String[str1.length];
        //先对str1数组进行散列，得到哈希表
        for (String str : str1) {
            int index = hash(str);
            String s = tmp[index];
            if(StringUtils.isEmpty( s )){
                tmp[index] = str;
            }else if(!s.equals( str )){
                int length = str1.length;
                for(int i=index+1;i<length;i++){
                    s = tmp[i];
                    if(StringUtils.isEmpty( str )){
                        tmp[i] = str;
                    }
                    if(i==length-1){
                        i= -1;
                        length = index;
                    }
                }
            }
        }
        //对str2数组进行散列，然后与哈希表比对,如果是重复字符串则放入res中
        for (String str : str2) {
            int index = hash(str);
            String s = tmp[index];
            if(!StringUtils.isEmpty( s )&&s.equals( str )){
                res.add( str );
            }else if(!StringUtils.isEmpty( s )&&!s.equals( str )){
                int length = tmp.length;
                for(int i=index+1;i<length;i++){
                    s = tmp[i];
                    if(!StringUtils.isEmpty( s )&&s.equals( str )){
                        res.add( str );
                    }
                    if(i == length-1){
                        i=-1;
                        length = index;
                    }
                }
            }

        }


        return res.isEmpty()?null:res;
    }

    //hash方法的伪代码
    private static int hash( String url ) {
        return 0;
    }

}
