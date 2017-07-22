package com.english.rockGod.pc.web.controler;

import com.english.rockGod.pc.web.entity.QueryEntity;
import com.english.rockGod.pc.web.exception.ApplicationException;
import com.english.rockGod.pc.web.framework.BeanMappingService;
import com.english.rockGod.pc.web.service.impl.TestService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Administrator on 2017/7/15/015.
 */
@Controller
@RequestMapping("/test")
@Log4j
public class testControler {

    @Autowired
    private TestService testService;
    @Autowired
    private BeanMappingService beanMappingService;

    @RequestMapping(value = "/check.action", method = RequestMethod.POST)
    public String exportExcel(HttpServletResponse response) throws Exception {
        List<QueryEntity> queryEntities= testService.query();
        if(CollectionUtils.isEmpty(queryEntities)){
            throw new ApplicationException("异常");
        }

        return "index";
    }
}
