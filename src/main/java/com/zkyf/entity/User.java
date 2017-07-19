package com.zkyf.entity;

import com.zkyf.entity.base.BaseEntity;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by Administrator on 2017/4/21.
 */
@Entity
@Table(name = "t_user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;
    
    @NotBlank(message = "用户名不能为空")
    private String userName;
    
    @Length(min = 6, message = "密码长度不能小于6位")
    private String passWord;
    
    private int state;
    
    private String realName;
    
    @NotBlank(message = "邮箱不能为空")
    private String eMail;
    
    @Length(max = 11, min = 11, message = "请输入真确的格式")
    private String phone;
    
    @ManyToMany(fetch = FetchType.EAGER)//立即从数据库中进行加载数据;
    @JoinTable(name = "UserRole", joinColumns = {@JoinColumn(name = "userId")}, inverseJoinColumns = {@JoinColumn(name = "roleId")})
    private Set<Role> roleList;// 一个用户具有多个角色
    public String getId(){
    
        return id;
    } public void setId(String id){
    
        this.id = id;
    } public String getUserName(){
    
        return userName;
    } public void setUserName(String userName){
    
        this.userName = userName;
    } public String getPassWord(){
    
        return passWord;
    } public void setPassWord(String passWord){
    
        this.passWord = passWord;
    } public int getState(){
    
        return state;
    } public void setState(int state){
    
        this.state = state;
    } public String getRealName(){
    
        return realName;
    } public void setRealName(String realName){
    
        this.realName = realName;
    } public String geteMail(){
    
        return eMail;
    } public void seteMail(String eMail){
    
        this.eMail = eMail;
    } public String getPhone(){
    
        return phone;
    } public void setPhone(String phone){
    
        this.phone = phone;
    }
    
    public Set<Role> getRoleList(){
        
        return roleList;
    }
    
    public void setRoleList(Set<Role> roleList){
        
        this.roleList = roleList;
    }
    
    /**
     * 密码加盐
     *
     * @return 加盐后的密码
     */
    public String getSalting(){
        
        return new Md5Hash(this.getPassWord() + getUserName()).toString();
    }
}
