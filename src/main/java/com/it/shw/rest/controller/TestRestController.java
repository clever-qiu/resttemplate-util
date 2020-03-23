package com.it.shw.rest.controller;

import com.alibaba.fastjson.JSON;
import com.it.shw.rest.entity.User;
import com.it.shw.rest.utils.RestTemplateUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Copyright: Harbin Institute of Technology.All rights reserved.
 * @Description: 用于测试RestTemplate接口调用
 * @author: thailandking
 * @since: 2020/3/19 15:22
 * @history: 1.2020/3/19 created by thailandking
 */
@RestController
@RequestMapping(value = "/test")
public class TestRestController {

    //创建工具类对象
    private final RestTemplateUtil restTemplateUtil = new RestTemplateUtil();
    //测试接口的地址
    private final String URL = "http://localhost:9024";

    //1、测试post请求json数据
    @GetMapping(value = "/postJson")
    public Map testPostJson() {
        User user = new User(1L, "社会王", "123abc");
        Map<String, String> headParams = new HashMap<>();
        headParams.put("token", "shw-123-jd");
        return restTemplateUtil.postJson(URL + "/post/json", JSON.toJSONString(user), null, headParams);
    }

    //2、测试post请求form数据
    @GetMapping(value = "/postForm")
    public Map testPostForm() {
        Map<String, Object> formParams = new HashMap<>();
        formParams.put("id", 1L);
        formParams.put("name", "社会王");
        formParams.put("pass", "123abc");
        Map<String, String> headParams = new HashMap<>();
        headParams.put("token", "shw-123-jd");
        return restTemplateUtil.postForm(URL + "/post/form", formParams, null, headParams);
    }

    //3、测试post请求file数据
    @PostMapping(value = "/postFile")
    public Map testPostFile(@RequestParam(value = "file") MultipartFile multipartFile) {
        try {
            Map<String, String> headParams = new HashMap<>();
            headParams.put("token", "shw-123-jd");
            return restTemplateUtil.postFile(URL + "/post/file", multipartFile, null, headParams);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //4、测试get请求path数据
    @GetMapping(value = "/getPath")
    public Map testGetPath() {
        Object[] params = new Object[]{1, 5};
        Map<String, String> headParams = new HashMap<>();
        headParams.put("token", "shw-123-jd");
        return restTemplateUtil.getPath(URL + "/get/path", params, null, headParams);
    }

    //5、测试get请求params数据
    @GetMapping(value = "/getParams")
    public Map testGetParams() {
        Map<String, String> inputParams = new HashMap<>();
        inputParams.put("id", "1");
        inputParams.put("name", "社会王");
        inputParams.put("pass", "123abc");
        Map<String, String> headParams = new HashMap<>();
        headParams.put("token", "shw-123-jd");
        return restTemplateUtil.getParams(URL + "/get/params", inputParams, null, headParams);
    }

    //6、测试get请求object params数据
    @GetMapping(value = "/getObjectParams")
    public Map testGetObjectParams() {
        Map<String, String> inputParams = new HashMap<>();
        inputParams.put("id", "1");
        inputParams.put("name", "社会王");
        inputParams.put("pass", "123456");
        Map<String, String> headParams = new HashMap<>();
        headParams.put("token", "shw-123-jd");
        return restTemplateUtil.getParams(URL + "/get/object/params", inputParams, null, headParams);
    }

    //7、测试put请求path数据
    @GetMapping(value = "/putPath")
    public Map testPutPath() {
        Object[] params = new Object[]{1};
        Map<String, String> headParams = new HashMap<>();
        headParams.put("token", "shw-123-jd");
        return restTemplateUtil.putPath(URL + "/put/path", params, null, headParams);
    }

    //8、测试put请求form数据
    @GetMapping(value = "/putForm")
    public Map testPutForm() {
        Map<String, Object> formParams = new HashMap<>();
        formParams.put("id", 1L);
        formParams.put("name", "社会王");
        formParams.put("pass", "123");
        Map<String, String> headParams = new HashMap<>();
        headParams.put("token", "shw-123-jd");
        return restTemplateUtil.putForm(URL + "/put/form", formParams, null, headParams);
    }

    //9、测试delete请求path数据
    @GetMapping(value = "/deletePath")
    public Map testDeletePath() {
        Object[] params = new Object[]{1};
        Map<String, String> headParams = new HashMap<>();
        headParams.put("token", "shw-123-jd");
        return restTemplateUtil.deletePath(URL + "/delete/path", params, null, headParams);
    }
}
