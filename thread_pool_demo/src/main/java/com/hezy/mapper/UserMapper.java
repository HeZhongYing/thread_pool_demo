package com.hezy.mapper;

import com.hezy.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    @Insert("insert into i_users (username, password) values (#{user.username}, #{user.password})")
    void insert(@Param("user") User user);
}
