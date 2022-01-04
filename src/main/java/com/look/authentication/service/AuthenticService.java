package com.look.authentication.service;

import com.look.entity.UserAccount;
import com.look.entity.UserInfo;

public interface AuthenticService {

    public int addAccount(UserAccount userAccount, String role , UserInfo userInfo);

}
