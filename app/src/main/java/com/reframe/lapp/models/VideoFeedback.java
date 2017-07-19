
package com.reframe.lapp.models;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class VideoFeedback {

    @SerializedName("createdAt")
    private String mCreatedAt;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("exerciseId")
    private String mExerciseId;
    @SerializedName("id")
    private String mId;
    @SerializedName("thumbnail")
    private String mThumbnail;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("typeMedia")
    private String mTypeMedia;
    @SerializedName("updatedAt")
    private String mUpdatedAt;
    @SerializedName("url")
    private String mUrl;

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getExerciseId() {
        return mExerciseId;
    }

    public void setExerciseId(String exerciseId) {
        mExerciseId = exerciseId;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(String thumbnail) {
        mThumbnail = thumbnail;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTypeMedia() {
        return mTypeMedia;
    }

    public void setTypeMedia(String typeMedia) {
        mTypeMedia = typeMedia;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

}
