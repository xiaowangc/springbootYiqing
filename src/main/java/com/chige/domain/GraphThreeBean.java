package com.chige.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//柱状图对象
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraphThreeBean {

    private String date; //日期
    private int confirm; //累计确诊人数
    private int dead; //累计死亡人数
    private int heal; //治愈人数
}
