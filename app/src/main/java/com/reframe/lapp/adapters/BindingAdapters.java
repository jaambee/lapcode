package com.reframe.lapp.adapters;

/**
 * Created by Aldo on 17-11-2016.
 */

import android.app.Activity;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.db.chart.model.LineSet;
import com.db.chart.renderer.AxisRenderer;
import com.db.chart.view.LineChartView;
import com.eugeneek.smilebar.SmileBar;
import com.github.florent37.viewanimator.ViewAnimator;
import com.manaschaudhari.android_mvvm.ViewModel;
import com.manaschaudhari.android_mvvm.adapters.ViewModelBinder;
import com.manaschaudhari.android_mvvm.adapters.ViewProvider;
import com.manaschaudhari.android_mvvm.utils.BindingUtils;
import com.ramotion.foldingcell.FoldingCell;
import com.reframe.lapp.BR;
import com.reframe.lapp.R;
import com.reframe.lapp.base.Navigator;
import com.reframe.lapp.constants.Constants;
import com.reframe.lapp.models.Datum;
import com.reframe.lapp.models.DatumChart;
import com.reframe.lapp.models.Evaluation;
import com.reframe.lapp.models.EvolutionData;
import com.reframe.lapp.models.Exercise;
import com.reframe.lapp.models.FeedBack;
import com.reframe.lapp.models.FeedBackResponse;
import com.reframe.lapp.models.ProfessorEvaluation;
import com.reframe.lapp.models.Scale;
import com.reframe.lapp.viewmodels.EvolutionViewModel;
import com.reframe.lapp.viewmodels.MainViewModel;
import com.reframe.lapp.viewmodels.TeacherViewModel;
import com.reframe.lapp.viewmodels.TutorialsViewModel;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import rx.Observable;
import rx.functions.Action0;

@SuppressWarnings("unused")
public class BindingAdapters {

    @NonNull
    public static final ViewModelBinder defaultBinder = (viewDataBinding, viewModel) -> viewDataBinding.setVariable(BR.vm, viewModel);

    @BindingAdapter("android:visibility")
    public static void bindVisibility(@NonNull View view, @Nullable Boolean visible) {
        int visibility = (visible != null && visible) ? View.VISIBLE : View.GONE;
        view.setVisibility(visibility);
    }

    /**
     * Binding Adapter Wrapper for checking memory leak
     */
    @BindingAdapter({"items", "view_provider"})
    public static void bindRecyclerViewAdapter(RecyclerView recyclerView, Observable<List<ViewModel>> items, ViewProvider viewProvider) {
        RecyclerView.Adapter previousAdapter = recyclerView.getAdapter();
        BindingUtils.bindAdapterWithDefaultBinder(recyclerView, items, viewProvider);
    }

