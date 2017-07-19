package com.reframe.lapp.viewmodels;

import android.databinding.ObservableField;
import android.support.annotation.Nullable;
import android.util.Log;

import com.manaschaudhari.android_mvvm.FieldUtils;
import com.manaschaudhari.android_mvvm.ViewModel;
import com.reframe.lapp.base.Navigator;
import com.reframe.lapp.constants.Constants;
import com.reframe.lapp.models.EvolutionData;
import com.reframe.lapp.network.LappService;

import java.util.List;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Aldo on 24-03-2017 to Lapp.
 */

public class ProfessorEvolutionViewModel implements ViewModel {
    @Nullable
    public final Action0 gotoMain;

    @Nullable
    public final Action0 getBasic;

    @Nullable
    public final Action0 getAdvanced;

    @Nullable
    public final Action1<EvolutionData> updateChart;

    public final ObservableField<List<EvolutionData>> items;

    public final ObservableField<EvolutionData> current = new ObservableField<>();

    public final ObservableField<Boolean> isVisible = new ObservableField<>(false);

    public final ObservableField<String> currentLevel = new ObservableField<>(Constants.BASIC);

    public final ObservableField<String> trigger = new ObservableField<>("");

    public final Navigator navigator;

    public ProfessorEvolutionViewModel(Navigator navigator, String studentId) {
        this.navigator = navigator;
        this.items = FieldUtils.toField(
                FieldUtils.toObservable(trigger)
                .flatMap(s -> LappService.getEvolutionInformation(currentLevel.get())
                        .flatMap(Observable::from)
                        .doOnNext(evolutionData -> Log.d("EVOLUTION", evolutionData.getName()))
                        .toList().last().doOnNext(evolutionDatas -> {
                            if(evolutionDatas.size() > 0) {
                                isVisible.set(true);
                                current.set(evolutionDatas.get(0));
                            } else {
                                isVisible.set(false);
                            }
                        }))
        );

        trigger.set(String.valueOf(Math.random()*1000));
        this.gotoMain = navigator::openMain;
        this.updateChart = chart -> current.set(chart);



        this.getBasic = ()-> {
            currentLevel.set(Constants.BASIC);
            Log.d("GETTING>","BASIC");
            trigger.set(String.valueOf(Math.random()*1000));
        };
        this.getAdvanced = ()-> {
            currentLevel.set(Constants.ADVANCED);
            Log.d("GETTING>","ADVANCED");
            trigger.set(String.valueOf(Math.random()*1000));
        };
    }
}
