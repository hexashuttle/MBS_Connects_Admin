package com.example.mbsconnectsadmin.live_class;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mbsconnectsadmin.R;
import com.google.android.material.textfield.TextInputLayout;


import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class create_class extends AppCompatActivity {

    TextInputLayout room_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);
        room_code = findViewById(R.id.code);


        try {
            JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(new URL(""))
                    .setFeatureFlag("welcomepage.enabled", false)
                    .setFeatureFlag("invite.enabled",false)
                    .setFeatureFlag("help.enabled",false)
                    .build();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public void create_room(View view) {

        if(!connected())
        {
            Toast.makeText(this, "Please connect to internet", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!validation()) {
            return;
        }
           String text=room_code.getEditText().getText().toString().trim();
            JitsiMeetConferenceOptions options
                    = new JitsiMeetConferenceOptions.Builder()
                    .setRoom(text)
                    .setFeatureFlag("invite.enabled",false)
                    .setFeatureFlag("help.enabled",false)
                    .build();
            JitsiMeetActivity.launch(this, options);

    }


    public boolean validation()
    {
         String stu=room_code.getEditText().getText().toString().trim();

        if(stu.isEmpty())
        {
            room_code.setError("Input the room code");
            return false;
        }
        room_code.setErrorEnabled(false);
        return true;
    }


    public void share(View view) {
        if(!validation())
        {
            return;
        }
        PackageManager pm=getPackageManager();
        try {

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String text = "Room Code to join class:  ";
            String code=room_code.getEditText().getText().toString().trim();
            text+=code;


            PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(waIntent, "Share with"));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "Please install whatsapp", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean connected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wificonn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileconn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wificonn != null && wificonn.isConnected()) || (mobileconn != null && mobileconn.isConnected())) {
            return true;
        }
        else {

            return false;

        }
    }
}