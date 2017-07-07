package com.english.rockGod.user.service.util;

import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;
import java.io.IOException;
import java.io.Reader;



import java.util.List;
/**
 * Created by bingkun on 2017/7/2.
 */
public class BaseDaoUtil {

    public static  <T extends BaseDao,F extends BaseEntity> void createOrUpdate(F entity,Class<T> dao){
        T t= Beans.getBean(dao);
        entity.check(entity);
        if(entity.getId()==0){
            t.create(entity);
        }else {
            t.update(entity);
        }
    }

    /**
     * |集合更新//
     *
     * @param oriList
     * @param curList
     * @param dao
     * @param <T>
     */
    public static <T extends BaseDao, F extends BaseEntity> void updateList(List<F> oriList, List<F> curList, Class<T> dao) {

        if (CollectionUtils.isEmpty(oriList) && CollectionUtils.isEmpty(curList)) return;
        T t = Beans.getBean(dao);
        if (CollectionUtils.isEmpty(oriList) && !CollectionUtils.isEmpty(curList)) {
            for (F cur:curList){
                EntityUtils.init(cur);
            }
            //curList.stream().forEach(cur -> EntityUtils.init(cur));
            t.batchCreate(curList);return;
        }
        if (CollectionUtils.isEmpty(curList) && !CollectionUtils.isEmpty(oriList)) {
            t.batchDelete(oriList);return;
        }
        List<Integer> oriIds= Lists.newArrayList();
        for (F ori:oriList){
            oriIds.add(ori.getId());
        }
        List<Integer> curIds=Lists.newArrayList();
        for (F cur:curList){
            curIds.add(cur.getId());
        }
       // List<Integer> oriIds = oriList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        //List<Integer> curIds = curList.stream().filter(o->o.getId()!=0).map(BaseEntity::getId).collect(Collectors.toList());
        for (F o:curList){
            if(o.getId()==0){
                EntityUtils.init(o);
                t.create(o);
            }else if(oriIds.contains(o.getId())){
                EntityUtils.update(o);
                t.update(o);
            }
        }
       /* curList.forEach(o->{
            if(o.getId()==0){
                EntityUtils.init(o);
                t.create(o);
            }else if(oriIds.contains(o.getId())){
                EntityUtils.update(o);
                t.update(o);
            }
        });*/
        for (F o:curList){
            if(!curIds.contains(o.getId())){
                t.delete(o);
            }
        }
       /* oriList.forEach(o->{
            if(!curIds.contains(o.getId())){
                t.delete(o);
            }
        });*/
    }


    public static  <T extends BaseDao,F extends BaseEntity> void batchCreate(List<F> entity,Class<T> dao){
        T t=Beans.getBean(dao);
        for (F  f:entity){
            f.check(f);
            if(f.getId()==0){
                t.create(f);
            }else {
                t.update(f);
            }
        }
    }

 /*   public void storedProcedures(){
        Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sqlSessionFactory.openSession();

        //select a particular student  by  id
        Student student = (Student) session.selectOne("Student.callById", 3);
    }*/

}
