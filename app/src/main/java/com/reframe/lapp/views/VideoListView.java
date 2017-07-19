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
import com.reframe.lapp.models.VideoFeedback;
import com.reframe.lapp.viewmodels.VideoListViewModel;

import java.lang.reflect.Type;
import java.util.List;

public class VideoListView extends BaseActivity {
    private List<VideoFeedback> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    protected ViewModel createViewModel() {
        Type listType = new TypeToken<List<VideoFeedback>>(){}.getType();
        Log.d("LIST>",getIntent().getStringExtra(Constants.LAPP_EXTRA));
        items = new Gson().fromJson(getIntent().getStringExtra(Constants.LAPP_EXTRA), listType);
        return new VideoListViewModel(getNavigator(), items);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_videofeedback_list;
    }
}
