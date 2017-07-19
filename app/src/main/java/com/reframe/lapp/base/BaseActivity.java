package com.reframe.lapp.base;

/**
 * Created by Aldo on 23-11-2016.
 */

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.manaschaudhari.android_mvvm.MvvmActivity;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.pixplicity.easyprefs.library.Prefs;
import com.reframe.lapp.R;
import com.reframe.lapp.constants.Constants;
import com.reframe.lapp.models.Audio;
import com.reframe.lapp.models.AudioFeedback;
import com.reframe.lapp.models.Evaluation;
import com.reframe.lapp.models.FeedBackResponse;
import com.reframe.lapp.models.ImageUpload;
import com.reframe.lapp.models.ProfessorEvaluation;
import com.reframe.lapp.models.Tutorial;
import com.reframe.lapp.models.User;
import com.reframe.lapp.models.VideoFeedback;
import com.reframe.lapp.network.LappService;
import com.reframe.lapp.viewmodels.tutorials.VideoItemViewModel;
import com.reframe.lapp.views.EvaluationView;
import com.reframe.lapp.views.Landing;
import com.reframe.lapp.views.ListView;
import com.reframe.lapp.views.MainActivity;
import com.reframe.lapp.views.PlayerView;
import com.reframe.lapp.views.ProfessorEvaluationView;
import com.reframe.lapp.views.Settings;
import com.reframe.lapp.views.TeacherActivity;
import com.reframe.lapp.views.VideoFeedbackPlayerView;
import com.reframe.lapp.views.VideoListView;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.lang.reflect.Type;
import java.util.List;

