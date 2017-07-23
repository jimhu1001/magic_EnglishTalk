package com.english.rockGod.pc.web.controler;

import com.english.rockGod.pc.web.dto.AjaxBase;
import com.english.rockGod.pc.web.dto.ResultMap;
import com.english.rockGod.pc.web.framework.BeanMappingService;
import com.english.rockGod.pc.web.service.impl.TestService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/7/15/015.
 */
@Controller
@RequestMapping("/test")
@Log4j
public class TestControler extends AjaxBase {

    @Autowired
    private TestService testService;
    @Autowired
    private BeanMappingService beanMappingService;

    @RequestMapping(value = "/check.action", method = RequestMethod.POST)
    @ResponseBody
    public ResultMap exportExcel(HttpServletResponse response) throws Exception {
       /* List<QueryEntity> queryEntities= testService.query();
        if(!CollectionUtils.isEmpty(queryEntities)){
            throw new ApplicationException("异常");
        }*/
        return generateResultMap(SAVE_SUCCESS);
    }
}
