package com.hvph.musicplay.content;

import android.content.Context;

import com.hvph.musicplay.content.implement.OfflineContentImpl;
import com.hvph.musicplay.content.implement.OnlineContentImpl;

/**
 * Created by HoangHVP on 11/21/2014.
 */
public class ContentFactory {
    public static IContent getContent(int contentType, Context context){
        switch (contentType){
            case ContentDefinition.CONTENT_TYPE_OFFLINE:
                return OfflineContentImpl.getInstance(context);

            case ContentDefinition.CONTENT_TYPE_ONLINE:
                return new OnlineContentImpl();
            default:
                return null;
        }
    }
}
