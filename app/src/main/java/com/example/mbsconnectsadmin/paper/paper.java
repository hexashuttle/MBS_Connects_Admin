package com.example.mbsconnectsadmin.paper;

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
import com.example.mbsconnectsadmin.timetable.time_table;
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

import java.io.File;
import java.util.HashMap;

public class paper extends AppCompatActivity {

    TextView textView;
    TextInputLayout paper_title;
    ProgressBar progressBar;
    Uri pdfdata;
    private final int REQ=99;
    String pdfName="", pdftitle;


    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper);

        //hookers
        textView=findViewById(R.id.paper_name);
        paper_title=findViewById(R.id.paper_title);
        progressBar=findViewById(R.id.paper_bar);


        databaseReference= FirebaseDatabase.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference();
    }

    public void choose_pdf5(View view) {
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
                    cursor= paper.this.getContentResolver().query(pdfdata, null, null, null, null);
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
            textView.setText(pdfName);

            Toast.makeText(this, "Your choose: "+pdfName, Toast.LENGTH_SHORT).show();
        }}


    public void upload_paper(View view) {
        if(!validation() | !connected())
        {
            return;
        }

        if(pdfdata==null)
        {
            Toast.makeText(this, "Please choose a pdf", Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        uploadpdf();


    }



    private void uploadpdf() {
        StorageReference reference=storageReference.child("paper/"+pdfName+"-"+System.currentTimeMillis()+".pdf");
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

                Toast.makeText(paper.this, "Error! "+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void uploadData(String downloadUrl) {
        String unqiuekey=databaseReference.child("paper").push().getKey();

        HashMap data=new HashMap();
        data.put("pdfTitle", pdftitle);
        data.put("pdfUrl",downloadUrl);

        databaseReference.child("paper").child(unqiuekey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(paper.this, "Book uploaded successfully", Toast.LENGTH_SHORT).show();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(paper.this,"Error! "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private boolean validation()
    {
        String stu=paper_title.getEditText().getText().toString().trim();
        if(stu.isEmpty())
        {
            paper_title.setError("Input the title");
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
        }
        else {
            Toast.makeText(this, "Please connect to internet", Toast.LENGTH_SHORT).show();
            return false;

        }
    }
}