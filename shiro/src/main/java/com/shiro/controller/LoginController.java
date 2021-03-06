package com.shiro.controller;

import com.shiro.model.ResultMap;
import com.shiro.repository.UserMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class LoginController {
    private ResultMap resultMap;
    private UserMapper userMapper;

    @Autowired
    public LoginController(ResultMap resultMap, UserMapper userMapper) {
        this.resultMap = resultMap;
        this.userMapper = userMapper;
    }

    @GetMapping(value = "/notlogin")
    public ResultMap notLogin() {
        return resultMap.success().message("您尚未登陆！");
    }

    @GetMapping(value = "/notRole")
    public ResultMap notRole() {
        return resultMap.success().message("您没有权限！");
    }

    @GetMapping(value = "/logout")
    public ResultMap logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return resultMap.success().message("成功注销！");
    }

    @PostMapping(value = "/login")
    public ResultMap login(String username, String password) {
        try {
            // 在认证提交前准备 token（令牌）
            UsernamePasswordToken passwordToken = new UsernamePasswordToken(username, password);
            // 从SecurityUtils里边创建一个 subject
            Subject subject = SecurityUtils.getSubject();
            // 执行认证登陆
            subject.login(passwordToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //根据权限，指定返回数据
        Set<String> roles = userMapper.getRole(username);
        for (String role : roles
                ) {
            if ("user".equals(role)) {
                return resultMap.success().message("欢迎用户登陆");
            }
            if ("admin".equals(role)) {
                return resultMap.success().message("欢迎管理员登陆");
            }
        }

        return resultMap.fail().message("权限错误！");
    }
}
