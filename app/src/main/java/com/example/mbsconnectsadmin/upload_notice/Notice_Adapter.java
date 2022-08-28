package com.example.mbsconnectsadmin.upload_notice;

import android.app.VoiceInteractor;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mbsconnectsadmin.Database.NoticeData;
import com.example.mbsconnectsadmin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Notice_Adapter extends RecyclerView.Adapter<Notice_Adapter.NoticeViewAdapter> {

    private Context context;
    private ArrayList<NoticeData>list;

    public Notice_Adapter(Context context, ArrayList<NoticeData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NoticeViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.notice_itemfeed,parent,false);

        return new NoticeViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewAdapter holder, int position) {

        NoticeData currentItem=list.get(position);
        holder.title_notice.setText(currentItem.getTitle());

        try {
            if(currentItem.getImage()!=null)
            Picasso.get().load(currentItem.getImage()).into(holder.image_notice);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.deletenotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Notice");
                reference.child(currentItem.getKey()).removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(context, "Notice Deleted", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context,"Error! Something went wong!",Toast.LENGTH_SHORT).show();
                            }
                        });
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NoticeViewAdapter extends RecyclerView.ViewHolder {
        private Button deletenotice;
        private TextView title_notice;
        private ImageView image_notice;
        public NoticeViewAdapter(@NonNull View itemView) {
            super(itemView);
            deletenotice=itemView.findViewById(R.id.delete_notice);
            title_notice=itemView.findViewById(R.id._notice_title);
            image_notice=itemView.findViewById(R.id._notice_image);
        }
    }
}
