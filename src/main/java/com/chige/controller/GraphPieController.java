package com.chige.controller;

import com.chige.domain.GraphPieBean;
import com.chige.handler.GraphHandler;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class GraphPieController {

    @GetMapping("/graphPie")
    public String graphPieList(Model model){
        ArrayList<GraphPieBean> graphPie = GraphHandler.getGraphPieData();
        ArrayList<String> nameList = new ArrayList<>();
        for (int i = 0; i < graphPie.size(); i++){
            nameList.add(graphPie.get(i).getName());
        }
        model.addAttribute("nameList",new Gson().toJson(nameList));
        model.addAttribute("graphPie",new Gson().toJson(graphPie));
        return "graphPie";
    }


}
