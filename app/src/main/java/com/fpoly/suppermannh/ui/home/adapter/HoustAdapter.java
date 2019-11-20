package com.fpoly.suppermannh.ui.home.adapter;

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

public class HoustAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Houst> list;
    private HomeLisenner lisenner;

    public HoustAdapter(Context context, List<Houst> list, HomeLisenner lisenner) {
        this.context = context;
        this.list = list;
        this.lisenner = lisenner;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_houst,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (list.size() == 0){
            return;
        }
        Houst houst = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tvName.setText(houst.getNames());
        viewHolder.tvAddress.setText(houst.getAddress());
        Glide.with(context)
                .load(Server.duongdananh+houst.getImages())
                .placeholder(R.drawable.img_hinhcho)
                .error(R.drawable.img_hinhcho)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontTransform()
                .dontAnimate()
                .into(viewHolder.roundedImageView);

        viewHolder.itemView.setOnClickListener(view -> {
            lisenner.onClick(list.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView roundedImageView;
        TextView tvName,tvAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            roundedImageView = itemView.findViewById(R.id.img_item_houst_home);
            tvName = itemView.findViewById(R.id.tv_name_nh_item_houst_home);
            tvAddress = itemView.findViewById(R.id.tv_address_item_houst_home);
        }
    }
}
