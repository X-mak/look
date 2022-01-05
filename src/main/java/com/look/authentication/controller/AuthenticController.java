package com.look.authentication.controller;

import com.look.authentication.service.AuthenticService;
import com.look.combinedentity.user.LoginUser;
import com.look.combinedentity.user.RegistyUser;
import com.look.common.Result;
import com.look.entity.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class AuthenticController {

    @Autowired
    AuthenticService authenticService;

    @PostMapping
    public Result<?> addUser(@RequestBody RegistyUser registyUser){
        int res = authenticService.addAccount(registyUser.getUserAccount(),
                registyUser.getRoleName(),
                registyUser.getUserInfo());
        if(res == -1)return Result.error("400","用户已注册!");
        return Result.success("注册成功!");
    }

    @GetMapping
    public Result<?> checkLogin(@RequestBody UserAccount userAccount){
        LoginUser loginUser = authenticService.checkLogin(userAccount);
        if(loginUser == null)
            return Result.error("400","用户名或密码错误!");
        return Result.success(loginUser,"登陆成功!");
    }

    @PutMapping("/pwd")
    public Result<?> changePwd(@RequestBody UserAccount userAccount,@RequestParam String newPwd){
        int res = authenticService.changePwd(userAccount, newPwd);
        if(res == -1)return Result.error("400","密码错误!");
        return Result.success("修改成功!");
    }

    @PutMapping("/info")
    public Result<?> changeInfo(@RequestBody LoginUser loginUser){
        int res = authenticService.changeInfo(loginUser);
        if(res == -1)return Result.error("400","修改失败!");
        return Result.success("修改成功!");
    }


}
