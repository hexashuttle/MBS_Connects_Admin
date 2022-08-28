package com.example.mbsconnectsadmin.timetable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mbsconnectsadmin.R;

public class schedule_menu extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_menu);
        radioGroup=findViewById(R.id.schedule);

    }

    public void next_timetable(View view) {
        if(!RadioValidation())
        {
            return ;
        }
        radioButton=findViewById(radioGroup.getCheckedRadioButtonId());
        String timetable=radioButton.getText().toString();
        Intent intent=new Intent(this,time_table.class);
        intent.putExtra("timetable",timetable);
        startActivity(intent);
    }

    private boolean RadioValidation()
    {
        if(radioGroup.getCheckedRadioButtonId()==-1)
        {
            Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}