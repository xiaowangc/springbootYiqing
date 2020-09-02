package com.chige.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data@AllArgsConstructor
@NoArgsConstructor
public class GraphBean implements Serializable {

    private String date;//日期
    private int nowConfirm;//当前日期的确诊人数

}
