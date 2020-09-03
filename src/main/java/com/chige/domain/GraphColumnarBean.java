package com.chige.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data@AllArgsConstructor
@NoArgsConstructor
public class GraphColumnarBean implements Serializable {

    private String area;//地区/省份
    private int import_abroad_number;//境外输入人数
}
