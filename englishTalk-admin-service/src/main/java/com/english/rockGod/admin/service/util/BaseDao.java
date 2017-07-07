package com.english.rockGod.admin.service.util;

import java.util.List;

/**
 * Created by bingkun on 2017/7/2.
 */
public interface BaseDao<T extends BaseEntity> {
     T findById(Integer id);

     int create(T entity);

     void update(T entity);

     void delete(T entity);

    //@Param("list") 自行命名 由于没有引入注解，本人注释了
    void batchCreate(List<T> list);
    //@Param("list")
    void batchDelete(List<T> list);

}
