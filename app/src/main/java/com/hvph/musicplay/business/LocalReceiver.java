package com.hvph.musicplay.business;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * Created by HoangHVP on 10/30/2014.
 */
public class LocalReceiver extends BroadcastReceiver {
    private LocalReceiverListener mLocalReceiver;

    public LocalReceiver(LocalReceiverListener localReceiver){
        this.mLocalReceiver = localReceiver;
    }

    public interface LocalReceiverListener{
        public void updateNowPlaying();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equalsIgnoreCase(Definition.ACTION_NOW_PLAYING_INDICATOR)){
            mLocalReceiver.updateNowPlaying();
        }
    }
}
