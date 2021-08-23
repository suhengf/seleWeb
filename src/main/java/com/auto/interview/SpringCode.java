package com.auto.interview;

/**
 * 介绍下spring 介绍下大致流程
 */
public class SpringCode {
    /**
     * 1.spring是一个快速开发框架 spring 帮程序员来管理对象
     * 2.spring源码是非常优秀的 涉及模式的应用 并发安全的实现 面向接口的涉及等
     * 3.在创建spring容器 也就是启动spring时
     *  a.首先会进行扫描 扫描得到所有的beanDefinition 对象 并存在一个map中
     *  b.然后筛选出非懒加载的单例 beanDefinition进行创建bean 对于多例不需要在启动过程进行创建 对于多例bean会在每次获取bean时
     *    利用beanDefinition去创建
     *  c.利用beanDefinition 创建bean的创建生命周期 这期间包括了合并beanDefinition 挂断构造方法 实例化 属性填充 初始化前
     *    初始化 初始化后等步骤  其中aop就是发生在初始化后这步骤中
     * 4.单例bean创建完之后 spring会发布一个容器启动事件
     * 5.spring 启动结束
     * 6.源码中会更复杂 比如提供一些模板方法 让子类去实现 比如beanFactoryPostProcessor 和beanPostProccessor 的注册
     *   spring的扫描是通过beanFactoryPostProcessor来实现的 依赖注入就是beanPostProcessor 来实现的
     *  7.在spring 启动过程中还会去处理@import等注解
     */


}
