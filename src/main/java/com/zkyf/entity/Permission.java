package com.zkyf.entity;

import com.zkyf.entity.base.BaseEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.List;

/**
 * Created by wanghuiwen on 17-1-7.
 * 系统模块类
 */
@Entity
@Table(name = "t_permission")
public class Permission extends BaseEntity {
    public static final String PRE_TYPE_MENU = "menu";
    public static final String PRE_TYPE_BUTTON = "button";
    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;//主键.
    @NotEmpty(message = "权限名称不能为空")
    private String name;//名称.

    @Column(columnDefinition = "enum('menu','button')")
    private String resourceType;//资源类型，[menu|button]
    /**
     * url 遍历菜单
     */
    private String url;
    @NotEmpty(message = "权限字符串不能为空")
    private String permission; //权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
    private Long parentId; //父编号
    private Boolean available = Boolean.TRUE;

    //角色 -- 权限关系：多对多关系;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.DETACH)
    @JoinTable(name = "RolePermission", inverseJoinColumns = {@JoinColumn(name = "roleId")}, joinColumns = {@JoinColumn(name = "permissionId")})
    private List<Role> roles;


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

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

}
