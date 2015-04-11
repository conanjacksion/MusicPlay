package com.hvph.musicplay.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.hvph.musicplay.MusicPlayApplication;
import com.hvph.musicplay.ui.fragment.BaseFragment;

/**
 * Created by HoangHVP on 11/13/2014.
 */
public class AppUtils {
    public static void loadFragmentData(FragmentManager fragmentManager, ViewPager viewpager, int position){
        Fragment currentFragment = getActiveFragment(viewpager,position,fragmentManager);
        if(currentFragment!=null){
            try {
                BaseFragment baseFragment = (BaseFragment) currentFragment;
                baseFragment.loadData();
            } catch (Exception ex){
                Log.e(MusicPlayApplication.TAG, "Current Fragment is not Base Fragment");
            }
        }
    }

    private static Fragment getActiveFragment(ViewPager container, int position, FragmentManager fragmentManager) {
        String name = makeFragmentName(container.getId(), position);
        return  fragmentManager.findFragmentByTag(name);
    }

    private static String makeFragmentName(int viewId, int index) {
        return "android:switcher:" + viewId + ":" + index;
    }
}