import cafe.adriel.androidaudiorecorder.AndroidAudioRecorder;
import cafe.adriel.androidaudiorecorder.model.AudioChannel;
import cafe.adriel.androidaudiorecorder.model.AudioSampleRate;
import cafe.adriel.androidaudiorecorder.model.AudioSource;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import gun0912.tedbottompicker.TedBottomPicker;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;
import rx.subscriptions.Subscriptions;
import rx_activity_result.Result;
import rx_activity_result.RxActivityResult;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class BaseActivity extends MvvmActivity {

    private static final String TAG = "BASE_ACTIVITY";
    private static int RECORD_REQUEST_CODE = 03;
    private static int VIDEO_FEEDBACK_REQUEST_CODE = 04;
    private static int VIDEO_LIST_REQUEST_CODE = 05;
    //container for batch RxJava unsubscription
    protected CompositeSubscription mCompositeSubscription;
    private ProgressDialog progress;
    private RxPermissions rxPermissions;
    private PublishSubject<Audio> audio;
    private PublishSubject<VideoFeedback> videoFeedback = PublishSubject.create();
    private PublishSubject<FeedBackResponse> feedback = PublishSubject.create();
    @NonNull
    protected Navigator getNavigator() {
        return new Navigator() {
            @Override
            public void openLogin() {

            }

            @Override
            public void doLogin(String email, String password) {
                mCompositeSubscription.add(
                        LappService.login(email, password).subscribe(accessToken -> {
                            if(!Prefs.getString(Constants.LAPP_TOKEN, "").equals(""))
                                mCompositeSubscription.add(LappService.getMe().subscribe(user -> {
                                    Prefs.putString(Constants.USER, new Gson().toJson(user, User.class));
                                    if(user.getRole().equals(Constants.STUDENT)) {
                                        openMain();
                                        Log.d("LOGIN>", getString(R.string.student));
                                    } else if (user.getRole().equals(Constants.PROFESSOR)) {
                                        openTeacher();
                                        Log.d("LOGIN>", getString(R.string.professor));
                                    }
                                }));
                        }, throwable -> showToast(throwable.getMessage()))
                );
            }

            @Override
            public void openMain() {
                navigate(MainActivity.class);
            }

            @Override
            public void openTeacher() {
                navigate(TeacherActivity.class);
            }

            @Override
            public void openSettings() {
                navigate(Settings.class);
            }

            @Override
            public void openVideo(Tutorial tutorial) {
                navigate(PlayerView.class, tutorial);
            }

            @Override
            public void openVideo(VideoFeedback videoFeedback) {
                navigate(VideoFeedbackPlayerView.class, videoFeedback);
            }

            @Override
            public void openVideoFeedback(VideoFeedback videoFeedback) {
                mCompositeSubscription.add(
                    navigateWithResult(VideoFeedbackPlayerView.class, videoFeedback, VIDEO_FEEDBACK_REQUEST_CODE)
                        .subscribe(result-> {
                            VideoFeedback videoSelected = new Gson().fromJson(result.data().getData().toString(), VideoFeedback.class);
                            Log.d(TAG, "RESULT VIDEO SELECTED>".concat(new Gson().toJson(videoSelected)));
                            selectVideoFeedback(videoSelected);
                        })
                );
            }

            @Override
            public void openList(List<Tutorial> items) {
                navigateList(ListView.class, items);
            }

            @Override
            public Observable<VideoFeedback> openFeedbackList(List<VideoFeedback> items) {
                return
                        navigateListWithResult(VideoListView.class, items, VIDEO_LIST_REQUEST_CODE)
                                .map(activityResult-> {
                                    Intent result = activityResult.data();
                                    Log.d(TAG, "RESULT_LISTA>".concat(result.getData().toString()));
                                    VideoFeedback callback = new Gson().fromJson(result.getData().toString(), VideoFeedback.class);
                                    return callback;
                                }).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
            }

            @Override
            public void selectVideoFeedback(VideoFeedback video) {
                Intent result = new Intent();
                result.setData(Uri.parse(new Gson().toJson(video)));
                setResult(RESULT_OK, result);
                finish();
            }

            @Override
            public void openEvaluation(Evaluation evaluation) {
                navigate(EvaluationView.class, evaluation);
            }

            @Override
            public void openEvaluation(ProfessorEvaluation evaluation) {
                navigate(ProfessorEvaluationView.class, evaluation);
            }

            @Override
            public void openEvolution() {
                Log.d(TAG, "openEvolution");
            }

            @Override
            public void openProfile() {
                Log.d(TAG, "openProfile");
            }

            @Override
            public void changeEmail() {
                DialogPlus dialog = DialogPlus.newDialog(BaseActivity.this)
                        .setContentHolder(new ViewHolder(R.layout.dialog_email_change))
                        .setExpanded(true, 650)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();
                TextView email      = (TextView) dialog.getHolderView().findViewById(R.id.email);
                TextView confirm    = (TextView) dialog.getHolderView().findViewById(R.id.confirm);
                Button save         = (Button)   dialog.getHolderView().findViewById(R.id.save);

                save.setOnClickListener(view -> {
                    String emailText = email.getText().toString();
                    String confirmText = confirm.getText().toString();

                    if(emailText.equals("") || confirmText.equals("")) {
                        Toast.makeText(BaseActivity.this, "Debes completar todos los campos", Toast.LENGTH_SHORT).show();
                    } else if(!emailText.equals(confirmText)) {
                        Toast.makeText(BaseActivity.this, "Ambos campos deben coincidir", Toast.LENGTH_SHORT).show();
                    } else {
                        mCompositeSubscription.add(
                                LappService.changeEmail(emailText).subscribe(successResponse -> {
                                    if(successResponse != null && successResponse.success) {
                                        Toast.makeText(BaseActivity.this, "Email cambiado con éxito", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText(BaseActivity.this, "Ha ocurrido un error, intente nuevamente", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                }, throwable -> {
                                    Toast.makeText(BaseActivity.this, "Ha ocurrido un error, intente nuevamente", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }));
                    }
                });
                dialog.show();
            }

            @Override
            public void changePassword() {
                DialogPlus dialog = DialogPlus.newDialog(BaseActivity.this)
                        .setContentHolder(new ViewHolder(R.layout.dialog_password_change))
                        .setExpanded(true, 800)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();

                TextView oldPassword           = (TextView) dialog.getHolderView().findViewById(R.id.old_password);
                TextView newPassword           = (TextView) dialog.getHolderView().findViewById(R.id.new_password);
                TextView confirmPassword       = (TextView) dialog.getHolderView().findViewById(R.id.confirm_password);
                Button save                    = (Button)   dialog.getHolderView().findViewById(R.id.save);

                save.setOnClickListener(view -> {
                    String oldText = oldPassword.getText().toString();
                    String newText = newPassword.getText().toString();
                    String confirmText = confirmPassword.getText().toString();

                    if(oldText.equals("") || newText.equals("") || confirmText.equals("")) {
                        Toast.makeText(BaseActivity.this, "Debes completar todos los campos", Toast.LENGTH_SHORT).show();
                    } else if(!newText.equals(confirmText)) {
                        Toast.makeText(BaseActivity.this, "Ambas contraseñas deben coincidir", Toast.LENGTH_SHORT).show();
                    } else {
                        mCompositeSubscription.add(
                                LappService.changePassword(oldText, newText).subscribe(successResponse -> {
                                    if(successResponse != null && successResponse.success) {
                                        Toast.makeText(BaseActivity.this, "Contraseña cambiada con éxito", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText(BaseActivity.this, "Ha ocurrido un error, intente nuevamente", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                }, throwable -> {
                                    Toast.makeText(BaseActivity.this, "Ha ocurrido un error, intente nuevamente", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }));
                    }
                });
                dialog.show();
            }

            @Override
            public Observable<FeedBackResponse> addFeedback(String exerciseId) {
	            FeedBackResponse result = new FeedBackResponse();
                    return LappService.getVideoFeedbackList(exerciseId)
                        .flatMap(this::openFeedbackList)
                        .flatMap(videoFeedback -> {
	                        result.setVideo(videoFeedback.getId());
                            result.setVideoUrl(videoFeedback.getUrl());
	                        return recordAudio();
                        })
                        .map(audioFeedback -> {
	                        result.setAudio(audioFeedback.getId());
                            result.setAudioUrl(audioFeedback.getUrl());
	                       return result;
                        })
                        .doOnNext(feedback->Log.d(TAG, "Feedback SELECTED=audio>".concat(feedback.getAudio()).concat(",video>").concat(feedback.getVideo())));
            }

            @Override
            public Observable<AudioFeedback> recordAudio() {
                String filePath = Environment.getExternalStorageDirectory() + "/recorded_audio.wav";
                int color = getResources().getColor(R.color.colorPrimaryDark);
                audio = PublishSubject.create();
                AndroidAudioRecorder.with(BaseActivity.this)
                        // Required
                        .setFilePath(filePath)
                        .setColor(color)
                        .setRequestCode(RECORD_REQUEST_CODE)

                        // Optional
                        .setSource(AudioSource.MIC)
                        .setChannel(AudioChannel.STEREO)
                        //.setSampleRate(AudioSampleRate.HZ_48000)
                        //.setAutoStart(true)
                        .setKeepDisplayOn(true)

                        // Start recording
                        .record();

                audio.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
                return audio.flatMap(audioCreated -> LappService.createAudio(audioCreated.getUrl()));

            }

            @Override
            public void backAction() {
                onBackPressed();
            }

            @Override
            public void doLogout() {
                Prefs.clear();
                navigate(Landing.class);
            }

            @Override
            public void closeActivity() {
                finish();
            }

            @Override
            public Observable<ImageUpload> selectImage() {
                progress = new ProgressDialog(BaseActivity.this);
                return Observable.create(subscriber -> {
                    mCompositeSubscription.add(rxPermissions.request(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(hasPermission -> {
                                if(hasPermission) {
                                    new TedBottomPicker.Builder(getApplicationContext())
                                            .setOnImageSelectedListener(uri -> {
                                                progress.setMessage("Subiendo imagen");
                                                progress.show();
                                                Observable<ImageUpload> uploadImage = LappService.uploadImage(uri.getPath());
                                                uploadImage.doOnError(Throwable::printStackTrace).doOnCompleted(() -> progress.dismiss()).subscribe(subscriber);
                                            })
                                            .create().show(getSupportFragmentManager());
                                } else
                                    Toast.makeText(getApplicationContext(),"Debe autorizar el uso de la cámara", Toast.LENGTH_SHORT);
                            }));
                });
            }

            private void copyToClipboard(String text) {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Texto copiado", text);
                clipboard.setPrimaryClip(clip);
            }

            private void showToast(String message) {
                Toast.makeText(BaseActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            private void navigate(Class<?> destination) {
                Intent intent = new Intent(BaseActivity.this, destination);
                startActivity(intent);
            }

            private void navigate(Class<?> destination, Object data) {

                Intent intent = new Intent(BaseActivity.this, destination);
                intent.putExtra(Constants.LAPP_EXTRA, (new Gson().toJson(data)));
                startActivity(intent);
            }

            private void navigateList(Class<?> destination, Object data) {

                Type listType = new TypeToken<List<VideoItemViewModel>>(){}.getType();
                Intent intent = new Intent(BaseActivity.this, destination);
                intent.putExtra(Constants.LAPP_EXTRA, (new Gson().toJson(data, listType)));
                startActivity(intent);

            }

            private Observable<Result<BaseActivity>> navigateWithResult(Class<?> destination, Object data, int requestCode) {

                Intent intent = new Intent(BaseActivity.this, destination);
	            intent.putExtra(Constants.LAPP_EXTRA, (new Gson().toJson(data)));

                return RxActivityResult.on(BaseActivity.this)
                        .startIntent(intent)
                        .doOnNext(activityResult -> {
                            Intent result = activityResult.data();
                            if(result != null && result.getData() != null) {
                                Log.d(TAG, "RESULT ITEM>".concat(result.getData().toString()));
                            }
                        });
            }

            private Observable<Result<BaseActivity>> navigateListWithResult(Class<?> destination, Object data, int requestCode) {

                Type listType = new TypeToken<List<VideoItemViewModel>>(){}.getType();
                Intent intent = new Intent(BaseActivity.this, destination);
                intent.putExtra(Constants.LAPP_EXTRA, (new Gson().toJson(data, listType)));

                return RxActivityResult.on(BaseActivity.this)
                        .startIntent(intent);

            }
        };
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RECORD_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "onActivityResult: AUDIO RECORD OK");
                mCompositeSubscription.add(
                        LappService.uploadMedia(Environment.getExternalStorageDirectory() + "/recorded_audio.wav")
                                .map(media -> {
                                   Audio audio = new Audio();
                                    audio.setUrl(media.getLocation());
                                    return audio;
                                })
                                .subscribe(audio));
            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "onActivityResult: AUDIO RECORD CANCELLED");
            }
        }/* else if (requestCode == VIDEO_FEEDBACK_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "onActivityResult: VIDEO FEEDBACK SELECTION OK>".concat(data.getData().toString()));

            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "onActivityResult: AUDIO RECORD CANCELLED");
            }
        } else if(requestCode == VIDEO_LIST_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
            }
        }*/

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rxPermissions = new RxPermissions(BaseActivity.this);
        mCompositeSubscription = Subscriptions.from();
        mCompositeSubscription.add(rxPermissions
                .request(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted-> {}));
    }

    @Override
    public void onBackPressed() {
        if(JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mCompositeSubscription != null && !mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
