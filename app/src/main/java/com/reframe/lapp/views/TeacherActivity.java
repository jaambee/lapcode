package com.reframe.lapp.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.manaschaudhari.android_mvvm.ViewModel;
import com.reframe.lapp.R;
import com.reframe.lapp.adapters.MainNavigationAdapter;
import com.reframe.lapp.base.BaseActivity;
import com.reframe.lapp.viewmodels.TeacherViewModel;
import com.reframe.lapp.views.main.ProfessorEvaluationsFragment;
import com.reframe.lapp.views.main.ProfileFragment;
import com.reframe.lapp.views.main.TutorialFragment;

public class TeacherActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uploadDeviceToken();
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        viewPager.setOffscreenPageLimit(3);
        MainNavigationAdapter adapter = new MainNavigationAdapter(this,getSupportFragmentManager());
        adapter.addPage(TutorialFragment.newInstance());
        adapter.addPage(ProfessorEvaluationsFragment.newInstance());
        adapter.addPage(ProfileFragment.newInstance());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (!bottomNavigationView.getMenu().getItem(viewPager.getCurrentItem()).isChecked())
                    bottomNavigationView.getMenu().getItem(viewPager.getCurrentItem()).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_video_library:
                    Log.d("MENU", "VIDEOS");
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.action_evaluations:
                    Log.d("MENU", "EVALUACIONES");
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.action_profile:
                    Log.d("MENU", "PROFILE");
                    viewPager.setCurrentItem(2);
                    //getNavigator().openFeedbackEditor();
                    return true;
            }
            return false;
        });
    }

    @NonNull
    @Override
    protected ViewModel createViewModel() {
        return new TeacherViewModel(getNavigator());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_teacher;
    }
}
