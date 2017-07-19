package com.zkyf.core.service;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by wanghuiwen on 17-1-6.
 *
 */
@NoRepositoryBean
public interface IBaseService<T, PK extends Serializable> {

    List<T> findAll();

    List<T> findAll(Sort sort);

    List<T> findAll(Iterable<PK> pks);

    <S extends T> List<S> save(Iterable<S> entities);

    void flush();

    <S extends T> S saveAndFlush(S entity);

    void deleteInBatch(Iterable<T> entities);

    void deleteAllInBatch();

    T getOne(PK pk);

    <S extends T> List<S> findAll(Example<S> example);

    <S extends T> List<S> findAll(Example<S> example, Sort sort);

    Page<T> findAll(Pageable pageable);

    <S extends T> S save(S entity);

    T findOne(PK pk);

    boolean exists(PK pk);

    long count();

    void delete(PK pk);

    void delete(T entity);

    void delete(Iterable<? extends T> entities);

    void deleteAll();

    <S extends T> S findOne(Example<S> example);

    <S extends T> Page<S> findAll(Example<S> example, Pageable pageable);

    <S extends T> long count(Example<S> example);

    <S extends T> boolean exists(Example<S> example);

    /**
     * 根据 map 构建where 动态查询 page
     *
     * @param pageable
     * @param where
     * @return
     */
    Page<T> PageByWhere(Pageable pageable, LinkedHashMap<String, Object> where);
}
