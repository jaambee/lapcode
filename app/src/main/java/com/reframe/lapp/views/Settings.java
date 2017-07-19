package com.reframe.lapp.views;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.manaschaudhari.android_mvvm.ViewModel;
import com.reframe.lapp.R;
import com.reframe.lapp.base.BaseActivity;
import com.reframe.lapp.viewmodels.SettingsViewModel;

/**
 * Created by Aldo on 24-03-2017 to Lapp.
 */

public class Settings extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    protected ViewModel createViewModel() {
        return new SettingsViewModel(getNavigator());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }
}
