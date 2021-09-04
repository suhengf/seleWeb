package com.auto.interview;

/**
 * 对mybatis的理解
 */
public class MybatisUnderStanding {

    /**
     * 1.对jdbc操作的封装
     * 2.持久层为什么要用mybatis 一些并发情况需要对sql进行调优 开发人员可以直接对sql进行优化
     * 3.mybatis执行流程
     *  3.1 1、加载配置文件，将配置文件的全部信息封装到Configuration对象中，其中每个select/insert/delete/update标签体被封装为mappedstatement对象，mappedstatemet对象以map的形式存储，其中key为namespace+id；
     *  3.2、创建SqlSessionFactory工厂对象；
     *  3.3、创建SqlSession对象；
     *  3.4、SqlSession对象调用getMapper()方法，创建接口的代理对象；
     *  3.5、代理对象调用接口的方法，被代理逻辑拦截，执行invoke()方法；
     *  Invoke()方法底层通过namespace+id获取对应的SQL语句，最终调用preparedstatement对象执行SQL语句。
     */


}
