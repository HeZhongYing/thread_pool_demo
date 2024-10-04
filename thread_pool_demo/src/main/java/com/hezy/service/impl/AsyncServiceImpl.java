package com.hezy.service.impl;

import com.hezy.pojo.User;
import com.hezy.service.AsyncService;
import com.hezy.service.UserService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@Service
public class AsyncServiceImpl implements AsyncService {

    @Async("threadPoolTaskExecutor")
    @Override
    public void executeAsync(List<User> batch, UserService userService, CountDownLatch countDownLatch) {
        try {
            for (User user : batch) {
                userService.insert(user);
            }
        } finally {
            countDownLatch.countDown();
        }
    }
}
