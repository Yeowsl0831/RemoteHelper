package com.example.prn763.remotehelper;



import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by prn763 on 12/15/2017.
 */

public class TabServicesOperation extends Fragment {
    final static String TAG = "TAB_SERVICE";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service_operation_tab, container, false);
        if (view != null){
            View.OnClickListener startListener = new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), ServiceOperation.class);
                    intent.putExtra("timer_in_sec", "5");
                    getContext().startService(intent);
                    //exit the app through button.
                    gotoHomeScreen();
                    //display toast message.
                    Toast.makeText(getContext(),"Service Started", Toast.LENGTH_SHORT).show();

                }
            };

            View.OnClickListener stopListener = new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), ServiceOperation.class);
                    getContext().stopService(intent);
                }
            };
            ViewHolder.getInstance().getStopButton(view).setOnClickListener(stopListener);
            ViewHolder.getInstance().getStartButton(view).setOnClickListener(startListener);

        }

        return view;
    }

    private void gotoHomeScreen(){
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}
