package com.example.prn763.remotehelper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;



/**
 * Created by prn763 on 12/14/2017.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {

    String title[] = {"Service", "Contact"};

    public MyPagerAdapter(android.support.v4.app.FragmentManager fm){
        super(fm);
    }
    @Override
    public CharSequence getPageTitle(int position){
        return title[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                TabServicesOperation serviceOperation = new TabServicesOperation();
                return serviceOperation;
            case 1:
                TabAccessProvider accessProvider = new TabAccessProvider();
                return accessProvider;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return title.length;
    }

}
