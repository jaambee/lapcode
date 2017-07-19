package com.reframe.lapp.utils;

import android.support.annotation.NonNull;

import com.manaschaudhari.android_mvvm.adapters.ViewProvider;
import com.reframe.lapp.R;
import com.reframe.lapp.viewmodels.EvaluationsProfessorViewModel;
import com.reframe.lapp.viewmodels.EvaluationsViewModel;
import com.reframe.lapp.viewmodels.EvolutionViewModel;
import com.reframe.lapp.viewmodels.ProfileViewModel;
import com.reframe.lapp.viewmodels.TutorialsViewModel;
import com.reframe.lapp.viewmodels.evaluations.EvaluationItemViewModel;
import com.reframe.lapp.viewmodels.evaluations.ProfessorEvaluationDetailModel;
import com.reframe.lapp.viewmodels.evaluations.ProfessorEvaluationItemViewModel;
import com.reframe.lapp.viewmodels.tutorials.TutorialItemViewModel;
import com.reframe.lapp.viewmodels.tutorials.VideoItemViewModel;
import com.reframe.lapp.viewmodels.videofeedback.VideoFeedbackItemViewModel;

public class ViewProviders {

    @NonNull
    public static ViewProvider getItemListing() {
        return vm -> {
            if (vm instanceof TutorialsViewModel) {
                return R.layout.tutorials_view;
            } else if (vm instanceof TutorialItemViewModel) {
                return R.layout.tutorial_item;
            } else if (vm instanceof EvaluationsViewModel) {
                return R.layout.evaluations_view;
            } else if (vm instanceof EvaluationsProfessorViewModel) {
                    return R.layout.evaluations_professor_view;
            } else if (vm instanceof EvolutionViewModel) {
                return R.layout.evolution_view;
            } else if (vm instanceof ProfileViewModel) {
                return R.layout.profile_view;
            } else if (vm instanceof VideoItemViewModel) {
                return R.layout.video_item;
            } else if (vm instanceof VideoFeedbackItemViewModel) {
                return R.layout.video_feedback_item;
            } else if (vm instanceof EvaluationItemViewModel) {
                return R.layout.evaluation_item;
            } else if (vm instanceof ProfessorEvaluationItemViewModel) {
                return R.layout.evaluation_professor_item;
            }else if (vm instanceof ProfessorEvaluationDetailModel) {
                return R.layout.professor_evaluation_detail;
            }
            return 0;
        };
    }
}
