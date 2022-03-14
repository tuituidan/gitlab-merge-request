package com.tuituidan.openhub.config.shiro;

import com.tuituidan.openhub.bean.entity.Developer;
import com.tuituidan.openhub.service.developer.DeveloperService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.SimpleAccountRealm;

/**
 * UserRealm.
 *
 * @author zhujunhan
 * @version 1.0
 * @date 2022/3/11 0011
 */
public class UserRealm extends SimpleAccountRealm {

    private DeveloperService developerServce;

    public UserRealm(DeveloperService servce) {
        developerServce = servce;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        Developer loginDeveloper = developerServce.getLoginDeveloper(username, new String(token.getPassword()));
        return new SimpleAuthenticationInfo(loginDeveloper, token.getCredentials(), username);
    }

}
