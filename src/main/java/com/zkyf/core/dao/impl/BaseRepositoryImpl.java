package com.zkyf.core.dao.impl;

import com.zkyf.core.dao.BaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/28.
 *
 */
@NoRepositoryBean
public class BaseRepositoryImpl<T, PK extends Serializable> extends SimpleJpaRepository<T, PK> implements BaseRepository<T, PK> {
    private static final String SELECT_ALL = "SELECT * FROM  ";
    private static final String SEELECT_COUNT = "SELECT count(id) as NUM FROM ";
    private final EntityManager entityManager;
    private final JpaEntityInformation<T, ?> entityInformation;
    private final Class<T> domainClass;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    //父类没有不带参数的构造方法，这里手动构造父类
    public BaseRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.entityManager = entityManager;
        this.domainClass = domainClass;
        this.entityInformation = JpaEntityInformationSupport.getEntityInformation(domainClass, entityManager);

    }

    protected Class<T> getDomainClass() {
        return entityInformation.getJavaType();
    }

    private RowMapper<T> getRowMapper() {
        return new BeanPropertyRowMapper<T>(getDomainClass());
    }

    private String getTableName() {
        return getDomainClass().getAnnotation(Table.class).name() + " ";
    }

    @Transactional(readOnly = true)
    public List<T> listByWhere(String where, Object... para) {
        if (where == null || where.equals("")) {
            return findAll();
        }
        try {
            logger.info(SELECT_ALL + getTableName() + where + ":" + Arrays.toString(para));
            Query query = getQuery(SELECT_ALL + getTableName() + where, para, getDomainClass());
            return query.getResultList();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public List<Map<String, Object>> mapByWhere(String select, String where, Object... para) {
        ColumnMapRowMapper clomap = new ColumnMapRowMapper();
        try {
            logger.info(select + getTableName() + where + ":" + Arrays.toString(para));
            Query query = getQuery(select + where, para);
            return query.getResultList();
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            return null;
        } catch (NoResultException e) {
            logger.error (e.getMessage ());
            return null;
        }
    }



    @Transactional(readOnly = true)
    public long count(String where, Object... para) {
        if (where != null && !where.equals("")) {
            try {
                logger.info(SEELECT_COUNT + getTableName() + where + ":" + Arrays.toString(para));
                Query query = getQuery(SEELECT_COUNT + getTableName() + where, para.length>0?para:null);
                return Long.parseLong(query.getSingleResult().toString());
            } catch (DataAccessException e) {
                logger.error(e.getMessage());
                return 0;
            } catch (NoResultException e) {
                logger.error (e.getMessage ());
                return 0;
            }
        }
        return count();
    }

    public Page<T> PageAll(Pageable pageable) {
        List<T> content = listByWhere(BulidSql(pageable, ""));
        if (content != null) return new PageImpl<T>(content, pageable, count());
        return null;
    }

    public Page<T> PageByWhere(Pageable pageable, String where, Object... para) {
        if (where == null || where.equals("")) return PageAll(pageable);
        long count =  count(where, para);
        where = BulidSql(pageable, where);
        List<T> list = listByWhere(where, para);
        if (list != null) return new PageImpl<T>(list, pageable,count);
        return null;
    }

    public Page<Map<String, Object>> PageMapByWhere(Pageable pageable, String select, String where, Object... para) {
        long count =  count(where, para);
        where = BulidSql(pageable, where);
        List<Map<String, Object>> content = mapByWhere(select, where, para);
        if (content != null) return new PageImpl<Map<String, Object>>(content, pageable, count);
        return null;
    }

    private String BulidSql(Pageable pageable, String where) {
        StringBuilder sql = new StringBuilder(where);
        sql.append(" limit ").append(pageable.getOffset()).append(" , ").append(pageable.getPageSize());
        return sql.toString();
    }

    private Query getQuery(String select, Object[] para, Class lass) {

        Query query = null;

        if (lass != null) query = entityManager.createNativeQuery(select, lass);

        else entityManager.createNativeQuery(select);

        if (para.length > 0) for (int i = 0; i < para.length; i++) query.setParameter(i + 1, para[i]);

        return query;
    }

    private Query getQuery(String select, Object[] para) {

        Query query = entityManager.createNativeQuery(select);

        if (para!=null&&para.length > 0) for (int i = 0; i < para.length; i++) query.setParameter(i + 1, para[i]);

        return query;
    }

}
