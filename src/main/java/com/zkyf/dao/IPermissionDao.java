package com.zkyf.dao;


import com.zkyf.core.dao.BaseRepository;
import com.zkyf.entity.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by wang on 17-2-14.
 * 权限
 */
@Repository("permissionDao")
public interface IPermissionDao extends BaseRepository<Permission, String> {
    List<Permission> findByName(String name);
}
