package com.reframe.lapp.viewmodels;

import com.manaschaudhari.android_mvvm.ViewModel;
import com.reframe.lapp.base.Navigator;
import com.reframe.lapp.models.Tutorial;

/**
 * Created by Aldo on 11-04-2017 to Lapp.
 */

public class PlayerViewModel implements ViewModel {

    public final String url;
    public final String title;
    public final String details;
    public final String level;
    public PlayerViewModel(Navigator navigator, Tutorial tutorial) {
        title = tutorial.getName().toUpperCase();
        details = tutorial.getDetail();
        url = tutorial.getUrl();
        level = tutorial.getLevel();
    }
}
