package com.auto.interview;

/**
 * 线程之间如何通讯
 */
public class ThreadContract {
    /**
     * 1.线程之间可以通过共享内存或者基于网络来通信
     * 2.如果是通过共享内存来进行通信 则需要考虑并发问题 什么时候堵塞 什么时候唤醒
     * 3.像 java中的wait（） notify （）就是堵塞和唤醒
     * 4.通过网络的就比较简单了 直接通过网络链接直接将通信数据发给对方 当前也要考虑并发 处理方式就是加锁
     */


}
