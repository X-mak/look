package com.look.authentication.service;

import com.look.combinedentity.user.LoginUser;
import com.look.entity.UserAccount;
import com.look.entity.UserInfo;

public interface AuthenticService {

    public int addAccount(UserAccount userAccount, String role , UserInfo userInfo);

    public LoginUser checkLogin(UserAccount userAccount);

    public int changePwd(UserAccount userAccount,String new_pwd);

    public int changeInfo(LoginUser loginUser);

}
