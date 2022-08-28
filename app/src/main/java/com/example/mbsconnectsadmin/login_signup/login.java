package com.example.mbsconnectsadmin.login_signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.renderscript.ScriptGroup;
import android.se.omapi.Session;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.mbsconnectsadmin.Database.SessionManager;
import com.example.mbsconnectsadmin.R;
import com.example.mbsconnectsadmin.navigation.navigation_menu;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;

public class login extends AppCompatActivity {

    CountryCodePicker login_pinview;
    TextInputLayout  phone_number,pass_login;
    ProgressBar progressBar;
    CheckBox remember_box;
    TextInputEditText phoneNumberEditText,passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //hookers
        pass_login=findViewById(R.id.login_password);
        progressBar=findViewById(R.id.progressbar);
        login_pinview=findViewById(R.id.login_country_code);
        phone_number=findViewById(R.id.login_phonenumber);
        remember_box=findViewById(R.id.remember_me);
        phoneNumberEditText=findViewById(R.id.phone_session);
        passwordEditText=findViewById(R.id.pass_session);


        // Check weather phone number and password is already saved in Shared Preferences or not
        SessionManager sessionManager = new SessionManager(login.this,SessionManager.SESSION_REMEMBERME);
        if(sessionManager.checkRememberMe()){
            HashMap<String,String> rememberMeDetail=sessionManager.getRemeberMeDetailsFromSession();
            phoneNumberEditText.setText(rememberMeDetail.get(SessionManager.KEY_SESSIONPHONENUMBER));
            passwordEditText.setText(rememberMeDetail.get(SessionManager.KEY_SESSIONPASSWORD));
        }

    }

    //userlogin
    public void letUserLogin()
    {
        if(!validation() | !validationPassword())
        {
            return;
        }
        if(!connected())
        {
            Toast.makeText(this, "Please connect to internet", Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        String pass=pass_login.getEditText().getText().toString().trim();
        String number_phone=phone_number.getEditText().getText().toString().trim();
        if(number_phone.charAt(0)=='0') {
                number_phone=number_phone.substring(1);
        }
        String _phone_number = "+" + login_pinview.getSelectedCountryCode() + number_phone;


        //rememberMe checkbox sessions
        if(remember_box.isChecked())
        {
            SessionManager sessionManager = new SessionManager(login.this,SessionManager.SESSION_REMEMBERME);
            sessionManager.createRememberMeSession(number_phone,pass);
        }




        Query checkUser= FirebaseDatabase.getInstance().getReference("users_teacher").orderByChild("_phone").equalTo(_phone_number);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    phone_number.setError(null);
                    phone_number.setErrorEnabled(false);

                    String system_password=snapshot.child(_phone_number).child("_password").getValue(String.class);
                    if(system_password.equals(pass))
                    {
                        pass_login.setError(null);
                        pass_login.setErrorEnabled(false);

                        Toast.makeText(login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(), navigation_menu.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(login.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(login.this, "User doesn't exist", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(login.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }


    public void forgetpassword(View view) {
        Intent intent=new Intent(this,forgot_password1.class);
        startActivity(intent);
    }

    public void create_account(View view) {
        Intent intent=new Intent(this,sign_up.class);
         startActivity(intent);
    }







    public boolean validation()
    {
        String stu = phone_number.getEditText().getText().toString().trim();
        if (stu.isEmpty()) {
            phone_number.setError("Input the phone number");
            return false;
        }
        else if(stu.length()<10 || stu.length()>10)
        {
            phone_number.setError("Phone number should be of 10 digits");
            return false;
        }
        else {
            phone_number.setError(null);
            phone_number.setErrorEnabled(false);
            return true;
        }
    }



    public boolean validationPassword() {
        String stu =pass_login.getEditText().getText().toString().trim();

        if (stu.isEmpty()) {
            pass_login.setError("Input the password");
            return false;
        }
        else if(stu.contains(" "))
        {
            pass_login.setError("Whitespaces are not allowed");
            return false;
        }
        else if(stu.length()<8)
        {
            pass_login.setError("Password should contain 8 characters");
            return false;
        }
        else
        {
            pass_login.setError(null);
            pass_login.setErrorEnabled(false);
            return true;
        }
    }

    public void login(View view) {
        
        if(!connected())
        {
            Toast.makeText(this, "Please connect to internet", Toast.LENGTH_SHORT).show();
            return;
        }

        letUserLogin();
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