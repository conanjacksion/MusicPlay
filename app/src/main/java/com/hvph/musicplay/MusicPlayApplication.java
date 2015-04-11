package com.hvph.musicplay;

import android.app.Application;
import android.content.Context;

import com.hvph.musicplay.database.DatabaseConnection;

/**
 * Created by bibo on 09/11/2014.
 */
public class MusicPlayApplication extends Application {

    public static String TAG = "MP";
    public static Context mContext;
    public static DatabaseConnection mDatabaseConnection;
    public static boolean mDatabaseExists;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mDatabaseExists = DatabaseConnection.checkDatabase(mContext);
        mDatabaseConnection = new DatabaseConnection(getApplicationContext());
        mDatabaseConnection.open();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mDatabaseConnection.close();
    }
}
