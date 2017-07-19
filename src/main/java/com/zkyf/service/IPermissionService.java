package com.zkyf.service;


import com.zkyf.core.service.IBaseService;
import com.zkyf.entity.Permission;

import java.util.List;

/**
 * Created by wanghuiwen on 17-2-13.
 */
public interface IPermissionService extends IBaseService<Permission, String> {
    List<Permission> findByName(String name);
}