    /**
     * Binding Adapter Wrapper for checking memory leak
     */
    @BindingAdapter({"items", "view_provider", "current", "vm"})
    public static void bindViewPagerAdapter(ViewPager viewPager, Observable<List<ViewModel>> items, ViewProvider viewProvider, Number current, ViewModel vm) {
        PagerAdapter previousAdapter = viewPager.getAdapter();
        BindingUtils.bindAdapterWithDefaultBinder(viewPager, items, viewProvider);
        if(previousAdapter != null) {
            previousAdapter.notifyDataSetChanged();
            Log.d("ADAPTER SIZE>", String.valueOf(previousAdapter.getCount()));
        }
        viewPager.setPageTransformer(true, new DefaultTransformer());
        if(current!=null)
            viewPager.setCurrentItem(current.intValue());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (vm instanceof MainViewModel) {
                    MainViewModel mvm = (MainViewModel) vm;
                    if (current.intValue() != position) {
                        switch (position) {
                            case 0:
                                Log.d("VAMOS A>", "tutoriales");
                                mvm.goTutorials.call();
                                break;
                            case 1:
                                Log.d("VAMOS A>", "evaluacion");
                                mvm.goEvaluations.call();
                                break;
                            case 2:
                                Log.d("VAMOS A>", "evolución");
                                mvm.goEvolution.call();
                                break;
                            case 3:
                                mvm.goProfile.call();
                                break;
                        }
                    }
                } else if (vm instanceof TeacherViewModel) {
                    TeacherViewModel tvm = (TeacherViewModel) vm;
                    if (current.intValue() != position) {
                        switch (position) {
                            case 0:
                                tvm.goTutorials.call();
                                break;
                            case 1:
                                tvm.goEvaluations.call();
                                break;
                            case 2:
                                tvm.goProfile.call();
                                break;
                        }
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        if(url != null) {
            if(!url.equals("")) {
                Picasso.with(context).load(url).fit().centerCrop().into(imageView);
            }
        }
    }

    @BindingAdapter("videoUrl")
    public static void setVideoUrl(JCVideoPlayerStandard player, String url) {
        if(url != null) {
            player.setUp(url, JCVideoPlayer.SCREEN_LAYOUT_NORMAL);
        }
    }

    @BindingAdapter("foldingCell")
    public static void setFoldingCell(FoldingCell foldingCell, Boolean status) {
        if(foldingCell != null) {
            if(status)
                foldingCell.unfold(false);
            else
                foldingCell.fold(false);
        }
    }


    @BindingAdapter("chartData")
    public static void setChartData(LineChartView chart, EvolutionData chartData) {
        if(chartData != null) {

            LineSet dataset = new LineSet();
            if(chartData.getChart().getData().size()>0) {
                for (DatumChart data : chartData.getChart().getData()) {
                    dataset.addPoint(String.valueOf(data.getX()), data.getY().floatValue());
                }
                dataset.setColor(Color.LTGRAY)
                        .setDotsColor(chart.getResources().getColor(R.color.colorPrimary))
                        .setThickness(5)
                        .endAt(chartData.getChart().getData().size());
                chart.setAxisColor(Color.GRAY).setAxisThickness(3);
                chart.setXAxis(true).setYAxis(true);
                chart.setYLabels(AxisRenderer.LabelPosition.OUTSIDE);
                chart.setStep(5);
                chart.reset();
                chart.addData(dataset);
                chart.notifyDataUpdate();
                chart.show();
            }
        }
    }

    @BindingAdapter({"addExercises", "navigator"})
    public static void setExamplePosts(LinearLayout container, List<Exercise> exercises, Navigator navigator) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        if(exercises != null) {
            //container.setMinimumHeight(80*(exercises.size()+1));
            View titleView = container.findViewById(R.id.titleGroup);
            container.removeAllViews();
            container.addView(titleView);
            for (Exercise exercise:exercises) {
                View exampleView = inflater.inflate(R.layout.exercise_list_item, null);
                TextView title = (TextView) exampleView.findViewById(R.id.exerciseName);
                title.setText(exercise.getName());
                exampleView.setOnClickListener(view -> {
                    navigator.openList(exercise.getTutorials());
                });
                container.addView(exampleView);
            }
        }
    }

    @BindingAdapter({"evolutionList", "navigator", "vm"})
    public static void setEvolutionList(LinearLayout container, List<EvolutionData> evolutions, Navigator navigator, EvolutionViewModel vm) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        if(evolutions != null) {
            //container.setMinimumHeight(80*(exercises.size()+1));
            container.removeAllViews();
            for (EvolutionData evolution:evolutions) {
                View evolutionView = inflater.inflate(R.layout.evolution_list_item, null);
                TextView title = (TextView) evolutionView.findViewById(R.id.exerciseName);
                TextView tries = (TextView) evolutionView.findViewById(R.id.tries);
                TextView points = (TextView) evolutionView.findViewById(R.id.points);
                title.setText(evolution.getName());
                tries.setText(String.valueOf(evolution.getTries()));
                points.setText(String.valueOf(evolution.getValue()));
                evolutionView.setOnClickListener(view -> {
                    vm.updateChart.call(evolution);
                });
                container.addView(evolutionView);
            }
        }
    }

