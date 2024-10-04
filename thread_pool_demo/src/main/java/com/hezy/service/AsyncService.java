package com.hezy.service;

import com.hezy.pojo.User;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author 10765
 * @create 2024/10/4 19:22
 */
public interface AsyncService {
    void executeAsync(List<User> batch, UserService userService, CountDownLatch countDownLatch);
}
