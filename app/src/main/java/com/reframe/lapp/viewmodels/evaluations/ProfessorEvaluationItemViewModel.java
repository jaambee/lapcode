package com.reframe.lapp.viewmodels.evaluations;

import android.support.annotation.Nullable;

import com.manaschaudhari.android_mvvm.ViewModel;
import com.reframe.lapp.base.Navigator;
import com.reframe.lapp.models.ProfessorEvaluation;
import com.reframe.lapp.utils.Utils;

import rx.functions.Action0;

/**
 * Created by Aldo on 24-03-2017 to Lapp.
 */

public class ProfessorEvaluationItemViewModel implements ViewModel {
    public final String name;
    public final String thumb;
    public final String image;
    public final String date;
    public final String level;
    public final Boolean approved;
    public final Boolean evaluated;
    @Nullable
    public final Action0 openDetails;

    public ProfessorEvaluationItemViewModel(Navigator navigator, ProfessorEvaluation evaluation) {
        name = evaluation.getExercise();
        thumb = evaluation.getThumbnail();
        image = evaluation.getPicture();
        date = Utils.getDateParsed(evaluation.getCreatedAt());
        level = Utils.getParsedLevel(evaluation.getLevel()).toUpperCase();
        approved = evaluation.getApproved();
        evaluated = evaluation.getEvaluated();
        this.openDetails = ()-> navigator.openEvaluation(evaluation);
    }
}
