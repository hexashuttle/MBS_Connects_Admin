package com.example.mbsconnectsadmin.assigment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mbsconnectsadmin.R;

public class assigment_options extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigment_options);
        radioGroup=findViewById(R.id.assign_radioGroup);
    }

    public boolean RadioValidation()
    {
        if (radioGroup.getCheckedRadioButtonId()==-1)
        {
            Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean connected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wificonn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileconn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wificonn != null && wificonn.isConnected()) || (mobileconn != null && mobileconn.isConnected())) {
            return true;
        } else {
            Toast.makeText(this, "Please connect to internet", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void next_labwork(View view) {
        if(!RadioValidation() | !connected())
            {
                return;
            }
        radioButton=findViewById(radioGroup.getCheckedRadioButtonId());
        String work=radioButton.getText().toString();
        Intent intent=new Intent(this,labwork.class);
        intent.putExtra("work",work);
        startActivity(intent);
    }
}