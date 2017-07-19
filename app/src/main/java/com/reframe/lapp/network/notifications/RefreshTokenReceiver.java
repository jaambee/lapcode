package com.reframe.lapp.network.notifications;

import rx.Observable;
import rx_fcm.FcmRefreshTokenReceiver;
import rx_fcm.TokenUpdate;

/**
 * Created by Aldo on 26-12-2016.
 */

public class RefreshTokenReceiver implements FcmRefreshTokenReceiver {
    @Override
    public void onTokenReceive(Observable<TokenUpdate> oTokenUpdate) {
        //if(!Prefs.getString(Constants.HAIP_TOKEN, "notoken").equals("notoken"))
            //oTokenUpdate.flatMap(tokenUpdate -> LappService.setDeviceToken(tokenUpdate.getToken())).subscribe(tokenUpdate -> {}, error -> {});
    }
}
