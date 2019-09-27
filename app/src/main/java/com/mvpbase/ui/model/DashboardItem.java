package com.mvpbase.ui.model;

import com.mvpbase.data.model.Lecture;

import java.util.ArrayList;

public class DashboardItem {
    private String type;
    private String header;
    private Lecture lecture;
    private ArrayList<Lecture> list;
    private String headerType;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Lecture getLecture() {
        return lecture;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }

    public ArrayList<Lecture> getList() {
        return list;
    }

    public void setList(ArrayList<Lecture> list) {
        this.list = list;
    }

    public String getHeaderType() {
        return headerType;
    }

    public void setHeaderType(String headerType) {
        this.headerType = headerType;
    }
}
