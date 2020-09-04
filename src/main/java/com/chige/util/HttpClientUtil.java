package com.chige.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpClientUtil {

    private static String urlStr = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_other";

    public static String doGet(String urlStr){
        String result = "";
        //闭合的连接对象
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        try {
            httpClient = HttpClients.createDefault();//使用默认的创建httpClient对象的方式
            //请求方法对象Get方法
            HttpGet httpGet = new HttpGet(urlStr);
            //设置请求头
            httpGet.addHeader("Accept","application/json");
            //设置参数
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(35000)
                    .setConnectTimeout(35000)
                    .setSocketTimeout(60000)
                    .build();
            //设置配置参数
            httpGet.setConfig(requestConfig);
            //执行请求
            httpResponse = httpClient.execute(httpGet);
            //从返回对象中获取返回数据
            HttpEntity entity = httpResponse.getEntity();
            result = EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void main(String[] args) {
        String result = doGet(urlStr);
        System.out.println(result);
    }
}
