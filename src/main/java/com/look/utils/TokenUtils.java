package com.look.utils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.look.combinedentity.user.LoginUser;
import com.look.entity.AccountRole;
import com.look.entity.UserInfo;
import com.look.mapper.AccountRoleMapper;
import com.look.mapper.UserInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class TokenUtils {

    @Autowired
    private AccountRoleMapper accountRoleMapper;

    private static AccountRoleMapper staticUserMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    private static UserInfoMapper staticInfoMapper;

    @PostConstruct
    public void init() {
        staticUserMapper = accountRoleMapper;
        staticInfoMapper = userInfoMapper;
    }


    public static String genToken(LoginUser loginUser) {
        return JWT.create().withExpiresAt(DateUtil.offsetDay(new Date(), 1)).withAudience(loginUser.getUserInfo().getUserAccount())
                .sign(Algorithm.HMAC256(loginUser.getUserInfo().getUserName()));
    }

    /**
     * 获取token中的用户信息
     * @return
     */
    public static LoginUser getLoginUser(){
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            String aud = JWT.decode(token).getAudience().get(0);
            UserInfo userInfo = staticInfoMapper.selectByPrimaryKey(aud);
            List<AccountRole> accountRoles = staticUserMapper.queryRoles(aud);
            List<String> ans = new ArrayList<>();
            for(AccountRole ar : accountRoles){
                ans.add(ar.getUserRole().getRoleName());
            }
            return new LoginUser(userInfo,ans);
        } catch (Exception e) {
            log.error("解析token失败", e);
            return null;
        }
    }





//    public static User getUser() {
//        try {
//            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//            String token = request.getHeader("token");
//            String aud = JWT.decode(token).getAudience().get(0);
//            Integer userId = Integer.valueOf(aud);
//            return staticUserMapper.selectById(userId);
//        } catch (Exception e) {
//            log.error("解析token失败", e);
//            return null;
//        }
//    }
}
