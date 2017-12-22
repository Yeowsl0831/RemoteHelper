package com.example.prn763.remotehelper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import static android.content.Context.WIFI_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link access_provider_tab.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link access_provider_tab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabAccessProvider extends Fragment{
    private static final String TAG = "ACCESS_TAB";
    private String ipList[] = {"123.323.12.121", "45.32.564.21", "565.212.32.442"};
    private ListAdapter listAdapter = null;
    private View view = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_access_provider_tab, container, false);

        //setup the send button
        View.OnClickListener onClickSendBtn = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        };
        ViewHolder.getInstance().getSendButton(view).setOnClickListener(onClickSendBtn);

        //set up list view for ip address
        listAdapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, ipList);
        ViewHolder.getInstance().getIpList(view).setAdapter(listAdapter);

        AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "List item selected");
                showDialogListView(view);
            }
        };
        ViewHolder.getInstance().getIpList(view).setOnItemClickListener(onItemClick);

        return view;
    }

    public void showDialogListView(View view){
        String feature[] = {"ShutDown Phone", "Track Location", "Hacking"};
        //setup the listener to pop up list
        DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                Log.d(TAG, "U have handle the selected item");
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle("Choose Your Option:");
        builder.setSingleChoiceItems(feature, 0, null);
        builder.setPositiveButton("Send", dialogListener);
        builder.show();
    }

    public void sendMessage(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, getWiFiIpAddress() + "/" + getPhoneNumber());
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    public String getWiFiIpAddress(){
        WifiManager wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(WIFI_SERVICE);
        String ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        return ipAddress;
    }

    public String getPhoneNumber(){
        String phoneNum = "invalid number!";
        TelephonyManager tMgr = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        try{
            phoneNum = tMgr.getLine1Number();
        }catch (SecurityException se){
            Log.d("AccessTab", "Invalid Phone Number");
        }

        return phoneNum;
    }
}
