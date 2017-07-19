
package com.reframe.lapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Evaluation {

    @SerializedName("approved")
    private Boolean mApproved;
    @SerializedName("createdAt")
    private String mCreatedAt;
    @SerializedName("exercise")
    private String mExercise;
    @SerializedName("feedBack")
    private List<FeedBack> mFeedBack;
    @SerializedName("group")
    private String mGroup;
    @SerializedName("info")
    private String mInfo;
    @SerializedName("level")
    private String mLevel;
    @SerializedName("professor")
    private String mProfessor;
    @SerializedName("scales")
    private List<Scale> mScales;
    @SerializedName("thumbnail")
    private String mThumbnail;
    @SerializedName("time")
    private Long mTime;
    @SerializedName("video")
    private String mVideo;

    public Boolean getApproved() {
        return mApproved;
    }

    public void setApproved(Boolean approved) {
        mApproved = approved;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getExercise() {
        return mExercise;
    }

    public void setExercise(String exercise) {
        mExercise = exercise;
    }

    public List<FeedBack> getFeedBack() {
        return mFeedBack;
    }

    public void setFeedBack(List<FeedBack> feedBack) {
        mFeedBack = feedBack;
    }

    public String getGroup() {
        return mGroup;
    }

    public void setGroup(String group) {
        mGroup = group;
    }

    public String getInfo() {
        return mInfo;
    }

    public void setInfo(String info) {
        mInfo = info;
    }

    public String getLevel() {
        return mLevel;
    }

    public void setLevel(String level) {
        mLevel = level;
    }

    public String getProfessor() {
        return mProfessor;
    }

    public void setProfessor(String professor) {
        mProfessor = professor;
    }

    public List<Scale> getScales() {
        return mScales;
    }

    public void setScales(List<Scale> scales) {
        mScales = scales;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(String thumbnail) {
        mThumbnail = thumbnail;
    }

    public Long getTime() {
        return mTime;
    }

    public void setTime(Long time) {
        mTime = time;
    }

    public String getVideo() {
        return mVideo;
    }

    public void setVideo(String video) {
        mVideo = video;
    }

}
