package com.english.rockGod.pc.web.cascade;

import com.english.rockGod.pc.web.cascadeUtil.CascadeAware;
import com.english.rockGod.pc.web.cascadeUtil.Param;
import com.english.rockGod.pc.web.entity.QueryEntity;
import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2017/7/23/023.
 */
@Component
@Log4j
public class TestCascade1 implements CascadeAware {

    public List<QueryEntity> queryByCondition(@Param("id") Integer id) {

        return Lists.newArrayList();
    }

}
