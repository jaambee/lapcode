package com.reframe.lapp.viewmodels.evaluations;

import android.databinding.ObservableField;
import android.support.annotation.Nullable;
import android.util.Log;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.manaschaudhari.android_mvvm.ViewModel;
import com.reframe.lapp.base.Navigator;
import com.reframe.lapp.models.ProfessorEvaluation;
import com.reframe.lapp.utils.Utils;

import java.io.File;

import rx.functions.Action0;

/**
 * Created by Aldo on 24-03-2017 to Lapp.
 */

public class ProfessorEvaluationItemViewModel implements ViewModel {
    public final String name;
    public final String username;
    public final String thumb;
    public final String image;
    public final String date;
    public final String level;
    public final Boolean approved;
    public final Boolean evaluated;
    public final ObservableField<Integer> progress = new ObservableField<>(0);
    public final ObservableField<Boolean> isCompleted = new ObservableField<>(false);

    private FileDownloadListener listener;
    @Nullable
    public final Action0 openDetails;

    public ProfessorEvaluationItemViewModel(Navigator navigator, ProfessorEvaluation evaluation) {
        username = evaluation.getStudentName();
        name = evaluation.getExercise();
        thumb = evaluation.getThumbnail();
        image = evaluation.getPicture();
        date = Utils.getDateParsed(evaluation.getCreatedAt());
        level = Utils.getParsedLevel(evaluation.getLevelName()).toUpperCase();
        approved = evaluation.getApproved();
        evaluated = evaluation.getEvaluated();

        this.openDetails = ()-> {
            Log.d("COMPLETED_STATUS",isCompleted.get().toString());
            if(!isCompleted.get())
                navigator.openEvaluation(evaluation);
            else
                navigator.showToast("Espere un momento, mientras se descarga la evaluación");
        };
        listener = new FileDownloadListener() {
            @Override
            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                Log.d("DOWNLOAD_PENDING", "true");
            }

            @Override
            protected void started(BaseDownloadTask task) {
                Log.d("DOWNLOAD_STARTED", "true");
            }

            @Override
            protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                Log.d("DOWNLOAD_CONNECTED", "true");
            }

            @Override
            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                int current = Math.round(soFarBytes*100/totalBytes);
                Log.d("DOWNLOAD_PROGRESS", "progress: ".concat(String.valueOf(current)));
                progress.set(current);
            }

            @Override
            protected void blockComplete(BaseDownloadTask task) {
            }

            @Override
            protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
                ex.printStackTrace();
            }

            @Override
            protected void completed(BaseDownloadTask task) {
                Log.d("DOWNLOAD_COMPLETED", "true");
                isCompleted.set(true);
            }

            @Override
            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                Log.d("DOWNLOAD_PAUSED", "true");
            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                e.printStackTrace();
            }

            @Override
            protected void warn(BaseDownloadTask task) {
                Log.d("DOWNLOAD_WARN", "true");
            }
        };

        Log.d("EVALUATION_DOWNLOADX ", evaluation.getVideo());

        if(evaluation.getVideo()!=null && !evaluation.getEvaluated())
            navigator.downloadFile(evaluation.getVideo(), evaluation.getId(), evaluation.getId(), listener);
    }


}