    @BindingAdapter({"addScales", "navigator"})
    public static void setScales(LinearLayout container, ProfessorEvaluation evaluation, Navigator navigator) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        container.removeAllViews();
        if(evaluation != null && evaluation.getScales() != null) {
            for(Scale scale:evaluation.getScales()) {
                LinearLayout subContainer = new LinearLayout(container.getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                subContainer.setLayoutParams(params);
                subContainer.setOrientation(LinearLayout.VERTICAL);
                TextView scaleTitle = new TextView(container.getContext());
                scaleTitle.setText(scale.getName());
                scaleTitle.setTypeface(null, Typeface.BOLD);
                scaleTitle.setPadding(64, 0,0,0);
                scaleTitle.setTextColor(container.getResources().getColor(R.color.colorPrimaryDark));
                scaleTitle.setTextSize(16);
                scaleTitle.setLayoutParams(params);
                subContainer.addView(scaleTitle);

                TextView scalePoints = new TextView(container.getContext());
                String text = "<font color=#757575>Puntaje:</font> <b><font color=#ffb300>".concat(String.valueOf(0)).concat("</font></b>");
                scalePoints.setText(Html.fromHtml(text));
                scalePoints.setPadding(0, 0,64,0);
                scalePoints.setTextColor(container.getResources().getColor(R.color.colorPrimaryDark));
                scalePoints.setGravity(Gravity.RIGHT);
                scalePoints.setLayoutParams(params);
                scale.setPoints(new Long(0));
                for (Datum datum:scale.getData()) {
                    View scaleView = inflater.inflate(R.layout.scale_view, null);
                    TextView name = (TextView) scaleView.findViewById(R.id.scaleName);
                    SmileBar value = (SmileBar) scaleView.findViewById(R.id.scaleValue);
                    name.setText(datum.getName());
                    value.setRating(datum.getPoints().intValue());
                    value.setEnabled(true);
                    value.setOnRatingSliderChangeListener(new SmileBar.OnRatingSliderChangeListener() {
                        @Override
                        public void onPendingRating(int i) {
                            scale.setPoints(scale.getPoints()-datum.getPoints()+new Long(i));
                            datum.setPoints(new Long(i));
                            String text = "<font color=#757575>Puntaje:</font> <b><font color=#ffb300>".concat(String.valueOf(scale.getPoints().intValue())).concat("</font></b>");
                            scalePoints.setText(Html.fromHtml(text));
                        }

                        @Override
                        public void onFinalRating(int i) {
                            scale.setPoints(scale.getPoints()-datum.getPoints()+new Long(i));
                            datum.setPoints(new Long(i));
                            String text = "<font color=#757575>Puntaje:</font> <b><font color=#ffb300>".concat(String.valueOf(scale.getPoints().intValue())).concat("</font></b>");
                            scalePoints.setText(Html.fromHtml(text));
                        }

                        @Override
                        public void onCancelRating() {

                        }
                    });
                    scale.setPoints(scale.getPoints()+datum.getPoints());
                    subContainer.addView(scaleView);
                }

                subContainer.addView(scalePoints);
                container.addView(subContainer);
            }
        }
    }

