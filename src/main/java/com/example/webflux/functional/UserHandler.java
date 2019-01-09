package com.example.webflux.functional;

import com.example.webflux.UserBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
public class UserHandler {

    private Logger logger = LoggerFactory.getLogger(UserHandler.class);

    private List<UserBean> userList;

    private UserBean user1;

    @Autowired
    private UserRepository userRepository;

    /**
     * 初始化数据
     */
    @PostConstruct
    public void init() {
        userList = new ArrayList<>();

        user1 = new UserBean();
        user1.setId("1");
        user1.setUsername("admin");
        user1.setNickname("管理员");

        UserBean user2 = new UserBean();
        user2.setId("2");
        user2.setUsername("staff");
        user2.setNickname("职员");

        userList.add(user1);
        userList.add(user2);
    }

    public Mono<ServerResponse> saveUser(ServerRequest request) {
        return request.bodyToMono(UserBean.class)
                .map(u -> userRepository.save(u))
                .flatMap(c -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(c, UserBean.class));
    }

    /**
     * 获取用户
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> getUser(ServerRequest request) {
        logger.info("path:" + request.path() + " thread info:" + Thread.currentThread().getName());

        Flux<UserBean> uf = Flux.fromIterable(userList)
                .publishOn(Schedulers.elastic())
                .map(k -> userSleep(k));

        Mono<UserBean> um = Mono.just(user1);
        logger.info("mono已执行");

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(uf, UserBean.class);
    }

    private UserBean userSleep(UserBean u) {
        logger.info("sleep信息： " + Thread.currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("sleep返回");
        return user1;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
//        IntStream.rangeClosed(1, 1000000).parallel().forEach(u -> printThread(u + ""));
        List<Integer> list = new ArrayList<>();
        for (int i=1; i< 1000000; i++) {
            list.add(i);
        }

//        list.stream().forEach(i -> printThread(i));
        list.parallelStream().forEach(i -> printThread(i));

        long end = System.currentTimeMillis();

        System.out.println(end - start);


//        /**获取单词，并且去重**/
//        List<String> list = Arrays.asList("hello welcome", "world hello", "hello world",
//                "hello world welcome");
//
//        //map和flatmap的区别
//        list.stream().map(item -> Arrays.stream(item.split(" "))).distinct().collect(Collectors.toList()).forEach(System.out::println);
//        System.out.println("---------- ");
//        list.stream().flatMap(item -> Arrays.stream(item.split(" "))).distinct().collect(Collectors.toList()).forEach(System.out::println);
//
//        //实际上返回的类似是不同的
//        List<Stream<String>> listResult = list.stream().map(item -> Arrays.stream(item.split(" "))).distinct().collect(Collectors.toList());
//        List<String> listResult2 = list.stream().flatMap(item -> Arrays.stream(item.split(" "))).distinct().collect(Collectors.toList());
//
//        System.out.println("---------- ");
//
//        //也可以这样
//        list.stream().map(item -> item.split(" ")).flatMap(Arrays::stream).distinct().collect(Collectors.toList()).forEach(System.out::println);
//
//        System.out.println("================================================");
//
//        /**相互组合**/
//        List<String> list2 = Arrays.asList("hello", "hi", "你好");
//        List<String> list3 = Arrays.asList("zhangsan", "lisi", "wangwu", "zhaoliu");
//
//        list2.stream().map(item -> list3.stream().map(item2 -> item + " " + item2)).collect(Collectors.toList()).forEach(System.out::println);
//        list2.stream().flatMap(item -> list3.stream().map(item2 -> item + " " + item2)).collect(Collectors.toList()).forEach(System.out::println);
//
//        //实际上返回的类似是不同的
//        List<Stream<String>> list2Result = list2.stream().map(item -> list3.stream().map(item2 -> item + " " + item2)).collect(Collectors.toList());
//        List<String> list2Result2 = list2.stream().flatMap(item -> list3.stream().map(item2 -> item + " " + item2)).collect(Collectors.toList());
    }

    private static void printThread(Integer u) {
        UUID uuid = UUID.randomUUID();
//        System.out.println("String: " + u + " Thread: " + Thread.currentThread().getName());
    }

}
