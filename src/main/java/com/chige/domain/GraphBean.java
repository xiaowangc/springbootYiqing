package com.chige.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data@AllArgsConstructor
@NoArgsConstructor
public class GraphBean implements Serializable {

    private String data;
    private int nowConfirm;

}
