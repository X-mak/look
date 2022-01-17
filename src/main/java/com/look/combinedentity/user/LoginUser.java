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
    private UserInfo userInfo;
    private List<String> userRole;
    private String token;

    public LoginUser(UserInfo userInfo, List<String> userRole) {
        this.userInfo = userInfo;
        this.userRole = userRole;
    }
}