    @BindingAdapter({"addScales", "navigator"})
    public static void setScales(LinearLayout container, Evaluation evaluation, Navigator navigator) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        container.removeAllViews();
        if(evaluation != null) {
            for(Scale scale:evaluation.getScales()) {
                LinearLayout subContainer = new LinearLayout(container.getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                subContainer.setLayoutParams(params);
                subContainer.setOrientation(LinearLayout.VERTICAL);
                TextView scaleTitle = new TextView(container.getContext());
                scaleTitle.setText(scale.getName());
                scaleTitle.setTypeface(null, Typeface.BOLD);
                scaleTitle.setPadding(64, 0,0,0);
                scaleTitle.setTextColor(container.getResources().getColor(R.color.colorPrimaryDark));
                scaleTitle.setTextSize(16);
                scaleTitle.setLayoutParams(params);
                subContainer.addView(scaleTitle);

                TextView scalePoints = new TextView(container.getContext());
                String text = "<font color=#757575>Puntaje:</font> <b><font color=#ffb300>".concat(String.valueOf(scale.getPoints().intValue())).concat("</font></b>");
                scalePoints.setText(Html.fromHtml(text));
                scalePoints.setPadding(0, 0,64,0);
                scalePoints.setTextColor(container.getResources().getColor(R.color.colorPrimaryDark));
                scalePoints.setGravity(Gravity.RIGHT);
                scalePoints.setLayoutParams(params);
                for (Datum datum:scale.getData()) {
                    View scaleView = inflater.inflate(R.layout.scale_view, null);
                    TextView name = (TextView) scaleView.findViewById(R.id.scaleName);
                    SmileBar value = (SmileBar) scaleView.findViewById(R.id.scaleValue);

                    name.setText(datum.getName());
                    value.setRating(datum.getPoints().intValue());
                    value.setEnabled(false);
                    subContainer.addView(scaleView);
                }

                subContainer.addView(scalePoints);
                container.addView(subContainer);
            }
        }
    }

