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

