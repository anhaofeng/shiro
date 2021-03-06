package com.shiro.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
    public  ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
     ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
     shiroFilterFactoryBean.setSecurityManager(securityManager);
        // setLoginUrl 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射
     shiroFilterFactoryBean.setLoginUrl("/notlogin");
        // 设置无权限时跳转的 url;
     shiroFilterFactoryBean.setUnauthorizedUrl("/notRole");
        // 设置拦截器
        Map<String,String> fileChaineDefinitionMap= new LinkedHashMap<>();
        //游客，开发权限
        fileChaineDefinitionMap.put("/guest/**","anon");
        //用户，需要角色权限 “user”
        fileChaineDefinitionMap.put("/user/**","roles[user]");
        //管理员，需要角色权限 “admin”
        fileChaineDefinitionMap.put("/admin/**","roles[admin]");
        //开放登陆接口
        fileChaineDefinitionMap.put("/login","anon");
        //其余接口一律拦截
        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
        fileChaineDefinitionMap.put("/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(fileChaineDefinitionMap);
        System.out.println("shior拦截器工厂类注入成功");
        return shiroFilterFactoryBean;
    }
    /**
     * 注入 securityManager
     */
    @Bean
    public  SecurityManager securityManager(CustomRealm customRealm){
        DefaultWebSecurityManager securityManager =new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(customRealm);
        return securityManager;

    }


}
