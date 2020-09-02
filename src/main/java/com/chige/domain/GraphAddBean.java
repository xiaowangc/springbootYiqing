package com.chige.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data@AllArgsConstructor
@NoArgsConstructor
public class GraphAddBean implements Serializable {

    private String date;
    private int addConfirm;//新增确诊
    private int addSuspect;//新增疑似

}
