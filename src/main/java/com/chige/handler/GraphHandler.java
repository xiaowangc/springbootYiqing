package com.chige.handler;

import com.chige.domain.GraphAddBean;
import com.chige.domain.GraphBean;
import com.chige.domain.GraphThreeBean;
import com.chige.util.HttpClientUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** 利用Echarst
 *
 */
public class GraphHandler {

    private static String urlStr = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_other";

    //数据清洗，将清洗后的数据使用对象存储，并将这些对象存放在集合中
    public static List<GraphBean> getGraphData(){
        List<GraphBean> result = new ArrayList<>();
        String resultStr = HttpClientUtil.doGet(urlStr);
        Gson gson = new GsonBuilder().create();
        Map map = gson.fromJson(resultStr, Map.class);
        String dataStr = (String) map.get("data");
        Map dataMap = gson.fromJson(dataStr, Map.class);
        ArrayList chainDayList = (ArrayList) dataMap.get("chinaDayList");

        for (int i = 0; i < chainDayList.size(); i++) {
            Map map1  = (Map) chainDayList.get(i);
            String date = (String) map1.get("date");
            double nowConfirm = (double) map1.get("nowConfirm");
            GraphBean graphBean = new GraphBean(date, (int) nowConfirm);
            result.add(graphBean);
        }
        return result;
    }

    public static List<GraphAddBean> getGraphAddData(){
        List<GraphAddBean> result = new ArrayList<>();
        String resultStr = HttpClientUtil.doGet(urlStr);
        Gson gson = new GsonBuilder().create();
        Map map = gson.fromJson(resultStr, Map.class);
        String dataStr = (String) map.get("data");
        Map dataMap = gson.fromJson(dataStr, Map.class);
        ArrayList chinaAddDayList = (ArrayList) dataMap.get("chinaDayAddList");

        for (int i = 0; i < chinaAddDayList.size(); i++) {
            Map map1  = (Map) chinaAddDayList.get(i);
            String date = (String) map1.get("date");
            double addConfirm = (double) map1.get("confirm");
            double addSuspect = (double) map1.get("suspect");
            GraphAddBean graphAddBean = new GraphAddBean(date, (int) addConfirm,(int)addSuspect);
            result.add(graphAddBean);
        }
        return result;
    }
    public static List<GraphThreeBean> getGraphThreeData() {
        List<GraphThreeBean> result = new ArrayList<>();
        String resultStr = HttpClientUtil.doGet(urlStr);
        Gson gson = new GsonBuilder().create();
        Map map = gson.fromJson(resultStr, Map.class);
        String dataStr = (String) map.get("data");
        Map dataMap = gson.fromJson(dataStr, Map.class);
        ArrayList chinaAddDayList = (ArrayList) dataMap.get("chinaDayList");

        for (int i = 0; i < chinaAddDayList.size(); i++) {
            Map map1  = (Map) chinaAddDayList.get(i);
            String date = (String) map1.get("date");
            double confirm = (double) map1.get("confirm");
            double dead = (double) map1.get("dead");
            double heal = (double) map1.get("heal");
            GraphThreeBean graphThreeBean = new GraphThreeBean(date, (int) confirm,(int)dead,(int)heal);
            result.add(graphThreeBean);
        }
        return result;
    }

    //测试方法
    public static void main(String[] args) {
        List<GraphThreeBean> graphThreeBeans = getGraphThreeData();
        System.out.println(graphThreeBeans);
    }


}
