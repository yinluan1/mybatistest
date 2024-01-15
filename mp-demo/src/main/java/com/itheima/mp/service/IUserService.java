package com.itheima.mp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.mp.domain.po.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IUserService extends IService<User> {
    void deduceMoneyById(Long id, Integer money);

    List<User> queryUsers(String name, Integer minBalance, Integer maxBalance, Integer status);
}
