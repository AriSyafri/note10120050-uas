package com.example.note10120050.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note10120050.R;

import java.util.ArrayList;

// NIM : 10120050
// Nama : Ari Syafri
// Kelas : IF2
public class VPAdapter extends RecyclerView.Adapter<VPAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ViewPagerItem> viewPagerItemArrayList;

    public VPAdapter(Context context, ArrayList<ViewPagerItem> viewPagerItemArrayList) {
        this.context = context;
        this.viewPagerItemArrayList = viewPagerItemArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewpager_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewPagerItem viewPagerItem = viewPagerItemArrayList.get(position);

        holder.imageView.setImageResource(viewPagerItem.imageID);
        holder.tvDesc.setText(viewPagerItem.description);
    }

    @Override
    public int getItemCount() {
        return viewPagerItemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivImage);
            tvDesc = itemView.findViewById(R.id.tvDesc);
        }
    }
}


