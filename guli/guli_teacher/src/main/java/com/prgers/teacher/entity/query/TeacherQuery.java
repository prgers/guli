package com.prgers.teacher.entity.query;

import lombok.Data;

import java.io.Serializable;

@Data
public class TeacherQuery implements Serializable {

    private String name;
    private Integer level;
    private String begin; //开始时间
    private String end; //结束时间
}
