package com.chige.handler;

import com.chige.domain.*;
import com.chige.util.HttpClientUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.*;

/** 利用Echarst
 *  首先，想要阅读这些代码之前，最好先访问下面两个网站，读取数据，最好是以JSON格式的
 */
public class GraphHandler {

    private static String urlStr = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_other";
    private static String urlStr1 = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5";
    //数据清洗，将清洗后的数据使用对象存储，并将这些对象存放在集合中
    //图表一：确诊人数的数据清洗
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

    //图表二：新增确诊、新增疑似的柱状图数据清洗
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

    //图表三：累计确诊人数、累计死亡人数、累计治愈人数 的数据清洗
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

    //图表四：境外输入的TOP10城市 【柱状图】
    public static ArrayList<GraphColumnarBean> getGraphColumnarData() {
        ArrayList<GraphColumnarBean> result = new ArrayList<>();
        String resultStr = HttpClientUtil.doGet(urlStr1);
        Gson gson = new GsonBuilder().create();
        Map map = gson.fromJson(resultStr, Map.class);
        String dataStr = (String) map.get("data");
        Map dataMap = gson.fromJson(dataStr, Map.class);
        ArrayList areaTree = (ArrayList) dataMap.get("areaTree");
        Map chain = (Map) areaTree.get(0);
        ArrayList chainChildren = (ArrayList) chain.get("children");
        //2.可以构建一个size 为10 的大顶堆，前十个数据就是想要的结果
        //注意：PriorityQueue默认是采用小顶堆，需要利用比较器，使之成为大顶堆
        PriorityQueue<GraphColumnarBean> queue = new PriorityQueue<>(10, (o1, o2) -> o2.getImport_abroad_number() - o1.getImport_abroad_number());

        //遍历34个地区
        for (int i = 0; i < chainChildren.size(); i++) {

            Map areaMap  = (Map) chainChildren.get(i);
            String areaName = (String) areaMap.get("name");//省份名称
            //1.判断逻辑 筛选出有[境外输入]数据
            ArrayList areaChildren = (ArrayList) areaMap.get("children");
            for(int j = 0; j < areaChildren.size(); j++){
                Map areaChildrenMap = (Map) areaChildren.get(j);
                if ("境外输入".equals(areaChildrenMap.get("name"))){
                    Map columnarMap = (Map) areaChildrenMap.get("total");
                    double confirm = (double) columnarMap.get("confirm");//境外输入数量
                    GraphColumnarBean graphColumnarBean = new GraphColumnarBean(areaName,(int)confirm);
                    queue.offer(graphColumnarBean);
                    break;
                }
            }
        }
        //从优先级队列中把所有数据取出来，放到一个集合里，拿出队列的前10个即可
        int index = 10;
        while(!queue.isEmpty() && index > 0){
            result.add(queue.poll());
            index--;
        }
        return result;
    }

    //图表五：31省本地病例，境外输入病例，港澳台病例 【饼图】
    public static ArrayList<GraphPieBean> getGraphPieData(){
        ArrayList<GraphPieBean> graphPieBeanList = new ArrayList<>();
        String resultStr = HttpClientUtil.doGet(urlStr);
        Gson gson = new GsonBuilder().create();
        Map map = gson.fromJson(resultStr, Map.class);
        String dataStr = (String) map.get("data");
        Map dataMap = gson.fromJson(dataStr, Map.class);
        Map map1 = (Map) dataMap.get("nowConfirmStatis");
        for(Object o : map1.keySet()){
            String name = (String)o;
            switch (name){
                case "gat":name = "港澳台病例 ";break;
                case "import":name = "境外输入病例 ";break;
                case "province":name="31省本土病例 ";break;
            }
            double number = (double) map1.get(o);
            GraphPieBean graphPieBean = new GraphPieBean((int) number,name);
            graphPieBeanList.add(graphPieBean);
        }
        return graphPieBeanList;
    }

    //测试方法
    public static void main(String[] args) {
//        List<GraphThreeBean> graphThreeBeans = getGraphThreeData();
//        System.out.println(graphThreeBeans);
        List<GraphPieBean> list =  getGraphPieData();
        System.out.println(list);
    }


}
//TODO 优化：爬取网站数据时每个方法里面都有相同的处理，抽取出来封装，只进行一次数据的爬取即可
