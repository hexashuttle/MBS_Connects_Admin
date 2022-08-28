package com.example.mbsconnectsadmin.fees;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mbsconnectsadmin.Database.FeeHelperClass;
import com.example.mbsconnectsadmin.Database.UserHelperClass;
import com.example.mbsconnectsadmin.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class yearly_fee extends AppCompatActivity {
    TextInputLayout name,fee;
    String course,type,year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yearly_fee);

        name=findViewById(R.id.fee_name);
        fee=findViewById(R.id.fee);

        course=getIntent().getStringExtra("course");
        type=getIntent().getStringExtra("type");
        year=getIntent().getStringExtra("year");

    }

    public void update1(View view) {
        if(!validationFee() | !validationName() | !connected())
        {
            return;
        }
        uploadFee();
    }



    private void uploadFee() {
        String s_name=name.getEditText().getText().toString().trim();
        String s_fee=fee.getEditText().getText().toString().trim();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference("fees_yearly");
        FeeHelperClass courseFee=new FeeHelperClass(course,year,type,s_name,s_fee,"0");
        reference.child(course).setValue(courseFee);
        Toast.makeText(this, "Fee Updated Successfully", Toast.LENGTH_SHORT).show();

    }



    public boolean validationName()
    {
        String s_name=name.getEditText().getText().toString().trim();
        String s_fee=fee.getEditText().getText().toString().trim();

        if(s_name.isEmpty())
        {
            name.setError("Input the name");
            return false;
        }
        name.setErrorEnabled(false);
        return true;
    }



    public boolean validationFee()
    {
        String s_fee=fee.getEditText().getText().toString().trim();

        if(s_fee.isEmpty())
        {
            fee.setError("Input the name");
            return false;
        }
        fee.setErrorEnabled(false);
        return true;
    }


    private boolean connected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wificonn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileconn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wificonn != null && wificonn.isConnected()) || (mobileconn != null && mobileconn.isConnected())) {
            return true;
        }
        else {
            Toast.makeText(this, "Please connect to internet", Toast.LENGTH_SHORT).show();
            return false;

        }
    }
}