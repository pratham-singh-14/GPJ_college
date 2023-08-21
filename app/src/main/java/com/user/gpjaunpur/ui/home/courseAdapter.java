package com.user.gpjaunpur.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.user.gpjaunpur.R;
import com.user.gpjaunpur.ui.home.courseModel;

import java.util.List;

public class courseAdapter extends PagerAdapter {
    private Context context;
    private List<courseModel> list;

    public courseAdapter(Context context, List<courseModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view=LayoutInflater.from(context).inflate(R.layout.course_item,container,false);
        ImageView courseIcon;
        TextView courseDescription,courseTitle;
        courseIcon=view.findViewById(R.id.courseIcon);
        courseTitle=view.findViewById(R.id.courseTitle);
        courseDescription=view.findViewById(R.id.courseDescription);
        courseIcon.setImageResource(list.get(position).getImg());
        courseTitle.setText(list.get(position).getTitle());
        courseDescription.setText(list.get(position).getDescription());
        container.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
