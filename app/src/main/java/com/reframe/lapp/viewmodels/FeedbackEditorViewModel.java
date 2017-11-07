package com.reframe.lapp.viewmodels;

import android.databinding.ObservableField;
import android.support.annotation.Nullable;

import com.manaschaudhari.android_mvvm.ViewModel;
import com.reframe.lapp.base.Navigator;
import com.reframe.lapp.models.VideoFeedback;

import rx.functions.Action0;

/**
 * Created by Aldo on 24-03-2017 to Lapp.
 */

public class FeedbackEditorViewModel implements ViewModel {
    //public final String name;
    //public final String thumb;
    //public final String videoUrl;
    //public final Float beginCut;
    //public final Float endCut;
    //public final String videoFeedbackUrl;
    //public final String audioUrl;
    public final ObservableField<Boolean> isOpen = new ObservableField<>(false);

    public FeedbackEditorViewModel(Navigator navigator) {
        //name = video.getTitle().toUpperCase();
        //thumb = video.getThumbnail();
        //this.openDetails = ()-> navigator.openVideoFeedback(video);
    }
}
