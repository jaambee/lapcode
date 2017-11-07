package com.reframe.lapp.viewmodels;

import android.databinding.ObservableField;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.manaschaudhari.android_mvvm.ViewModel;
import com.pixplicity.easyprefs.library.Prefs;
import com.reframe.lapp.base.Navigator;
import com.reframe.lapp.constants.Constants;
import com.reframe.lapp.models.User;
import com.reframe.lapp.utils.Utils;

import rx.functions.Action0;

/**
 * Created by Aldo on 24-03-2017 to Lapp.
 */

public class ProfileViewModel implements ViewModel {
    @Nullable
    public final Action0 gotoMain;

    public final ObservableField<String> name = new ObservableField<>("");
    public final ObservableField<String> birth = new ObservableField<>("");
    public final ObservableField<String> country = new ObservableField<>("");
    public final ObservableField<String> institution = new ObservableField<>("");
    public final ObservableField<String> specialty = new ObservableField<>("");
    public final ObservableField<String> level = new ObservableField<>("");
    private final ObservableField<User> user = new ObservableField<>(null);
    public ProfileViewModel(Navigator navigator) {

        this.gotoMain =  navigator::openMain;
        if(!Prefs.getString(Constants.USER, "no_user").equals("no_user")) {
            Log.d("PROFILE", Prefs.getString(Constants.USER, "no_user"));
            this.user.set(new Gson().fromJson(Prefs.getString(Constants.USER, "no_user"), User.class));
            if(user.get() != null && !user.get().equals("no_user")) {
                name.set(user.get().getName());
                if(user.get().getAge()!=null)
                    birth.set(Utils.getYears(user.get().getAge()));
                if(user.get().getLevel()!=null)
                    level.set(Utils.getParsedLevel(user.get().getLevel().getName()));
                country.set(user.get().getCountry());
                institution.set(user.get().getInstitution());
                specialty.set(user.get().getSpecialty());
            }
        }

    }
}
