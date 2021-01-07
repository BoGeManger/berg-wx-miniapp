package com.berg.dao.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.berg.dao.page.PageHelper;
import com.berg.vo.common.PageInVo;
import com.github.pagehelper.Page;
import com.berg.dao.page.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

@Slf4j
public abstract class ServiceImpl<M extends BaseMapper<T>, T> extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<M, T> implements IService<T> {

    //region mybatis-plus方法重写
    @Override
    public boolean saveBatch(Collection<T> entityList, int batchSize) {
        String sqlStatement = sqlStatement(SqlMethod.INSERT_ONE);
        return executeBatch(entityList, batchSize, (sqlSession, entity) -> sqlSession.insert(sqlStatement, entity));
    }

    @Override
    public boolean saveOrUpdate(T entity) {
        if (null != entity) {
            Class<?> cls = entity.getClass();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
            Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!");
            String keyProperty = tableInfo.getKeyProperty();
            Assert.notEmpty(keyProperty, "error: can not execute. because can not find column for id from entity!");
            Object idVal = ReflectionKit.getFieldValue(entity, tableInfo.getKeyProperty());
            return StringUtils.checkValNull(idVal) || Objects.isNull(getById((Serializable) idVal)) ? save(entity) : updateById(entity);
        }
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
        TableInfo tableInfo = TableInfoHelper.getTableInfo(entityClass);
        Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!");
        String keyProperty = tableInfo.getKeyProperty();
        Assert.notEmpty(keyProperty, "error: can not execute. because can not find column for id from entity!");
        return executeBatch(entityList, batchSize, (sqlSession, entity) -> {
            Object idVal = ReflectionKit.getFieldValue(entity, keyProperty);
            if (StringUtils.checkValNull(idVal) || Objects.isNull(getById((Serializable) idVal))) {
                sqlSession.insert(tableInfo.getSqlStatement(SqlMethod.INSERT_ONE.getMethod()), entity);
            } else {
                MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
                param.put(Constants.ENTITY, entity);
                sqlSession.update(tableInfo.getSqlStatement(SqlMethod.UPDATE_BY_ID.getMethod()), param);
            }
        });
    }

    @Override
    public boolean updateBatchById(Collection<T> entityList, int batchSize) {
        String sqlStatement = sqlStatement(SqlMethod.UPDATE_BY_ID);
        return executeBatch(entityList, batchSize, (sqlSession, entity) -> {
            MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
            param.put(Constants.ENTITY, entity);
            sqlSession.update(sqlStatement, param);
        });
    }
    //endregion

    @Override
    public boolean saveOrUpdateById(T entity) {
        if (null != entity) {
            Class<?> cls = entity.getClass();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
            Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!");
            String keyProperty = tableInfo.getKeyProperty();
            Assert.notEmpty(keyProperty, "error: can not execute. because can not find column for id from entity!");
            Object idVal = ReflectionKit.getFieldValue(entity, tableInfo.getKeyProperty());
            Boolean isAdd = false;
            if(StringUtils.checkValNull(idVal)){
                isAdd = true;
            }
            if (idVal.equals(0)) {
                isAdd = true;
            }
            if (idVal.equals("0")) {
                isAdd = true;
            }
            if (isAdd) {
                return  save(entity);
            }else{
                return updateById(entity);
            }
        }
        return false;
    }

    @Override
    public boolean saveOrUpdateBatchById(Collection<T> entityList, int batchSize) {
        TableInfo tableInfo = TableInfoHelper.getTableInfo(entityClass);
        Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!");
        String keyProperty = tableInfo.getKeyProperty();
        Assert.notEmpty(keyProperty, "error: can not execute. because can not find column for id from entity!");
        return executeBatch(entityList, batchSize, (sqlSession, entity) -> {
            Object idVal = ReflectionKit.getFieldValue(entity, keyProperty);
            Boolean isAdd = false;
            if(StringUtils.checkValNull(idVal)){
                isAdd = true;
            }
            if (idVal.equals(0)) {
                isAdd = true;
            }
            if (idVal.equals("0")) {
                isAdd = true;
            }
            if (isAdd) {
                sqlSession.insert(tableInfo.getSqlStatement(SqlMethod.INSERT_ONE.getMethod()), entity);
            } else {
                MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
                param.put(Constants.ENTITY, entity);
                sqlSession.update(tableInfo.getSqlStatement(SqlMethod.UPDATE_BY_ID.getMethod()), param);
            }
        });
    }

