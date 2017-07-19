package com.reframe.lapp.views.main;

/**
 * Created by Aldo on 30-05-2017 to Lapp.
 */

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.manaschaudhari.android_mvvm.ViewModel;
import com.reframe.lapp.R;
import com.reframe.lapp.base.BaseFragment;
import com.reframe.lapp.viewmodels.EvaluationsProfessorViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfessorEvaluationsFragment extends BaseFragment {

    public static ProfessorEvaluationsFragment newInstance() {
        return new ProfessorEvaluationsFragment();
    }

    @NonNull
    @Override
    protected ViewModel createViewModel() {
        return new EvaluationsProfessorViewModel(getNavigator());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.evaluations_professor_view;
    }
}

