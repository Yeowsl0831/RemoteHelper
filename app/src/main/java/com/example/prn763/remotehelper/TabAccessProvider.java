package com.example.prn763.remotehelper;

import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;


import java.util.ArrayList;
import java.util.List;

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
    private PhoneListCustomAdapter phoneListCustomAdapter;
    private View view = null;
    private int mFeatureListPosition;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_access_provider_tab, container, false);

        //setup the send button
        View.OnClickListener onClickSendBtn = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), Searchable.class);
                i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
                getActivity().finish();

            }
        };
        ViewHolder.getInstance().getSendButton(view).setOnClickListener(onClickSendBtn);

        setupAddedContactList(view);

        return view;
    }

    public void setupAddedContactList(View view){

        List<String> phoneList = getDateBasePhoneList();

        phoneListCustomAdapter = new PhoneListCustomAdapter(phoneList);

        ViewHolder.getInstance().getIpList(view).setAdapter(phoneListCustomAdapter);
    }

    public List<String> getDateBasePhoneList(){
        Log.d(TAG, "Launched PhoneListCustomAdapter");
        List<String> phoneList = new ArrayList<>();
        Cursor dbCursor = DBHandler.getInstance(getActivity()).getDataBase();
        while (dbCursor.moveToNext())
        {
            String name=dbCursor.getString(1);
            String phoneNumber = dbCursor.getString(2);
            phoneList.add(name+":"+phoneNumber);
            Log.d(TAG, name);
        }
        dbCursor.close();

        return phoneList;
    }

    public void setPosition(int position){
        mFeatureListPosition = position;
    }
    public int getPosition(){
        return mFeatureListPosition;
    }
    public void showDialogListView(final String phoneNum, final String msg){

        final String feature[] = {"ShutDown Phone", "Track Location", "Hacking", "Delete Contact"};
        //setup the listener to pop up list
        DialogInterface.OnClickListener sendDialogListener = new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                sendMessage(phoneNum, feature[getPosition()]);
            }
        };

        DialogInterface.OnClickListener listDialogListener = new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                setPosition(which);
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle("Choose Your Option:");
        builder.setSingleChoiceItems(feature, 0, listDialogListener);
        builder.setPositiveButton("Send", sendDialogListener);
        builder.show();
    }

    public void sendMessage(String phoneNum, String Msg){
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNum,null,Msg, null, null);
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

    public class PhoneListCustomAdapter extends BaseAdapter{
        List<String> phoneList;

        PhoneListCustomAdapter(List<String> list){
            phoneList = list;
        }

        @Override
        public int getCount() {
            int num = 0;
            if (phoneList != null){
                num = phoneList.size();
            }
            return num;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.contact_list_layout, viewGroup, false);

            if(phoneList != null){
                final String [] contactDetails = phoneList.get(i).split(":");

                ViewHolder.getInstance().getUserContactName(view).setText(contactDetails[0]);
                ViewHolder.getInstance().getUserContactNumber(view).setText(contactDetails[1]);


                Button send = view.findViewById(R.id.addContact);
                send.setText("Send");

                View.OnClickListener onclick = new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        showDialogListView(contactDetails[1].replaceAll("[ +()-]", ""), "Location");
                    }
                };
                send.setOnClickListener(onclick);

            }else{
                    Log.d(TAG, "PhoneListCustomAdapter->getView() Error Occurred.");
            }
            return view;
        }
    }
}
