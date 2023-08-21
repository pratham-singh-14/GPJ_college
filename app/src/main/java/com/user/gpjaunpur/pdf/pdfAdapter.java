package com.user.gpjaunpur.pdf;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.user.gpjaunpur.R;

import java.util.ArrayList;
import java.util.List;

public class pdfAdapter extends RecyclerView.Adapter<pdfAdapter.pdfViewHolder> {
    private Context context;
    private List<pdfData> list;

    public pdfAdapter(Context context, List<pdfData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public pdfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pdf_item_layout,parent,false);
        return new pdfViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull pdfViewHolder holder, int position) {
        holder.pdfname.setText(list.get(holder.getAdapterPosition()).getPdfTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent x=new Intent(context,PdfViewer.class);
               x.putExtra("pdfUrl",list.get(holder.getAdapterPosition()).getPdfUrl());
               context.startActivity(x);
            }
        });
holder.download.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(list.get(holder.getAdapterPosition()).getPdfUrl()));
        context.startActivity(intent);
        Toast.makeText(context, "Downloading....", Toast.LENGTH_SHORT).show();
    }
});
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void Filteredlist(ArrayList<pdfData> filterlist){
        list=filterlist;
        notifyDataSetChanged();
    }

    public class pdfViewHolder extends RecyclerView.ViewHolder {
        TextView pdfname;
        ImageView download;


        public pdfViewHolder(@NonNull View itemView) {
            super(itemView);
            pdfname= itemView.findViewById(R.id.pdfname);
            download=itemView.findViewById(R.id.download);
        }
    }
}
