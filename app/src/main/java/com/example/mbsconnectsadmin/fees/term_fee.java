package com.example.mbsconnectsadmin.fees;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mbsconnectsadmin.Database.FeeHelperClass;
import com.example.mbsconnectsadmin.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class term_fee extends AppCompatActivity {
    TextInputLayout name,term1_fee,term2_fee;

    String course,type,year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_fee);
        name=findViewById(R.id.fee_name1);
        term1_fee=findViewById(R.id.fee1);
        term2_fee=findViewById(R.id.fee2);

        course=getIntent().getStringExtra("course");
        type=getIntent().getStringExtra("type");
        year=getIntent().getStringExtra("year");
    }

    public void update(View view) {
        if(!validationTerm1() | !validationName() | !validationTerm2() | !connected())
        {
            return;
        }
        uploadFee();
    }




    private void uploadFee() {
        String s_name=name.getEditText().getText().toString().trim();
        String s_fee1=term1_fee.getEditText().getText().toString().trim();
        String s_fee2=term2_fee.getEditText().toString().trim();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference("fees_installment");
        FeeHelperClass courseFee=new FeeHelperClass(course,year,type,s_name,s_fee1,s_fee2);
        reference.child(course).setValue(courseFee);
        Toast.makeText(this, "Fee Updated Successfully", Toast.LENGTH_SHORT).show();
    }



    public boolean validationName()
    {
        String s_name=name.getEditText().getText().toString().trim();


        if(s_name.isEmpty())
        {
            name.setError("Input the name");
            return false;
        }
        name.setErrorEnabled(false);
        return true;
    }



    public boolean validationTerm1()
    {
        String s_fee=term1_fee.getEditText().getText().toString().trim();

        if(s_fee.isEmpty())
        {
            term1_fee.setError("Input the name");
            return false;
        }
        term1_fee.setErrorEnabled(false);
        return true;
    }





    public boolean validationTerm2()
    {
        String s_fee=term2_fee.getEditText().getText().toString().trim();

        if(s_fee.isEmpty())
        {
            term2_fee.setError("Input the name");
            return false;
        }
        term2_fee.setErrorEnabled(false);
        return true;
    }



    private boolean connected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wificonn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileconn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wificonn != null && wificonn.isConnected()) || (mobileconn != null && mobileconn.isConnected())) {
            return true;
        }
        else {
            Toast.makeText(this, "Please connect to internet", Toast.LENGTH_SHORT).show();
            return false;

        }
    }
}