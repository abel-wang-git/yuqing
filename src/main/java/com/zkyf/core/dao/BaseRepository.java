package com.zkyf.core.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/28.
 *
 */
@NoRepositoryBean
public interface BaseRepository<T, PK extends Serializable> extends JpaRepository<T, PK> {
    List<T> listByWhere(String sql, Object... para);

    List<Map<String, Object>> mapByWhere(String select, String where, Object... para);

    long count(String where, Object... para);

    Page<T> PageAll(Pageable pageable);

    Page<T> PageByWhere(Pageable pageable, String where, Object... para);

    Page<Map<String, Object>> PageMapByWhere(Pageable pageable, String select, String where, Object... para);

}
