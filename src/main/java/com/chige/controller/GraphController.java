package com.chige.controller;

import com.chige.domain.GraphBean;
import com.chige.handler.GraphHandler;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GraphController {

    @GetMapping("/graph")
    public String graphShow(Model model){
        List<GraphBean> graphData = GraphHandler.getGraphData();
        Map<String, ArrayList> listMap = resolverGraphBean(graphData);
        //一般model对象中发送给前端的对象都需要是JSON格式的，便于数据的传输
        model.addAttribute("dateList",new Gson().toJson(listMap.get("dateList")));
        model.addAttribute("nowConfirmList",new Gson().toJson(listMap.get("nowConfirmList")));
        return "graph";
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
