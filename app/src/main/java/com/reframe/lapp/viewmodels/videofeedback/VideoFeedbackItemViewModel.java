package com.reframe.lapp.viewmodels.videofeedback;

import android.databinding.ObservableField;
import android.support.annotation.Nullable;

import com.manaschaudhari.android_mvvm.ViewModel;
import com.reframe.lapp.base.Navigator;
import com.reframe.lapp.models.VideoFeedback;

import rx.functions.Action0;

/**
 * Created by Aldo on 24-03-2017 to Lapp.
 */

public class VideoFeedbackItemViewModel implements ViewModel {
    public final String name;
    public final String thumb;
    public final ObservableField<Boolean> isOpen = new ObservableField<>(false);
    @Nullable
    public final Action0 openDetails;

    public VideoFeedbackItemViewModel(Navigator navigator, VideoFeedback video) {
        name = video.getTitle().toUpperCase();
        thumb = video.getThumbnail();
        this.openDetails = ()-> navigator.openVideoFeedback(video);
    }
}