    @BindingAdapter({"addFeedback", "navigator"})
    public static void setFeedback(LinearLayout container, Evaluation evaluation, Navigator navigator) {
        Context context = container.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        container.removeAllViews();
        if(evaluation != null) {
            for(FeedBack feedback:evaluation.getFeedBack()) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                MediaPlayer mediaPlayer = new MediaPlayer();
                View playerView = inflater.inflate(R.layout.feedback_player_view, null);
                playerView.setLayoutParams(params);
                ImageView playControl = (ImageView) playerView.findViewById(R.id.feedbackPlayerControl);
                SeekBar seekBar = (SeekBar) playerView.findViewById(R.id.seekBar);
                JCVideoPlayerStandard player = (JCVideoPlayerStandard) playerView.findViewById(R.id.player);
                seekBar.setProgress(0);
                seekBar.setMax(100);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mediaPlayer.setDataSource(feedback.getAudio().getUrl());
                    mediaPlayer.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.setOnPreparedListener(mp -> {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        //mPlayerControl.setImageResource(R.drawable.ic_play);
                    } else {
                        //mediaPlayer.start();
                        //mPlayerControl.setImageResource(R.drawable.ic_pause);
                    }
                });
                Handler mHandler = new Handler();
//Make sure you update Seekbar on UI thread
                ((Activity)context).runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if(mediaPlayer != null){

                            long totalDuration = mediaPlayer.getDuration();
                            long currentDuration = mediaPlayer.getCurrentPosition();

                            Double percentage = (double) 0;

                            long currentSeconds = (int) (currentDuration / 1000);
                            long totalSeconds = (int) (totalDuration / 1000);

                            // calculating percentage
                            percentage =(((double)currentSeconds)/totalSeconds)*100;

                            // Updating progress bar
                            int progress = percentage.intValue();
                            //Log.d("Progress", ""+progress);
                            seekBar.setProgress(progress);
                        }
                        mHandler.postDelayed(this, 100);
                    }
                });

                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                        if(mediaPlayer != null && b){
                            int totalDuration = mediaPlayer.getDuration();
                            int currentDuration = 0;
                            totalDuration = (int) (totalDuration / 1000);
                            currentDuration = (int) ((((double)seekBar.getProgress()) / 100) * totalDuration);
                            // return current duration in milliseconds
                            int currentPosition = currentDuration * 1000;
                            // forward or backward to certain seconds
                            mediaPlayer.seekTo(currentPosition);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                playControl.setOnClickListener(view -> {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    } else {
                        mediaPlayer.start();
                    }
                } );
                if(feedback.getVideo().getUrl() != null) {
                    player.setUp(feedback.getVideo().getUrl(), JCVideoPlayer.SCREEN_LAYOUT_NORMAL);
                } else {
                    player.setVisibility(View.GONE);
                }
                container.addView(playerView);
            }
        }
    }

    @BindingAdapter({"addFeedback", "navigator"})
    public static void setFeedback(LinearLayout container, ProfessorEvaluation evaluation, Navigator navigator) {
        Context context = container.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        container.removeAllViews();
        if(evaluation != null && evaluation.getFeedBack().size()>0) {
            for(FeedBackResponse feedback:evaluation.getFeedBack()) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                MediaPlayer mediaPlayer = new MediaPlayer();
                View playerView = inflater.inflate(R.layout.feedback_player_view, null);
                playerView.setLayoutParams(params);
                ImageView playControl = (ImageView) playerView.findViewById(R.id.feedbackPlayerControl);
                SeekBar seekBar = (SeekBar) playerView.findViewById(R.id.seekBar);
                JCVideoPlayerStandard player = (JCVideoPlayerStandard) playerView.findViewById(R.id.player);
                seekBar.setProgress(0);
                seekBar.setMax(100);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mediaPlayer.setDataSource(feedback.getAudioUrl());
                    mediaPlayer.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.setOnPreparedListener(mp -> {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        //mPlayerControl.setImageResource(R.drawable.ic_play);
                    } else {
                        //mediaPlayer.start();
                        //mPlayerControl.setImageResource(R.drawable.ic_pause);
                    }
                });
                Handler mHandler = new Handler();
                //Make sure you update Seekbar on UI thread
                ((Activity)context).runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if(mediaPlayer != null){

                            long totalDuration = mediaPlayer.getDuration();
                            long currentDuration = mediaPlayer.getCurrentPosition();

                            Double percentage = (double) 0;

                            long currentSeconds = (int) (currentDuration / 1000);
                            long totalSeconds = (int) (totalDuration / 1000);

                            // calculating percentage
                            percentage =(((double)currentSeconds)/totalSeconds)*100;

                            // Updating progress bar
                            int progress = percentage.intValue();
                            //Log.d("Progress", ""+progress);
                            seekBar.setProgress(progress);
                        }
                        mHandler.postDelayed(this, 100);
                    }
                });

                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                        if(mediaPlayer != null && b){
                            int totalDuration = mediaPlayer.getDuration();
                            int currentDuration = 0;
                            totalDuration = (int) (totalDuration / 1000);
                            currentDuration = (int) ((((double)seekBar.getProgress()) / 100) * totalDuration);
                            // return current duration in milliseconds
                            int currentPosition = currentDuration * 1000;
                            // forward or backward to certain seconds
                            mediaPlayer.seekTo(currentPosition);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                playControl.setOnClickListener(view -> {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    } else {
                        mediaPlayer.start();
                    }
                } );
                if(feedback.getVideoUrl() != null) {
                    player.setUp(feedback.getVideoUrl(), JCVideoPlayer.SCREEN_LAYOUT_NORMAL);
                } else {
                    player.setVisibility(View.GONE);
                }
                container.addView(playerView);
            }
        }
    }

    @BindingAdapter({"configTablayout", "current"})
    public static void setConfigTablayout(TabLayout tabLayout, ViewModel viewModel, String current) {
        if(current!=null) {
            TabLayout.Tab tab;
            if(current.equals(Constants.BASIC)) {
                tab = tabLayout.getTabAt(0);
                if(tab!=null)
                    tab.select();
            }
            else if(current.equals(Constants.ADVANCED)) {
                tab = tabLayout.getTabAt(1);
                if(tab!=null)
                    tab.select();
            }
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(viewModel instanceof TutorialsViewModel) {
                    final TutorialsViewModel hvm = (TutorialsViewModel) viewModel;
                    switch (tab.getPosition()) {
                        case 0:
                            hvm.getBasic.call();
                            break;
                        case 1:
                            hvm.getAdvanced.call();
                            break;
                    }
                } else
                if(viewModel instanceof EvolutionViewModel) {
                    final EvolutionViewModel hvm = (EvolutionViewModel) viewModel;
                    switch (tab.getPosition()) {
                        case 0:
                            hvm.getBasic.call();
                            break;
                        case 1:
                            hvm.getAdvanced.call();
                            break;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @BindingAdapter({"bottomNavigation", "current"})
    public static void setBottomNavigation(BottomNavigationView bottomNavigation, ViewModel vm, Number current) {
        BottomNavigationViewHelper.removeShiftMode(bottomNavigation);

        if (bottomNavigation != null) {
            if(current != null) {
                if(vm instanceof MainViewModel) {
                    MainViewModel m_vm = (MainViewModel) vm;
                    if (!bottomNavigation.getMenu().getItem(m_vm.current.get().intValue()).isChecked())
                        bottomNavigation.getMenu().getItem(m_vm.current.get().intValue()).setChecked(true);
                } else if(vm instanceof TeacherViewModel) {
                    TeacherViewModel t_vm = (TeacherViewModel) vm;
                    if (!bottomNavigation.getMenu().getItem(t_vm.current.get().intValue()).isChecked())
                        bottomNavigation.getMenu().getItem(t_vm.current.get().intValue()).setChecked(true);
                }
            }
            bottomNavigation.setOnNavigationItemSelectedListener(item -> {
                if(vm instanceof MainViewModel) {
                    MainViewModel mainvm = (MainViewModel) vm;
                    switch (item.getItemId()) {
                        case R.id.action_tutorials:
                            mainvm.goTutorials.call();
                            return true;
                        case R.id.action_evaluations:
                            mainvm.goEvaluations.call();
                            return true;
                        case R.id.action_evolution:
                            mainvm.goEvolution.call();
                            return true;
                        case R.id.action_profile:
                            mainvm.goProfile.call();
                            return true;
                    }
                } else if(vm instanceof TeacherViewModel) {
                    TeacherViewModel teachervm = (TeacherViewModel) vm;
                    switch (item.getItemId()) {
                        case R.id.action_video_library:
                            teachervm.goTutorials.call();
                            return true;
                        case R.id.action_evaluations:
                            teachervm.goEvaluations.call();
                            return true;
                        case R.id.action_evolution:
                            teachervm.goProfile.call();
                            return true;
                    }
                }
                return false;
            });

        }
    }

    @BindingAdapter("animateUp")
    public static void setAnimateUp(View view, long delay) {
        ViewAnimator.animate(view).scale(0.8f).translationY(-200f).startDelay(delay).duration(500).start();
    }

    @BindingAdapter("animateFade")
    public static void setAnimateFade(View view, long delay) {
        ViewAnimator.animate(view).fadeIn().startDelay(delay).duration(500).start();
    }

    @BindingAdapter({"pullToRefresh","isLoading"})
    public static void setPullToRefresh(SwipeRefreshLayout swipe, Action0 listener, Boolean isLoading) {
        swipe.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
        swipe.setOnRefreshListener(() -> {
            if(listener != null) {
                listener.call();
            }
        });
        if(isLoading != null)
            swipe.setRefreshing(isLoading);
    }

    @BindingConversion
    public static View.OnClickListener toOnClickListener(final Action0 listener) {
        if (listener != null) {
            return view -> listener.call();
        } else {
            return null;
        }
    }

    static class BottomNavigationViewHelper {

        static void removeShiftMode(BottomNavigationView view) {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
            try {
                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
                shiftingMode.setAccessible(true);
                shiftingMode.setBoolean(menuView, false);
                shiftingMode.setAccessible(false);
                for (int i = 0; i < menuView.getChildCount(); i++) {
                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                    item.setShiftingMode(false);
                    // set once again checked value, so view will be updated
                    item.setChecked(item.getItemData().isChecked());
                }
            } catch (NoSuchFieldException e) {
                Log.e("ERROR NO SUCH FIELD", "Unable to get shift mode field");
            } catch (IllegalAccessException e) {
                Log.e("ERROR ILLEGAL ALG", "Unable to change value of shift mode");
            }
        }
    }
}