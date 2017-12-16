package com.english.rockGod.pc.web.controler;

import com.english.rockGod.pc.web.cascadeUtil.Cacheable;
import com.english.rockGod.pc.web.dto.AjaxBase;
import com.english.rockGod.pc.web.dto.PermissionEnums;
import com.english.rockGod.pc.web.dto.ResultMap;
import com.english.rockGod.pc.web.enums.Authority;
import com.english.rockGod.pc.web.enums.CsAuthority;
import com.english.rockGod.pc.web.framework.BeanMappingService;
import com.english.rockGod.pc.web.service.impl.TestService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

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


    @Authority(permissions = PermissionEnums.USER_QUERY)
    @RequestMapping(value = "/check.action", method = RequestMethod.POST)
    @ResponseBody
    public ResultMap exportExcel(HttpServletResponse response,HttpServletRequest request) throws Exception {
        Map<Object,Object> hash=request.getParameterMap();
       /* List<QueryEntity> queryEntities= testService.query();
        if(!CollectionUtils.isEmpty(queryEntities)){
            throw new ApplicationException("异常");
        }*/
        return generateResultMap(SAVE_SUCCESS,(getHtml(request.getParameter("url"))));
    }

    private String getHtml(String url)throws Exception{
        URL url1=new URL(url);//使用java.net.URL
        URLConnection connection=url1.openConnection();//打开链接
        InputStream in=connection.getInputStream();//获取输入流
        InputStreamReader isr=new InputStreamReader(in);//流的包装
        BufferedReader br=new BufferedReader(isr);

        String line;
        StringBuffer sb=new StringBuffer();
        while((line=br.readLine())!=null){//整行读取
            sb.append(line,0,line.length());//添加到StringBuffer中
            sb.append('\n');//添加换行符
        }
        //关闭各种流，先声明的后关闭
        br.close();
        isr.close();
        in.close();
        return sb.toString();
    }
}
