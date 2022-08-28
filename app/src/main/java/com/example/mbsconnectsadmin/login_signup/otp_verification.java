package com.example.mbsconnectsadmin.login_signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.mbsconnectsadmin.Database.UserHelperClass;
import com.example.mbsconnectsadmin.R;
import com.example.mbsconnectsadmin.navigation.navigation_menu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.security.AuthProvider;
import java.util.concurrent.TimeUnit;

public class otp_verification extends AppCompatActivity {

       PinView pinView;
       FirebaseAuth mAuth;

       String codebysystem,_name,_email,_password,_radio,_phone,whattoDo;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

            //hooker
            pinView=findViewById(R.id.pinview);

            //
            mAuth=FirebaseAuth.getInstance();



            //intent data
             _name = getIntent().getStringExtra("name");
             _email = getIntent().getStringExtra("email");
             _password = getIntent().getStringExtra("password");
             _radio=getIntent().getStringExtra("radio");
            _phone=getIntent().getStringExtra("phone");
            whattoDo =getIntent().getStringExtra("whattoDo");





            sendVerificationCode(_phone);

        }

    private void sendVerificationCode(String phone) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codebysystem=s;
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    String code=phoneAuthCredential.getSmsCode();
                    if(code!=null)
                    {
                        pinView.setText(code);
                        verifyCode(code);
                    }

                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(otp_verification.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            };

    private void verifyCode(String code) {
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(codebysystem,code);
        signInUsingCredentails(credential);
    }

    private void signInUsingCredentails(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information


                            if(whattoDo.equals("forgetpassword"))
                            {
                                UpdateData();
                            }

                            else {
                                uploadUserData();
                                taskComplete();
                            }

                        }
                        else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(otp_verification.this, "Error verification failed! Please try agin", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void UpdateData() {
        Intent intent=new Intent(this, newpassword_password3.class);
        intent.putExtra("phone",_phone);
        startActivity(intent);
        finish();
    }

    private void taskComplete() {
        Toast.makeText(this, "User Registered", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }




    private void uploadUserData() {
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference("users_teacher");
        UserHelperClass addNewUser=new UserHelperClass(_name,_email,_password,_radio,_phone);
        reference.child(_phone).setValue(addNewUser);

    }

    public void menu(View view) {

        String code=pinView.getText().toString();
        if(!code.isEmpty())
        {
            verifyCode(code);
        }



    }


    public void back(View view) {
        Intent intent =new Intent(this, signup_page3.class);
        startActivity(intent);
    }
}