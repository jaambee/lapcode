package com.reframe.lapp.views;

import android.annotation.SuppressLint;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.google.gson.Gson;
import com.manaschaudhari.android_mvvm.ViewModel;
import com.orhanobut.dialogplus.DialogPlus;
import com.pixplicity.easyprefs.library.Prefs;
import com.reframe.lapp.Lapp;
import com.reframe.lapp.R;
import com.reframe.lapp.adapters.ExerciseAdapter;
import com.reframe.lapp.adapters.FeedbackAdapter;
import com.reframe.lapp.adapters.VideoFeedbackitemAdapter;
import com.reframe.lapp.base.BaseActivity;
import com.reframe.lapp.constants.Constants;
import com.reframe.lapp.models.Audio;
import com.reframe.lapp.models.AudioFeedback;
import com.reframe.lapp.models.Exercise;
import com.reframe.lapp.models.FeedBackResponse;
import com.reframe.lapp.models.FeedbackQuery;
import com.reframe.lapp.models.Group;
import com.reframe.lapp.models.ProfessorEvaluation;
import com.reframe.lapp.models.TimeRefer;
import com.reframe.lapp.models.Video;
import com.reframe.lapp.models.VideoFeedback;
import com.reframe.lapp.network.LappService;
import com.reframe.lapp.utils.RxBus;
import com.reframe.lapp.viewmodels.FeedbackEditorViewModel;

