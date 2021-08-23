package com.auto.interview;

/**
 * hashMap put方法讲解
 * 恒心 没有恒心的人 一事无成  持之以恒  成大事的标配 小人常立志 君子立长志
 * 有恒产者有恒心  collcetions mybatis
 */
public class HashMapMethodPut {


    public static void main(String[] args) {
        //1.都是根据key 通过哈希算法 与(数组长度-1) 运算得出数组下标
        //2.如果数组下标为空 则将key 和value 封装成 Entry对象 jdk1.8 是node对象 并放入该位置
        //3.如果数组下标对象不为空 则需要分情况讨论
        //jdk 1.7 判断是否需要扩容 如果不扩容就生成 Entry对象 并使用头插法添加到当前链表
        //jdk 1.8 则先判断当前位置的node的类型 看是否红黑树 node 还是链表node
        //如果是红黑树  则将key 和 value 封装为一个红黑树节点并添加到红黑树中去 在这个过程中会判断红黑树是否存在key 如果存在则更新value
        //如果此位置上的node对象是链表节点 则将key和value 封装到为一个链表node并通过尾插法插入到链表的最后位置去  因为尾插法需要遍历链表
        //在遍历链表的过程中会判断是否存在当前key 如果存在则更新value 当遍历完链表后 将新链表node插入链表中  插入到链表后 会看到当前链表的节点个数 如果超过了8
        // 那么则会将该链表转成红黑树
        //将key和value 封装位node 插入链表或者红黑树 在判断是否需要进行扩容  如果需要就扩容  先插入 后判断需要不需要扩容


    }


}

