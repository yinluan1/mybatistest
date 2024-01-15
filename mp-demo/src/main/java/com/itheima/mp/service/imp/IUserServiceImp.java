package com.itheima.mp.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.service.IUserService;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class IUserServiceImp extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public void deduceMoneyById(Long id, Integer money) {
        //1,查询用户
        User user = getById(id);
        //2.校验用户状态
        if (user==null||user.getStatus() ==2){
            throw new RuntimeException("用户状态异常");
        }
        //3.校验余额是否充足
        if (user.getBalance()<money){
            throw new RuntimeException("用户余额不足");
        }
        //4.减扣余额
        int reBalance = user.getBalance()-money;
        lambdaUpdate()
                .set(User::getBalance,reBalance)
                .set(reBalance == 0,User::getStatus,2)
                .eq(User::getId,id)
                .eq(User::getBalance,user.getBalance())
                .update();
    }

    @Override
    public List<User> queryUsers(String name, Integer minBalance, Integer maxBalance, Integer status) {

        return  lambdaQuery()
                .like(name != null, User::getUsername, name)
                .gt(minBalance != null, User::getBalance, minBalance)
                .le(maxBalance != null, User::getBalance, maxBalance)
                .eq(status != null, User::getStatus, status)
                .list();
    }
}
