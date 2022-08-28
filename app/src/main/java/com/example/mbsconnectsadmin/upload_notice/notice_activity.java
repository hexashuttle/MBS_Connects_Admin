package com.example.mbsconnectsadmin.upload_notice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mbsconnectsadmin.Database.NoticeData;
import com.example.mbsconnectsadmin.R;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class notice_activity extends AppCompatActivity {
    TextInputLayout notice_header;
    ImageView notice_image;
    ProgressBar progressBar;

    private final int REQ = 1;
    private Bitmap bitmap;
    String downloadUrl = "";

    private DatabaseReference reference;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice2);

        //hookers
        notice_header = findViewById(R.id.notice_title);
        notice_image = findViewById(R.id.image);
        progressBar = findViewById(R.id.progress_bar_notice);





        /*reference= FirebaseDatabase.getInstance().getReference().child("eventimage");
        storageReference= FirebaseStorage.getInstance().getReference().child("eventimage");*/

        reference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    public void uploadnotice(View view) {
        if (!connected()) {
            Toast.makeText(this, "Please connect to internet", Toast.LENGTH_SHORT).show();
        }
        if (!validation()) {
            return;
        }
        if (bitmap == null) {
            uploadData();
        } else {
            uploadImage();
        }

    }

    public void find_image(View view) {
        openGallery();
    }

    public boolean validation() {
        String stu = notice_header.getEditText().getText().toString().trim();
        if (stu.isEmpty()) {
            notice_header.setError("Input the title of the notice");
            return false;
        }
        notice_header.setErrorEnabled(false);
        return true;
    }



    private boolean connected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wificonn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileconn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wificonn != null && wificonn.isConnected()) || (mobileconn != null && mobileconn.isConnected())) {
            return true;
        } else {

            return false;

        }
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
            notice_image.setImageBitmap(bitmap);
        }
    }

    private void openGallery() {

        Intent pickimage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickimage, REQ);
    }


    private void uploadData() {
        reference = reference.child("Notice");
        String ntitle = notice_header.getEditText().getText().toString().trim();

        final String uniquekey = reference.push().getKey();


        Calendar cal = Calendar.getInstance();
        SimpleDateFormat currentData = new SimpleDateFormat("dd-MM-yy");
        String date = currentData.format(cal.getTime());


        Calendar cal2 = Calendar.getInstance();
        SimpleDateFormat currenttime = new SimpleDateFormat("hh:mm a");
        String time = currenttime.format(cal.getTime());


        NoticeData noticeData = new NoticeData(ntitle, downloadUrl, date, time, uniquekey);
        reference.child(uniquekey).setValue(noticeData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(notice_activity.this, "Notice Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(notice_activity.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void uploadImage() {
        progressBar.setVisibility(View.VISIBLE);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finalImage = baos.toByteArray();
        final StorageReference filePath;
        filePath = storageReference.child("Notice").child(finalImage + "jpg");
        final UploadTask uploadTask = filePath.putBytes(finalImage);
        uploadTask.addOnCompleteListener(notice_activity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = String.valueOf(uri);
                                    uploadData();
                                }
                            });
                        }
                    });
                } else {
                    Toast.makeText(notice_activity.this, "Error! Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}