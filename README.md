# 参考链接

[朱晔和你聊Spring系列S1E5：Spring WebFlux小探](https://juejin.im/post/5bb6f41ce51d450e6b0e0327)

[Netty 源码分析之 三 我就是大名鼎鼎的 EventLoop(一)](https://segmentfault.com/a/1190000007403873)

# 访问

`application.yml`中没有配置端口，使用默认的8080

`http://localhost:8080/test`

`http://localhost:8080/users`



# Spring WebFlux

Spring Boot 2 基于Spring 5。Spring 5 引入了新的响应式框架WebFlux，功能层级与spring-mvc对等。

> Spring reative Web框架，是5.0中的新功能，是一个完全的reactive并且非阻塞的web框架。它适合处理那种event-loop 风格的事情，也就是事件驱动的。它支持Servlet容器（Tomcat，Jetty，Servlet 3.1+），也支持非Servlet的运行时（比如：Netty，Undertow），因为它的基础不是Servlet API，而是构建在Reactive Streams和Reactor项目之上的。

## WebClient



## 与Spring MVC的对比



## 支持的数据库



## 两种方式

controller注解、Router





## 关于性能

[Spring性能说明章节](https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html#webflux-performance)

> Performance has many characteristics and meanings. Reactive and non-blocking generally do not make applications run faster. They can, in some cases, (for example, if using the `WebClient` to execute remote calls in parallel). On the whole, it requires more work to do things the non-blocking way and that can increase slightly the required processing time.

> The key expected benefit of reactive and non-blocking is the ability to scale with a small, fixed number of threads and less memory. That makes applications more resilient under load, because they scale in a more predictable way. In order to observe those benefits, however, you need to have some latency (including a mix of slow and unpredictable network I/O). That is where the reactive stack begins to show its strengths, and the differences can be dramatic.

# 响应式编程

搜索响应式编程，可以看到有篇经常被转载的文章，文章是前端的响应式。

英文原文: [The introduction to Reactive Programming you've been missing](https://gist.github.com/staltz/868e7e9bc2a7b8c1f754)

中文译文：[响应式编程，是明智的选择](https://juejin.im/entry/5a4313ef5188255de57e0a18)  或者搜索`响应式编程`

## 介绍



关于响应式打个比方，比如在Java中，有如下赋值

```
Integer a = 1;
Integer b = 2;
Integer c = a + b;

b = 5;
System.out.println("c is: " + c);
```

输出c is: 3

在Excel表格中，如果设置C1单元格的值为=A1+B1，那么修改A1的值时，C1的值也会变化。

## 线程模型的对比

**阻塞式的：**

请求准备好了-》从线程池中分配一个线程-》Controller-》Service-》DB

1. 假如在service中，我们需要去高德将坐标转换为地址，我们要发送个http请求
2. 或者在DB层，我们在等数据库返回查询结果，如果是复杂的sql，可能需要执行个一秒

这时候线程是一直在等待的外部返回结果的。

高并发时，我们通过增大线程数来服务更多请求。



**非阻塞式的：**

请求准备好了-》EventLoop排队-》在worker线程执行，如果遇到请求DB或其他http，则发送一个异步响应式请求。不阻塞此worker-》EventLoop去将此worker服务其他请求-》worker不停服务，不停执行。有响应后处理请求。

但这样就有个问题，如果worker线程中不小心执行了阻塞式的代码，比如for个100亿次。那么这个worker就阻塞了，无法服务其他请求。遇到阻塞的代码时，需要到其他线程中去执行，不让阻塞代码在worker线程中执行。

worker线程与cpu的核心数（对等还是*2没有验证），那么理论上worker是一直



JVM每个线程都需占用内存，可以通过`java -XX:+PrintFlagsFinal -version | grep ThreadStackSize`查看

[JDK7 VM Options说明](https://www.oracle.com/technetwork/java/javase/tech/vmoptions-jsp-140102.html)

[JDK8 VM Options说明](https://docs.oracle.com/javase/8/docs/technotes/tools/windows/java.html)

根据JDK版本及平台会不同，考虑到我们一般都是部署到Linux 64位服务器上，JDK8以上，可以当作JVM一个线程会占用1M内存。

那么线程越多，占用内存就越多。









## 需注意的地方





https://juejin.im/post/5bb6f41ce51d450e6b0e0327)