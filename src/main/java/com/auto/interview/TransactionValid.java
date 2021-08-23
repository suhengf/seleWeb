package com.auto.interview;

/**
 * 事务失效的情况
 */
public class TransactionValid {
    /**
     * 1.spring 事务是基于代理实现的 所以某个加了@transactional的方法 只有是其他类调用 这个注解才会剩下 如果是自己调 是不会生效
     * 2.某个方法是private 因为基于cglib实现 基于父子类实现  子类是不能重载父类的private 方法 所以无法利用代理 也会导致@Transaction失效
     */



}
