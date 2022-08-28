package com.example.mbsconnectsadmin.teacher_data;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import com.example.mbsconnectsadmin.Database.teacher_Data;
import com.example.mbsconnectsadmin.R;


import java.util.ArrayList;
import java.util.List;

public class view_teacher extends AppCompatActivity {

    private RecyclerView bca,bba,bcom,mcom,bsc,msc;
    private LinearLayout bcanodata,bbanodata,bcomnodata,mcomnodata,mscnodata,bscnodata;
    private List<teacher_Data> list1,list2,list3,list4,list5,list6;

    private teacher_adaptor adaopter;

    private DatabaseReference reference, dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_teacher);


        bca=findViewById(R.id.bca);
        bba=findViewById(R.id.bba);
        bcom=findViewById(R.id.bcom);
        mcom=findViewById(R.id.mcom);
        bsc=findViewById(R.id.bsc);
        msc=findViewById(R.id.msc);

        bscnodata=findViewById(R.id.bscanodata);
        bcanodata=findViewById(R.id.bcanodata);
        bbanodata=findViewById(R.id.bbanodata);
        bcomnodata=findViewById(R.id.bcomnodata);
        mcomnodata=findViewById(R.id.mcomnodata);
        mscnodata=findViewById(R.id.mscnodata);
        reference= FirebaseDatabase.getInstance().getReference().child("teacher");

        bca();

        bbaa();

        mcom();

        bcom();

        bsc();

        msc();
    }
    private void msc() {
        dbRef=reference.child("MSc");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list6= new ArrayList<>();
                if(!snapshot.exists()){
                    mscnodata.setVisibility(View.VISIBLE);
                    msc.setVisibility(View.GONE);
                }
                else
                {
                    mscnodata.setVisibility(View.GONE);
                    msc.setVisibility(View.VISIBLE);
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        teacher_Data data=dataSnapshot.getValue(teacher_Data.class);
                        list6.add(data);
                        msc.setHasFixedSize(true);
                        msc.setLayoutManager(new LinearLayoutManager(view_teacher.this));
                        adaopter=new teacher_adaptor(list6, view_teacher.this,"MSc");
                        msc.setAdapter(adaopter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(view_teacher.this, "Error! "+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void bsc() {
        dbRef=reference.child("BSc");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list5= new ArrayList<>();
                if(!snapshot.exists()){
                    bscnodata.setVisibility(View.VISIBLE);
                    bsc.setVisibility(View.GONE);
                }
                else
                {
                    bscnodata.setVisibility(View.GONE);
                    bsc.setVisibility(View.VISIBLE);
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        teacher_Data data=dataSnapshot.getValue(teacher_Data.class);
                        list5.add(data);
                        bsc.setHasFixedSize(true);
                        bsc.setLayoutManager(new LinearLayoutManager(view_teacher.this));
                        adaopter=new teacher_adaptor(list5, view_teacher.this,"BSc");
                        bsc.setAdapter(adaopter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(view_teacher.this, "Error! "+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void bcom() {
        dbRef=reference.child("BCOM");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list4= new ArrayList<>();
                if(!snapshot.exists()){
                    bcomnodata.setVisibility(View.VISIBLE);
                    bcom.setVisibility(View.GONE);
                }
                else
                {
                    bcomnodata.setVisibility(View.GONE);
                    bcom.setVisibility(View.VISIBLE);
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        teacher_Data data=dataSnapshot.getValue(teacher_Data.class);
                        list4.add(data);
                        bcom.setHasFixedSize(true);
                        bcom.setLayoutManager(new LinearLayoutManager(view_teacher.this));
                        adaopter=new teacher_adaptor(list4, view_teacher.this,"BCOM");
                        bcom.setAdapter(adaopter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(view_teacher.this, "Error! "+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void mcom() {
        dbRef=reference.child("MCOM");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list3= new ArrayList<>();
                if(!snapshot.exists()){
                    mcomnodata.setVisibility(View.VISIBLE);
                    mcom.setVisibility(View.GONE);
                }
                else
                {
                    mcomnodata.setVisibility(View.GONE);
                    mcom.setVisibility(View.VISIBLE);
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        teacher_Data data=dataSnapshot.getValue(teacher_Data.class);
                        list3.add(data);
                        mcom.setHasFixedSize(true);
                        mcom.setLayoutManager(new LinearLayoutManager(view_teacher.this));
                        adaopter=new teacher_adaptor(list3, view_teacher.this, "MCOM");
                        mcom.setAdapter(adaopter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(view_teacher.this, "Error! "+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void bbaa() {
        dbRef=reference.child("BBA");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list2= new ArrayList<>();
                if(!snapshot.exists()){
                    bbanodata.setVisibility(View.VISIBLE);
                    bba.setVisibility(View.GONE);
                }
                else
                {
                    bbanodata.setVisibility(View.GONE);
                    bba.setVisibility(View.VISIBLE);
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        teacher_Data data=dataSnapshot.getValue(teacher_Data.class);
                        list2.add(data);
                        bba.setHasFixedSize(true);
                        bba.setLayoutManager(new LinearLayoutManager(view_teacher.this));
                        adaopter=new teacher_adaptor(list2, view_teacher.this,"BBA");
                        bba.setAdapter(adaopter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(view_teacher.this, "Error! "+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void bca() {


        dbRef=reference.child("BCA");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list1= new ArrayList<>();
                if(!snapshot.exists()){
                    bcanodata.setVisibility(View.VISIBLE);
                    bca.setVisibility(View.GONE);
                }
                else
                {
                    bcanodata.setVisibility(View.GONE);
                    bca.setVisibility(View.VISIBLE);
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        teacher_Data data=dataSnapshot.getValue(teacher_Data.class);
                        list1.add(data);
                        bca.setHasFixedSize(true);
                        bca.setLayoutManager(new LinearLayoutManager(view_teacher.this));
                        adaopter=new teacher_adaptor(list1, view_teacher.this,"BCA");
                        bca.setAdapter(adaopter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(view_teacher.this, "Error! "+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


}
