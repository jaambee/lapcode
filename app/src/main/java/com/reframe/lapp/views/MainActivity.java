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
import com.reframe.lapp.constants.Notifications;
import com.reframe.lapp.network.LappService;
import com.reframe.lapp.viewmodels.MainViewModel;
import com.reframe.lapp.views.main.EvaluationsFragment;
import com.reframe.lapp.views.main.EvolutionFragment;
import com.reframe.lapp.views.main.ProfileFragment;
import com.reframe.lapp.views.main.TutorialFragment;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx_fcm.internal.RxFcm;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uploadDeviceToken();
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        viewPager.setOffscreenPageLimit(4);
        MainNavigationAdapter adapter = new MainNavigationAdapter(this,getSupportFragmentManager());
        adapter.addPage(TutorialFragment.newInstance());
        adapter.addPage(EvaluationsFragment.newInstance());
        adapter.addPage(EvolutionFragment.newInstance());
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
                case R.id.action_tutorials:
                    Log.d("MENU", "TUTORIALES");
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.action_evaluations:
                    Log.d("MENU", "EVALUACIONES");
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.action_evolution:
                    Log.d("MENU", "EVOLUCION");
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.action_profile:
                    Log.d("MENU", "PROFILE");
                    viewPager.setCurrentItem(3);
                    return true;
            }
            return false;
        });
        if(getIntent().getStringExtra("notification_type")!=null){
            String type = getIntent().getStringExtra("notification_type");
            if(type.equals(Notifications.NEW_LEVEL))
                getNavigator().showSweetAlert("FELICITACIONES", "Haz subido de nivel!");
        }
    }

    @NonNull
    @Override
    protected ViewModel createViewModel() {
        return new MainViewModel(getNavigator());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
}
