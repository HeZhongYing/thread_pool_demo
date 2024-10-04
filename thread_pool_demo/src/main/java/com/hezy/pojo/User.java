package com.hezy.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 10765
 * @create 2024/10/4 18:55
 */
@Data
public class User implements Serializable {

    private Integer id;

    private String username;

    private String password;
}
