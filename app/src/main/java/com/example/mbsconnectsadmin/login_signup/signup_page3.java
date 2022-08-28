package com.example.mbsconnectsadmin.login_signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mbsconnectsadmin.R;
import com.example.mbsconnectsadmin.navigation.navigation_menu;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

public class signup_page3 extends AppCompatActivity {

    TextInputLayout phone_no;
    CountryCodePicker codePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page3);

        //Hookers
        phone_no=findViewById(R.id.phonenumber);
        codePicker=findViewById(R.id.country_code);
    }

    public void back(View view) {
        Intent intent=new Intent(this, sign_up_page2.class);
        startActivity(intent);
    }

    public void signup_next(View view) {

        if(!validation())
        {
            return;
        }

        String number_phone=phone_no.getEditText().getText().toString().trim();
        String _phone_number="+"+codePicker.getSelectedCountryCode()+number_phone;


        String _name = getIntent().getStringExtra("name");
        String _email = getIntent().getStringExtra("email");
        String _password = getIntent().getStringExtra("password");
        String _radio=getIntent().getStringExtra("radio");



        Intent intent=new Intent(this, otp_verification.class);

        intent.putExtra("name",_name);
        intent.putExtra("email",_email);
        intent.putExtra("password",_password);
        intent.putExtra("radio",_radio);
        intent.putExtra("phone",_phone_number);
        intent.putExtra("whattoDo","hey");

        startActivity(intent);
    }


    public boolean validation()
    {
        String stu = phone_no.getEditText().getText().toString().trim();
        if (stu.isEmpty()) {
            phone_no.setError("Input the phone number");
            return false;
        }
        else if(stu.length()<10 || stu.length()>10)
        {
            phone_no.setError("Phone number should be of 10 digits");
            return false;
        }
        else {
            phone_no.setError(null);
            phone_no.setErrorEnabled(false);
            return true;
        }
    }
}