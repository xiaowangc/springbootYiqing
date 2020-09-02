package com.chige.controller;

import com.chige.domain.GraphThreeBean;
import com.chige.handler.GraphHandler;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GraphThreeController {

    @GetMapping("/graphThree")
    public String graphShow(Model model){
        ArrayList<String> dateList = new ArrayList<>();
        ArrayList<Integer> confirmList = new ArrayList<>();
        ArrayList<Integer> healList = new ArrayList<>();
        ArrayList<Integer> deadList = new ArrayList<>();

        List<GraphThreeBean> graphThreeBeans = GraphHandler.getGraphThreeData();
        for(int i = 0; i < graphThreeBeans.size(); i++){
            GraphThreeBean graphThreeBean = graphThreeBeans.get(i);
            dateList.add(graphThreeBean.getDate());
            confirmList.add(graphThreeBean.getConfirm());
            deadList.add(graphThreeBean.getDead());
            healList.add(graphThreeBean.getHeal());
        }
        model.addAttribute("dateList",new Gson().toJson(dateList));
        model.addAttribute("confirmList",new Gson().toJson(confirmList));
        model.addAttribute("deadList",new Gson().toJson(deadList));
        model.addAttribute("healList",new Gson().toJson(healList));

        return "graphThree";
    }
}
