package com.reframe.lapp.utils;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by Aldo on 03-11-2016.
 */

public class RxBus {

    private static RxBus instance;

    private PublishSubject<Object> subject = PublishSubject.create();

    public static synchronized RxBus getInstance() {
        if(instance == null)
            instance = new RxBus();
        return instance;
    }

    /**
     * Publish some object
     */
    public void publish(Object object) {
        subject.onNext(object);
    }

    public Observable<Object> getEvents() {
        return subject;
    }
}
