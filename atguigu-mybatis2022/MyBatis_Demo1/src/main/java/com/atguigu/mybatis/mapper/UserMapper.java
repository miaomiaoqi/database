package com.atguigu.mybatis.mapper;

import com.atguigu.mybatis.pojo.User;

import java.util.List;

public interface UserMapper {

    /**
     * 添加用户信息
     */
    int insertUser();

    /**
     * 修改用户信息
     *
     * @author miaoqi
     * @date 2023-12-25 21:26:9
     *
     * @return
     */
    void updateUser();

    /**
     * 删除用户信息
     *
     * @author miaoqi
     * @date 2023-12-25 22:12:28
     *
     * @return
     */
    void deleteUser();

    User getUserById();

    List<User> getAllUser();

}
