package com.zkyf.entity;


import com.alibaba.fastjson.annotation.JSONField;
import com.zkyf.entity.base.BaseEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by wanghuiwen on 17-1-5.
 *
 */
@Entity
@Table(name = "t_role")
public class Role extends BaseEntity {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;
    @NotEmpty(message = "角色名称不能为空")
    private String name;
    private Boolean available = Boolean.TRUE;
    // 用户 - 角色关系定义;
    @JSONField(serialize = false)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "UserRole", joinColumns = {@JoinColumn(name = "roleId")}, inverseJoinColumns = {@JoinColumn(name = "userId")})
    private List<User> users;// 一个角色对应多个用户
    //角色 -- 权限关系：多对多关系;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "RolePermission", joinColumns = {@JoinColumn(name = "roleId")}, inverseJoinColumns = {@JoinColumn(name = "permissionId")})
    private Set<Permission> permissions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Set<Permission> getPermissions () {

        return permissions;
    }

    public void setPermissions (Set<Permission> permissions) {

        this.permissions = permissions;
    }
}
