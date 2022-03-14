package com.tuituidan.openhub.config.shiro;

import com.tuituidan.openhub.service.developer.DeveloperService;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ShiroConfig.
 *
 * @author zhujunhan
 * @version 1.0
 * @date 2022/3/11 0011
 */
@Configuration
public class ShiroConfig {

    @Value(value = "#{'${non-require-login}'.split(',')}")
    private String[] nonNeedLoginPath;

    private static final int SESSION_TIMEOUT = 1000 * 60 * 60 * 24;

    /**
     * 配置自定义Realm
     *
     * @param servce servce
     * @return UserRealm
     */
    @Bean
    public UserRealm userRealm(DeveloperService servce) {
        return new UserRealm(servce);
    }

    /**
     * 配置url过滤器
     *
     * @return ShiroFilterChainDefinition
     */
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition(SecurityManager securityManager) {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        for (String permitUrl : nonNeedLoginPath) {
            chainDefinition.addPathDefinition(permitUrl, "anon");
        }
        chainDefinition.addPathDefinition("/**", "authc");
        return chainDefinition;
    }

    @Bean
    public DefaultWebSessionManager getDefaultWebSessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(SESSION_TIMEOUT);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdCookieEnabled(true);
        return sessionManager;
    }

    /**
     * 配置security并设置userReaml
     *
     * @param realm realm
     * @return SessionsSecurityManager
     */
    @Bean
    public SessionsSecurityManager securityManager(UserRealm realm, SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

}
