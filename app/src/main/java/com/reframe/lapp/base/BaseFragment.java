package com.reframe.lapp.base;

/**
 * Created by Aldo on 23-11-2016.
 */
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.reframe.lapp.utils.MvvmFragment;

import rx.subscriptions.CompositeSubscription;
import rx.subscriptions.Subscriptions;

public abstract class BaseFragment extends MvvmFragment {

    //container for batch RxJava unsubscription
    protected CompositeSubscription mCompositeSubscription = Subscriptions.from();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @NonNull
    protected Navigator getNavigator() {
        return ((BaseActivity)getActivity()).getNavigator();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.unsubscribe();
    }
}