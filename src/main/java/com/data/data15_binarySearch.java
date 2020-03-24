package com.data;

/**
 * @author: wp
 * @Title: data15_binarySearch
 * @Description: 二分查找
 * @date 2020/3/23 16:21
 */
public class data15_binarySearch {
    //循环实现的最简单二分查找
    public static int bsearch01(int[] array,int value){
        int low = 0;
        int high = array.length-1;
        while(low<=high){
            int middle = low + ((high-low)>>1);
            if(array[middle]==value){
                return middle;
            }else if(array[middle]>value){
                high = middle-1;
            }else{
                low = middle+1;
            }
        }
        return -1;
    }

    //通过递归(recursion)实现的最简单二分查找
    public static int bsearch02(int[] array,int low,int high,int value){
        if(low>high){
            return -1;
        }
        int middle = low + ((high-low)>>1);
        if(array[middle]==value){
            return middle;
        }else if(array[middle]>value){
            high = middle-1;
        }else{
            low = middle+1;
        }
        return bsearch02( array,low,high,value );
    }

    public static double sqrt(double x){
        double low = 0.0;
        double high = x;
        if(x==1){
            return 1;
        }else if(x>0&&x<1){
            low = x;
            high = 1;
        }else{
            low = 1;
        }
        double middle = 0;
        while((high-low) > 0.000001){
            middle = low + ((high-low)/2);
            if(middle*middle<x){
                low = middle;
            }else if(middle*middle>x){
                high = middle;
            }else{
                return middle;
            }
        }
        return middle;

    }

    //查找等于给定值的第一个元素
    public static int bsearch03(int[] array,int value){
        int low = 0;
        int high = array.length-1;
        if(low<=high){
            int middle = low + ((high-low)>>1);
            if(array[middle]>=value){
                high = middle -1;
            }else{
                low = middle+1;
            }
        }
        if(low<array.length-1&&array[low]==value){
            return low;
        }else{
            return -1;
        }
    }

    //查找等于给定值的最后一个元素
    public static int bsearch04(int[] array,int value){
        int low = 0;
        int high = array.length-1;
        while(low<=high){
            int middle = low + ((high-low)>>1);
            if(array[middle]<=value){
                low = middle+1;
            }else{
                high = middle -1;
            }
        }
        if(high<array.length-1&&array[high]==value){
            return high;
        }
        return -1;
    }

    //查找大于等于给定值的第一个元素
    public static int bsearch05(int[] array,int value){
        int low = 0;
        int high = array.length-1;
        while(low<=high){
            int middle = low + ((high-low)>>1);
            if(array[middle]>=value){
                high = middle - 1;
            }else{
                low = middle + 1;
            }
        }
        if(low<array.length-1&&array[low]>=value){
            return low;
        }
        return -1;
    }

    //查找小于等于给定值的最后一个元素
    public static int bsearch06(int[] array,int value){
        int low = 0;
        int high = array.length-1;
        while(low<=high){
            int middle = low + ((high-low)>>1);
            if(array[middle]<=value){
                low = middle + 1;
            }else {
                high = middle - 1;
            }
        }
        if(high<array.length-1&&array[high]<=value){
            return high;
        }

        return -1;
    }

    public static void main( String[] args ) {
        int[] array = {8,11,19,23,27,33,33,44,55,55,98};
        int index = bsearch01( array, 11 );
        index = bsearch02( array, 0, 9, 11 );
        index = bsearch03( array,33 );
        index = bsearch04( array,33 );
        index = bsearch05( array,41 );
        index = bsearch06( array,33 );
        System.out.println(index);

    }
}
