
package com.reframe.lapp.models;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class FeedBackResponse {

    @SerializedName("audio")
    private Audio mAudio;
    @SerializedName("video")
    private Video mVideo;

    private String mAudioUrl;

    private String mVideoUrl;


    public Audio getAudio() {
        return mAudio;
    }

    public void setAudio(Audio audio) {
        mAudio = audio;
    }

    public Video getVideo() {
        return mVideo;
    }

    public void setVideo(Video video) {
        mVideo = video;
    }

    public String getAudioUrl() {
        return mAudio.getUrl();
    }

    public void setAudioUrl(String mAudioUrl) {
        this.mAudio.setUrl(mAudioUrl);
    }

    public String getVideoUrl() {
        return mVideo.getUrl();
    }

    public void setVideoUrl(String mVideoUrl) {
        this.mVideo.setUrl(mVideoUrl);
    }
}
