package com.reframe.lapp.viewmodels.tutorials;

import android.databinding.ObservableField;
import android.support.annotation.Nullable;

import com.manaschaudhari.android_mvvm.ViewModel;
import com.reframe.lapp.base.Navigator;
import com.reframe.lapp.models.Tutorial;

import rx.functions.Action0;

/**
 * Created by Aldo on 24-03-2017 to Lapp.
 */

public class VideoItemViewModel implements ViewModel {
    public final String name;
    public final String thumb;
    @Nullable
    public final Action0 openDetails;

    public VideoItemViewModel(Navigator navigator, Tutorial tutorial) {
        name = tutorial.getName().toUpperCase();
        thumb = tutorial.getThumbnail();
        this.openDetails = ()-> navigator.openVideo(tutorial);
    }
}
