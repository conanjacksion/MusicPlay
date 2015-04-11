package com.hvph.musicplay.content;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.hvph.musicplay.MusicPlayApplication;
import com.hvph.musicplay.ui.activity.MainActivity;
import com.hvph.musicplay.ui.activity.WelcomeActivity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by bibo on 28/03/2015.
 */
public class DataInitialization extends AsyncTask<String, Void, Void> implements OnContentEventListener {
    private ProgressDialog mProgressDialog;
    private Context mContext;
    private DataInitializationListener dataInitializationListener;
    private IContent mOfflineContent;

    public DataInitialization(Context context) {
        this.mContext = context;
        this.mProgressDialog = new ProgressDialog(mContext);
        this.dataInitializationListener = (WelcomeActivity) context;
        this.mOfflineContent = ContentFactory.getContent(ContentDefinition.CONTENT_TYPE_OFFLINE, mContext);
    }

    @Override
    public void onGetDataFromSourceFinished(HashMap<Integer, ArrayList> data) {
        mOfflineContent.mapDataFromSourceToDatabase(data);
    }

    @Override
    protected Void doInBackground(String... params) {
        if (!MusicPlayApplication.mDatabaseExists || params[0].equalsIgnoreCase(MainActivity.FORCE_REFRESH_DATABASE)) {
//            mOfflineContent.registerEventListener(this);
//            mOfflineContent.getDataFromSource();
            mOfflineContent.mapDataFromSourceToDatabase();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog.setMessage("Load data ...");
        mProgressDialog.setTitle("Waiting...");
        mProgressDialog.show();
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mProgressDialog.dismiss();
        dataInitializationListener.onInitDataFinished();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    public interface DataInitializationListener {
        public void onInitDataFinished();
    }
}

