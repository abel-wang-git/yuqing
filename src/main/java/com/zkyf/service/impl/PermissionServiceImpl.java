package com.zkyf.service.impl;

import com.zkyf.core.service.impl.BaseServiceImpl;
import com.zkyf.dao.IPermissionDao;
import com.zkyf.entity.Permission;
import com.zkyf.service.IPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wang on 17-2-14.
 *
 */
@Service("permissionService")
public class PermissionServiceImpl extends BaseServiceImpl<Permission, String> implements IPermissionService {
    @Resource
    private IPermissionDao permissionDao;

    public List<Permission> findByName(String name) {
        List<Permission> list = permissionDao.findByName(name);
        if (list.size()>0) {
            return list;
        }
        return null;
    }
}
