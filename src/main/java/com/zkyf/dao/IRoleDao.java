package com.zkyf.dao;



import com.zkyf.core.dao.BaseRepository;
import com.zkyf.entity.Role;
import org.springframework.stereotype.Repository;

/**
 * Created by wang on 17-2-14.
 * 角色
 */
@Repository("roleDao")
public interface IRoleDao extends BaseRepository<Role, String> {
    Role getFirstByName(String name);
}
