package com.auto.interview;

/**
 * dubbo是如何做系统交互的
 */
public class DubboInteview {

    /**
     * dubbo是基于 RPC来完成服务之间的调用
     * dubbo支持很多协议 比如http协议 rest 他们底层所使用的技术是不太一样的 比如dubbo协议底层使用的是netty 也可以使用mina http协议 或者jetty
     *服务消费者在调用某个服务时 会将当前所调用的服务接口信息 当前方法信息 所传入的入参信息组装为 Invocation 对象 然后根据不同数据组织方式和
     * 传输方式将这个对象传送给服务提供者 服务提供者收到这个对象之后 利用反射执行对应的方法 得到结果之后 再通过网络响应给服务消费者
     *
     */

}
