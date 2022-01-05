package com.look.combinedentity.user;

import com.look.entity.UserAccount;
import com.look.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistyUser {
    private UserAccount userAccount;
    private UserInfo userInfo;
    private String roleName;
}
