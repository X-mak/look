package com.look.authentication.controller;

import com.look.authentication.service.AuthenticService;
import com.look.combinedentity.RegistyUser;
import com.look.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return Result.success();
    }

}
