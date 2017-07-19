package com.reframe.lapp.viewmodels;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.manaschaudhari.android_mvvm.ViewModel;
import com.reframe.lapp.base.Navigator;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action0;

/**
 * Created by Aldo on 20-03-2017 to Lapp.
 */

public class MainViewModel implements ViewModel {
    @NonNull
    public final Navigator navigator;

    private final List<ViewModel> screens;

    public final Observable<List<ViewModel>> rxScreens;
    public final ObservableField<Number> current = new ObservableField<>(2);
    public final ObservableField<String> profileImage = new ObservableField<>("");
    private final TutorialsViewModel tutorialsVM;
    private final EvaluationsViewModel evaluationsVM;
    private final EvolutionViewModel evolutionVM;
    private final ProfileViewModel profileVM;

    @Nullable
    public final Action0 goTutorials;

    @Nullable
    public final Action0 goEvaluations;

    @Nullable
    public final Action0 goEvolution;

    @Nullable
    public final Action0 goProfile;

    @Nullable
    public final Action0 goSettings;

    @Nullable
    public final Action0 changeImage;

    public MainViewModel(@NonNull Navigator navigator) {

        Log.d("ESTAMOS EN>", "MAIN VIEW MODEL!");
        this.tutorialsVM = new TutorialsViewModel(navigator);
        this.evaluationsVM = new EvaluationsViewModel(navigator);
        this.evolutionVM = new EvolutionViewModel(navigator);
        this.profileVM = new ProfileViewModel(navigator);

        this.screens = new ArrayList<>();
        this.screens.add(tutorialsVM);
        this.screens.add(evaluationsVM);
        this.screens.add(evolutionVM);
        this.screens.add(profileVM);

        Log.d("LARGO RXSCREEN>", String.valueOf(this.screens.size()));
        this.goTutorials = ()-> current.set(0);
        this.goEvaluations = ()-> current.set(1);
        this.goEvolution = ()-> current.set(2);
        this.goProfile = ()-> current.set(3);
        this.goSettings = navigator::openSettings;

        this.rxScreens = Observable.from(screens).toList().takeLast(1);
        this.changeImage = () -> navigator.selectImage().subscribe(imageUpload -> profileImage.set(imageUpload.getLocation()), Throwable::printStackTrace);
        this.navigator = navigator;
    }
}
