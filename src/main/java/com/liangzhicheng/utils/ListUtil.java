package com.liangzhicheng.utils;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {

    /**
     * 拆分集合
     * @param list 需要拆分的集合
     * @param subListLength 每个子集合的元素个数
     * @param <T> 泛型对象
     * @return 返回拆分后的各个集合组成的列表
     */
    public static <T> List<List<T>> split(List<T> list, int subListLength){
        if((list == null && list.size() <= 0) || subListLength <= 0){
            return new ArrayList<>();
        }
        List<List<T>> handleList = new ArrayList<>();
        int size = list.size();
        if(size <= subListLength){
            //数据记录不足subListLength指定的大小
            handleList.add(list);
        }else{
            int num = size / subListLength;
            int last = size % subListLength;
            //首先num个集合，每个大小都是subListLength个元素
            for(int i = 0; i < num; i++){
                List<T> itemList = new ArrayList<>();
                for(int j = 0; j < subListLength; j++){
                    itemList.add(list.get(i * subListLength + j));
                }
                handleList.add(itemList);
            }
            //最后last进行处理
            if(last > 0){
                List<T> itemList = new ArrayList<>();
                for(int i = 0; i < last; i++){
                    itemList.add(list.get(num * subListLength + i));
                }
                handleList.add(itemList);
            }
        }
        return handleList;
    }

    /**
     * 构建集合
     * @return 返回构建集合
     */
    public static List<String> buildList(){
        List<String> list = new ArrayList<>();
        Integer size = 1099;
        for(int i = 0; i < size; i++){
            list.add(String.format("%s%s", "lzc -- ", i));
        }
        return list;
    }

    public static void main(String[] args){
        //大集合拆分多个小集合
        List<List<String>> splitList = split(buildList(), 100);
        int num = 0;
        //对大集合里的每一个小集合进行操作
        for(List<String> str : splitList){
            System.out.println(String.format("row:%s -->> size:%s,result:%s", ++num, str.size(), str));
        }
    }

}
