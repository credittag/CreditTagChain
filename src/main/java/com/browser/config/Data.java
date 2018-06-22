package com.browser.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/12/8 0008.
 */
@Component
public class Data implements InitializingBean {

    private Set<String> data;

    @Override
    public void afterPropertiesSet(){
        data = new HashSet<>();
    }

    public void addData(String num){
        data.add(num);
    }

    public Set<String> getData() {
        return data;
    }

    public void setData(Set<String> data) {
        this.data = data;
    }
}
