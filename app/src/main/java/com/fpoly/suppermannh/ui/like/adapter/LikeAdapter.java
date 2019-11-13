package com.fpoly.suppermannh.ui.like.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.api.Server;
import com.fpoly.suppermannh.lisenner.HomeLisenner;
import com.fpoly.suppermannh.model.Houst;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class LikeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Houst> likes;
    HomeLisenner likeLisenner;

    public LikeAdapter(Context context, List<Houst> likes, HomeLisenner likeLisenner) {
        this.context = context;
        this.likes = likes;
        this.likeLisenner = likeLisenner;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_like,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Houst like = likes.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tvName.setText(like.getNames());
        viewHolder.tvAddress.setText(like.getAddress());
        Glide.with(context)
                .load(Server.duongdananh+like.getImages())
                .placeholder(R.drawable.img_hinhcho)
                .error(R.drawable.img_hinhcho)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontTransform()
                .dontAnimate()
                .into(viewHolder.roundedImageView);
        viewHolder.itemView.setOnClickListener(view -> {
            likeLisenner.onClick(likes.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return likes.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView roundedImageView;
        TextView tvName,tvAddress;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            roundedImageView = itemView.findViewById(R.id.img_like_fragment);
            tvName = itemView.findViewById(R.id.tv_name_nh_like_fragment);
            tvAddress = itemView.findViewById(R.id.tv_address_like_fragment);
        }
    }
}
