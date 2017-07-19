package com.reframe.lapp.viewmodels;

import com.manaschaudhari.android_mvvm.ViewModel;
import com.reframe.lapp.base.Navigator;
import com.reframe.lapp.models.Tutorial;
import com.reframe.lapp.viewmodels.tutorials.VideoItemViewModel;

import java.util.List;

import rx.Observable;

/**
 * Created by Aldo on 07-04-2017 to Lapp.
 */

public class ListViewModel implements ViewModel {
    public final Observable<List<VideoItemViewModel>> items;
    public ListViewModel(Navigator navigator, List<Tutorial> items) {

        this.items = Observable.from(items).map(tutorial -> new VideoItemViewModel(navigator, tutorial)).toList();
    }
}
