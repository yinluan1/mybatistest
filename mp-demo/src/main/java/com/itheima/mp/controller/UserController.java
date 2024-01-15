package com.itheima.mp.controller;


import cn.hutool.core.bean.BeanUtil;
import com.itheima.mp.domain.dto.UserFormDTO;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.query.UserQuery;
import com.itheima.mp.domain.vo.UserVO;
import com.itheima.mp.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "用户管理接口")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @ApiOperation("新增用户接口")
    @PostMapping
    public void saveUser(@RequestBody UserFormDTO userFormDTO){
        User user = BeanUtil.copyProperties(userFormDTO, User.class);
        iUserService.save(user);
    }

    @ApiOperation("删除用户接口")
    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable("id") Long id){
        iUserService.removeById(id);
    }

    @ApiOperation("查询用户接口")
    @GetMapping ("{id}")
    public UserVO selectUser(@PathVariable("id") Long id){
        User user = iUserService.getById(id);
        return BeanUtil.copyProperties(user,UserVO.class);
    }

    @ApiOperation("批量查询用户接口")
    @GetMapping
    public List<UserVO> selectUsers(@RequestParam("ids") List<Long> ids){

        List<User> users = iUserService.listByIds(ids);
        return BeanUtil.copyToList(users, UserVO.class);
    }

    @ApiOperation("扣减用户余额接口")
    @PutMapping("{id}/deduction/{money}")
    public void deduceMoneyById(@PathVariable("id") Long id,@PathVariable("money") Integer money){
        iUserService.deduceMoneyById(id,money);
    }


    @ApiOperation("根据复杂条件查询用户接口")
    @GetMapping("/list")
    public List<UserVO> queryUsers( UserQuery userQuery){

        List<User> users = iUserService.queryUsers(userQuery.getName(),userQuery.getMinBalance(),userQuery.getMaxBalance(),userQuery.getStatus());
        return BeanUtil.copyToList(users, UserVO.class);
    }
}
