package com.example.mbsconnectsadmin.login_signup;

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
import com.example.mbsconnectsadmin.navigation.navigation_menu;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class newpassword_password3 extends AppCompatActivity {

    TextInputLayout new_password,confirm_password;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpassword_password3);

        //hookers
        new_password=findViewById(R.id.new_password);
        confirm_password=findViewById(R.id.confirm_password);
        progressBar=findViewById(R.id.bar2);
    }




    public void chamge_password(View view) {

        if(!validationPassword())
        {
            return;
        }
        if(!confirm_pass())
        {
            Toast.makeText(this, "Password didn't match", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!connected())
        {
            Toast.makeText(this, "Please check your internet", Toast.LENGTH_SHORT).show();
        }

        progressBar.setVisibility(View.VISIBLE);
        String number=getIntent().getStringExtra("phone");

        String password1=new_password.getEditText().getText().toString().trim();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users_teacher");
        reference.child(number).child("_password").setValue(password1);

        Intent intent=new Intent(getApplicationContext(), password_update.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();


    }

    private boolean confirm_pass() {
        String password1=new_password.getEditText().getText().toString().trim();

        String password2=confirm_password.getEditText().getText().toString().trim();
        if(password1.equals(password2))
        {
            return true;
        }
        return false;
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


    public boolean validationPassword() {
        String stu =new_password.getEditText().getText().toString().trim();

        if (stu.isEmpty()) {
            new_password.setError("Input the password");
            return false;
        }
        else if(stu.contains(" "))
        {
            new_password.setError("Whitespaces are not allowed");
            return false;
        }
        else if(stu.length()<8)
        {
            new_password.setError("Password should contain atleast 8 characters");
            return false;
        }
        else
        {
            new_password.setError(null);
            new_password.setErrorEnabled(false);
            return true;
        }
    }
}