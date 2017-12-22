package com.example.prn763.remotehelper;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.facebook.login.widget.LoginButton;

/**
 * Created by prn763 on 12/17/2017.
 */

public class ViewHolder{
    private static ViewHolder m_viewHolder = null;
    private ViewPager mViewPager;
    private TabLayout mTab;
    private Button mSendBtn; //send message
    private Button mStartBtn;
    private Button mStopBtn;
    private ListView mIpList;
    private LoginButton mFacebookLoginBtn;

    ViewHolder(){
        //do ntg
    }

    public static ViewHolder getInstance(){
        if(m_viewHolder == null){
            m_viewHolder = new ViewHolder();
        }
        return m_viewHolder;
    }

    public ViewPager getViewPage(AppCompatActivity act){
        if (mViewPager == null){
            mViewPager = act.findViewById(R.id.myViewPager);
        }
        return mViewPager;

    }

    public TabLayout getTabLayout(AppCompatActivity act){
        if(mTab == null){
            mTab = act.findViewById(R.id.tabs);
        }
        return mTab;
    }

    public Button getSendButton(View view){
        if(mSendBtn == null){
            mSendBtn = view.findViewById(R.id.send);
        }
        return mSendBtn;
    }

    public Button getStartButton(View view){
        if(mStartBtn == null){
            mStartBtn = view.findViewById(R.id.start);
        }
        return mStartBtn;
    }

    public Button getStopButton(View view){
        if(mStopBtn == null){
            mStopBtn = view.findViewById(R.id.stop);
        }
        return mStopBtn;
    }

    public ListView getIpList(View view){
        if(mIpList == null){
            mIpList = view.findViewById(R.id.ipAddressList);
        }
        return mIpList;
    }

    public LoginButton getFacebookLoginButton(AppCompatActivity act){
        if(mFacebookLoginBtn == null){
            mFacebookLoginBtn = act.findViewById(R.id.login_button);
        }
        return mFacebookLoginBtn;
    }
}
