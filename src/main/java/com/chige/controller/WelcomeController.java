package com.chige.controller;

import com.chige.domain.DataBean;
import com.chige.domain.MapBean;
import com.chige.service.DataService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WelcomeController {

    @Autowired
    private DataService dataService;
    @GetMapping("/level1/{path}")
    public String level1(@PathVariable("path")String path, Model model){
        if ("map".equals(path)){
            List<MapBean> mapBeanList = new ArrayList<>();
            List<DataBean> dataBeanList = dataService.list();
            for (int i = 0; i < dataBeanList.size(); i++){
                MapBean mapBean = new MapBean(dataBeanList.get(i).getArea(),dataBeanList.get(i).getNowConfirm());
                mapBeanList.add(mapBean);
            }
            model.addAttribute("mapBeanList",new Gson().toJson(mapBeanList));
        }
        return "/level1/" + path;
    }
    @GetMapping("/level2/{path}")
    public String level2(@PathVariable("path")String path){
        return "/level2/" + path;
    }
    @GetMapping("/level3/{path}")
    public String level3(@PathVariable("path")String path){
        return "/level3/" + path;
    }


    @GetMapping("/welcome")
    public String welcome(){
        return "welcome";
    }
}
