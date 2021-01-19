package com.prgers.teacher.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FirstClassSubject {

    private String id;
    private String title;
    private List<SecondClassSubject> children = new ArrayList<>();
}
