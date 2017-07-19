package com.reframe.lapp.viewmodels;

import android.databinding.ObservableField;
import android.support.annotation.Nullable;
import android.util.Log;

import com.manaschaudhari.android_mvvm.FieldUtils;
import com.manaschaudhari.android_mvvm.ViewModel;
import com.reframe.lapp.base.Navigator;
import com.reframe.lapp.constants.Constants;
import com.reframe.lapp.network.LappService;
import com.reframe.lapp.viewmodels.tutorials.TutorialItemViewModel;

import java.util.List;

import rx.Observable;
import rx.functions.Action0;

/**
 * Created by Aldo on 24-03-2017 to Lapp.
 */

public class TutorialsViewModel implements ViewModel {

    public final Observable<List<TutorialItemViewModel>> items;
    //private final Observable<ListView<Group>> groupObservable;
    public final ObservableField<Boolean> isRefreshing = new ObservableField<>(false);

    @Nullable
    public final Action0 gotoMain;

    @Nullable
    public final Action0 getBasic;

    @Nullable
    public final Action0 getAdvanced;

    @Nullable
    public final Action0 onPullToRefresh;

    public final ObservableField<String> filter = new ObservableField<>(Constants.BASIC);

    public final ObservableField<String> trigger = new ObservableField<>("");

    public TutorialsViewModel(Navigator navigator) {
        this.gotoMain = navigator::openMain;
        this.items = FieldUtils.toObservable(trigger)
                .flatMap(s -> LappService.getLevels()
                        .flatMap(Observable::from)
                        .filter(level -> level.getName().equals(filter.get()))
                        .first())
                        .flatMap(level -> LappService.getGroups(level.getId())
                        .flatMap(Observable::from)
                        .doOnNext(group -> Log.d("ESTAMOS>", group.getName()))
                        .map(group -> new TutorialItemViewModel(navigator, group))
                        .toList().doOnNext(tutorialItemViewModels -> isRefreshing.set(false)));

        trigger.set(String.valueOf(Math.random()*1000));
        this.onPullToRefresh = () -> {
            isRefreshing.set(true);
            trigger.set(String.valueOf(Math.random()*1000));
        };

        this.getBasic = ()-> {
            filter.set(Constants.BASIC);
            Log.d("GETTING>","BASIC");
            trigger.set(String.valueOf(Math.random()*1000));
        };
        this.getAdvanced = ()-> {
            filter.set(Constants.ADVANCED);
            Log.d("GETTING>","ADVANCED");
            trigger.set(String.valueOf(Math.random()*1000));
        };


    }
}
