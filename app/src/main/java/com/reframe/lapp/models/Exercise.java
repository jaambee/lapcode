
package com.reframe.lapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Exercise {

    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("tutorials")
    private List<Tutorial> mTutorials;
    @SerializedName("feedBack")
    private List<VideoFeedback> mFeedBack;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public List<Tutorial> getTutorials() {
        return mTutorials;
    }

    public void setTutorials(List<Tutorial> tutorials) {
        mTutorials = tutorials;
    }

    public List<VideoFeedback> getVideoFeedbacks() {
        return mFeedBack;
    }

    public void setVideoFeedbacks(List<VideoFeedback> videoFeedbacks) {
        mFeedBack = videoFeedbacks;
    }

}
