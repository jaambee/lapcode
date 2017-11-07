package com.reframe.lapp.viewmodels;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.manaschaudhari.android_mvvm.ViewModel;
import com.reframe.lapp.base.Navigator;
import com.reframe.lapp.models.User;
import com.reframe.lapp.network.LappService;
import com.reframe.lapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action0;

/**
 * Created by Aldo on 20-03-2017 to Lapp.
 */

public class TeacherViewModel implements ViewModel {
    @NonNull
    public final Navigator navigator;

    private final List<ViewModel> screens;

    public final Observable<List<ViewModel>> rxScreens;
    public final ObservableField<Number> current = new ObservableField<>(0);
    public final ObservableField<String> profileImage = new ObservableField<>("");
    private final TutorialsViewModel tutorialsVM;
    private final EvaluationsProfessorViewModel evaluationsVM;
    private final ProfileViewModel profileVM;

    @Nullable
    public final Action0 goTutorials;

    @Nullable
    public final Action0 goEvaluations;

    @Nullable
    public final Action0 goProfile;

    @Nullable
    public final Action0 goSettings;

    @Nullable
    public final Action0 changeImage;

    public TeacherViewModel(@NonNull Navigator navigator) {
        this.tutorialsVM = new TutorialsViewModel(navigator);
        this.evaluationsVM = new EvaluationsProfessorViewModel(navigator);
        this.profileVM = new ProfileViewModel(navigator);

        this.screens = new ArrayList<>();
        this.screens.add(tutorialsVM);
        this.screens.add(evaluationsVM);
        this.screens.add(profileVM);

        this.goTutorials = ()-> current.set(0);
        this.goEvaluations = ()-> current.set(1);
        this.goProfile = ()-> current.set(2);
        this.goSettings = navigator::openSettings;

        LappService.getMe().map(User::getPicture).subscribe(profileImage::set);

        this.rxScreens = Observable.from(screens).toList();
        this.changeImage = () -> navigator.selectImage().subscribe(imageUpload -> profileImage.set(imageUpload.getLocation()), Throwable::printStackTrace);
        this.navigator = navigator;
    }
}
