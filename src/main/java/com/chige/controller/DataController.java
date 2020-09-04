package com.chige.controller;

import com.chige.domain.*;
import com.chige.handler.GraphHandler;
import com.chige.service.DataService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DataController {

    @Autowired
    private DataService dataService;
    @GetMapping("/")
    public String list(Model model){
        List<DataBean> dataList = dataService.list();
        model.addAttribute("dataList",dataList);
        //=============================================

        //==========GraphAddController=================
        ArrayList<String> addDateList = new ArrayList<>();
        ArrayList<Integer> addConfirmList = new ArrayList<>();
        ArrayList<Integer> addSuspectList = new ArrayList<>();
        List<GraphAddBean> graphAddBeans = GraphHandler.getGraphAddData();
        for(int i = 0; i < graphAddBeans.size(); i++){
            GraphAddBean graphAddBean = graphAddBeans.get(i);
            addDateList.add(graphAddBean.getDate());
            addConfirmList.add(graphAddBean.getAddConfirm());
            addSuspectList.add(graphAddBean.getAddSuspect());
        }
        model.addAttribute("addDateList",new Gson().toJson(addDateList));
        model.addAttribute("addConfirmList",new Gson().toJson(addConfirmList));
        model.addAttribute("addSuspectList",new Gson().toJson(addSuspectList));

        //==========GraphColumnarController=================
        ArrayList<GraphColumnarBean> list =  GraphHandler.getGraphColumnarData();
        ArrayList<String> areaNameList = new ArrayList<>();
        ArrayList<Integer> importAbroadNumber = new ArrayList<>();
        for (int i = 0; i < list.size(); i++){
            GraphColumnarBean graphColumnarBean = list.get(i);
            areaNameList.add(graphColumnarBean.getArea());
            importAbroadNumber.add(graphColumnarBean.getImport_abroad_number());
        }
        model.addAttribute("areaNameList",new Gson().toJson(areaNameList));
        model.addAttribute("importAbroadNumber",new Gson().toJson(importAbroadNumber));

        //==========GraphColumnarController=================
        List<GraphBean> graphData = GraphHandler.getGraphData();
        Map<String, ArrayList> listMap = resolverGraphBean(graphData);
        //一般model对象中发送给前端的对象都需要是JSON格式的，便于数据的传输
        model.addAttribute("dateList",new Gson().toJson(listMap.get("dateList")));
        model.addAttribute("nowConfirmList",new Gson().toJson(listMap.get("nowConfirmList")));

        //==========GraphPieController=================
        ArrayList<GraphPieBean> graphPie = GraphHandler.getGraphPieData();
        ArrayList<String> nameList = new ArrayList<>();
        for (int i = 0; i < graphPie.size(); i++){
            nameList.add(graphPie.get(i).getName());
        }
        model.addAttribute("nameList",new Gson().toJson(nameList));
        model.addAttribute("graphPie",new Gson().toJson(graphPie));

        //==========GraphThreeController=================
        ArrayList<Integer> confirmList = new ArrayList<>();
        ArrayList<Integer> healList = new ArrayList<>();
        ArrayList<Integer> deadList = new ArrayList<>();
        List<GraphThreeBean> graphThreeBeans = GraphHandler.getGraphThreeData();
        for(int i = 0; i < graphThreeBeans.size(); i++){
            GraphThreeBean graphThreeBean = graphThreeBeans.get(i);
            confirmList.add(graphThreeBean.getConfirm());
            deadList.add(graphThreeBean.getDead());
            healList.add(graphThreeBean.getHeal());
        }
        model.addAttribute("confirmList",new Gson().toJson(confirmList));
        model.addAttribute("deadList",new Gson().toJson(deadList));
        model.addAttribute("healList",new Gson().toJson(healList));

        //==========MapBeanController=================
        List<MapBean> mapBeanList = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++){
            MapBean mapBean = new MapBean(dataList.get(i).getArea(),dataList.get(i).getNowConfirm());
            mapBeanList.add(mapBean);
        }
        model.addAttribute("mapBeanList",new Gson().toJson(mapBeanList));
        return "list";
    }
    //解析GraphBean对象，将对象中的数据 与前端图表需要的数据对应
    public static Map<String,ArrayList> resolverGraphBean(List<GraphBean> graphData){
        Map<String,ArrayList> result = new HashMap<>();
        ArrayList<String> dateList = new ArrayList<>();
        ArrayList<Integer> nowConfirmList = new ArrayList<>();
        for (int i = 0; i < graphData.size(); i++){
            GraphBean graphBean = graphData.get(i);
            dateList.add(graphBean.getDate());
            nowConfirmList.add(graphBean.getNowConfirm());
        }
        result.put("dateList",dateList);
        result.put("nowConfirmList",nowConfirmList);
        return result;
    }




}
