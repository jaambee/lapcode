package com.reframe.lapp;

import android.app.Application;
import android.content.ContextWrapper;

import com.manaschaudhari.android_mvvm.utils.BindingUtils;
import com.pixplicity.easyprefs.library.Prefs;
import com.reframe.lapp.adapters.BindingAdapters;
import com.reframe.lapp.network.notifications.AppFcmReceiverData;
import com.reframe.lapp.network.notifications.AppFcmReceiverUIBackground;
import com.reframe.lapp.network.notifications.RefreshTokenReceiver;

import rx_activity_result.RxActivityResult;
import rx_fcm.internal.RxFcm;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Aldo on 07-11-2016.
 */

public class Lapp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        RxActivityResult.register(this);
        // Initialize the Prefs class
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();

        //For clear sessions - only dev
        //Prefs.clear();

        // Initialize data binders
        BindingUtils.setDefaultBinder(BindingAdapters.defaultBinder);

        // Initialize font manager
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Light.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        // Initialize firebase notifications
        RxFcm.Notifications
                .init(this, new AppFcmReceiverData(), new AppFcmReceiverUIBackground());
        RxFcm.Notifications
                .onRefreshToken(new RefreshTokenReceiver());

        //Reset sessions
        //Prefs.clear();
    }
}
