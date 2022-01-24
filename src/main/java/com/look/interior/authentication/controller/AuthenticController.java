package com.look.interior.authentication.controller;

import com.github.pagehelper.PageInfo;
import com.look.entity.Subscribe;
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

    @PostMapping("/subscribe")
    public Result<?> subscribeSomeone(@RequestBody Subscribe subscribe){
        int res = authenticService.subscribeSomeone(subscribe);
        if(res == -1)return Result.error("400","关注失败!");
        return Result.success("关注成功!");
    }

    @DeleteMapping("/subscribe")
    public Result<?> cancelSubscribe(@RequestParam Integer id,@RequestParam String mainAccount){
        int res = authenticService.cancelSubscribe(id, mainAccount);
        if(res == -1)return Result.error("400","取关失败!");
        return Result.success("取关成功!");
    }

    @GetMapping("/subscribe/{pageNum}")
    public Result<?> getSubscribeList(@RequestParam String userAccount,@RequestParam Integer pageSize,@PathVariable Integer pageNum){
        PageInfo<UserInfo> subscribeList = authenticService.getSubscribeList(userAccount, pageNum, pageSize);
        long total = subscribeList.getTotal();
        return Result.success(subscribeList.getList(),total+"");
    }

    @GetMapping("/subscribe/valid")
    public Result<?> checkValidity(@RequestParam String mainAccount,@RequestParam String followAccount){
        int res = authenticService.checkSubscribed(mainAccount, followAccount);
        if(res == -1)return Result.error("400","未关注!");
        return Result.success("已关注!");
    }
}
