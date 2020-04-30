package com.example.listdemo.adaptor;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.listdemo.R;
import com.example.listdemo.model.DataModel;

import java.util.List;


public class DataModelAdapter extends RecyclerView.Adapter<DataModelAdapter.MyViewHolder> {

    private final Context context;
    private DataModelClickListener aboutCanadaClickListener;
    private List<DataModel> list;

    public DataModelAdapter(Context context, DataModelClickListener aboutCanadaClickListener) {
        this.context = context;
        this.aboutCanadaClickListener = aboutCanadaClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_about_canada, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final DataModel dataModel = list.get(position);
        if (dataModel.getThumbnailUrl() != null) {
            Glide
                    .with(context)
                    .load(dataModel.getThumbnailUrl())
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.mipmap.ic_launcher)
                    .into(holder.ivImage);
            holder.ivImage.setVisibility(View.VISIBLE);
        } else {
            holder.ivImage.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    public void setData(List<DataModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivImage;
        private View view;

        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (aboutCanadaClickListener != null) {
                        aboutCanadaClickListener.onClick(list.get(getAdapterPosition()));
                    }
                }
            });

        }
    }

    public interface DataModelClickListener {

        void onClick(DataModel dataModel);
    }
}