import java.io.File;
import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import omrecorder.AudioRecordConfig;
import omrecorder.OmRecorder;
import omrecorder.PullTransport;
import omrecorder.PullableSource;
import omrecorder.Recorder;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FeedbackEditorView extends BaseActivity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener{

    private static final String TAG = "FEEDBACK_EDITOR";
    private ProfessorEvaluation evaluation;
    private SurfaceView video;
    private LinearLayout vumeter;
    private ImageView recordButton;
    private ImageView feedbackSelect;
    private ImageView deleteAudio;
    private Button addFeedback;
    private TextView recordText;
    private TextView videoFeedbackDescription;
    private TextView beginLabel;
    private TextView endLabel;
    private VideoFeedback videoFeedbackSelected;
    private SurfaceHolder surfaceHolder;

    private int position = 0;

    private Boolean isRecording = false;
    private Boolean isPlaying = false;
    private Boolean isPrepared = false;
    private Boolean iHaveRecord = false;

    private DialogPlus dialog;

    private MediaPlayer mediaPlayer;
    private MediaPlayer audioPlayer;

    private CrystalRangeSeekbar range;

    private SeekBar audioBar;
    private int currentMin;
    private int currentMax;
    private Recorder recorder;
    private SweetAlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    protected ViewModel createViewModel() {
        setupRange();
        setupAudioRecord();
        setupFeedback();
        evaluation = new Gson().fromJson(getIntent().getStringExtra(Constants.LAPP_EXTRA), ProfessorEvaluation.class);
        return new FeedbackEditorViewModel(getNavigator());
    }

    private void setupFeedback() {
        FeedBackResponse response = new FeedBackResponse();
        feedbackSelect = (ImageView) findViewById(R.id.feedbackSelect);
        videoFeedbackDescription = (TextView) findViewById(R.id.videoFeedbackDescription);
        addFeedback = (Button) findViewById(R.id.add_feedback);
        addFeedback.setEnabled(false);
        addFeedback.setOnClickListener(v -> {
            mCompositeSubscription.add(
                    LappService.uploadMedia(file().getPath())
                        .flatMap(media -> {
                            Log.d(TAG, media.toString());
                            return LappService.createAudio(media.getLocation())
                                .flatMap(audioFeedback -> {
                                    FeedbackQuery query = new FeedbackQuery();
                                    TimeRefer time = new TimeRefer();
                                    Audio audio = new Audio();
                                    Video video = new Video();
                                    audio.setUrl(audioFeedback.getUrl());
                                    video.setUrl(this.videoFeedbackSelected.getUrl());
                                    response.setAudio(audio);
                                    response.setVideo(video);
                                    query.setAudio(audioFeedback.getId());
                                    query.setVideo(this.videoFeedbackSelected.getId());

                                    time.setBeginCut(range.getSelectedMinValue().intValue());
                                    time.setEndCut(range.getSelectedMaxValue().intValue());
                                    query.setTimeRefer(time);
                                    Log.d("VIDEO_FEEDBACK", new Gson().toJson(query));
                                    return LappService.addFeedback(evaluation.getId(), query);

                                }); })

                    .subscribe(successResponse -> {
                        if(successResponse.success) {
                            getNavigator().selectFeedback(response);
                        }
                    }, Throwable::printStackTrace)
            );
        });
        feedbackSelect.setOnClickListener(v -> {
            Log.d(TAG, "setupFeedback: CLICK");
            LappService.getProfessorVideoFeedbackList(evaluation.getLevelId())
                    .take(1)
                    .subscribe(videoFeedbacks -> {
                                final Group[] selected = new Group[1];
                        dialog = DialogPlus.newDialog(FeedbackEditorView.this)
                                .setAdapter(new FeedbackAdapter(FeedbackEditorView.this, videoFeedbacks))
                                .setCancelable(true)
                                .setExpanded(false)
                                .setOnItemClickListener((dx, item, view, pos) -> {
                                    Log.d(TAG, "setupFeedback: ITEM CLICK"+(new Gson().toJson(item)));
                                    selected[0] = (Group) item;
                                    dx.dismiss();
                                })
                                .setOnDismissListener(dialog1 -> {
                                    if(selected[0]!=null)
                                        openFeedbackList(selected[0]);
                                })
                                .create();
                    }, Throwable::printStackTrace,
                            () -> {
                                dialog.show();
                            });
        });
    }

    private void openFeedbackList(Group group) {

        final Exercise[] selected = new Exercise[1];
        DialogPlus d = DialogPlus.newDialog(FeedbackEditorView.this)
                .setAdapter(new ExerciseAdapter(FeedbackEditorView.this, group.getExercises()))
                .setCancelable(true)
                .setExpanded(false)
                .setOnItemClickListener((dp, item, view, pos) -> {
                    Log.d(TAG, "setupFeedback: ExERCISE CLICK"+String.valueOf(pos));
                    selected[0] = (Exercise) item;
                    dp.dismiss();
                })
                .setOnDismissListener(dialog1 -> {
                    if(selected[0] != null)
                        openVideoList(selected[0]);
                })
                .create();
        d.show();
    }

    private void openVideoList(Exercise exercise) {
        final VideoFeedback[] selected = new VideoFeedback[1];
        DialogPlus d = DialogPlus.newDialog(FeedbackEditorView.this)
                .setAdapter(new VideoFeedbackitemAdapter(FeedbackEditorView.this, exercise.getVideoFeedbacks()))
                .setCancelable(true)
                .setExpanded(false)
                .setOnItemClickListener((dp, item, view, pos) -> {
                    Log.d(TAG, "setupFeedback: VideoFeedback CLICK"+String.valueOf(pos));
                    selected[0] = (VideoFeedback) item;
                    dp.dismiss();
                })
                .setOnDismissListener(dp-> {
                    if(selected[0]!=null)
                    setVideo(selected[0]);
                })
                .create();
        d.show();
    }

    private void setVideo(VideoFeedback video) {
        this.videoFeedbackSelected = video;
        Glide.with(FeedbackEditorView.this)
                .load(video.getThumbnail())
                .centerCrop()
                .into(feedbackSelect);
        addFeedback.setEnabled(true);
        videoFeedbackDescription.setText(video.getTitle());
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupAudioRecord() {


        if(recorder == null)
            initializeRecorder();

        deleteAudio = (ImageView) findViewById(R.id.deleteAudio);
        audioBar = (SeekBar) findViewById(R.id.audioBar);
        vumeter = (LinearLayout) findViewById(R.id.vumeter);
        vumeter.setAlpha(0);

        deleteAudio.setVisibility(View.GONE);
        audioBar.setVisibility(View.GONE);

        recordButton = (ImageView) findViewById(R.id.recordBtn);
        recordText = (TextView) findViewById(R.id.recordDescription);

        audioBar.setProgress(0);
        audioBar.setMax(100);

        recordButton.setOnTouchListener((v1, eventx) -> {
            if(eventx.getAction() == MotionEvent.ACTION_DOWN) {
                animateOver(recordButton);
                return true;
            } else if(eventx.getAction() == MotionEvent.ACTION_UP) {
                animateOutside(recordButton);
                if(isRecording) {
                    recordText.setVisibility(View.GONE);
                    vumeter.setVisibility(View.GONE);
                    audioBar.setVisibility(View.VISIBLE);
                    deleteAudio.setVisibility(View.VISIBLE);
                    deleteAudio.setOnTouchListener((v2, event) -> {

                        if(event.getAction() == MotionEvent.ACTION_DOWN) {
                            animateOver(deleteAudio);
                            return true;
                        } else if(event.getAction() == MotionEvent.ACTION_UP && event.getAction()!=MotionEvent.ACTION_OUTSIDE) {
                            animateOutside(deleteAudio);
                            //audioPlayer.release();

                            alert = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("¿Está seguro de borra el audio?")
                                    .setContentText("No se podrá recuperar!")
                                    .setConfirmClickListener(sDialog -> {
                                        if(file().delete()) {
                                            sDialog
                                                    .setTitleText("Eliminado!")
                                                    .setContentText("El audio ha sido eliminado!")
                                                    .setConfirmText("OK")
                                                    .setConfirmClickListener(sad-> {
                                                        animateRecorderState("out");
                                                        isRecording = false;
                                                        iHaveRecord = false;
                                                        isPlaying = false;
                                                        initializeRecorder();
                                                        sDialog.dismissWithAnimation();
                                                    })
                                                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                        }
                                    });
                            alert.setCancelable(true);
                            alert.setCanceledOnTouchOutside(true);
                            alert.show();
                            return true;
                        }
                        return false;
                    });

                    recordButton.setImageResource(R.drawable.ic_play);
                    isRecording = false;
                    try {
                        recorder.stopRecording();
                        prepareAudio();
                        animateRecorderState("enter");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    iHaveRecord = true;
                } else if(iHaveRecord) {

                    Handler mHandler = new Handler();
                    if(audioPlayer == null) {
                        audioPlayer = new MediaPlayer();
                        audioPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    }
//Make sure you update Seekbar on UI thread
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            if(audioPlayer != null){

                                try {
                                    long totalDuration = audioPlayer.getDuration();
                                    long currentDuration = audioPlayer.getCurrentPosition();

                                    Double percentage = (double) 0;

                                    long currentSeconds = (int) (currentDuration / 1000);
                                    long totalSeconds = (int) (totalDuration / 1000);

                                    // calculating percentage
                                    percentage =(((double)currentSeconds)/totalSeconds)*100;

                                    // Updating progress bar
                                    int progress = percentage.intValue();
                                    //Log.d("Progress", ""+progress);
                                    audioBar.setProgress(progress);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            mHandler.postDelayed(this, 100);
                        }
                    });
                    if (isPlaying) {
                        isPlaying = false;
                        audioPlayer.pause();
                        recordButton.setImageResource(R.drawable.ic_play);
                    } else {
                        isPlaying = true;
                        audioPlayer.start();
                        recordButton.setImageResource(R.drawable.ic_pause);
                    }

                }
                else {
                    recordText.setText("Grabando...");
                    if(recorder == null)
                        initializeRecorder();
                    recorder.startRecording();
                    isRecording = true;
                }
                return true;
            }
            return false;
        });

        audioBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                if(audioPlayer != null){
                    int totalDuration = audioPlayer.getDuration();
                    int currentDuration = 0;
                    totalDuration = (int) (totalDuration / 1000);
                    currentDuration = (int) ((((double)seekBar.getProgress()) / 100) * totalDuration);
                    // return current duration in milliseconds
                    int currentPosition = currentDuration * 1000;
                    // forward or backward to certain seconds
                    audioPlayer.seekTo(currentPosition);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void initializeRecorder() {
        recorder = OmRecorder.wav(
                new PullTransport.Default(mic(), audioChunk -> animateVoice((float) (audioChunk.maxAmplitude() / 200.0))), file());
    }
    private void prepareAudio() {

        audioPlayer = new MediaPlayer();
        audioPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        audioPlayer.setOnCompletionListener(mp -> {
            isPlaying = false;
            audioPlayer.seekTo(0);
            audioPlayer.pause();
            recordButton.setImageResource(R.drawable.ic_play);
        });
        try {
            audioPlayer.setDataSource(file().getPath());
            audioPlayer.prepareAsync();
            isPrepared = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void animateVoice(final float maxPeak) {
        vumeter.animate().scaleY(maxPeak).alpha(0.2f+maxPeak).setDuration(10).start();
    }

    private void animateOver(View view) {
        view
                .animate()
                .scaleY(1.3f)
                .scaleX(1.3f)
                .setInterpolator(new OvershootInterpolator())
                .alpha(0.6f)
                .setDuration(300)
                .start();
    }

    private void animateOutside(View view) {
        view
                .animate()
                .scaleY(1f)
                .scaleX(1f)
                .setInterpolator(new OvershootInterpolator())
                .alpha(1f)
                .setDuration(300)
                .start();
    }

    private void animateOut(View view) {
        view
                .animate()
                .scaleY(0f)
                .scaleX(0f)
                .setInterpolator(new OvershootInterpolator())
                .alpha(0f)
                .setDuration(300)
                .withEndAction(() -> view.setVisibility(View.GONE))
                .start();
    }

    private void animateIn(View view, float endAlpha) {
        view
                .animate()
                .withStartAction(() -> {
                    view.setVisibility(View.VISIBLE);
                    view.setAlpha(0f);
                })
                .scaleY(1f)
                .scaleX(1f)
                .setInterpolator(new OvershootInterpolator())
                .alpha(endAlpha)
                .setDuration(300)
                .start();
    }

    private void animateRecorderState(String state) {
        if(state.equals("enter")) {
            animateOut(recordText);
            animateOut(vumeter);
            animateIn(deleteAudio, 1f);
            animateIn(audioBar, 1f);
            recordButton.setImageResource(R.drawable.ic_play);
        } else if(state.equals("out")) {
            recordText.setText("Haz click para grabar el audio sobre el Feedback que deseas enviar");
            animateIn(recordText, 1f);
            animateIn(vumeter, 0f);
            animateOut(deleteAudio);
            animateOut(audioBar);
            recordButton.setImageResource(R.drawable.ic_record_voice);
        }
    }

    private void setupRange() {
        range = (CrystalRangeSeekbar) findViewById(R.id.range);
        video = (SurfaceView) findViewById(R.id.video);
        beginLabel = (TextView) findViewById(R.id.beginCutLabel);
        endLabel = (TextView) findViewById(R.id.endCutLabel);
        surfaceHolder = video.getHolder();

        surfaceHolder.addCallback(this);
        range.setEnabled(false);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.feedback_editor;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

        range.setEnabled(true);
        range.setMinValue(0);
        currentMin = 0;
        range.setMaxValue(mediaPlayer.getDuration());
        currentMax = mediaPlayer.getDuration();
        video.setOnClickListener(v -> {
            if(mediaPlayer.isPlaying())
                mediaPlayer.pause();
            else
                mediaPlayer.start();
        });
        mediaPlayer.seekTo(position);
        if(position == 0) {
            mediaPlayer.start();
        }

        range.setSteps(1);
        range.setOnRangeSeekbarChangeListener((minValue, maxValue) -> {
            mediaPlayer.pause();
            float beginSec = minValue.floatValue()/1000;
            float endSec = maxValue.floatValue()/1000;
            beginLabel.setText(String.valueOf(beginSec).concat(" s"));
            endLabel.setText(String.valueOf(endSec).concat(" s"));

            if(currentMin != minValue.intValue()) {
                mediaPlayer.seekTo(minValue.intValue());
                currentMin = minValue.intValue();
            } else if(currentMax != maxValue.intValue()) {
                mediaPlayer.seekTo(maxValue.intValue());
                currentMax = maxValue.intValue();
            }
        });
    }

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDisplay(surfaceHolder);
            mediaPlayer.setDataSource(this, Uri.parse("android.resource://" + getPackageName() + "/"+ R.raw.test));
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private PullableSource mic() {
	    return new PullableSource.AutomaticGainControl(
            new PullableSource.NoiseSuppressor(
                new PullableSource.Default(
                    new AudioRecordConfig.Default(
                        MediaRecorder.AudioSource.MIC, AudioFormat.ENCODING_PCM_16BIT,
                        AudioFormat.CHANNEL_IN_MONO, 44100
                    )
                )
            )
	    );
    }

    @NonNull private File file() {
        return new File(getApplicationContext().getFilesDir(), "audio.wav");
    }
}
