package com.example.mbsconnectsadmin.support;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import com.example.mbsconnectsadmin.R;
import com.google.android.material.textfield.TextInputLayout;

public class support_activity extends AppCompatActivity {

    TextInputLayout email_body;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        //hookers

        email_body=findViewById(R.id.email);




    }

    public void send_mail(View view) {
        if(!connected())
        {
            Toast.makeText(this, "Please connect to internet", Toast.LENGTH_SHORT).show();
        }

       if(!validation_body())
       {
           return;
       }

        String address="utkarshrajmishra811545@gmail.com";

        String body=email_body.getEditText().getText().toString().trim();


        Intent it = new Intent(Intent.ACTION_SEND);
        it.putExtra(Intent.EXTRA_EMAIL, new String[]{address});
        it.putExtra(Intent.EXTRA_SUBJECT,"Bug in MBS Connects Admin");
        it.putExtra(Intent.EXTRA_TEXT,body);
        it.setType("message/rfc822");
        startActivity(Intent.createChooser(it,"Choose Mail App"));

    }


    public boolean validation_body()
    {
        String body=email_body.getEditText().getText().toString().trim();

        if(body.isEmpty())
        {
            email_body.setError("Input the body");
            return false;
        }
        email_body.setErrorEnabled(false);
        return true;
    }



    private boolean connected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wificonn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileconn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wificonn != null && wificonn.isConnected()) || (mobileconn != null && mobileconn.isConnected())) {
            return true;
        } else {

            return false;

        }
    }

}