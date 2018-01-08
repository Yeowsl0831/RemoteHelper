package com.example.prn763.remotehelper;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prn763 on 12/26/2017.
 */

public class Searchable extends AppCompatActivity {
    static final String TAG = "SEARCH";
    Toolbar toolbar;
    myCustomAdapter phoneListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        //if permission granted, will performance to display the phone contact at permission result
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},1);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);






    }

    public void setSearchView(){

        ViewHolder.getInstance().getSearchView(this).setSuggestions(getContactList().toArray(new String[getContactList().size()]));
        ViewHolder.getInstance().getSearchView(this).setEllipsize(true);
        ViewHolder.getInstance().getSearchView(this).setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String [] contactDetails = query.split(":");
                showDialogListView(contactDetails[0], contactDetails[1]);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "onQueryTextChange:"+newText);
                return false;
            }
        });

        ViewHolder.getInstance().getSearchView(this).setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.search_action);
        ViewHolder.getInstance().getSearchView(this).setMenuItem(item);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search_action:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private List<String> getContactList() {
        List<String> phoneList = new ArrayList<>();
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            phoneList.add(name+":"+phoneNumber);
        }
        phones.close();

        return phoneList;
    }

    public void showDialogListView(final String name, final String phoneNum){
        //setup the listener to pop up list
        DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                DBHandler.getInstance(getApplicationContext()).insertDatabase(name, phoneNum);
                Intent i = new Intent(Searchable.this, MainActivity.class);
                i.putExtra("is_From_Search_Activity", true);
                startActivity(i);
                finish();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Add Contact");
        builder.setMessage("Add ["+name+"] with ["+phoneNum+"] to your list?");
        builder.setPositiveButton("Confirm", dialogListener);
        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case 1: {
                //set up phone list
                phoneListAdapter = new myCustomAdapter();
                ViewHolder.getInstance().getPhoneContactList(this).setAdapter(phoneListAdapter);

                setSearchView();

                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        phoneListAdapter=null;
        this.finish();
    }

    public class myCustomAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return getContactList().size();
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
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.contact_list_layout, viewGroup, false);

            final String [] contactDetails = getContactList().get(i).split(":");

            ViewHolder.getInstance().getUserContactName(view).setText(contactDetails[0]);
            ViewHolder.getInstance().getUserContactNumber(view).setText(contactDetails[1]);

            Button onBtn = ViewHolder.getInstance().getUserAddRequest(view);

            View.OnClickListener onClick = new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    showDialogListView(contactDetails[0], contactDetails[1]);
                }
            };
            onBtn.setOnClickListener(onClick);
            return view;
        }
    }
}
