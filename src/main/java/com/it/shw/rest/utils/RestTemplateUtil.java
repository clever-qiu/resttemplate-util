package com.it.shw.rest.utils;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * @Copyright: Harbin Institute of Technology.All rights reserved.
 * @Description: RestTemplate工具类
 * @author: thailandking
 * @since: 2020/3/19 15:24
 * @history: 1.2020/3/19 created by thailandking
 */
@Data
public class RestTemplateUtil {

    /**
     * @Author thailandking
     * @Date 2020/3/19 17:37
     * @LastEditors thailandking
     * @LastEditTime 2020/3/19 17:37
     * @Description 获取自定义RestTemplate
     */
    public RestTemplate getCustomRestTemplate(Integer timeout) {
        //校验
        if (timeout == null || timeout <= 0) {
            timeout = 1000 * 5;
        }
        //timeout
        RequestConfig config = RequestConfig.custom()
                .setConnectionRequestTimeout(timeout)
                .setConnectTimeout(timeout)
                .setSocketTimeout(3 * timeout)
                .build();
        HttpClientBuilder builder = HttpClientBuilder.create().setDefaultRequestConfig(config).setRetryHandler(new DefaultHttpRequestRetryHandler(3, false));
        HttpClient httpClient = builder.build();
        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(requestFactory);
    }

    /**
     * @Author thailandking
     * @Date 2020/3/19 17:37
     * @LastEditors thailandking
     * @LastEditTime 2020/3/19 17:37
     * @Description 获取自定义header
     */
    public HttpHeaders getCustomHeaders(Map<String, String> headerParams, MediaType mediaType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAll(headerParams);
        if (mediaType != null) {
            headers.setContentType(mediaType);
        }
        return headers;
    }

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

