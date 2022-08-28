package com.example.mbsconnectsadmin.fees;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mbsconnectsadmin.R;

public class fee_option extends AppCompatActivity {
    private RadioGroup radioGroup;
    private RadioButton radioButton,year_wise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_option);

        //hookers
        radioGroup=findViewById(R.id.fee_style);
        year_wise=findViewById(R.id.year_fee);
    }

    public void final_fee(View view) {

       if(!validation())
       {
           return;
       }
        String course=getIntent().getStringExtra("course");
        String year=getIntent().getStringExtra("year");
        radioButton=findViewById(radioGroup.getCheckedRadioButtonId());
        String type=radioButton.getText().toString();
        Intent intent;
        if(year_wise.isChecked()) {
            intent = new Intent(this, yearly_fee.class);
        }
        else
        {
            intent = new Intent(this, term_fee.class);
        }
        intent.putExtra("course",course);
        intent.putExtra("year",year);
        intent.putExtra("type",type);
        startActivity(intent);


    }

    public boolean validation()
    {
        if(radioGroup.getCheckedRadioButtonId()==-1)
        {
            Toast.makeText(this, "Please choose the fee style", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}