package com.example.webflux.functional;

import com.example.webflux.UserBean;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends ReactiveCrudRepository<UserBean, Integer> {
}
