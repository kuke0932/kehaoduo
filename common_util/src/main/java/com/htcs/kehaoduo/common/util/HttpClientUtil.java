package com.htcs.kehaoduo.common.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HttpClientUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * post请求
     *
     * @param url      资源地址
     * @param map      参数列表
     * @param encoding 编码
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static String post(String url, Map<String, String> map, String encoding) throws ParseException, IOException {
        String body = "";
        //创建httpclient对象  
        CloseableHttpClient client = HttpClients.createDefault();
        //创建post方式请求对象  
        HttpPost httpPost = new HttpPost(url);
        //装填参数  
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (map != null) {
            for (Entry<String, String> entry : map.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        //设置参数到请求对象中  
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));
        logger.info("请求地址：{}", url);
        logger.info("请求参数：{}", nvps.toString());
        //执行请求操作，并拿到结果（同步阻塞）  
        CloseableHttpResponse response = client.execute(httpPost);
        //获取结果实体  
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //按指定编码转换结果实体为String类型  
            body = EntityUtils.toString(entity, encoding);
        }
        EntityUtils.consume(entity);
        //释放链接  
        response.close();
        return body;
    }

    /**
     * get请求
     *
     * @param url      资源地址
     * @param map      参数列表
     * @param encoding 编码
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static String get(String url, String encoding) throws ParseException, IOException {
        String body = "";
        //创建httpclient对象  
        CloseableHttpClient client = HttpClients.createDefault();
        //创建post方式请求对象  
        HttpGet httpGet = new HttpGet(url);
        //设置参数到请求对象中  
        logger.info("请求地址：{}", url);
        //执行请求操作，并拿到结果（同步阻塞）  
        CloseableHttpResponse response = client.execute(httpGet);
        //获取结果实体  
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //按指定编码转换结果实体为String类型  
            body = EntityUtils.toString(entity, encoding);
        }
        EntityUtils.consume(entity);
        //释放链接  
        response.close();
        return body;
    }

    /**
     * 以JSON格式POST请求的方法
     *
     * @param url
     * @param map 参数
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String postJson(String url, Map<String, Object> map) throws ClientProtocolException, IOException {
        return postJson(url, map, null);
    }

    /**
     * 以JSON格式POST请求的方法
     *
     * @param url
     * @param map      参数
     * @param encoding 编码
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static String postJson(String url, Map<String, Object> map, String encoding) throws ClientProtocolException, IOException {

        String data = null;
        if (map != null) {
            data = JSONObject.toJSONString(map);
        }
        logger.info("url:" + url + "  param:" + data);
        //设置默认编码
        if (encoding == null) {
            encoding = "UTF-8";
        }
        String body = "";
        //创建httpclient对象  
        CloseableHttpClient client = HttpClients.createDefault();
        //创建post方式请求对象  
        HttpPost httpPost = new HttpPost(url);
        //设置参数到请求对象中  
        StringEntity requestEntity = new StringEntity(data, encoding);
        requestEntity.setContentEncoding(encoding);
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setEntity(requestEntity);
        //执行请求操作，并拿到结果（同步阻塞）  
        CloseableHttpResponse response = client.execute(httpPost);
        //获取结果实体  
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //按指定编码转换结果实体为String类型  
            body = EntityUtils.toString(entity, encoding);
        }
        EntityUtils.consume(entity);
        //释放链接  
        response.close();
        return body;
    }


    public static void main(String[] args) throws ParseException, IOException {
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("access_id", "581232706233");
        paraMap.put("access_key", "2e5l6gap3Rbe0LtN1OlI55Dehu9ss9hF");
        paraMap.put("num", "15");
        paraMap.put("page", "1");
        System.out.println(postJson("http://openapi.aodianyun.com/v2/LSS.GetApp", paraMap));
    }

    public static String uploadFileToAoDianYun(String url, Map<String, Object> paraMap, String encoding) throws ClientProtocolException, IOException {
        String data = null;
        if (paraMap != null) {
            data = JSONObject.toJSONString(paraMap);
        }
        //logger.info("url:" + url+"  param:"+data);
        //设置默认编码
        if (encoding == null) {
            encoding = "UTF-8";
        }
        String body = "";
        //创建httpclient对象  
        CloseableHttpClient client = HttpClients.createDefault();
        //创建post方式请求对象  
        HttpPost httpPost = new HttpPost(url);
        //设置参数到请求对象中  
        StringEntity requestEntity = new StringEntity(data, encoding);
        requestEntity.setContentEncoding(encoding);
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setEntity(requestEntity);
        //执行请求操作，并拿到结果（同步阻塞）  
        CloseableHttpResponse response = client.execute(httpPost);
        //获取结果实体  
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //按指定编码转换结果实体为String类型  
            body = EntityUtils.toString(entity, encoding);
        }
        EntityUtils.consume(entity);
        //释放链接  
        response.close();
        return body;
    }
}