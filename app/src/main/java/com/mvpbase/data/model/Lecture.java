package com.mvpbase.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

// 20180829
public class Lecture implements Serializable {


    private String lecture_id;
    private String title;
    private String desc;
    private String thumb;
    private String like;
    private String like_stat;
    private ArrayList<String> tag;
    private String hashtag;
    private String teacher;
    private String cate_id;
    @SerializedName("cate_name")
    private String category;


    @SerializedName("start_date")
    private String startDate;

    @SerializedName("last_date")
    private String lastDate;

    @SerializedName("study_stat")
    private int progress;

    private Boolean isLikeOn = false;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public Boolean getLikeOn() {
        return isLikeOn;
    }

    public void setLikeOn(Boolean likeOn) {
        isLikeOn = likeOn;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLike() {
        return like;
    }


    public void setLike(String like) {
        this.like = like;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLecture_id() {
        return lecture_id;
    }

    public void setLecture_id(String lecture_id) {
        this.lecture_id = lecture_id;
    }

    public String getLike_stat() {
        return like_stat;
    }

    public void setLike_stat(String like_stat) {
        this.like_stat = like_stat;
        if (like_stat.equals("on"))
            setLikeOn(true);
        else
            setLikeOn(false);
    }

    public ArrayList<String> getTag() {
        return tag;
    }

    public void setTag(ArrayList<String> tag) {
        this.tag = tag;

    }

    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }
}
