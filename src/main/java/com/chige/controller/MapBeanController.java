package com.chige.controller;

import com.chige.domain.DataBean;
import com.chige.domain.MapBean;
import com.chige.handler.GraphHandler;
import com.chige.service.DataService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MapBeanController {

    @Autowired
    private DataService dataService;
    @GetMapping("/map")
    public String mapBean(Model model){
        List<MapBean> mapBeanList = new ArrayList<>();
        List<DataBean> dataBeanList = dataService.list();
        for (int i = 0; i < dataBeanList.size(); i++){
            MapBean mapBean = new MapBean(dataBeanList.get(i).getArea(),dataBeanList.get(i).getNowConfirm());
            mapBeanList.add(mapBean);
        }
        model.addAttribute("mapBeanList",new Gson().toJson(mapBeanList));
        return "map";

    }

}
