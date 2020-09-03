package com.chige.controller;

import com.chige.domain.GraphColumnarBean;
import com.chige.handler.GraphHandler;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class GraphColumnarController {

    @GetMapping("/graphColumnar")
    public String graphColumnar(Model model) {
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
        return "graphColumnar";
    }

}
