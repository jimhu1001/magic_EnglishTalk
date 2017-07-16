package com.english.rockGod.pc.web.service.impl;

import com.english.rockGod.pc.web.dao.TestDao;
import com.english.rockGod.pc.web.entity.QueryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/7/15/015.
 */

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestDao testDao;

    @Override
    public List<QueryEntity> query() {
       return testDao.query();
    }
}
