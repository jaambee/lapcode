package com.reframe.lapp.viewmodels.evaluations;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.manaschaudhari.android_mvvm.ViewModel;
import com.reframe.lapp.base.Navigator;
import com.reframe.lapp.models.Evaluation;
import com.reframe.lapp.utils.Utils;

import rx.functions.Action0;

/**
 * Created by Aldo on 24-03-2017 to Lapp.
 */

public class EvaluationDetailModel implements ViewModel {

    public final String exerciseName;
    public final String level;
    public final String exerciseGroup;
    public final String video;
    public final String professor;
    public final Evaluation evaluation;

    @Nullable
    public final Action0 gotoMain;

    @NonNull
    public final Navigator navigator;

    public EvaluationDetailModel(@NonNull Navigator navigator, Evaluation evaluation) {
        this.navigator = navigator;
        this.evaluation = evaluation;
        this.professor = evaluation.getProfessor();
        exerciseName = evaluation.getExercise();
        level = Utils.getParsedLevel(evaluation.getLevel());
        exerciseGroup = evaluation.getGroup();
        video = evaluation.getVideo();
        this.gotoMain = navigator::openMain;
    }
}
