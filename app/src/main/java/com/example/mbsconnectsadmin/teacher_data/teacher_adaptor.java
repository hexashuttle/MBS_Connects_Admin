package com.example.mbsconnectsadmin.teacher_data;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mbsconnectsadmin.Database.teacher_Data;
import com.example.mbsconnectsadmin.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class teacher_adaptor extends RecyclerView.Adapter<teacher_adaptor.teacherviewadapoter> {
    private List<teacher_Data> list;
    private Context context;
    private String category;

    public teacher_adaptor(List<teacher_Data> list, Context context, String category) {
        this.list = list;
        this.context = context;
        this.category=category;
    }


    @NonNull
    @Override
    public teacher_adaptor.teacherviewadapoter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.teacher_item_layout,parent, false);
        return new teacherviewadapoter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull teacher_adaptor.teacherviewadapoter holder, int position) {
        teacher_Data item=list.get(position);
        holder.name.setText(item.getName());
        holder.email.setText(item.getEmail());

        try {
            Picasso.get().load(item.getImage()).into(holder.image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, update_teacher.class);
                intent.putExtra("name", item.getName());
                intent.putExtra("mail",item.getEmail());
                intent.putExtra("image", item.getImage());
                intent.putExtra("key", item.getKey());
                intent.putExtra("number",item.getNumber());
                intent.putExtra("category", category);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class teacherviewadapoter extends RecyclerView.ViewHolder {

        private TextView name,email;
        private Button update;
        private ImageView image;

        public teacherviewadapoter(@NonNull View itemView) {
            super(itemView);

            name=(TextView) itemView.findViewById(R.id.teacheranme);
            email=(TextView) itemView.findViewById(R.id.teacheremail2);
            image=itemView.findViewById(R.id.teacherphoto2);
            update=itemView.findViewById(R.id.teaxherupdate);
        }
    }
}
