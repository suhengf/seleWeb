package com.auto.interview;

/**
 * threadLocal 线程本地存储
 * 1.可以利用该机制将数据缓存到某个线程内部 该线程可以在任意时刻 任意方法中获取缓存的数据
 * 2.threadLocal  底层是通过threadLocalMap 每个Thread 对象都存在一个ThreadLocalMap  map的key为ThreadLocal对象
 * map的value为需要缓存的对象
 * 3.如果线程池中使用thradlocal会造成内存泄露 因为当threadLocal 对象使用完之后 应该要把设置的key和value 也就是entry对象进行回收
 * 但线程池中的线程不会回收 而线程对象是通过强引用指向ThreadLocalMap ThreadLocalMap也是通过强引用指向entry对象 线程不会被回收
 * entry对象也不会被回收 从而出现内存泄露 解决方法是 在使用了ThreadLocal对象之后 手动调ThreadLocal的remove方法 手动清除entry对象
 * 4.ThreadLocal 经典的应用场景就是链接管理 (一个线程持有一个链接 该链接对象可以在不同的方法之间进行传递 线程之间不共享同一个链接)
 */
public class ThreadLocalTest {
    public static void main(String[] args) {


    }

}
