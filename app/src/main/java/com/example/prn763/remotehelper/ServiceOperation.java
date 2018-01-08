package com.example.prn763.remotehelper;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;


/**
 * Created by prn763 on 12/15/2017.
 */

public class ServiceOperation extends Service {
    final static String TAG = "SERVICES";
    private Thread thread;
    private boolean mTimerThread = true;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        String timer = intent.getStringExtra("timer_in_sec");
        startTimer(Long.valueOf(timer));
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        stopTimer();
        super.onDestroy();
    }

    private void startTimer(final long timerInSec){
        final long milisec = timerInSec*1000;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                while(mTimerThread){
                    try{
                        synchronized (this){
                            wait(milisec);
                            //the state of mTimerThread might change within given time.therefore need
                            //to check after timer expired.
                            if(mTimerThread){
                                updateMainActivityGUI(getSmsContent(milisec));
                            }

                            Log.d(TAG, "Fire Timer Expired An Action");
                        }
                    }catch(Exception e){
                        Log.d(TAG, "Wait Timer Error/No read sms permission");
                    }
                }
            }
        };
        thread = new Thread(r);
        thread.start();
    }

    private void stopTimer(){
        if(thread!=null){
            mTimerThread = false;
            thread=null;
        }
    }

    public String getSmsContent(long milis){
        // public static final String INBOX = "content://sms/inbox";
        // public static final String SENT = "content://sms/sent";
        // public static final String DRAFT = "content://sms/draft";
        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);

        if (cursor.moveToFirst()) { // must check the result to prevent exception
            do {
                for(int idx=0;idx<cursor.getCount();idx++)
                {
                    //get the phone number
                    long contactNum = cursor.getLong(2);
                    Log.d(TAG, "contactNum:"+contactNum);
                    Long time = cursor.getLong(cursor.getColumnIndexOrThrow("date"));

                    if((isTimeWithinRange(time, milis) == true) && (isRegisteredContact(Long.toString(contactNum)) == true)){
                        //CharSequence timestamp = DateFormat.format("MM/dd/yyyy HH:mm:ss", new Date(time));
                        //get the last message
                        Log.d(TAG, "Found!!!");
                        return cursor.getString(cursor.getColumnIndexOrThrow("body")).toString();
                    }
                }
                // use msgData
            } while (cursor.moveToPrevious());
        } else {
            // empty box, no SMS
        }
        return null;
    }

    private void updateMainActivityGUI(final String content){
        Log.d(TAG, "***Operation Recognize as "+content+"***");
    }

    private boolean isTimeWithinRange(long time, long userSetTimeInMilis){
        boolean isWithinTime = false;
        Date currentTime = Calendar.getInstance().getTime();
        Date inboxTime = new Date(time);

        if(currentTime.getTime() - inboxTime.getTime() < userSetTimeInMilis){
            isWithinTime = true;
        }

        return isWithinTime;
    }

    private boolean isRegisteredContact(String contactNumInInbox){
        Cursor contactDB = DBHandler.getInstance(getApplicationContext()).getDataBase();

        while(contactDB.moveToNext()){
                String contactNum = contactDB.getString(2);
                contactNum = contactNum.replaceAll("[ ()-]", "");
                String inboxContactNum = contactNumInInbox.replaceFirst("6", "");
                Log.d(TAG,"contactNum:"+contactNum+" inboxContactNum:"+inboxContactNum);
                if(contactNum.equals(inboxContactNum)){
                    return true;
                }
        }
        contactDB.close();

        return false;
    }
}
