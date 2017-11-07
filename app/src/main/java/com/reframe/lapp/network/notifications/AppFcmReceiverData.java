package com.reframe.lapp.network.notifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.google.gson.Gson;
import com.pixplicity.easyprefs.library.Prefs;
import com.reframe.lapp.R;
import com.reframe.lapp.constants.Constants;
import com.reframe.lapp.constants.Notifications;
import com.reframe.lapp.models.User;
import com.reframe.lapp.views.MainActivity;
import com.reframe.lapp.views.TeacherActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.goncalves.pugnotification.interfaces.ImageLoader;
import br.com.goncalves.pugnotification.interfaces.OnImageLoadingCompleted;
import br.com.goncalves.pugnotification.notification.PugNotification;
import rx.Observable;
import rx.Subscription;
import rx_fcm.FcmReceiverData;
import rx_fcm.Message;
import rx_fcm.internal.RxFcm;

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


            Log.d("NEW NOTIFICATION",message.payload().toString());

            String jsonPayload = message.payload().getString("payload");
            showNotification(jsonPayload);

            /*
            PugNotification.with(message.application().getApplicationContext())
                                        .load()
                                        .title(title)
                                        .message(content)
                                        .click(pendingIntent)
                                        .smallIcon(R.drawable.ic_launcher)
                                        .largeIcon(R.drawable.ic_launcher)
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

    private void showNotification(String jsonPayload) {

        Intent pending;
        User user;

        Log.d("SHOW_NOTIFICATION", jsonPayload);
        try {
            JSONObject object = new JSONObject(jsonPayload);
            String title = object.getString("title");
            String subtitle = object.getString("subtitle");
            String body = object.getString("body");
            String type = object.getString("type");


            if(!Prefs.getString(Constants.USER, "no_user").equals("no_user")) {
                user = new Gson().fromJson(Prefs.getString(Constants.USER, "nouser"), User.class);

                if(user.getRole().equals(User.PROFESSOR) && type.equals(Notifications.NEW_EVALUATION)) {
                    pending = new Intent(context, TeacherActivity.class);
                    pending.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, pending, PendingIntent.FLAG_UPDATE_CURRENT);
                    PugNotification.with(context)
                            .load()
                            .title(title)
                            .message(body)
                            .smallIcon(R.drawable.ic_launcher)
                            .largeIcon(R.drawable.ic_launcher)
                            .flags(Notification.DEFAULT_ALL)
                            .autoCancel(true)
                            .click(pendingIntent)
                            .simple()
                            .build();
                } else if(user.getRole().equals(User.STUDENT) && type.equals(Notifications.EVALUATION_RECEIVED)) {
                    pending = new Intent(context, MainActivity.class);
                    pending.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, pending, PendingIntent.FLAG_UPDATE_CURRENT);
                    PugNotification.with(context)
                            .load()
                            .title(title)
                            .message(body)
                            .smallIcon(R.drawable.ic_launcher)
                            .largeIcon(R.drawable.ic_launcher)
                            .flags(Notification.DEFAULT_ALL)
                            .autoCancel(true)
                            .click(pendingIntent)
                            .simple()
                            .build();
                } else if(user.getRole().equals(User.STUDENT) && type.equals(Notifications.NEW_LEVEL)) {

                    pending = new Intent(context, MainActivity.class);
                    pending.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    pending.putExtra("notification_type", Notifications.NEW_LEVEL);
                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, pending, PendingIntent.FLAG_UPDATE_CURRENT);
                    PugNotification.with(context)
                            .load()
                            .title(title)
                            .message(body)
                            .smallIcon(R.drawable.ic_launcher)
                            .largeIcon(R.drawable.ic_launcher)
                            .flags(Notification.DEFAULT_ALL)
                            .autoCancel(true)
                            .click(pendingIntent)
                            .simple()
                            .build();
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
