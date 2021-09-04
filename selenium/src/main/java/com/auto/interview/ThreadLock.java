package com.auto.interview;

/**
 * 如何查看线程死锁
 */
public class ThreadLock {
    /**
     * 1.jstack 命令查看 jstack 命令会显示发生了线程死锁的线程
     * 2.或者两个线程区操作数据库时 数据库 发生了锁 这是可以查询数据库死锁的情况
     * 1.查看是锁表
     * show open tables where in_use>0;
     * 2.查看进程
     * show processlist
     * 3.查看正在死锁的事务
     * select * from information_schema.innodb_locks;
     * 4.查看等待锁的事务
     * select * from information_schema.innodb.waits;
     *
     */
}
