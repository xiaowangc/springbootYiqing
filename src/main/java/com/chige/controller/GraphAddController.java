package com.chige.controller;

import com.chige.domain.GraphAddBean;
import com.chige.domain.GraphBean;
import com.chige.handler.GraphHandler;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class GraphAddController {

    @GetMapping("/graphAdd")
    public String graphShow(Model model){
        ArrayList<String> dateList = new ArrayList<>();
        ArrayList<Integer> addConfirmList = new ArrayList<>();
        ArrayList<Integer> addSuspectList = new ArrayList<>();
        List<GraphAddBean> graphAddBeans = GraphHandler.getGraphAddData();
        for(int i = 0; i < graphAddBeans.size(); i++){
            GraphAddBean graphAddBean = graphAddBeans.get(i);
            dateList.add(graphAddBean.getDate());
            addConfirmList.add(graphAddBean.getAddConfirm());
            addSuspectList.add(graphAddBean.getAddSuspect());
        }
        model.addAttribute("dateList",new Gson().toJson(dateList));
        model.addAttribute("addConfirmList",new Gson().toJson(addConfirmList));
        model.addAttribute("addSuspectList",new Gson().toJson(addSuspectList));

         return "graphAdd";
    }

}
