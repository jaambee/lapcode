package com.reframe.lapp.viewmodels.evaluations;

import android.support.annotation.Nullable;

import com.manaschaudhari.android_mvvm.ViewModel;
import com.reframe.lapp.base.Navigator;
import com.reframe.lapp.models.Evaluation;
import com.reframe.lapp.utils.Utils;

import rx.functions.Action0;

/**
 * Created by Aldo on 24-03-2017 to Lapp.
 */

public class EvaluationItemViewModel implements ViewModel {
    public final String name;
    public final String thumb;
    public final String date;
    public final String level;
    public final Boolean approved;
    @Nullable
    public final Action0 openDetails;

    public EvaluationItemViewModel(Navigator navigator, Evaluation evaluation) {
        name = evaluation.getExercise();
        thumb = evaluation.getThumbnail();
        date = Utils.getDateParsed(evaluation.getCreatedAt());
        level = Utils.getParsedLevel(evaluation.getLevelName()).toUpperCase();
        approved = evaluation.getApproved();
        this.openDetails = ()-> navigator.openEvaluation(evaluation);
    }
}
