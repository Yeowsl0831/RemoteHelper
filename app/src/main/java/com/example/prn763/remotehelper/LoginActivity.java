package com.example.prn763.remotehelper;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by prn763 on 12/21/2017.
 */

public class LoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.login);


        callbackManager = CallbackManager.Factory.create();
        ViewHolder.getInstance().getFacebookLoginButton(this).registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);

                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        displayUserInfo(object);
                    }
                });
                Bundle parameter = new Bundle();
                parameter.putString("fields", "first_name, last_name, email, id");
                graphRequest.setParameters(parameter);
                graphRequest.executeAsync();
                Log.d("Main", "Login Success");
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),"Cancelled!",Toast.LENGTH_SHORT).show();
                Log.d("Main", "Login Cancelled");
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(),"On Error!",Toast.LENGTH_SHORT).show();
                Log.d("Main", "Login Errored");
            }
        });
    }

    private void displayUserInfo(JSONObject object) {
        String first_name, last_name, email, id;
        try {
            first_name = object.getString("first_name");
            last_name = object.getString("last_name");
            email = object.getString("email");
            id = object.getString("id");

            Toast.makeText(getApplicationContext(), email, Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
