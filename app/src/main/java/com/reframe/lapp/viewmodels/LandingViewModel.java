package com.reframe.lapp.viewmodels;

import android.databinding.ObservableField;
import android.support.annotation.Nullable;

import com.manaschaudhari.android_mvvm.ViewModel;
import com.reframe.lapp.base.Navigator;

import rx.functions.Action0;

/**
 * Created by Aldo on 24-03-2017 to Lapp.
 */

public class LandingViewModel implements ViewModel {
    @Nullable
    public final Action0 doLogin;

    public final ObservableField<String> email = new ObservableField<>("");
    public final ObservableField<String> password = new ObservableField<>("");

    public LandingViewModel(Navigator navigator) {

        this.doLogin = () -> navigator.doLogin(email.get(), password.get());
    }
}
