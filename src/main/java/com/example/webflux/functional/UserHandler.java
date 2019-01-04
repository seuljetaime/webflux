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
import java.util.List;
import java.util.concurrent.TimeUnit;

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

}
