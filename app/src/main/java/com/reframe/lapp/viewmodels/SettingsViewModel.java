package com.reframe.lapp.viewmodels;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.manaschaudhari.android_mvvm.ViewModel;
import com.reframe.lapp.base.Navigator;

import rx.functions.Action0;

/**
 * Created by Aldo on 20-03-2017 to Lapp.
 */

public class SettingsViewModel implements ViewModel {
    @NonNull
    public final Navigator navigator;
    @Nullable
    public final Action0 goBack;
	@Nullable
	public final Action0 changeEmail;
	@Nullable
	public final Action0 changePassword;
    @Nullable
    public final Action0 doLogout;

    public SettingsViewModel(@NonNull Navigator navigator) {
        this.navigator = navigator;
        this.goBack = navigator::backAction;
	    this.changeEmail = navigator::changeEmail;
	    this.changePassword = navigator::changePassword;
        this.doLogout = navigator::doLogout;
    }
}
