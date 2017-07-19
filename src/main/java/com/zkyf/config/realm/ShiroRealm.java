package com.zkyf.config.realm;

import com.zkyf.entity.Permission;
import com.zkyf.entity.Role;
import com.zkyf.entity.User;
import com.zkyf.service.IUserService;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

/**
 * Created by wanghuiwen on 17-2-13.
 * shiro 认证和授权
 */
public class ShiroRealm extends AuthorizingRealm {
    @Resource
    private IUserService userService;
    
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User userInfo = (User) principals.getPrimaryPrincipal();
        User user = userService.findOne(userInfo.getId());
        for (Role role : user.getRoleList()) {
            authorizationInfo.addRole(role.getName());
            for (Permission p : role.getPermissions()) {
                authorizationInfo.addStringPermission(p.getPermission());
            }
        }
        return authorizationInfo;
    }

    /**
     * 认证信息.(身份验证)
     * :
     * Authentication 是用来验证用户身份
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        //获取用户的输入的账号.
        String username = token.getPrincipal().toString();
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        User userInfo = userService.getByuserName(username);
        if (userInfo == null) {
            return null;
        }
        //userInfo.setPermissions(userService.findPermissions(user));
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo,
                userInfo.getPassWord(),
                ByteSource.Util.bytes(userInfo.getPassWord()),//密码
                getName()  //realm name
        );
        return authenticationInfo;
    }

    @Override
    public String getName() {
        return "ShiroRealm";
    }
}
