package com.hezy.controller;

import com.hezy.pojo.User;
import com.hezy.service.AsyncService;
import com.hezy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@RestController
@RequestMapping("user")
public class UserController {

    /**
     * 总记录数
     */
    private final static int SIZE = 40 * 10000;

    @Autowired
    private UserService userService;

    @Autowired
    private AsyncService asyncService;

    @GetMapping("insert1")
    public void insert1() {
        ArrayList<User> list = new ArrayList<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            User user = new User();
            user.setUsername("user" + i);
            user.setPassword("password" + i);
            list.add(user);
        }

        long startTime = System.currentTimeMillis();
        // 批量插入
        for (User user : list) {
            userService.insert(user);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("不用线程池===插入40万条记录耗时:" + ((endTime - startTime) / 1000) + "s");
    }

    @GetMapping("insert2")
    public void insert2() {
        ArrayList<User> list = new ArrayList<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            User user = new User();
            user.setUsername("user" + i);
            user.setPassword("password" + i);
            list.add(user);
        }
        // 将数据分成4000批，每批插入100条
        List<List<User>> batchList = new ArrayList<>();
        for (int i = 0; i < list.size(); i += 100) {
            batchList.add(list.subList(i, i + 100));
        }

        long startTime = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(batchList.size());
        // 线程池分批插入
        for (List<User> batch : batchList) {
            asyncService.executeAsync(batch, userService, countDownLatch);
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("使用线程池===插入40万条记录耗时:" + ((endTime - startTime) / 1000) + "s");
    }
}
