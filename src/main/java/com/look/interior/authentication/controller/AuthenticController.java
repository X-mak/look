package com.look.interior.authentication.controller;

import com.look.entity.UserInfo;
import com.look.interior.authentication.service.AuthenticService;
import com.look.combinedentity.user.LoginUser;
import com.look.combinedentity.user.RegistyUser;
import com.look.common.Result;
import com.look.entity.UserAccount;
import com.look.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class AuthenticController {

    @Autowired
    AuthenticService authenticService;

    @GetMapping("/confirm")
    public Result<?> getUser(){
        LoginUser loginUser = TokenUtils.getLoginUser();
        return Result.success(loginUser,"查询成功!");
    }

    @PostMapping
    public Result<?> addUser(@RequestBody RegistyUser registyUser){
        int res = authenticService.addAccount(registyUser.getUserAccount(),
                registyUser.getRoleName(),
                registyUser.getUserInfo());
        if(res == -1)return Result.error("400","用户已注册!");
        return Result.success("注册成功!");
    }

    @GetMapping("/check")
    public Result<?> checkLogin(@RequestParam String userName,@RequestParam  String pwd){
        LoginUser loginUser = authenticService.checkLogin(new UserAccount(userName,pwd));
        if(loginUser == null)
            return Result.error("400","用户名或密码错误!");
        loginUser.setToken(TokenUtils.genToken(loginUser));
        return Result.success(loginUser,"登陆成功!");
    }

    @PutMapping("/pwd")
    public Result<?> changePwd(@RequestBody UserAccount userAccount,@RequestParam String newPwd){
        int res = authenticService.changePwd(userAccount, newPwd);
        if(res == -1)return Result.error("400","密码错误!");
        return Result.success("修改成功!");
    }

    @PutMapping("/info")
    public Result<?> changeInfo(@RequestBody UserInfo userInfo){
        int res = authenticService.changeInfo(userInfo);
        if(res == -1)return Result.error("400","修改失败!");
        return Result.success("修改成功!");
    }

    @PostMapping("/role/{role}")
    public Result<?> addRole(@RequestParam String userAccount,@PathVariable int role){
        int res = authenticService.addRole(userAccount, role);
        if(res == -1)return Result.error("400","添加失败!");
        return Result.success("添加成功!");
    }

    @GetMapping
    public Result<?> getAccount(@RequestParam String userName){
        LoginUser loginUser = authenticService.getUser(userName);
        if(loginUser.getUserRole().size() == 0){
            return Result.error("400","没有该账号");
        }
        return Result.success(loginUser,"查询成功!");
    }
}
