package com.reframe.lapp.network.notifications;

import com.pixplicity.easyprefs.library.Prefs;
import com.reframe.lapp.constants.Constants;
import com.reframe.lapp.network.LappService;

import rx.Observable;
import rx_fcm.FcmRefreshTokenReceiver;
import rx_fcm.TokenUpdate;

/**
 * Created by Aldo on 26-12-2016.
 */

public class RefreshTokenReceiver implements FcmRefreshTokenReceiver {
    @Override
    public void onTokenReceive(Observable<TokenUpdate> oTokenUpdate) {
        if(!Prefs.getString(Constants.LAPP_TOKEN, "notoken").equals("notoken"))
            oTokenUpdate.flatMap(tokenUpdate -> LappService.setDeviceToken(tokenUpdate.getToken())).subscribe(tokenUpdate -> {}, error -> {});
    }
}
