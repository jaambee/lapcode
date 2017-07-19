package com.reframe.lapp.viewmodels;

import android.databinding.ObservableField;
import android.support.annotation.Nullable;

import com.manaschaudhari.android_mvvm.FieldUtils;
import com.manaschaudhari.android_mvvm.ViewModel;
import com.reframe.lapp.base.Navigator;
import com.reframe.lapp.network.LappService;
import com.reframe.lapp.viewmodels.evaluations.EvaluationItemViewModel;

import java.util.List;

import rx.Observable;
import rx.functions.Action0;

/**
 * Created by Aldo on 24-03-2017 to Lapp.
 */

public class EvaluationsViewModel implements ViewModel {

    public final Observable<List<EvaluationItemViewModel>> items;

    public final ObservableField<String> trigger = new ObservableField<>("");
    @Nullable
    public final Action0 gotoMain;

    public EvaluationsViewModel(Navigator navigator) {
        this.items = FieldUtils.toObservable(trigger)
                .flatMap(s -> LappService.getEvaluationTimeline()
                        .flatMap(Observable::from)
                        .map(evaluation -> new EvaluationItemViewModel(navigator, evaluation))
                        .toList());

        trigger.set(String.valueOf(Math.random()*1000));
        this.gotoMain = navigator::openMain;
    }
}
