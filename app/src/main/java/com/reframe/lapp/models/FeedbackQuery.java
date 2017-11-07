
package com.reframe.lapp.models;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class FeedbackQuery {

    @SerializedName("audio")
    private String mAudio;
    @SerializedName("timeRefer")
    private TimeRefer mTimeRefer;
    @SerializedName("video")
    private String mVideo;

    public String getAudio() {
        return mAudio;
    }

    public void setAudio(String audio) {
        mAudio = audio;
    }

    public TimeRefer getTimeRefer() {
        return mTimeRefer;
    }

    public void setTimeRefer(TimeRefer timeRefer) {
        mTimeRefer = timeRefer;
    }

    public String getVideo() {
        return mVideo;
    }

    public void setVideo(String video) {
        mVideo = video;
    }

}
