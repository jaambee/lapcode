package com.reframe.lapp.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.manaschaudhari.android_mvvm.ViewModel;
import com.reframe.lapp.R;
import com.reframe.lapp.base.BaseActivity;
import com.reframe.lapp.constants.Constants;
import com.reframe.lapp.models.Tutorial;
import com.reframe.lapp.viewmodels.ListViewModel;

import java.lang.reflect.Type;
import java.util.List;

public class ListView extends BaseActivity {
    private List<Tutorial> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    protected ViewModel createViewModel() {
        Type listType = new TypeToken<List<Tutorial>>(){}.getType();
        Log.d("LIST>",getIntent().getStringExtra(Constants.LAPP_EXTRA));
        items = new Gson().fromJson(getIntent().getStringExtra(Constants.LAPP_EXTRA), listType);
        return new ListViewModel(getNavigator(), items);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_list;
    }
}
