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

}
