package com.reframe.lapp.views;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.manaschaudhari.android_mvvm.ViewModel;
import com.pixplicity.easyprefs.library.Prefs;
import com.reframe.lapp.R;
import com.reframe.lapp.base.BaseActivity;
import com.reframe.lapp.constants.Constants;
import com.reframe.lapp.network.LappService;
import com.reframe.lapp.viewmodels.LandingViewModel;

/**
 * Created by Aldo on 24-03-2017 to Lapp.
 */

public class Landing extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String token = Prefs.getString(Constants.LAPP_TOKEN, "");
        if(!token.equals("")) {
            mCompositeSubscription.add(
                    LappService.getMe().subscribe(user -> {
                        if(user.getRole().equals(Constants.PROFESSOR)) {
                            getNavigator().openTeacher();
                        } else if(user.getRole().equals(Constants.STUDENT)) {
                            getNavigator().openMain();
                        }
                    })
            );
        }
    }

    @NonNull
    @Override
    protected ViewModel createViewModel() {
        return new LandingViewModel(getNavigator());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_landing;
    }
}
