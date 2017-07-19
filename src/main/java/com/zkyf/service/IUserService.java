package com.zkyf.service;


import com.zkyf.core.service.IBaseService;
import com.zkyf.entity.User;

/**
 * Created by Administrator on 2017/4/21.
 */
public interface IUserService extends IBaseService<User,String> {
    User getByuserName(String username);
}
