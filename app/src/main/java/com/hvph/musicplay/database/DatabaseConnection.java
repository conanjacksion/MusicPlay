package com.hvph.musicplay.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hvph.musicplay.business.Definition;
import com.hvph.musicplay.dao.DaoFactory;

import java.io.File;

/**
 * Created by bibo on 09/11/2014.
 */
public class DatabaseConnection {
    /** The name of the database file on the device. */
    private static final String DATABASE_NAME = "com.hvph.musicplay.db";
    /** The version of the database this code works with. */
    public static final int DATABASE_CURRENT_VERSION = 1;
    /** Variable to hold the database instance. */
    private SQLiteDatabase mDB;
    /** Database open/upgrade helper. */
    private DatabaseHelper mOpenHelper;
    /** The context within which to work. */
    private final Context mContext;

    /**
     * A helper class is used to manage database.
     */
    public static final class DatabaseHelper extends SQLiteOpenHelper {

        /**
         * Constructor.
         *
         * @param context
         *            the context keeps this connection.
         */
        public DatabaseHelper(final Context context) {
            super(context, DATABASE_NAME, null, DATABASE_CURRENT_VERSION);
        }

        @Override
        public void onCreate(final SQLiteDatabase db) {
            DaoFactory.getDao(Definition.TYPE_ARTIST).createTable(db);
            DaoFactory.getDao(Definition.TYPE_FOLDER).createTable(db);
            DaoFactory.getDao(Definition.TYPE_ALBUM).createTable(db);
            DaoFactory.getDao(Definition.TYPE_GENRE).createTable(db);
            DaoFactory.getDao(Definition.TYPE_SONG).createTable(db);
            DaoFactory.getDao(Definition.TYPE_PLAYLIST).createTable(db);
            DaoFactory.getDao(Definition.TYPE_PLAYLIST_ITEM).createTable(db);
        }

        @Override
        public void onUpgrade(final SQLiteDatabase db, final int oldVersion,
                              final int newVersion) {
            // Not need to do anything for the first version.
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    /**
     * Construct database service with context of the application.
     *
     * @param context
     *            the Context within which to work
     */
    public DatabaseConnection(final Context context) {
        mContext = context;
        mOpenHelper = new DatabaseHelper(mContext);
    }

    /**
     * Get context.
     */
    public final Context getContext() {
        return mContext;
    }

    /**
     * Get database connection.
     */
    public final SQLiteDatabase getDatabase() {
        return mDB;
    }

    /** Open the database. */
    public final void open() {
        if (mOpenHelper == null) {
            return;
        }
        mDB = mOpenHelper.getWritableDatabase();
    }

    /** Close the database. */
    public final void close() {
        if (mDB != null) {
            mDB.close();
        }
    }

    /**
     * Check if the database exist
     *
     * @return true if it exists, false if it doesn't
     */
    public static boolean checkDatabase(Context context) {
        File database=context.getDatabasePath(DATABASE_NAME);
        if (!database.exists()) {
            return false;
        } else {
            return true;
        }
    }
}
