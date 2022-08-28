package com.example.mbsconnectsadmin.fees;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mbsconnectsadmin.R;

public class choose_year extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_year);

        radioGroup=findViewById(R.id.year);

    }

    public void year_next(View view) {
        if(!validation())
        {
            return;
        }
        radioButton=findViewById(radioGroup.getCheckedRadioButtonId());
        String year=radioButton.getText().toString();
        year=year.substring(0,3);
        String course=getIntent().getStringExtra("course");
        String flag=getIntent().getStringExtra("flag");
        Intent intent;
        if(flag.equals("true")) {
             intent = new Intent(this, fee_option.class);
        }
        else
        {
            intent=new Intent(this,yearly_fee.class);
        }
        intent.putExtra("year",year);
        intent.putExtra("course",course);
        startActivity(intent);
    }

    public boolean validation()
    {
        if(radioGroup.getCheckedRadioButtonId()==-1)
        {
            Toast.makeText(this, "Please select a class", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}