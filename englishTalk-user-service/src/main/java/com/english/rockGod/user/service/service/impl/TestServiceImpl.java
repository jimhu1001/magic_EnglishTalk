package com.english.rockGod.user.service.service.impl;

import com.english.rockGod.admin.service.framework.BeanMappingService;
import com.english.rockGod.user.api.dto.TestEntityDto;
import com.english.rockGod.user.api.service.TestService;
import com.english.rockGod.user.service.dao.ExampleDao;
import com.english.rockGod.user.service.entity.TestEntity;

/**
 * Created by Administrator on 2017/7/7/007.
 */
public class TestServiceImpl implements TestService {

    @org.springframework.beans.factory.annotation.Autowired
    private ExampleDao exampleDao;
    @org.springframework.beans.factory.annotation.Autowired
    private BeanMappingService beanMappingService;

    @Override
    public TestEntityDto findById(int id) {
        TestEntity testEntity=exampleDao.findById(id);
        return beanMappingService.transform(testEntity,TestEntityDto.class);
    }
}
