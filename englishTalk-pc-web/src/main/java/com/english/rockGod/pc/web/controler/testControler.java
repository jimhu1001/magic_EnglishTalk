package com.english.rockGod.pc.web.controler;

import com.english.rockGod.pc.web.service.impl.TestService;
import com.english.rockGod.pc.web.util.Beans;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/7/15/015.
 */
@Controller
@RequestMapping("/test")
@Log4j
public class testControler {

    @Autowired
    private TestService testService;

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public String exportExcel(HttpServletResponse response) throws Exception {
        Beans.getBean(TestService.class);
        testService.query();
        return "success!";
    }
}
