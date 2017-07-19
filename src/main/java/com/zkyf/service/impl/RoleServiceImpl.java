package com.zkyf.service.impl;

import com.zkyf.core.service.impl.BaseServiceImpl;
import com.zkyf.dao.IRoleDao;
import com.zkyf.entity.Role;
import com.zkyf.service.IRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wang on 17-2-14.
 */
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role, String> implements IRoleService {
    @Resource
    private IRoleDao roleDao;

    public Role getByname(String name) {
        Role roles = roleDao.getFirstByName(name);
        if (roles != null) {
            return roles;
        }
        return null;
    }
}
