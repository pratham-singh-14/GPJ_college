package com.user.gpjaunpur.ui.gallery;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.user.gpjaunpur.FullImageView;
import com.user.gpjaunpur.R;

import java.util.ArrayList;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.galleryViewAdapter> {
    private Context context;
    private ArrayList<String> images;   //beacause database store only url;

    public GalleryAdapter(Context context, ArrayList<String> images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public galleryViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gallery_image, parent, false);
        return new galleryViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull galleryViewAdapter holder, int position) {
        try {
            Glide.with(context).load(images.get(holder.getAdapterPosition())).into(holder.imagee);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.imagee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FullImageView.class);

                    intent.putExtra("image", images.get(holder.getAbsoluteAdapterPosition()));

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class galleryViewAdapter extends RecyclerView.ViewHolder {
        ImageView imagee;

        public galleryViewAdapter(@NonNull View itemView) {
            super(itemView);
            imagee = itemView.findViewById(R.id.imagee);
        }
    }
}
