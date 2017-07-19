package com.reframe.lapp.views;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.manaschaudhari.android_mvvm.ViewModel;
import com.reframe.lapp.R;
import com.reframe.lapp.base.BaseActivity;
import com.reframe.lapp.constants.Constants;
import com.reframe.lapp.models.ProfessorEvaluation;
import com.reframe.lapp.viewmodels.evaluations.ProfessorEvaluationDetailModel;

public class ProfessorEvaluationView extends BaseActivity {
    private ProfessorEvaluation item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    protected ViewModel createViewModel() {
        item = new Gson().fromJson(getIntent().getStringExtra(Constants.LAPP_EXTRA), ProfessorEvaluation.class);
        return new ProfessorEvaluationDetailModel(getNavigator(), item);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.professor_evaluation_detail;
    }
}
