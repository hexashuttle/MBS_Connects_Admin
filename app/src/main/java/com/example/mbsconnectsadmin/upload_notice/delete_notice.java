package com.example.mbsconnectsadmin.upload_notice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mbsconnectsadmin.Database.NoticeData;
import com.example.mbsconnectsadmin.MainActivity;
import com.example.mbsconnectsadmin.R;
import com.example.mbsconnectsadmin.navigation.navigation_menu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class delete_notice extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ArrayList<NoticeData> list;
    private Notice_Adapter adapter;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_notice);

        recyclerView=findViewById(R.id.deletenotice_recyclerview);
        progressBar=findViewById(R.id.deletenotice_progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        reference= FirebaseDatabase.getInstance().getReference().child("Notice");
        
        getNotice();
    }

    private void getNotice() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list=new ArrayList<>();
                for(DataSnapshot dataSnapshot:snapshot.getChildren())

                {
                        NoticeData data=dataSnapshot.getValue(NoticeData.class);
                        list.add(data);
                }
                adapter=new Notice_Adapter(delete_notice.this,list);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                recyclerView.setAdapter(adapter);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(delete_notice.this, "Error! "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}