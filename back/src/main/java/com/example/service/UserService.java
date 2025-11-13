package com.example.service;

import cn.hutool.core.util.StrUtil;
import com.example.entity.Account;
import com.example.entity.Admin;
import com.example.entity.User;
import com.example.exception.CustomerException;
import com.example.mapper.UserMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Resource
    UserMapper userMapper;

    public String user(String name) {
        if(name.equals("user")) {
            return "user";
        } else {
            throw new CustomerException("账号错误");
        }

    }

    public List<User> selectAll() {
        return userMapper.selectAll(null);
    }


    public PageInfo<User> selectPage(Integer pageNum, Integer pageSize,User user) {
        //开启分页查询
        PageHelper.startPage(pageNum,pageSize);
        List<User> list = userMapper.selectAll(user);
        return PageInfo.of(list);
    }

    public void add(User user){
        User dbUser = userMapper.selectByUsername(user.getUsername());
        if(dbUser != null){
            throw new CustomerException("账号重复");
        }
        if(StrUtil.isBlank(user.getName())){
            user.setName(user.getUsername());
        }
        if(StrUtil.isBlank(user.getPassword())){
            user.setPassword("123");
        }
        user.setRole("USER");
        userMapper.insert(user);
    }

    public void update(User user) {
        userMapper.updateById(user);
    }

    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    }

    public void deleteBatch(List<User> list) {
        for (User user : list) {
            this.deleteById(user.getId());
        }
    }

    public User login(Account account) {
        //验证用户是否存在
        User dbUser = userMapper.selectByUsername(account.getUsername());
        if(dbUser == null){
            throw new CustomerException("账号不存在");
        }
        //验证密码是否正确
        if(!dbUser.getPassword().equals(account.getPassword())){
            throw new CustomerException("账号或密码错误");
        }
        //创建token并返回给前端
        String token = TokenUtils.createToken(dbUser.getId()+"-"+"USER",dbUser.getPassword());
        dbUser.setToken(token);
        return dbUser;
    }

    public void register(User user) {
        this.add(user);
    }

    public User selectById(String userId) {
        return userMapper.selectById(userId);
    }

    public void updatePassword(Account account) {
        User user = userMapper.selectById(account.getId().toString());
        if (!account.getNewPassword().equals(account.getNewPassword2())) {
             throw new CustomerException("500","两次密码不匹配");
        } else {
            user.setPassword(account.getNewPassword());
            userMapper.updatePassword(account);
        }
    }
}
