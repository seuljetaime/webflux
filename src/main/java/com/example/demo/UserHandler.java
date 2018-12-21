package com.example.demo;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserHandler {

    private List<UserBean> userList;

    /**
     * 初始化数据
     */
    @PostConstruct
    public void init() {
        userList = new ArrayList<>();

        UserBean user1 = new UserBean();
        user1.setId(1);
        user1.setUsername("admin");
        user1.setNickname("管理员");

        UserBean user2 = new UserBean();
        user2.setId(2);
        user2.setUsername("staff");
        user2.setNickname("职员");

        userList.add(user1);
        userList.add(user2);
    }

    /**
     * 获取用户列表
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> getUserList(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(userList), List.class);
    }

}
