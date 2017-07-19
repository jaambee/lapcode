package com.reframe.lapp.network.notifications;

import rx.Observable;
import rx_fcm.FcmReceiverUIBackground;
import rx_fcm.Message;

/**
 * Created by Aldo on 26-12-2016.
 */

public class AppFcmReceiverUIBackground implements FcmReceiverUIBackground {
    @Override
    public void onNotification(Observable<Message> oMessage) {
        oMessage.subscribe(message -> {
            /*String jsonPayload = message.payload().getString("payload");
            try {
                JSONObject object = new JSONObject(jsonPayload);
                String title = object.getString("title");
                String image = object.getString("image");
                String content = object.getString("message");
                String campaignId = object.getString("campaignId");

                PugNotification.with(message.application().getApplicationContext())
                        .load()
                        .title(title)
                        .message(content)
                        .smallIcon(R.mipmap.ic_launcher)
                        .largeIcon(R.mipmap.ic_launcher)
                        .flags(Notification.DEFAULT_ALL)
                        .autoCancel(true)
                        .simple()
                        .build();

            } catch (JSONException e) {
                e.printStackTrace();
            }*/
        });
    }
}
