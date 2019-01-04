package com.example.webflux.annotation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ApiController {

    @GetMapping("/test")
    public Mono<String> hello() {
        System.out.println("test线程信息： " + Thread.currentThread().getName() + " " + Thread.currentThread().getId());
        return Mono.just("Hello World");
    }

}