    /**
     * ALL 请求Form数据
     *
     * @param url          请求URL
     * @param formParams   请求Form数据Map
     * @param timeout      超时时间
     * @param headerParams 头部参数
     * @return 数据Map，success标识
     */
    public Map<String, Object> allForm(HttpMethod method, String url, Map<String, Object> formParams, Integer timeout, Map<String, String> headerParams) {
        RestTemplate customRestTemplate = getCustomRestTemplate(timeout);
        HttpHeaders customHeaders = getCustomHeaders(headerParams, MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, Object> requestParams = new LinkedMultiValueMap<>();
        requestParams.setAll(formParams);
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(requestParams, customHeaders);
        ResponseEntity<String> resp = customRestTemplate.exchange(url, method, entity, String.class);
        return handleResult(resp);
    }

    /**
     * All 请求Path数据
     *
     * @param url          请求URL
     * @param pathParams   请求Path数据
     * @param timeout      超时时间
     * @param headerParams 头部参数
     * @return 数据Map，success标识
     */
    public Map<String, Object> allPath(HttpMethod method, String url, Object[] pathParams, Integer timeout, Map<String, String> headerParams) {
        RestTemplate customRestTemplate = getCustomRestTemplate(timeout);
        HttpHeaders customHeaders = getCustomHeaders(headerParams, null);
        for (Object pathParam : pathParams) {
            url = url + "/" + pathParam;
        }
        HttpEntity entity = new HttpEntity<>(null, customHeaders);
        ResponseEntity<String> resp = customRestTemplate.exchange(url, method, entity, String.class);
        return handleResult(resp);
    }

    /**
     * POST 请求Json数据
     *
     * @param url          请求URL
     * @param jsonParams   请求Json数据
     * @param timeout      超时时间
     * @param headerParams 头部参数
     * @return 数据Map，success标识
     */
    public Map<String, Object> postJson(String url, String jsonParams, Integer timeout, Map<String, String> headerParams) {
        RestTemplate customRestTemplate = getCustomRestTemplate(timeout);
        HttpHeaders customHeaders = getCustomHeaders(headerParams, MediaType.APPLICATION_JSON_UTF8);
        HttpEntity entity = new HttpEntity<>(jsonParams, customHeaders);
        ResponseEntity<String> resp = customRestTemplate.postForEntity(url, entity, String.class);
        return handleResult(resp);
    }

    /**
     * POST 请求Form数据
     *
     * @param url          请求URL
     * @param formParams   请求Form数据Map
     * @param timeout      超时时间
     * @param headerParams 头部参数
     * @return 数据Map，success标识
     */
    public Map<String, Object> postForm(String url, Map<String, Object> formParams, Integer timeout, Map<String, String> headerParams) {
        return allForm(HttpMethod.POST, url, formParams, timeout, headerParams);
    }

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
    public Map<String, Object> postFile(String url, MultipartFile uploadFile, Integer timeout, Map<String, String> headerParams) throws IOException {
        RestTemplate customRestTemplate = getCustomRestTemplate(timeout);
        HttpHeaders customHeaders = getCustomHeaders(headerParams, MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> requestParams = new LinkedMultiValueMap<>();
        HttpHeaders fileHeader = new HttpHeaders();
        fileHeader.setContentType(MediaType.parseMediaType(uploadFile.getContentType()));
        fileHeader.setContentDispositionFormData("file", uploadFile.getOriginalFilename());
        HttpEntity<ByteArrayResource> fileEntity = new HttpEntity<>(new ByteArrayResource(uploadFile.getBytes()), fileHeader);
        requestParams.set("file", fileEntity);
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(requestParams, customHeaders);
        ResponseEntity<String> resp = customRestTemplate.postForEntity(url, entity, String.class);
        return handleResult(resp);
    }

    /**
     * GET 请求Path数据
     *
     * @param url          请求URL
     * @param pathParams   请求Path数据
     * @param timeout      超时时间
     * @param headerParams 头部参数
     * @return 数据Map，success标识
     */
    public Map<String, Object> getPath(String url, Object[] pathParams, Integer timeout, Map<String, String> headerParams) {
        return allPath(HttpMethod.GET, url, pathParams, timeout, headerParams);
    }

    /**
     * GET 请求Params数据
     * inputParams必须为String
     *
     * @param url          请求URL
     * @param inputParams  请求Params数据
     * @param timeout      超时时间
     * @param headerParams 头部参数
     * @return 数据Map，success标识
     */
    public Map<String, Object> getParams(String url, Map<String, String> inputParams, Integer timeout, Map<String, String> headerParams) {
        RestTemplate customRestTemplate = getCustomRestTemplate(timeout);
        HttpHeaders customHeaders = getCustomHeaders(headerParams, null);
        HttpEntity entity = new HttpEntity<>(customHeaders);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.setAll(inputParams);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        URI uri = builder.queryParams(params).build().encode().toUri();
        ResponseEntity<String> resp = customRestTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        return handleResult(resp);
    }

    /**
     * PUT 请求Path数据
     *
     * @param url          请求URL
     * @param pathParams   请求Path数据
     * @param timeout      超时时间
     * @param headerParams 头部参数
     * @return 数据Map，success标识
     */
    public Map<String, Object> putPath(String url, Object[] pathParams, Integer timeout, Map<String, String> headerParams) {
        return allPath(HttpMethod.PUT, url, pathParams, timeout, headerParams);
    }

    /**
     * PUT 请求Form数据
     *
     * @param url          请求URL
     * @param formParams   请求Form数据Map
     * @param timeout      超时时间
     * @param headerParams 头部参数
     * @return 数据Map，success标识
     */
    public Map<String, Object> putForm(String url, Map<String, Object> formParams, Integer timeout, Map<String, String> headerParams) {
        return allForm(HttpMethod.PUT, url, formParams, timeout, headerParams);
    }

    /**
     * DELETE 请求Path数据
     *
     * @param url          请求URL
     * @param pathParams   请求Path数据
     * @param timeout      超时时间
     * @param headerParams 头部参数
     * @return 数据Map，success标识
     */
    public Map<String, Object> deletePath(String url, Object[] pathParams, Integer timeout, Map<String, String> headerParams) {
        return allPath(HttpMethod.DELETE, url, pathParams, timeout, headerParams);
    }
}
