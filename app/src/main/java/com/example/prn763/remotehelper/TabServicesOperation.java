package com.example.prn763.remotehelper;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by prn763 on 12/15/2017.
 */

public class TabServicesOperation extends Fragment {


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
                    getContext().startService(intent);
                }
            };

            View.OnClickListener stopListener = new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), ServiceOperation.class);
                    getContext().stopService(intent);
                }
            };

            ViewHolder.getInstance().getStartButton(view).setOnClickListener(startListener);
            ViewHolder.getInstance().getStopButton(view).setOnClickListener(stopListener);
        }

        return view;
    }
}
