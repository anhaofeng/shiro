package com.shiro.controller;

import com.shiro.model.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PerssionController {

     ResultMap resultMap;
    @Autowired
    public PerssionController(ResultMap resultMap) {
        this.resultMap = resultMap;
    }

    @GetMapping(value = "/guest/enter")
    public ResultMap login() {
        return resultMap.success().message("欢迎进入，您的身份是游客");
    }

    @GetMapping(value = "/guest/getMessage")
    public ResultMap submitLogin() {
        return resultMap.success().message("您拥有获得该接口的信息的权限！");
    }

    @GetMapping(value = "/user/getMessage")
    public ResultMap getUserMessage() {
        return resultMap.success().message("您拥有用户权限，可以获得该接口的信息！");
    }
    @GetMapping(value = "/admin/getMessage")
    public ResultMap getAdminMessage() {
        return resultMap.success().message("您拥有管理员权限，可以获得该接口的信息！");
    }



}
