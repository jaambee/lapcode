package com.reframe.lapp.base;

import android.content.DialogInterface;

import com.liulishuo.filedownloader.FileDownloadListener;
import com.reframe.lapp.models.AudioFeedback;
import com.reframe.lapp.models.Evaluation;
import com.reframe.lapp.models.FeedBackResponse;
import com.reframe.lapp.models.FeedbackQuery;
import com.reframe.lapp.models.ImageUpload;
import com.reframe.lapp.models.ProfessorEvaluation;
import com.reframe.lapp.models.ProfessorScore;
import com.reframe.lapp.models.Tutorial;
import com.reframe.lapp.models.VideoFeedback;

import java.io.File;
import java.util.List;

import rx.Observable;

/**
 * Created by Aldo on 20-03-2017 to Lapp.
 */

public interface Navigator {

    void openLogin();

    void doLogin(String email, String password);

    void openMain();

    void openTeacher();

    void openSettings();

    void openVideo(Tutorial tutorial);

    void openVideo(VideoFeedback videoFeedback);

    void openVideoFeedback(VideoFeedback videoFeedback);

    void openList(List<Tutorial> items);

    void openEvaluation(Evaluation evaluation);

    void openEvaluation(ProfessorEvaluation evaluation);

    Observable<VideoFeedback>  openFeedbackList(List<VideoFeedback> items);

    void selectVideoFeedback(VideoFeedback video);

    void selectFeedback(FeedBackResponse feedbackQuery);

    void evaluate(ProfessorEvaluation evaluation);

    void evaluateProfessor(ProfessorScore score);

    Observable<FeedBackResponse> openFeedbackEditor(ProfessorEvaluation evaluation);

    void openEvolution();

    void openProfile();

	void changeEmail();

	void changePassword();

    //Observable<FeedBackResponse> addFeedback(String exerciseId);

    Observable<AudioFeedback> recordAudio();

    void backAction();

    void doLogout();

    void closeActivity();

    void downloadFile(String url, String filename, String id, FileDownloadListener listener);

    void showToast(String message);

    void showSweetAlert(String title, String message);
    Observable<ImageUpload> selectImage();
}
