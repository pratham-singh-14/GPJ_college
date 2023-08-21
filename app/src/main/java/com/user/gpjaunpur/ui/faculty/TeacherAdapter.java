package com.user.gpjaunpur.ui.faculty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.user.gpjaunpur.R;

import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewAdapter>
{
    private List<TeacherData> list;
    private Context context;

    public TeacherAdapter(List<TeacherData> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public TeacherViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.faculty_item_layout, parent,false);
        return new TeacherViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewAdapter holder, int position) {
        TeacherData item= list.get(position);
        holder.name.setText(item.getName());
        holder.post.setText(item.getPost());
        holder.joining.setText(item.getJoining());
        holder.email.setText(item.getEmail());
        try {
            Glide.with(context).load(item.getImage()).into(holder.image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TeacherViewAdapter extends RecyclerView.ViewHolder {
        private TextView name,post,joining,email;

        private ImageView image;
        public TeacherViewAdapter(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.teacherName);
            post=itemView.findViewById(R.id.teacherPost);
            joining=itemView.findViewById(R.id.teacherJoin);
            email=itemView.findViewById(R.id.teacherEmail);
            image=itemView.findViewById(R.id.teacherImage);

        }
    }

}
