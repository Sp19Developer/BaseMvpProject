package com.mvpbase.data.network.model;

import com.mvpbase.data.model.Lecture;

import java.util.ArrayList;

public class DashboardResponse {

    private String type;
    private String header;
    private ArrayList<Lecture> lecture;
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

    public ArrayList<Lecture> getLecture() {
        return lecture;
    }

    public void setLecture(ArrayList<Lecture> lecture) {
        this.lecture = lecture;
    }

    public String getHeaderType() {
        return headerType;
    }

    public void setHeaderType(String headerType) {
        this.headerType = headerType;
    }
}
