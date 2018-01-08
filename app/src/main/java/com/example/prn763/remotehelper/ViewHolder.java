package com.example.prn763.remotehelper;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;


/**
 * Created by prn763 on 12/17/2017.
 */

public class ViewHolder{
    private final static String TAG = "VIEWHOLDER";
    private static ViewHolder m_viewHolder = null;
    private ViewPager mViewPager = null;
    private TabLayout mTab = null;
    private Button mSendBtn = null; //send message
    private Button mStartBtn = null;
    private Button mStopBtn = null;
    private Button mAddContactRequestBtn = null;
    private ListView mIpList = null;
    private ListView mPhoneContactList = null;
    private TextView mContactName = null;
    private TextView mContactNumber = null;
    private MaterialSearchView mSearchView = null;


    ViewHolder(){
        //do ntg
    }

    public static ViewHolder getInstance(){
        m_viewHolder = new ViewHolder();
        return m_viewHolder;
    }

    public ViewPager getViewPage(AppCompatActivity act){
        mViewPager = act.findViewById(R.id.myViewPager);
        return mViewPager;
    }

    public TabLayout getTabLayout(AppCompatActivity act){
        mTab = act.findViewById(R.id.tabs);
        return mTab;
    }

    public Button getSendButton(View view){
        mSendBtn = view.findViewById(R.id.send);
        return mSendBtn;
    }

    public Button getStartButton(View view){
        mStartBtn = view.findViewById(R.id.start);
        return mStartBtn;
    }

    public Button getStopButton(View view){
        mStopBtn = view.findViewById(R.id.stop);
        return mStopBtn;
    }

    public ListView getIpList(View view){
        mIpList = view.findViewById(R.id.ipAddressList);
        return mIpList;
    }


    public ListView getPhoneContactList(AppCompatActivity act){
        mPhoneContactList = act.findViewById(R.id.phoneContactList);
        return mPhoneContactList;
    }

    public TextView getUserContactName(View view){
        mContactName = view.findViewById(R.id.userName);
        return mContactName;
    }

    public TextView getUserContactNumber(View view){
        mContactNumber = view.findViewById(R.id.userPhoneNumber);
        return mContactNumber;
    }

    public Button getUserAddRequest(View view){
        mAddContactRequestBtn = view.findViewById(R.id.addContact);
        return mAddContactRequestBtn;
    }

    public MaterialSearchView getSearchView(AppCompatActivity act){
        if(mSearchView == null){
            mSearchView = act.findViewById(R.id.search_view);
        }
        return mSearchView;
    }
}
