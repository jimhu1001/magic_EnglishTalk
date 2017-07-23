package com.english.rockGod.pc.web.cascade;

import com.english.rockGod.pc.web.cascadeUtil.Cascade;
import com.english.rockGod.pc.web.dto.ContextContainer;
import com.english.rockGod.pc.web.dto.Field;
import com.english.rockGod.pc.web.exception.ApplicationException;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/7/23/023.
 */
@Controller
@RequestMapping("/cascade")
@Log4j
public class CascadeController {

    @Autowired
    private Cascade cascade;

    @RequestMapping(value = "/query.action", method = RequestMethod.POST)
    @ResponseBody
    public Object cascade(@RequestBody List<Field> fields) {
        try {
            Object result = cascade.process(fields, new HashMap() {{
                put("operator", ContextContainer.getUserContext());
            }});

            return result;
        } catch (Exception e) {
            if (e.getMessage().contains("param type not match")) {
                throw new ApplicationException("页面输入错误");
            }
            log.error("cascade异常", e);
            throw new ApplicationException(e.getMessage());
        }
    }
}
