package com.example.mbsconnectsadmin.login_signup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Pair;
import android.view.View;
import android.widget.Button;

import com.example.mbsconnectsadmin.MainActivity;
import com.example.mbsconnectsadmin.R;
import com.google.android.material.textfield.TextInputLayout;

public class sign_up extends AppCompatActivity {

    Button next,login;

    TextInputLayout name,email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //hookers
        name=findViewById(R.id.full_name);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        next=findViewById(R.id.next_btn);
        login=findViewById(R.id.login_btn);

    }

    public void signup_next(View view) {

        if(!validationName() | !validationEmail() | !validationPassword())
        {
            return;
        }

        String n = name.getEditText().getText().toString().trim();
        String e = email.getEditText().getText().toString().trim();
        String p = password.getEditText().getText().toString().trim();

        Intent intent=new Intent(getApplicationContext(),sign_up_page2.class);
        intent.putExtra("name",n);
        intent.putExtra("email",e);
        intent.putExtra("password",p);
        Pair[] pairs=new Pair[2];
        pairs[0]=new Pair<View,String>(next,"sign_transition");
        pairs[1]=new Pair<View,String>(next,"login_tranition");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(sign_up.this, pairs);
            startActivity(intent, activityOptions.toBundle());
        } else {
            startActivity(intent);
        }

    }

    public void login_button(View view) {
        Intent intent=new Intent(getApplicationContext(),login.class);

            startActivity(intent);

    }


    //validation functions
    public boolean validationName() {
        String stu = name.getEditText().getText().toString().trim();
        if (stu.isEmpty()) {
            name.setError("Input the name");
            return false;
        } else {
            name.setError(null);
            name.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validationEmail() {
        String stu = email.getEditText().getText().toString().trim();
        String checkString="[a-zA-Z0-9.-_]+@[a-z]+\\.+[a-z]+";
        if (stu.isEmpty()) {
            email.setError("Input the email");
            return false;
        }
        else if(!stu.matches(checkString))
        {
            email.setError("Email badly formatted");
            return false;
        }
        else
        {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validationPassword() {
        String stu =password.getEditText().getText().toString().trim();

        if (stu.isEmpty()) {
            password.setError("Input the password");
            return false;
        }
        else if(stu.contains(" "))
        {
            password.setError("Whitespaces are not allowed");
            return false;
        }
        else if(stu.length()<8)
        {
            password.setError("Password should contain atleast 8 characters");
            return false;
        }
        else
        {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }


}