package com.it.shw.rest.controller;

import com.it.shw.rest.entity.FileData;
import com.it.shw.rest.entity.User;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Copyright: Harbin Institute of Technology.All rights reserved.
 * @Description: 用于测试提供的服务接口
 * @author: thailandking
 * @since: 2020/3/19 17:06
 * @history: 1.2020/3/19 created by thailandking
 */
@RestController
public class DataController {

    //1、post请求json数据
    @PostMapping(value = "/post/json")
    public User postJsonData(@RequestBody User user, HttpServletRequest request) {
        String token = request.getHeader("token");
        user.setName(user.getName() + "_" + token);
        return user;
    }

    //2、post请求form数据
    @PostMapping(value = "/post/form")
    public User postParamsData(@RequestParam Long id, @RequestParam String name,
                               @RequestParam String pass, HttpServletRequest request) {
        String token = request.getHeader("token");
        User user = new User();
        user.setId(id);
        user.setName(name + "_" + token);
        user.setPass(pass);
        return user;
    }

    //3、post请求file数据
    @PostMapping(value = "/post/file")
    public FileData postFileData(@RequestParam("file") MultipartFile uploadFile, HttpServletRequest request) {
        try {
            String token = request.getHeader("token");
            FileUtils.copyInputStreamToFile(uploadFile.getInputStream(),
                    new File("D:\\test\\" + uploadFile.getOriginalFilename()));
            return new FileData(1L, uploadFile.getOriginalFilename(), token);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //4、get请求path数据
    @GetMapping(value = "/get/path/{page}/{size}")
    public Map<String, Object> getPathData(@PathVariable(value = "page") Integer page,
                                           @PathVariable(value = "size") Integer size, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        String token = request.getHeader("token");
        User user = new User(10L, "社会王", "123abc");
        List<User> users = new ArrayList<>();
        users.add(user);
        resultMap.put("code", "ok");
        resultMap.put("data", users);
        resultMap.put("token", token);
        resultMap.put("page", page);
        resultMap.put("size", size);
        return resultMap;
    }

    //5、get请求params数据
    @GetMapping(value = "/get/params")
    public User getParamsData(@RequestParam Long id, @RequestParam String name,
                              @RequestParam String pass, HttpServletRequest request) {
        String token = request.getHeader("token");
        User user = new User();
        user.setId(id);
        user.setName(name + "_" + token);
        user.setPass(pass);
        return user;
    }

    //6、get请求object params数据
    @GetMapping(value = "/get/object/params")
    public User getParamsObjectData(User user, HttpServletRequest request) {
        String token = request.getHeader("token");
        user.setName(user.getName() + "_" + token);
        return user;
    }

    //7、put请求path数据
    @PutMapping(value = "/put/path/{id}")
    public User putPathData(@PathVariable Long id, HttpServletRequest request) {
        User user = new User();
        String token = request.getHeader("token");
        user.setName(token);
        user.setId(id);
        return user;
    }

    //8、put请求form数据
    @PutMapping(value = "/put/form")
    public User putParamsData(User user, HttpServletRequest request) {
        String token = request.getHeader("token");
        user.setName(user.getName() + "_" + token);
        return user;
    }

    //9、delete请求path数据
    @DeleteMapping(value = "/delete/path/{id}")
    public User deletePathData(@PathVariable Long id, HttpServletRequest request) {
        User user = new User();
        String token = request.getHeader("token");
        user.setName(token);
        user.setId(id);
        return user;
    }
}
