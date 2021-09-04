package com.auto.interview;

/**
 * spring事务机制
 */
public class SpringTransactional {
    /**
     * 1.spring 事务底层是基于数据库事务和aop机制的
     * 2。针对使用了@transactional注解的bean spring会创建一个代理对象作为bean
     * 3.当调用代理对象的方法的时候 会先判断该方法上是否加了@Transactional注解
     * 4.如果加了 则利用事务管理器创建一个数据库链接
     * 5.执行完数据 如果没有发生异常 就提交事务
     * 6.如果出现了异常 这个异常是需要回滚的 就会回滚事务 否则任然提交事务
     * 7.
     */


}