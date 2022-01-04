package com.look.combinedentity;

import com.look.entity.UserAccount;
import com.look.entity.UserInfo;
import lombok.Data;

@Data
public class RegistyUser {
    private UserAccount userAccount;
    private UserInfo userInfo;
    private String roleName;
}
