package com.reframe.lapp.viewmodels;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.manaschaudhari.android_mvvm.FieldUtils;
import com.manaschaudhari.android_mvvm.ViewModel;
import com.reframe.lapp.Lapp;
import com.reframe.lapp.base.Navigator;
import com.reframe.lapp.constants.Constants;
import com.reframe.lapp.models.ProfessorEvaluation;
import com.reframe.lapp.network.LappService;
import com.reframe.lapp.utils.RxBus;
import com.reframe.lapp.viewmodels.evaluations.ProfessorEvaluationItemViewModel;

import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by Aldo on 24-03-2017 to Lapp.
 */

public class EvaluationsProfessorViewModel implements ViewModel {

    public final Observable<List<ProfessorEvaluationItemViewModel>> items;

    public final ObservableField<String> trigger = new ObservableField<>("");


    public final ObservableField<Boolean> isRefreshing = new ObservableField<>(false);

    @Nullable
    public final Action0 onPullToRefresh;
    @Nullable
    public final Action0 gotoMain;

    public EvaluationsProfessorViewModel(Navigator navigator) {
        Observable<List<ProfessorEvaluation>> getTimelines = LappService.getProfessorEvaluationTimeline();

        this.items = FieldUtils.toObservable(trigger)
            .flatMap(s -> LappService.getProfessorEvaluationTimeline().flatMap(Observable::from)
                .filter(professorEvaluation -> !professorEvaluation.getEvaluated())
                .concatWith(getTimelines.flatMap(Observable::from)
                .filter(ProfessorEvaluation::getEvaluated))
                .map(evaluation -> new ProfessorEvaluationItemViewModel(navigator, evaluation))
                .toList())
            .doOnNext(vms -> {
                isRefreshing.set(false);
            });

        trigger.set(String.valueOf(Math.random()*1000));
        this.onPullToRefresh = () -> {
            Log.d("UPDATE", "EvaluationsProfessorViewModel: updating list ");
            isRefreshing.set(true);
            trigger.set(String.valueOf(Math.random()*1000));
        };

        RxBus.getInstance().getEvents().subscribe(e -> {
            Log.d("UPDATE", (String)e);
            onPullToRefresh.call();
        });
        this.gotoMain = navigator::openMain;
    }
}
