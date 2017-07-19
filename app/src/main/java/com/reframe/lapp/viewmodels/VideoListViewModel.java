package com.reframe.lapp.viewmodels;

import com.manaschaudhari.android_mvvm.ViewModel;
import com.reframe.lapp.base.Navigator;
import com.reframe.lapp.models.VideoFeedback;
import com.reframe.lapp.viewmodels.videofeedback.VideoFeedbackItemViewModel;

import java.util.List;

import rx.Observable;

/**
 * Created by Aldo on 07-04-2017 to Lapp.
 */

public class VideoListViewModel implements ViewModel {
    public final Observable<List<VideoFeedbackItemViewModel>> items;
    public VideoListViewModel(Navigator navigator, List<VideoFeedback> items) {
        this.items = Observable.from(items)
                .map(video -> new VideoFeedbackItemViewModel(navigator, video))
                .toList();
    }
}
