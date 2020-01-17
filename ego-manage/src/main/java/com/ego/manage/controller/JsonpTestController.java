package com.ego.manage.controller;

import com.ego.commons.pojo.EgoResult;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JsonpTestController {
    /**
     * jsonp测试
     * @param callback ajax上传的函数名
     * @return
     */
    @RequestMapping("jsonp/test")
    @ResponseBody
    public MappingJacksonValue jsonpTest(String callback){
        EgoResult egoResult = new EgoResult();
        egoResult.setStatus(200);
        egoResult.setData("jsonp测试成功");
        //把构造方法参数转换为json字符串并当作最终返回值函数的参数
        MappingJacksonValue mjv = new MappingJacksonValue(egoResult);
        //最终返回结果中函数名
        mjv.setJsonpFunction(callback);

        return mjv;
    }
}
