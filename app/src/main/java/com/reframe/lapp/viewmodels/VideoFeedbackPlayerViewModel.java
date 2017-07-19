package com.reframe.lapp.viewmodels;

import android.support.annotation.Nullable;

import com.manaschaudhari.android_mvvm.ViewModel;
import com.reframe.lapp.base.Navigator;
import com.reframe.lapp.models.VideoFeedback;

import rx.functions.Action0;

/**
 * Created by Aldo on 11-04-2017 to Lapp.
 */

public class VideoFeedbackPlayerViewModel implements ViewModel {

    public final String url;
    public final String title;
    public final String details;

    @Nullable
    public final Action0 selectVideo;

    public VideoFeedbackPlayerViewModel(Navigator navigator, VideoFeedback video) {
        title = video.getTitle().toUpperCase();
        details = video.getDescription();
        url = video.getUrl();
        selectVideo = ()-> navigator.selectVideoFeedback(video);
    }
}
