---
title: RestTemplate封装
date: 2020-03-23 20:53:01
tags: [RestTemplate]
---

## 一、常见Restful接口

### 1、POST

- json

```java
//1、post请求json数据
@PostMapping(value = "/post/json")
public User postJsonData(@RequestBody User user, HttpServletRequest request) {
    
}
```

- form

```java
//2、post请求form数据
@PostMapping(value = "/post/form")
public User postParamsData(@RequestParam Long id, @RequestParam String name,
                           @RequestParam String pass, HttpServletRequest request) {
    
}
```

<!-- more -->

- file

```java
//3、post请求file数据
@PostMapping(value = "/post/file")
public FileData postFileData(@RequestParam("file") MultipartFile uploadFile, HttpServletRequest request) {
    
}
```

### 2、GET

- path

```java
//4、get请求path数据
@GetMapping(value = "/get/path/{page}/{size}")
public Map<String, Object> getPathData(@PathVariable(value = "page") Integer page,
                                       @PathVariable(value = "size") Integer size, HttpServletRequest request) {
    
}
```

- params

```java
//5、get请求params数据
@GetMapping(value = "/get/params")
public User getParamsData(@RequestParam Long id, @RequestParam String name,
                          @RequestParam String pass, HttpServletRequest request) {
    
}
```

- object params

```java
//6、get请求object params数据
@GetMapping(value = "/get/object/params")
public User getParamsObjectData(User user, HttpServletRequest request) {
    
}
```

### 3、PUT

- path

```java
//7、put请求path数据
@PutMapping(value = "/put/path/{id}")
public User putPathData(@PathVariable Long id, HttpServletRequest request) {
    
}
```

- form

```java
//8、put请求form数据
@PutMapping(value = "/put/form")
public User putParamsData(User user, HttpServletRequest request) {
    
}
```

### 4、DELETE

```java
//9、delete请求path数据
@DeleteMapping(value = "/delete/path/{id}")
public User deletePathData(@PathVariable Long id, HttpServletRequest request) {
    
}
```

## 二、基于RestTemplate封装API

### 1、POST 请求Json数据

```java
/**
     * POST 请求Json数据
     *
     * @param url          请求URL
     * @param jsonParams   请求Json数据
     * @param timeout      超时时间
     * @param headerParams 头部参数
     * @return 数据Map，success标识
     */
public Map<String, Object> postJson(String url, 
                                    String jsonParams, 
                                    Integer timeout, 
                                    Map<String, String> headerParams) {
    
}
```

### 2、POST 请求Form数据

```java
/**
     * POST 请求Form数据
     *
     * @param url          请求URL
     * @param formParams   请求Form数据Map
     * @param timeout      超时时间
     * @param headerParams 头部参数
     * @return 数据Map，success标识
     */
public Map<String, Object> postForm(String url, 
                                    Map<String, Object> formParams, 
                                    Integer timeout, 
                                    Map<String, String> headerParams) {
    
}
```

### 3、POST 请求File数据

```java
/**
     * POST 请求File数据
     * 调用方controller接口 @RequestParam("file") MultipartFile uploadFile
     *
     * @param url          请求URL
     * @param uploadFile   请求File数据
     * @param timeout      超时时间
     * @param headerParams 头部参数
     * @return 数据Map，success标识
     */
public Map<String, Object> postFile(String url, 
                                    MultipartFile uploadFile, 
                                    Integer timeout, 
                                    Map<String, String> headerParams) throws IOException{
    
}
```

### 4、GET 请求Path数据

```java
/**
     * GET 请求Path数据
     *
     * @param url          请求URL
     * @param pathParams   请求Path数据
     * @param timeout      超时时间
     * @param headerParams 头部参数
     * @return 数据Map，success标识
     */
public Map<String, Object> getPath(String url, 
                                   Object[] pathParams, 
                                   Integer timeout, 
                                   Map<String, String> headerParams) {
    
}
```

### 5、GET 请求Params数据

```java
/**
     * GET 请求Params数据
     * inputParams必须为(String,String)
     *
     * @param url          请求URL
     * @param inputParams  请求Params数据
     * @param timeout      超时时间
     * @param headerParams 头部参数
     * @return 数据Map，success标识
     */
public Map<String, Object> getParams(String url, 
                                     Map<String, String> inputParams, 
                                     Integer timeout, 
                                     Map<String, String> headerParams) {
    
}
```

