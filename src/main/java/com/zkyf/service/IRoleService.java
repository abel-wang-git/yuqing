package com.zkyf.service;


import com.zkyf.core.service.IBaseService;
import com.zkyf.entity.Role;

/**
 * Created by wanghuiwen on 17-2-13.
 * 角色服务类
 */
public interface IRoleService extends IBaseService<Role, String> {
    Role getByname(String name);
}
