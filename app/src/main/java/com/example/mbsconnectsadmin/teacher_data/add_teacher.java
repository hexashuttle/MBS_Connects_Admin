package com.example.mbsconnectsadmin.teacher_data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mbsconnectsadmin.Database.teacher_Data;
import com.example.mbsconnectsadmin.MainActivity;
import com.example.mbsconnectsadmin.R;
import com.example.mbsconnectsadmin.navigation.navigation_menu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class add_teacher extends AppCompatActivity {
    String item="";
    String [] items={"BSc","BCom","BCA","BBA","MCom","MSc"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;
    TextInputLayout name,email,number;
    String department="";
    int REQ = 1;
    ImageView teacher_image;
    private Bitmap bitmap;
    String teach_name,teach_mail,teach_number,post,downloadUrl="";

    private ProgressBar progressBar;
    private DatabaseReference reference,dbef;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);


        //hookers
        autoCompleteTxt = findViewById(R.id.auto_complete_txt_department);
        adapterItems = new ArrayAdapter<String>(this,R.layout.drop_down,items);
        autoCompleteTxt.setAdapter(adapterItems);
        name=findViewById(R.id.teacher_name);
        email=findViewById(R.id.teache_email);
        number=findViewById(R.id.teacher_number);
        progressBar=findViewById(R.id.teacher_progress);
        teacher_image = findViewById(R.id.addtechaerimage);



        reference= FirebaseDatabase.getInstance().getReference().child("teacher");
        storageReference= FirebaseStorage.getInstance().getReference().child("teacher");



        //DropDown
        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item = parent.getItemAtPosition(position).toString();
                department=item;
            }
        });


        teacher_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

    }

    public void upload_teacher(View view) {
        if(!Name_validation() | !Email_validation() | !Number_validation() | !Department_validation() | !connected())
        {
            return;
        }
        if(bitmap==null)
        {
            Toast.makeText(this, "Please choose an image", Toast.LENGTH_SHORT).show();
            return;
        }
        insertImage();

    }

    private void insertImage() {
        progressBar.setVisibility(View.VISIBLE);
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalImage=baos.toByteArray();
        final StorageReference filePath;
        filePath=storageReference.child(finalImage+"jpg");
        final UploadTask uploadTask=filePath.putBytes(finalImage);
        uploadTask.addOnCompleteListener(add_teacher.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful())
                {
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl=String.valueOf(uri);
                                    insertData();
                                }
                            });
                        }
                    });
                }
                else
                {
                    Toast.makeText(add_teacher.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void insertData() {
        dbef=reference.child(item);


        final String uniquekey=dbef.push().getKey();
        teach_name=name.getEditText().getText().toString().trim();
        teach_mail=email.getEditText().getText().toString().trim();
        teach_number=number.getEditText().getText().toString().trim();



        teacher_Data teacherData=new teacher_Data(teach_name,teach_mail,teach_number,downloadUrl,uniquekey);
        dbef.child(uniquekey).setValue(teacherData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(add_teacher.this, "Teacher added successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(add_teacher.this, "Error! "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




    }


    private void openGallery() {


        Intent pickimage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickimage, REQ);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            teacher_image.setImageBitmap(bitmap);
        }
    }



    public boolean Name_validation()
    {
        String teacher_name=name.getEditText().getText().toString().trim();
        if(teacher_name.isEmpty())
        {
            name.setError("Input teacher name");
            return false;
        }
        name.setErrorEnabled(false);
        return true;
    }


    public boolean Email_validation()
    {
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


    public boolean Number_validation()
    {
        String teacher_number=number.getEditText().getText().toString().trim();
        if(teacher_number.isEmpty())
        {
            number.setError("Input the phone number");
            return false;
        }
        else if(teacher_number.length()!=10)
        {
            number.setError("Phone number must be of 10 digits");
            return false;
        }
        number.setErrorEnabled(false);
        return true;
    }

    public boolean Department_validation()
    {
      if(department.equals(""))
      {
          Toast.makeText(this, "Please select a department", Toast.LENGTH_SHORT).show();
            return false;
      }
      return true;
    }


    private boolean connected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wificonn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileconn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wificonn != null && wificonn.isConnected()) || (mobileconn != null && mobileconn.isConnected())) {
            return true;
        } else {
            Toast.makeText(this, "Please connect to internet", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}