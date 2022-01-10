package com.look.interior.authentication.service;

import com.look.combinedentity.user.LoginUser;
import com.look.entity.UserAccount;
import com.look.entity.UserInfo;

public interface AuthenticService {

    public int addAccount(UserAccount userAccount, Integer role , UserInfo userInfo);

    public LoginUser checkLogin(UserAccount userAccount);

    public int changePwd(UserAccount userAccount,String new_pwd);

    public int changeInfo(LoginUser loginUser);

    public int addRole(String userAccount,Integer role);

    public LoginUser getUser(String userAccount);

}
