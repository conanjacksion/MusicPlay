package com.hvph.musicplay.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * Created by HoangHVP on 10/10/2014.
 */
public class Image {
    public static Bitmap getThumbnail(Context context, Uri uri, int filterType) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        byte[] rawArt = null;
        Bitmap art = null;
        BitmapFactory.Options bfo = new BitmapFactory.Options();
        if(uri != null) {
            try {
                mmr.setDataSource(context, uri);
                rawArt = mmr.getEmbeddedPicture();
            }catch (Exception ex){}
        }else{
            rawArt = null;
        }
        // if rawArt is null then no cover art is embedded in the file or is not
        // recognized as such.
        if (null != rawArt) {
            //art = BitmapFactory.decodeByteArray(rawArt, 0, rawArt.length, bfo);
            art = decodeSampledBitmapFromByteArray(rawArt,128,128);
        } else {
//            String thumbnailResource = "";
//            switch (filterType){
//                case Definition.TYPE_SONG:
//                    thumbnailResource = "song_thumbnail_default";
//                    break;
//                case Definition.TYPE_GENRE:
//                    thumbnailResource = "genre_thumbnail_default";
//                    break;
//                case Definition.TYPE_FOLDER:
//                    thumbnailResource = "folder_thumbnail_default";
//                    break;
//                case Definition.TYPE_PLAYLIST:
//                    thumbnailResource = "folder_thumbnail_default";
//                    break;
//            }
//            //art = BitmapFactory.decodeResource(context.getResources(),
//            //        context.getResources().getIdentifier(thumbnailResource, "drawable", context.getPackageName()));
//            art = decodeSampledBitmapFromResource(context.getResources(),
//                    context.getResources().getIdentifier(thumbnailResource, "drawable", context.getPackageName()),
//                    128,128);

        }
        return art;
    }

    public static Bitmap getThumbnail(Context context, Uri uri) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        byte[] rawArt = null;
        Bitmap art = null;
        BitmapFactory.Options bfo = new BitmapFactory.Options();
        if(uri != null) {
            try {
                mmr.setDataSource(context, uri);
                rawArt = mmr.getEmbeddedPicture();
            }catch (Exception ex){}
        }else{
            rawArt = null;
        }
        // if rawArt is null then no cover art is embedded in the file or is not
        // recognized as such.
        if (null != rawArt) {
            //art = BitmapFactory.decodeByteArray(rawArt, 0, rawArt.length, bfo);
            art = decodeSampledBitmapFromByteArray(rawArt,128,128);
        } else {
//            String thumbnailResource = "";
//            switch (filterType){
//                case Definition.TYPE_SONG:
//                    thumbnailResource = "song_thumbnail_default";
//                    break;
//                case Definition.TYPE_GENRE:
//                    thumbnailResource = "genre_thumbnail_default";
//                    break;
//                case Definition.TYPE_FOLDER:
//                    thumbnailResource = "folder_thumbnail_default";
//                    break;
//                case Definition.TYPE_PLAYLIST:
//                    thumbnailResource = "folder_thumbnail_default";
//                    break;
//            }
//            //art = BitmapFactory.decodeResource(context.getResources(),
//            //        context.getResources().getIdentifier(thumbnailResource, "drawable", context.getPackageName()));
//            art = decodeSampledBitmapFromResource(context.getResources(),
//                    context.getResources().getIdentifier(thumbnailResource, "drawable", context.getPackageName()),
//                    128,128);

        }
        return art;
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromByteArray(byte[] res, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(res, 0, res.length, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(res, 0, res.length, options);
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static void loadImage(Context context,Uri uri,ImageView imageView, int imageDefaultResourceId){
        LoadImageAsync loadImageAsync = new LoadImageAsync(context,imageView,imageDefaultResourceId);
        loadImageAsync.execute(uri);
    }

    private static class LoadImageAsync extends AsyncTask<Uri,Void,Bitmap> {
        private Context mContext;
        private ImageView mImageView;
        private int mImageDefaultResourceId;

        public LoadImageAsync(Context context, ImageView imageView, int imageDefaultResourceId) {
            this.mContext = context;
            this.mImageView = imageView;
            this.mImageDefaultResourceId = imageDefaultResourceId;
        }

        @Override
        protected Bitmap doInBackground(Uri... params) {
            return Image.getThumbnail(mContext,params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(bitmap != null){
                mImageView.setImageBitmap(bitmap);
            }
            else{
                mImageView.setImageResource(mImageDefaultResourceId);
            }
        }
    }
}


