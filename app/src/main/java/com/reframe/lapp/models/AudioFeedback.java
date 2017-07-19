
package com.reframe.lapp.models;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class AudioFeedback {

    @SerializedName("createdAt")
    private String mCreatedAt;
    @SerializedName("id")
    private String mId;
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

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
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
