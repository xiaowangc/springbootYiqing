package com.chige.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data@AllArgsConstructor
@NoArgsConstructor
@TableName("illness")
public class DataBean implements Serializable {

    private String area;
    private Integer nowConfirm;//采用驼峰式命名，对应的数据库字段名为now_confirm
    private Integer confirm;
    private Integer heal;
    private Integer dead;
}
