package com.english.rockGod.pc.web.dto;

import com.google.gson.Gson;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by jimHu on 17/7/23.
 */
@Controller
public class AjaxBase {
    protected final static String SAVE_SUCCESS = "保存成功";
    protected final static String CREATE_SUCCESS = "创建成功";
    protected final static String PUBLISH_SUCCESS = "发布成功";
    protected final static String DELETE_SUCCESS = "删除成功";
    protected final static String SUBMIT_SUCCESS = "提交成功";


    @ExceptionHandler(RuntimeException.class)
    public ModelAndView operateExp(RuntimeException ex, HttpServletRequest request) {
        if (ex instanceof HttpMessageNotReadableException) {
            return new ModelAndView(new View() {
                @Override
                public String getContentType() {
                    return "application/json";
                }

                @Override
                public void render(Map<String, ?> map, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
                    httpServletResponse.setContentType(getContentType());
                    httpServletResponse.setStatus(500);
                    PrintWriter writer = httpServletResponse.getWriter();
                    writer.print(new Gson().toJson(ResultMap.builder().code("500").msg("页面输入错误")));
                    writer.flush();
                }
            });
        }
        throw ex;
    }

    protected ResultMap generateResultMap(String msg) {
        return ResultMap.builder().msg(msg).build();
    }

    protected ResultMap generateResultMap(String msg, Object data) {
        return ResultMap.builder().msg(msg).data(data).build();
    }

    protected ResultMap generateDataResultMap(Object data) {
        return ResultMap.builder().msg("").data(data).build();
    }

}
