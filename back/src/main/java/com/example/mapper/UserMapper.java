package com.example.mapper;

import com.example.entity.Account;
import com.example.entity.User;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

public interface UserMapper {

    List<User> selectAll(User user);

    void insert(User user);

    User selectByUsername(String username);

    void updateById(User user);

    @Delete("delete from `user` where id =#{id}")
    void deleteById(Integer id);

    User selectById(String userId);

    void updatePassword(Account account);
}