### 6、PUT 请求Path数据

```java
/**
     * PUT 请求Path数据
     *
     * @param url          请求URL
     * @param pathParams   请求Path数据
     * @param timeout      超时时间
     * @param headerParams 头部参数
     * @return 数据Map，success标识
     */
public Map<String, Object> putPath(String url, 
                                   Object[] pathParams, 
                                   Integer timeout, 
                                   Map<String, String> headerParams) {
    
}
```

### 7、PUT 请求Form数据

```java
/**
     * PUT 请求Form数据
     *
     * @param url          请求URL
     * @param formParams   请求Form数据Map
     * @param timeout      超时时间
     * @param headerParams 头部参数
     * @return 数据Map，success标识
     */
public Map<String, Object> putForm(String url, 
                                   Map<String, Object> formParams, 
                                   Integer timeout, 
                                   Map<String, String> headerParams) {
    
}
```

### 8、DELETE 请求Path数据

```java
/**
     * DELETE 请求Path数据
     *
     * @param url          请求URL
     * @param pathParams   请求Path数据
     * @param timeout      超时时间
     * @param headerParams 头部参数
     * @return 数据Map，success标识
     */
public Map<String, Object> deletePath(String url, 
                                      Object[] pathParams, 
                                      Integer timeout, 
                                      Map<String, String> headerParams) {
}
```

## 三、使用

### 0、准备

- 创建SpringBoot工程
- 添加依赖

```xml
<!--fastjson-->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.47</version>
</dependency>

<!--httpclient-->
<dependency>
    <groupId>org.apache.httpcomponents</groupId>
    <artifactId>httpclient</artifactId>
</dependency>
```

- 导入**RestTemplateUtil.java**文件

### 1、POST 请求Json数据

```java
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
```

### 2、POST 请求Form数据

```java
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
```

### 3、POST 请求File数据

```java
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
```

### 4、GET 请求Path数据

```java
//4、测试get请求path数据
@GetMapping(value = "/getPath")
public Map testGetPath() {
    Object[] params = new Object[]{1, 5};
    Map<String, String> headParams = new HashMap<>();
    headParams.put("token", "shw-123-jd");
    return restTemplateUtil.getPath(URL + "/get/path", params, null, headParams);
}
```

### 5、GET 请求Params数据

```java
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
```

### 6、PUT 请求Path数据

```java
//6、测试put请求path数据
@GetMapping(value = "/putPath")
public Map testPutPath() {
    Object[] params = new Object[]{1};
    Map<String, String> headParams = new HashMap<>();
    headParams.put("token", "shw-123-jd");
    return restTemplateUtil.putPath(URL + "/put/path", params, null, headParams);
}
```

### 7、PUT 请求Form数据

```java
//7、测试put请求form数据
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
```

### 8、DELETE 请求Path数据

```java
//8、测试delete请求path数据
@GetMapping(value = "/deletePath")
public Map testDeletePath() {
    Object[] params = new Object[]{1};
    Map<String, String> headParams = new HashMap<>();
    headParams.put("token", "shw-123-jd");
    return restTemplateUtil.deletePath(URL + "/delete/path", params, null, headParams);
}
```

## 四、统一返回

- 统一返回为Map格式，调用接口的响应数据必须能解析成**Map对象**，**不然会报错**
- **success为成功标识，data为返回数据，业务获取解析data部分即可**

```java
/**
     * @Author thailandking
     * @Date 2020/3/19 17:47
     * @LastEditors thailandking
     * @LastEditTime 2020/3/19 17:47
     * @Description 处理调用结果 (接口返回数据必须能解析成Map对象格式)
     */
public Map<String, Object> handleResult(ResponseEntity<String> resp) {
    Map<String, Object> resultMap = new HashMap<>();
    if (resp.getStatusCodeValue() == 200) {
        String body = resp.getBody();
        Map dataMap = JSON.parseObject(body, Map.class);
        resultMap.put("success", true);
        resultMap.put("data", dataMap);
    } else {
        resultMap.put("success", false);
    }
    return resultMap;
}
```

- 成功返回

```json
{
  "data": {
    "pass": "123abc",
    "name": "社会王_shw-123-jd",
    "id": 1
  },
  "success": true
}
```

- 错误返回

```json
{
  "success": false
}
```

## 源码链接

- https://github.com/ThailandKing/resttemplate-util.git

