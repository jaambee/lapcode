package com.reframe.lapp.viewmodels.evaluations;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.manaschaudhari.android_mvvm.ViewModel;
import com.reframe.lapp.base.Navigator;
import com.reframe.lapp.models.Evaluation;
import com.reframe.lapp.models.Professor;
import com.reframe.lapp.models.ProfessorScore;
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
    public final Boolean isEvaluated;
    public final ObservableField<ProfessorScore> score = new ObservableField<>(new ProfessorScore());

    @Nullable
    public final Action0 gotoMain;

    @Nullable
    public final Action0 evaluateProfessor;

    @NonNull
    public final Navigator navigator;

    public EvaluationDetailModel(@NonNull Navigator navigator, Evaluation evaluation) {
        this.navigator = navigator;
        this.evaluation = evaluation;
        this.isEvaluated = evaluation.getProfessor().getEvaluated();
        this.score.get().setEvaluationId(evaluation.getId());
        this.professor = evaluation.getProfessor().getName();
        exerciseName = evaluation.getExercise();
        level = Utils.getParsedLevel(evaluation.getLevelName());
        exerciseGroup = evaluation.getGroup();
        video = evaluation.getVideo();
        this.gotoMain = navigator::openMain;
        this.evaluateProfessor = () -> {
         if(score.get()!= null) navigator.evaluateProfessor(score.get());
         else navigator.showToast("Debes evaluar al profesor antes!");
        };
    }
}
