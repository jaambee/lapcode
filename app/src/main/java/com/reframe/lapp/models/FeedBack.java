
package com.reframe.lapp.models;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class FeedBack {

    @SerializedName("audio")
    private Audio mAudio;
    @SerializedName("video")
    private Video mVideo;

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

}
