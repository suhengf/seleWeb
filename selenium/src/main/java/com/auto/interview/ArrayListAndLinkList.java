package com.auto.interview;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

/**
 * ArrayList 和 linkedList区别
 * 1.底层数据结构不同  ArrayList 底层是基于数组实现的 linkedList 底层是基于链表实现的
 * 2.由于底层数据结构的不同 他么所适用的场景不同 ArrayList 适合于随机查找 linkedlist 更适合于 删除和添加 (查询 添加 删除的时间复杂度也不同)
 * 3.另外ArrayList 和 linkedList都实现了List接口 但是linkedList还额外实现了Deque接口 所以linkedList还可以当队列用
 */
public class ArrayListAndLinkList {
    public static void main(String[] args) {
        /**
         * 指定下表查询  查询ArrayList由于 linkedList 但是如果查询 first 和 last linkedlist 优于ArrayList
         * 因为linkedList 底层有一个属性first  last
         *
         */

        ArrayList arrayList = new ArrayList();
        arrayList.get(1);//指定下标 查询
        arrayList.add(1);//添加到数据最后一个元素  反应还是比较快  扩容的话会比较慢
        arrayList.add(1,1);//(涉及到数组的移动)


        //双端队列
        LinkedList linkedList = new LinkedList();
        linkedList.get(2);//遍历链表
        linkedList.getFirst();//不涉及遍历
        linkedList.getLast();//不涉及遍历
        linkedList.add(1);//往列表添加 到最后一个元素  链表不存在扩容这个概念
        linkedList.add(1,1);//遍历找到那个下标  插入比较快  具体比较插入的话


        //Vector：数组 底层所有的方法都加锁了 synchronized
        Vector vector = new Vector();
        vector.add(1);


    }
}
