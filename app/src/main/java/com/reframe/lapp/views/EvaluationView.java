package com.reframe.lapp.views;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.manaschaudhari.android_mvvm.ViewModel;
import com.reframe.lapp.R;
import com.reframe.lapp.base.BaseActivity;
import com.reframe.lapp.constants.Constants;
import com.reframe.lapp.models.Evaluation;
import com.reframe.lapp.viewmodels.evaluations.EvaluationDetailModel;

public class EvaluationView extends BaseActivity {
    private Evaluation item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    protected ViewModel createViewModel() {
        item = new Gson().fromJson(getIntent().getStringExtra(Constants.LAPP_EXTRA), Evaluation.class);
        return new EvaluationDetailModel(getNavigator(), item);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.evaluation_detail;
    }
}
