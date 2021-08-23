package com.auto.interview;

/**
 * 你们系统如果排查jvm问题
 *
 * 调优不是一下子的 而是分析 推理 实践 总结 在分析 最终得到具体的问题
 */
public class JvmProblem {

    /**
     * 1.对于还在正常运行的系统
     *  1.1 可以使用jmap查看jvm各个区域的使用情况
     *  1.2 可以通过jstack 来查看各个线程运行情况 比如哪些线程堵塞 是否出现死锁
     *  1.3 可以通过jstack 来查看垃圾回收情况 特别是fullGc 如果fullGc频繁 那么就得调优
     *  1.4 通过各个命令看结果 或者jvisualvm等工具来分析
     *  1.5 首先 初步猜测频繁发送fullgc的原因 如果频繁发生fullGc 但是又一直没有出现内存 那么表明fullGc 实际上回收了很多对象
     *      所以这些对象最好能在youngGC过程中就直接被回收掉 避免进入老年代 对于这种情况 就需要考虑这些存活时间 不长的对象是不是比较大
     *      导致新生代放不下 直接进入老年代 尝试加大年轻代的大小 如果改完之后 fullgc 减少 则证明修改有效
     *  1.6 同时,还可以找到占用cpu 最多的线程 定位到具体的方法 优化这个方法的执行 看是否能避免某些对象的创建 进而节省内存
     * 2.对已经发生oom的系统
     *  2.1 一般生产系统都会设置当系统发生oom时 生成当时的dump文件 (-XX:headDumpOnOutOfMemory -XX:headDumpPath:/usr/local/base)
     *  2.2 利用jsisualvm 等工具来分析dump文件
     *  2.3  根据dump文件来找到异常的实例对象 和异常的线程 定位到具体代码
     *  2.4  然后在进行具体的分析和调试
     */

}
