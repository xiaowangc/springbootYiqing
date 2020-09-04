package com.chige.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data@AllArgsConstructor
public class GraphPieBean implements Serializable {
    private Integer value; //各自对应的数量
    private String name;  //港澳台、31省本土、境外输入病例
}
