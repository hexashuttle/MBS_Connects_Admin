package com.example.mbsconnectsadmin.fees;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mbsconnectsadmin.R;

public class course_activity extends AppCompatActivity {
   private RadioGroup radioGroup;
    private  RadioButton radioButton;
    RadioButton bba,bca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        //hookers
        radioGroup=findViewById(R.id.courses);
        bba=findViewById(R.id.bba);
        bca=findViewById(R.id.bca);
    }

    public void fee_next(View view) {
        if (!validation()) {
            return;
        }
        radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
        String _text = radioButton.getText().toString();
        Toast.makeText(this, " "+_text, Toast.LENGTH_SHORT).show();
        Intent intent;
        _text=_text.substring(0,3);
        String flag="false";
        if(bba.isChecked() || bca.isChecked())
        { flag="true";

        }

            intent = new Intent(this, choose_year.class);


        intent.putExtra("course", _text);
        intent.putExtra("flag", flag);
        startActivity(intent);
    }



    public boolean validation()
    {

        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select a course", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;

    }
    }

}