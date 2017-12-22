package com.example.prn763.remotehelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



public class MainActivity extends AppCompatActivity {
    private MyPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        ViewHolder.getInstance().getTabLayout(this).setupWithViewPager(ViewHolder.getInstance().getViewPage(this));
        ViewHolder.getInstance().getViewPage(this).setAdapter(mPagerAdapter);

    }
}
