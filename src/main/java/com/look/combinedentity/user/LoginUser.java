package com.look.combinedentity.user;

import com.look.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser {
    private String userAccount;
    private List<String> userRole;
    private String userName;
    private String userImg;

    public LoginUser(String userAccount, String userName, String userImg) {
        this.userAccount = userAccount;
        this.userName = userName;
        this.userImg = userImg;
    }

    public LoginUser(String userAccount, String userName) {
        this.userAccount = userAccount;
        this.userName = userName;
    }
}
