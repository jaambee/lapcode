package com.reframe.lapp.viewmodels.tutorials;

import android.databinding.ObservableField;
import android.support.annotation.Nullable;

import com.manaschaudhari.android_mvvm.ViewModel;
import com.reframe.lapp.base.Navigator;
import com.reframe.lapp.models.Exercise;
import com.reframe.lapp.models.Group;

import java.util.List;

import rx.functions.Action0;

/**
 * Created by Aldo on 24-03-2017 to Lapp.
 */

public class TutorialItemViewModel implements ViewModel {
    public final String name;
    public final List<Exercise> exercises;
    public final ObservableField<Boolean> isOpen = new ObservableField<>(false);
    public final Navigator navigator;
    public final Boolean isUnlocked;
    @Nullable
    public final Action0 openCell;
    @Nullable
    public final Action0 closeCell;

    public TutorialItemViewModel(Navigator navigator, Group group) {
        this.name = "Grupo "+group.getName();
        this.exercises = group.getExercises();
        this.isUnlocked = group.getAvailable();
        this.navigator = navigator;
        this.openCell = () -> {
            if(isUnlocked)
                isOpen.set(true);
        };
        this.closeCell = () -> {
            if(isUnlocked)
                isOpen.set(false);
        };
    }
}
