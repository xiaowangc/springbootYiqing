package com.chige.handler;

import com.chige.domain.DataBean;
import com.chige.service.DataService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class DataHandler {
    private static String urlStr = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5";

    @Autowired
    private DataService dataService;

    /** 1.读取实时数据 需要用到HttpURLConnection，这个方法与HttpURLConnectionUtil中的方法一样【自己封装的】
     *
     * @param urlStr
     * @return 返回从指定url中读取到的实时数据
     */
    public static String getUrlStr(String urlStr){
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        BufferedReader br = null;
        StringBuilder sb = null; //返回结果
        try {
            URL url = new URL(urlStr);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(15000);//请求连接超时时间  与网络链路、网速等状态有关
            urlConnection.setReadTimeout(60000);//读取连接超时  与服务器的性能，配置等有关
            //设置本次http连接的请求方法
            urlConnection.setRequestMethod("GET");
            //设置http响应报文 返回的数据格式
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.connect();//前提参数设置好之后，开启一次连接
            if(urlConnection.getResponseCode() != 200){
                //请求超时或失败时返回失败状态码
                return "Error-Code";
            }

            inputStream = urlConnection.getInputStream();
            br = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
            sb  = new StringBuilder() ;//一次读取一行信息
            String str;
            while((str = br.readLine()) != null){
                sb.append(str);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (inputStream != null) inputStream.close();
                if (br != null) br.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return sb.toString();
    }




    /** 2.处理实时数据
     *  （信息来自腾讯疫情实时数据），返回全国34个地区的疫情情况
     * @return
     * @throws IOException
     */
    public static List<DataBean> myHandleData() throws IOException {

        //一：读取数据文件信息

//        FileReader fileReader = new FileReader("info.txt");
//        char[] cbuf = new char[1024];
//        int read;
//        StringBuilder sb = new StringBuilder();
//        while( (read = fileReader.read(cbuf)) > 0){
//            sb.append(new String(cbuf,0,read));
//        }
//        fileReader.close();

        //getUrlStr(String urlStr) 这个方法与HttpURLConnectionUtil中的方法一样【自己封装的】
        String urlStr = getUrlStr(DataHandler.urlStr);
        //二：利用Gson将字符串信息转换成map对象
        Gson gson = new GsonBuilder().create();
        Map strMap = gson.fromJson(urlStr, Map.class);
        String dataStr = (String)strMap.get("data");
        Map dataMap = gson.fromJson(dataStr, Map.class);

        //三：从map对象中提取想要的信息
        ArrayList areaTree = (ArrayList) dataMap.get("areaTree");
        Map chain = (Map) areaTree.get(0);
        ArrayList chainChildren = (ArrayList) chain.get("children");

         List<DataBean> result = new ArrayList<>();
        //四：遍历34个地区的信息，并将提取的信息存放如DataBean的集合中
        for(int i = 0; i < chainChildren.size(); i++){
            Map child = (Map) chainChildren.get(i);
            Map childTotal = (Map) child.get("total");

            String areaName = (String) child.get("name");
            double nowConfirm =  (Double) childTotal.get("nowConfirm");
            double confirm = (Double) childTotal.get("confirm");
            double heal = (Double) childTotal.get("heal");
            double dead = (Double) childTotal.get("dead");

            DataBean dataBean = new DataBean(areaName,(int)nowConfirm,(int)confirm,(int)heal,(int)dead);
            result.add(dataBean);
        }
        return result;
    }

    //在项目启动的时候顺便读取一次数据
    @PostConstruct
    public void initData(){
        System.out.println("服务器启动时，触发读取数据...");
        List<DataBean> dataBeans = null;
        try {
            dataBeans = myHandleData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        saveData(dataBeans);
    }
    //将收集到的数据存放到数据库中
    private void saveData(List<DataBean> dataBeans){
        if (dataBeans != null){
            dataService.remove(null);//清空数据库表中数据
            dataService.saveBatch(dataBeans);
        }
    }

    /** 设计一个方法，用来指定时间内更新数据
     *  @Scheduled()
     *  cron表达式  [秒 分 时 日 月 星期 年] 可以指定多久触发一次
     *  fixedRate=10000  指定频率地执行任务
     *  fixedDelay=10000 指定间隔地执行任务
     */
    @Scheduled(cron = "0 0 0/4 * * ?")  //定时任务：每四个小时触发一次updateData()
    private void updateData(){
        try {
            System.out.println("更新数据");
            List<DataBean> dataBeans = myHandleData();
            saveData(dataBeans);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

    }
}
