
package com.reframe.lapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Evaluation {

    @SerializedName("approved")
    private Boolean mApproved;
    @SerializedName("id")
    private String mId;
    @SerializedName("createdAt")
    private String mCreatedAt;
    @SerializedName("exercise")
    private String mExercise;
    @SerializedName("feedBack")
    private List<FeedBack> mFeedBack;
    @SerializedName("times")
    private List<Time> mTimes;
    @SerializedName("group")
    private String mGroup;
    @SerializedName("info")
    private String mInfo;
    @SerializedName("levelName")
    private String mLevelName;
    @SerializedName("levelId")
    private String mLevelId;
    @SerializedName("professor")
    private Professor mProfessor;
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

    public String getLevelName() {
        return mLevelName;
    }

    public void setLevelName(String levelName) {
        mLevelName = levelName;
    }

    public String getLevelId() {
        return mLevelId;
    }

    public void setLevelId(String levelId) {
        mLevelId = levelId;
    }

    public Professor getProfessor() {
        return mProfessor;
    }

    public void setProfessor(Professor professor) {
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

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getVideo() {
        return mVideo;
    }

    public void setVideo(String video) {
        mVideo = video;
    }

    public List<Time> getTimes() {
        return mTimes;
    }

    public void setTimes(List<Time> times) {
        mTimes = times;
    }

}
