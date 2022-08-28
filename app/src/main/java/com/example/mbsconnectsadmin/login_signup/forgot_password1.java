package com.example.mbsconnectsadmin.login_signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mbsconnectsadmin.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class forgot_password1 extends AppCompatActivity {

    TextInputLayout userphone_number;
    CountryCodePicker code;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password2);

        //hookers
        userphone_number=findViewById(R.id.phonenumber_forgetpassword);
        code=findViewById(R.id.country_code_forgot);
         progressBar=findViewById(R.id.bar);
    }

    public void next(View view) {

        if(!validation())
        {
            return;
        }

        if(!checkConnection())
        {
            Toast.makeText(this, "Please Connect to internet", Toast.LENGTH_SHORT).show();
            return;
        }

        String number=userphone_number.getEditText().getText().toString().trim();
        if(number.charAt(0)=='0') {
            number=number.substring(1);
        }
        String _phone_number = "+" + code.getSelectedCountryCode() +number;
        Query checkUser= FirebaseDatabase.getInstance().getReference("users_teacher").orderByChild("_phone").equalTo(_phone_number);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    userphone_number.setError(null);
                    userphone_number.setErrorEnabled(false);

                    Intent intent=new Intent(getApplicationContext(),otp_verification.class);
                    intent.putExtra("phone",_phone_number);
                    intent.putExtra("whattoDo","forgetpassword");
                    startActivity(intent);

                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(forgot_password1.this, "User doesn't exist", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(forgot_password1.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


    public Boolean validation()
    {
        String stu = userphone_number.getEditText().getText().toString().trim();
        if (stu.isEmpty()) {
            userphone_number.setError("Input the phone number");
            return false;
        }
        else if(stu.length()<10 || stu.length()>10)
        {
            userphone_number.setError("Phone number should be of 10 digits");
            return false;
        }
        else {
            userphone_number.setError(null);
            userphone_number.setErrorEnabled(false);
            return true;
        }
    }


    public Boolean checkConnection()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wificonn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileconn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wificonn != null && wificonn.isConnected()) || (mobileconn != null && mobileconn.isConnected())) {
            return true;
        } else {

            return false;

        }
    }



    public void back(View view) {
        Intent intent=new Intent(this, login.class);
        startActivity(intent);
    }
}