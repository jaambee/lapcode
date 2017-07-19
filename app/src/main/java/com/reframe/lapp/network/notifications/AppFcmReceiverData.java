package com.reframe.lapp.network.notifications;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import br.com.goncalves.pugnotification.interfaces.ImageLoader;
import br.com.goncalves.pugnotification.interfaces.OnImageLoadingCompleted;
import rx.Observable;
import rx.Subscription;
import rx_fcm.FcmReceiverData;
import rx_fcm.Message;

/**
 * Created by Aldo on 20-12-2016.
 */

public class AppFcmReceiverData implements FcmReceiverData, ImageLoader {
    Subscription subscription;
    Context context;
    @Override
    public Observable<Message> onNotification(Observable<Message> oMessage) {
        return oMessage.doOnNext(message -> {
            context = message.application().getApplicationContext();

            /*
            PugNotification.with(message.application().getApplicationContext())
                                        .load()
                                        .title(title)
                                        .message(content)
                                        .click(pendingIntent)
                                        .smallIcon(R.mipmap.ic_launcher)
                                        .largeIcon(R.mipmap.ic_launcher)
                                        .flags(Notification.DEFAULT_ALL)
                                        .autoCancel(true)
                                        .color(R.color.colorPrimaryDark)
                                        .custom()
                                        .setImageLoader(this)
                                        .background(image)
                                        .setPlaceholder(R.drawable.pugnotification_ic_placeholder)
                                        .build();
             */
        });
    }

    @Override
    public void load(String uri, final OnImageLoadingCompleted onCompleted) {
        Picasso.with(context).load(uri).into(getViewTarget(onCompleted));
    }
    private static Target getViewTarget(final OnImageLoadingCompleted onCompleted) {
        return new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                onCompleted.imageLoadingCompleted(bitmap);
            }
            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        };
    }

    @Override
    public void load(int imageResId, OnImageLoadingCompleted onCompleted) {
        Picasso.with(context).load(imageResId).into(getViewTarget(onCompleted));
    }
}
