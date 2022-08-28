package com.example.mbsconnectsadmin.teacher_data;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mbsconnectsadmin.R;
import com.example.mbsconnectsadmin.navigation.navigation_menu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class update_teacher extends AppCompatActivity {

    private ImageView teacher_image;
    TextInputEditText teacher_name, teacher_mail,teacher_number;
    String tname,tmail, tnumber;
    int REQ=1;
    Bitmap bitmap;
    String downloadUrl="";
    String name,email,number,image;
    ProgressBar progressBar;
    private DatabaseReference reference,dbef;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_teacher);

        teacher_image=findViewById(R.id.update_teacher_image);
        teacher_name=findViewById(R.id.updateteachername);
        teacher_mail=findViewById(R.id.updateteachermail);
        teacher_number=findViewById(R.id.update_teacher_number);

        progressBar=findViewById(R.id.progress);

        name=getIntent().getStringExtra("name");
        email=getIntent().getStringExtra("mail");
        number=getIntent().getStringExtra("number");
        image=getIntent().getStringExtra("image");

        reference= FirebaseDatabase.getInstance().getReference().child("teacher");
        storageReference= FirebaseStorage.getInstance().getReference().child("teacher");

        try {
            Picasso.get().load(image).into(teacher_image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        teacher_name.setText(name);
        teacher_mail.setText(email);
        teacher_number.setText(number);

    }

    public void upload_teacher(View view) {
    }

    public void delete_teacher(View view) {
        String key=getIntent().getStringExtra("key");
        String category=getIntent().getStringExtra("category");
        reference.child(category).child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(update_teacher.this, "Teacher Delete Successfully", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(update_teacher.this, navigation_menu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(update_teacher.this, "Error! "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    }