    @Override
    public <E> E getById(java.io.Serializable id,Class<E> cls){
        E target = null;
        try{
            T source = getById(id);
            if(source!=null){
                target = cls.newInstance();
                BeanUtils.copyProperties(source, target);
            }
        }catch (Exception ex){
            log.error("实体转换失败:"+ex.getMessage());
        }
        return target;
    }

    @Override
    public <E> E getOne(Wrapper<T> queryWrapper,Class<E> cls){
        E target = null;
        try{
            T source = getOne(queryWrapper);
            if(source!=null){
                target = cls.newInstance();
                BeanUtils.copyProperties(source, target);
            }
        }catch (Exception ex){
            log.error("实体转换失败:"+ex.getMessage());
        }
        return target;
    }

    @Override
    public T getOneLimit(QueryWrapper<T> queryWrapper){
        queryWrapper.last("limit 1");
        T entity = getOne(queryWrapper);
        return entity;
    }

    @Override
    public T getOneLimit(LambdaQueryWrapper<T> queryWrapper){
        queryWrapper.last("limit 1");
        T entity = getOne(queryWrapper);
        return entity;
    }

    @Override
    public <E> E getOneLimit(QueryWrapper<T> queryWrapper,Class<E> cls){
        E target = null;
        try{
            queryWrapper.last("limit 1");
            T source = getOne(queryWrapper);
            if(source!=null){
                target = cls.newInstance();
                BeanUtils.copyProperties(source, target);
            }
        }catch (Exception ex){
            log.error("实体转换失败:"+ex.getMessage());
        }
        return target;
    }

    @Override
    public <E> E getOneLimit(LambdaQueryWrapper<T> queryWrapper,Class<E> cls){
        E target = null;
        try{
            queryWrapper.last("limit 1");
            T source = getOne(queryWrapper);
            if(source!=null){
                target = cls.newInstance();
                BeanUtils.copyProperties(source, target);
            }
        }catch (Exception ex){
            log.error("实体转换失败:"+ex.getMessage());
        }
        return target;
    }

    @Override
    public <E> List<E> list(Class<E> cls){
        List<E> target = new ArrayList<>();
        List<T> source = list();
        try{
            for (T item:source) {
                E entity = cls.newInstance();;
                BeanUtils.copyProperties(item, entity);
                target.add(entity);
            }
        }catch (Exception ex){
            log.error("实体转换失败:"+ex.getMessage());
        }
        return target;
    }

    @Override
    public <E> List<E> list(Wrapper<T> queryWrapper,Class<E> cls){
        List<E> target = new ArrayList<>();
        List<T> source = list(queryWrapper);
        try{
            for (T item:source) {
                E entity = cls.newInstance();;
                BeanUtils.copyProperties(item, entity);
                target.add(entity);
            }
        }catch (Exception ex){
            log.error("实体转换失败:"+ex.getMessage());
        }
        return target;
    }

    @Override
    public <I extends PageInVo,E> PageInfo<E> page(I input, Supplier<List<E>> function) {
        Page page = PageHelper.startPage(input.getPageIndex(), input.getPageSize());
        List<E> list = function.get();
        PageInfo<E> pageInfo = new PageInfo<E>(page);
        pageInfo.setList(list);
        return pageInfo;
    }

    @Override
    public <E> PageInfo<E> page(int pageIndex,int pageSize, Supplier<List<E>> function) {
        Page page = PageHelper.startPage(pageIndex, pageSize);
        List<E> list = function.get();
        PageInfo<E> pageInfo = new PageInfo<E>(page);
        pageInfo.setList(list);
        return pageInfo;
    }

    @Override
    public <I extends PageInVo,E> PageInfo<E> page(I input,int total, Supplier<List<E>> function) {
        Page page = PageHelper.startPage(input.getPageIndex(), input.getPageSize(),false);
        List<E> list = function.get();
        page.setPages((total + input.getPageSize() - 1) / input.getPageSize());
        page.setTotal(total);
        PageInfo<E> pageInfo = new PageInfo<>(page);
        pageInfo.setList(list);
        return pageInfo;
    }

    @Override
    public <E> PageInfo<E> page(int pageIndex,int pageSize,int total,Supplier<List<E>> function) {
        Page page = PageHelper.startPage(pageIndex, pageSize,false);
        List<E> list = function.get();
        page.setPages((total + pageSize - 1) / pageSize);
        page.setTotal(total);
        PageInfo<E> pageInfo = new PageInfo<>(page);
        pageInfo.setList(list);
        return pageInfo;
    }
}
