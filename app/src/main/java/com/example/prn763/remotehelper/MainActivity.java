package com.example.prn763.remotehelper;

import android.content.Intent;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;


public class MainActivity extends AppCompatActivity {
    private MyPagerAdapter mPagerAdapter;
    private final static String TAG = "Main_Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        ViewHolder.getInstance().getTabLayout(this).setupWithViewPager(ViewHolder.getInstance().getViewPage(this));
        ViewHolder.getInstance().getViewPage(this).setAdapter(mPagerAdapter);

        Intent intent = getIntent();
        if(intent.getBooleanExtra("is_From_Search_Activity", false)){
            ViewHolder.getInstance().getTabLayout(this).getTabAt(1).select();
            //reset flag
            intent.putExtra("is_From_Search_Activity", false);

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(this, ServiceOperation.class);
            stopService(intent);
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "Kill MainActivity");
        Intent intent = new Intent(this, ServiceOperation.class);
        stopService(intent);
        //Process.killProcess(Process.myPid());
        super.onDestroy();
    }
}
