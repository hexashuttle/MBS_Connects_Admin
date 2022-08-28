package com.example.mbsconnectsadmin.login_signup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mbsconnectsadmin.MainActivity;
import com.example.mbsconnectsadmin.R;

public class sign_up_page2 extends AppCompatActivity {

    RadioGroup radioGroup,select_text;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page2);


        //hookers
        radioGroup=findViewById(R.id.radio_group);


    }

    public void signup_next(View view) {

        String _name = getIntent().getStringExtra("name");
        String _email = getIntent().getStringExtra("email");
        String _password = getIntent().getStringExtra("password");



        if(!validationRadioButton())
        {
            return;
        }
        radioButton=findViewById(radioGroup.getCheckedRadioButtonId());
        String _text=radioButton.getText().toString();

        Intent intent = new Intent(getApplicationContext(), signup_page3.class);
        intent.putExtra("name",_name);
        intent.putExtra("email",_email);
        intent.putExtra("password",_password);
        intent.putExtra("radio",_text);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.next_btn), "sign_tranition");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(sign_up_page2.this, pairs);
            startActivity(intent, activityOptions.toBundle());
        } else {
            startActivity(intent);
        }
    }

    public void back(View view) {
        Intent intent=new Intent(this,sign_up.class);
        startActivity(intent);
    }


    public boolean validationRadioButton() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select a designation", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}