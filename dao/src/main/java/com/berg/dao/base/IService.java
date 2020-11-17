package com.berg.dao.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.berg.vo.common.PageInVo;
import com.berg.dao.page.PageInfo;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public interface IService<T>  extends com.baomidou.mybatisplus.extension.service.IService<T>{

    //region mybatis-plus方法重写
    @Override
    default boolean saveBatch(Collection<T> entityList) {
        return saveBatch(entityList, DEFAULT_BATCH_SIZE);
    }

    boolean saveBatch(Collection<T> entityList, int batchSize);

    @Override
    boolean saveOrUpdate(T entity);

    @Override
    default boolean saveOrUpdateBatch(Collection<T> entityList) {
        return saveOrUpdateBatch(entityList, DEFAULT_BATCH_SIZE);
    }

    boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize);

    @Override
    default boolean updateBatchById(Collection<T> entityList) {
        return this.updateBatchById(entityList, DEFAULT_BATCH_SIZE);
    }

    boolean updateBatchById(Collection<T> entityList, int batchSize);
    //endregion

    boolean saveOrUpdateById(T entity);

    default boolean saveOrUpdateBatchById(Collection<T> entityList){
        return  saveOrUpdateBatchById(entityList, DEFAULT_BATCH_SIZE);
    }

    boolean saveOrUpdateBatchById(Collection<T> entityList , int batchSize);

    <E> E getById(java.io.Serializable id,Class<E> cls);

    <E> E getOne(Wrapper<T> queryWrapper,Class<E> cls);

    T getOneLimit(QueryWrapper<T> queryWrapper);

    T getOneLimit(LambdaQueryWrapper<T> queryWrapper);

    <E> E getOneLimit(QueryWrapper<T> queryWrapper,Class<E> cls);

    <E> E getOneLimit(LambdaQueryWrapper<T> queryWrapper,Class<E> cls);

    <E> List<E> list(Class<E> cls);

    <E> List<E> list(Wrapper<T> queryWrapper, Class<E> cls);

    <I extends PageInVo,E> PageInfo<E> page(I input, Supplier<List<E>> function);

    <E> PageInfo<E> page(int pageIndex,int pageSize, Supplier<List<E>> function);
}
