package com.shiro.model;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ResultMap extends HashMap {
    public ResultMap() {
    }

    public ResultMap success() {
        this.put("result", "success");
        return this;
    }

    public ResultMap fail() {
        this.put("result", "faile");
        return this;
    }

    public ResultMap message(Object message) {
        this.put("message", message);
        return this;
    }


}
