package com.activiti.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息
 * Created by wangyong on 2017/7/16.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    //用户编号，用户名
    private String userId, userName;

}
