
package com.reframe.lapp.models;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class FeedBackResponse {

    @SerializedName("audio")
    private String mAudio;
    @SerializedName("video")
    private String mVideo;

    private String mAudioUrl;

    private String mVideoUrl;


    public String getAudio() {
        return mAudio;
    }

    public void setAudio(String audio) {
        mAudio = audio;
    }

    public String getVideo() {
        return mVideo;
    }

    public void setVideo(String video) {
        mVideo = video;
    }

    public String getAudioUrl() {
        return mAudioUrl;
    }

    public void setAudioUrl(String mAudioUrl) {
        this.mAudioUrl = mAudioUrl;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }

    public void setVideoUrl(String mVideoUrl) {
        this.mVideoUrl = mVideoUrl;
    }
}
