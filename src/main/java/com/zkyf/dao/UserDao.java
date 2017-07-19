package com.zkyf.dao;


import com.zkyf.core.dao.BaseRepository;
import com.zkyf.entity.User;
import org.springframework.stereotype.Repository;



/**
 * Created by Administrator on 2017/4/21.
 */
@Repository
public interface UserDao extends BaseRepository<User, String> {

    User findFristByUserName(String Name);
}
