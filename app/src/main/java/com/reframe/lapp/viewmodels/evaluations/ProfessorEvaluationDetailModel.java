package com.reframe.lapp.viewmodels.evaluations;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.manaschaudhari.android_mvvm.ViewModel;
import com.manaschaudhari.android_mvvm.FieldUtils;
import com.manaschaudhari.android_mvvm.utils.BindingUtils;
import com.reframe.lapp.base.Navigator;
import com.reframe.lapp.models.FeedBackResponse;
import com.reframe.lapp.models.ProfessorEvaluation;
import com.reframe.lapp.utils.Utils;

import rx.functions.Action0;

/**
 * Created by Aldo on 24-03-2017 to Lapp.
 */

public class ProfessorEvaluationDetailModel implements ViewModel {

    public final String exerciseName;
    public final String level;
    public final String video;
    public final String exerciseGroup;
    public final String student;
    public final ObservableField<ProfessorEvaluation> evaluation = new ObservableField<>();
    private ProfessorEvaluation localEvaluation;
    @Nullable
    public final Action0 gotoMain;

    @Nullable
    public final Action0 addFeedback;

    @Nullable
    public final Action0 sendEvaluation;

    @NonNull
    public final Navigator navigator;

    public ProfessorEvaluationDetailModel(@NonNull Navigator navigator, ProfessorEvaluation mEvaluation) {
        this.navigator = navigator;
        this.localEvaluation = mEvaluation;
        this.evaluation.set(localEvaluation);
        this.student = localEvaluation.getStudentName();
        this.exerciseName = localEvaluation.getExercise();
        this.level = Utils.getParsedLevel(localEvaluation.getLevel());
        this.video = localEvaluation.getVideo();
        this.exerciseGroup = localEvaluation.getGroup();
        this.gotoMain = navigator::openMain;
        this.addFeedback = () -> {
            navigator.addFeedback(localEvaluation.getExerciseId())
                    .subscribe(this::addNewFeedBack);
        };
        this.sendEvaluation = () -> {
            Log.d("EVALUATION FINISHED>", new Gson().toJson(evaluation.get()));
        };
    }

    private void addNewFeedBack(FeedBackResponse fb) {
        localEvaluation.addFeedback(fb);
        evaluation.set(localEvaluation);
        evaluation.notifyChange();
    }
}
