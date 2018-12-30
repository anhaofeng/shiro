package com.shiro.controller;

import com.shiro.model.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.security.auth.login.AccountException;

public class ExceptionController {
    private final ResultMap resultMap;
    @Autowired
    public ExceptionController(ResultMap resultMap) {
        this.resultMap = resultMap;
    }
    @ExceptionHandler(AccountException.class)
    public ResultMap handleShiroException(Exception ex) {
        return resultMap.fail().message(ex.getMessage());
    }
}
