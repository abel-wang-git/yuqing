package com.zkyf.service.impl;


import com.zkyf.core.service.impl.BaseServiceImpl;
import com.zkyf.dao.UserDao;
import com.zkyf.entity.User;
import com.zkyf.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/4/21.
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User,String> implements IUserService {
    @Resource
    private UserDao userDao;
    public User getByuserName(String username){
        return userDao.findFristByUserName(username);
    }
}
