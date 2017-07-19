package com.zkyf.core.service.impl;


import com.zkyf.core.dao.BaseRepository;
import com.zkyf.core.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghuiwen on 17-1-6.
 * service 基类
 */
@NoRepositoryBean
public abstract class BaseServiceImpl<T, PK extends Serializable> implements IBaseService<T, PK> {
    protected BaseRepository<T, PK> baseDao;

    @Autowired
    protected void setBaseDao (BaseRepository<T,PK> baseDao) {
        this.baseDao = baseDao;
    }


    public List<T> findAll() {
        return baseDao.findAll();
    }

    public List<T> findAll(Sort sort) {
        return baseDao.findAll(sort);
    }

    public List<T> findAll(Iterable<PK> pks) {
        return baseDao.findAll(pks);
    }

    public <S extends T> List<S> save(Iterable<S> entities) {
        return baseDao.save(entities);
    }

    public void flush() {
        baseDao.flush();
    }

    public <S extends T> S saveAndFlush(S entity) {
        return baseDao.saveAndFlush(entity);
    }

    public void deleteInBatch(Iterable<T> entities) {
        baseDao.deleteInBatch(entities);
    }

    public void deleteAllInBatch() {
        baseDao.deleteAllInBatch();
    }

    public T getOne(PK pk) {
        return baseDao.getOne(pk);
    }

    public <S extends T> List<S> findAll(Example<S> example) {
        return baseDao.findAll(example);
    }

    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        return baseDao.findAll(example, sort);
    }

    public Page<T> findAll(Pageable pageable) {
        return baseDao.findAll(pageable);
    }

    public <S extends T> S save(S entity) {
        return baseDao.save(entity);
    }

    public T findOne(PK pk) {
        return baseDao.findOne(pk);
    }

    public boolean exists(PK pk) {
        return baseDao.exists(pk);
    }

    public long count() {
        return baseDao.count();
    }

    public void delete(PK pk) {
        baseDao.delete(pk);
    }

    public void delete(T entity) {
        baseDao.delete(entity);
    }

    public void delete(Iterable<? extends T> entities) {
        baseDao.delete(entities);
    }

    public void deleteAll() {
        baseDao.deleteAll();
    }

    public <S extends T> S findOne(Example<S> example) {
        return baseDao.findOne(example);
    }

    public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
        return baseDao.findAll(example, pageable);
    }

    public <S extends T> long count(Example<S> example) {
        return baseDao.count(example);
    }

    public <S extends T> boolean exists(Example<S> example) {
        return baseDao.exists(example);
    }

    public Page<T> PageByWhere (Pageable pageable,LinkedHashMap<String,Object> where) {

        StringBuilder sqlWhere = new StringBuilder ("where 1=1");
        List<Object> para = new ArrayList<Object> ();
        for (Map.Entry<String,Object> entry : where.entrySet ()) {
            sqlWhere.append (" ").append (entry.getKey ()).append (" ");
            para.add (entry.getValue ());
        }
        return baseDao.PageByWhere (pageable,sqlWhere.toString (),para.toArray ());
    }
}
