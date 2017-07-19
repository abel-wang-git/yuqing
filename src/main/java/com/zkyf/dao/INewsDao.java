package com.zkyf.dao;

import com.zkyf.core.dao.BaseRepository;
import com.zkyf.entity.News;
import org.springframework.stereotype.Repository;

/**
 * Created by wanghuiwen on 17-7-18.
 */
@Repository("newsDao")
public interface INewsDao  extends BaseRepository<News, String> {
}
