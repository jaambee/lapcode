
package com.reframe.lapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ProfessorEvaluation {

    @SerializedName("approved")
    private Boolean mApproved;
    @SerializedName("createdAt")
    private String mCreatedAt;
    @SerializedName("evaluated")
    private Boolean mEvaluated;
    @SerializedName("exercise")
    private String mExercise;
    @SerializedName("exerciseId")
    private String mExerciseId;
    @SerializedName("feedBack")
    private List<FeedBackResponse> mFeedBackResponse;
    @SerializedName("times")
    private List<Time> mTimes;
    private List<FeedbackQuery> mFeedBack;
    @SerializedName("group")
    private String mGroup;
    @SerializedName("id")
    private String mId;
    @SerializedName("info")
    private String mInfo;
    @SerializedName("levelName")
    private String mLevelName;
    @SerializedName("levelId")
    private String mLevelId;
    @SerializedName("picture")
    private String mPicture;
    @SerializedName("scales")
    private List<Scale> mScales;
    @SerializedName("studentId")
    private String mStudentId;
    @SerializedName("studentName")
    private String mStudentName;
    @SerializedName("thumbnail")
    private String mThumbnail;
    @SerializedName("time")
    private Long mTime;
    @SerializedName("approveTime")
    private Long mApproveTime;
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

    public Boolean getEvaluated() {
        return mEvaluated;
    }

    public void setEvaluated(Boolean evaluated) {
        mEvaluated = evaluated;
    }

    public String getExercise() {
        return mExercise;
    }

    public void setExercise(String exercise) {
        mExercise = exercise;
    }

    public String getExerciseId() {
        return mExerciseId;
    }

    public void setExerciseId(String exerciseId) {
        mExerciseId = exerciseId;
    }

    public List<Time> getTimes() {
        return mTimes;
    }

    public void setTimes(List<Time> times) {
        mTimes = times;
    }

    public List<FeedbackQuery> getFeedBack() {
        return mFeedBack;
    }

    public void setFeedBack(List<FeedbackQuery> feedBack) {
        mFeedBack = feedBack;
    }

    public List<FeedBackResponse> getFeedBackResponse() {
        return mFeedBackResponse;
    }

    public void setFeedBackResponse(List<FeedBackResponse> feedBack) { mFeedBackResponse = feedBack; }

    public void addFeedback(FeedBackResponse feedBack) {
        mFeedBackResponse.add(feedBack);
    }

    public String getGroup() {
        return mGroup;
    }

    public void setGroup(String group) {
        mGroup = group;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
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

    public String getPicture() {
        return mPicture;
    }

    public void setPicture(String picture) {
        mPicture = picture;
    }

    public List<Scale> getScales() {
        return mScales;
    }

    public void setScales(List<Scale> scales) {
        mScales = scales;
    }

    public String getStudentId() {
        return mStudentId;
    }

    public void setStudentId(String studentId) {
        mStudentId = studentId;
    }

    public String getStudentName() {
        return mStudentName;
    }

    public void setStudentName(String studentName) {
        mStudentName = studentName;
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

    public Long getApproveTime() {
        return mApproveTime;
    }

    public void setApproveTime(Long approveTime) {
        mApproveTime= approveTime;
    }

    public String getVideo() {
        return mVideo;
    }

    public void setVideo(String video) {
        mVideo = video;
    }

}
