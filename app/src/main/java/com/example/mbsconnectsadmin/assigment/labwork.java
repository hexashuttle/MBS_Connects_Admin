package com.example.mbsconnectsadmin.assigment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mbsconnectsadmin.R;
import com.example.mbsconnectsadmin.uploadbook.book_upload;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;

public class labwork extends AppCompatActivity {
    TextInputEditText editText;
    private ProgressBar progressBar;
    Uri pdfdata;
    TextView pdfname;
    private final int REQ=99;
    String pdfName="", pdftitle;

    String type;

    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labwork);

        editText=findViewById(R.id.title_ass);
        progressBar=findViewById(R.id.lab_progressbar);
        pdfname=findViewById(R.id.file_name);

        type=getIntent().getStringExtra("work");

        databaseReference= FirebaseDatabase.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference();


    }


    public boolean titleValidation()
    {
        String stu=editText.getText().toString().trim();
        if(stu.isEmpty())
        {
            editText.setError("Input the title");
            return false;
        }

        return true;
    }

    public void upload_result4(View view) {
        if(pdfdata==null)
        {
            Toast.makeText(this, "Please a pdf", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!titleValidation() | !connected())
        {
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        uploadpdf();
    }

    private void uploadpdf() {
        StorageReference reference=storageReference.child(type+"/"+pdfName+"-"+System.currentTimeMillis()+".pdf");
        reference.putFile(pdfdata).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressBar.setVisibility(View.GONE);
                Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri uri=uriTask.getResult();
                uploadData(String.valueOf(uri));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);

                Toast.makeText(labwork.this, "Error! "+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void uploadData(String downloadUrl) {
        String unqiuekey=databaseReference.child(type).push().getKey();

        HashMap data=new HashMap();
        data.put("pdfTitle", pdftitle);
        data.put("pdfUrl",downloadUrl);

        databaseReference.child(type).child(unqiuekey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(labwork.this, "Book uploaded successfully", Toast.LENGTH_SHORT).show();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(labwork.this,"Error! "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




    public void choose_assignment(View view) {
        Intent pickpdf=new Intent();
        pickpdf.setType("application/pdf");
        pickpdf.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(pickpdf, "Select PDF"), REQ);
    }

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ && resultCode==RESULT_OK)
        {
            pdfdata=data.getData();
            if(pdfdata.toString().startsWith("content://"))
            {
                Cursor cursor=null;
                try {
                    cursor= labwork.this.getContentResolver().query(pdfdata, null, null, null, null);
                    if(cursor!=null && cursor.moveToFirst())
                    {
                        pdfName=cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if(pdfdata.toString().startsWith("file://"))
            {
                pdfName=new File(pdfName.toString()).getName();

            }
            pdfname.setText(pdfName);
            Toast.makeText(this, "Your choose: "+pdfname, Toast.LENGTH_SHORT).show();
        }}


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