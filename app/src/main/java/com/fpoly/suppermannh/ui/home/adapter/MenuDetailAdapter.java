package com.fpoly.suppermannh.ui.home.adapter;

import android.content.Context;
import android.text.TextUtils;
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
import com.fpoly.suppermannh.lisenner.MenuDetailLisenner;
import com.fpoly.suppermannh.model.Menu;
import com.fpoly.suppermannh.untils.FormatUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class MenuDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    int ITEM = 1;
    int LOAD_MORE = 2;
    private boolean onLoadMore = true;
    Context context;
    List<Menu> list;
    private MenuDetailLisenner menuDetailLisenner;

    public MenuDetailAdapter(Context context, List<Menu> list,MenuDetailLisenner menuDetailLisenner) {
        this.context = context;
        this.list = list;
        this.menuDetailLisenner = menuDetailLisenner;
    }

    public boolean isOnLoadMore() {
        return onLoadMore;
    }

    public void setOnLoadMore(boolean onLoadMore) {
        this.onLoadMore = onLoadMore;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == ITEM) {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.item_menu_detail, parent, false);
            return new MenuDetailAdapter.ViewHordel(view);
        } else {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.loadmore, parent, false);
            return new MenuDetailAdapter.LoadHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MenuDetailAdapter.ViewHordel) {
            Menu menus =  list.get(position);
            ViewHordel  viewHordel = (ViewHordel) holder;

            viewHordel.tv_namenh.setMaxLines(1);
            viewHordel.tv_namenh.setEllipsize(TextUtils.TruncateAt.END);
            viewHordel.tv_namenh.setText(menus.getNamenh());

            viewHordel.tv_name_mon_an.setMaxLines(1);
            viewHordel.tv_name_mon_an.setEllipsize(TextUtils.TruncateAt.END);
            viewHordel.tv_name_mon_an.setText(menus.getNames());
            viewHordel.tvPrice.setText(FormatUtils.convertEstimatedPrice(menus.getPrices())+" Đồng");
            Glide.with(context)
                    .load(Server.duongdananh+menus.getImages())
                    .placeholder(R.drawable.img_hinhcho)
                    .error(R.drawable.img_hinhcho)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontTransform()
                    .dontAnimate()
                    .into(viewHordel.imageView);
            if (menus.getDates().equals("day")){
                viewHordel.tvDate.setTextColor(0xCC00CC00);
                viewHordel.tvDate.setText("Cả ngày");
            }else if (menus.getDates().equals("lunch")){
                viewHordel.tvDate.setTextColor(0x99FF9900);
                viewHordel.tvDate.setText("Bữa trưa");
            }else if (menus.getDates().equals("dinner")){
                viewHordel.tvDate.setTextColor(0x99FF9900);
                viewHordel.tvDate.setText("Bữa tối");
            }
            viewHordel.itemView.setOnClickListener(view -> {
                menuDetailLisenner.onclick(list.get(position));
            });

        } else if (holder instanceof MenuDetailAdapter.LoadHolder){

        }


    }

    @Override
    public int getItemViewType(int position) {

        if (onLoadMore){
            if (position == list.size() - 1)
                return LOAD_MORE;
            else return ITEM;
        }else return ITEM;
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHordel extends RecyclerView.ViewHolder {
        RoundedImageView imageView;
        TextView tv_namenh,tv_name_mon_an,tvPrice,tvDate;
        public ViewHordel(@NonNull View itemView) {
            super(itemView);
            imageView =  itemView.findViewById(R.id.imgAvatar_xem_them);
            tv_namenh =  itemView.findViewById(R.id.tv_name_nh_xem_them);
            tv_name_mon_an =  itemView.findViewById(R.id.tv_name_mon_an_xem_them);
            tvPrice =  itemView.findViewById(R.id.tv_price_mon_an_xem_them);
            tvDate =  itemView.findViewById(R.id.tv_time_mon_an_xem_them);

        }
    }
    public class LoadHolder extends RecyclerView.ViewHolder {
        public LoadHolder(View view) {
            super(view);
        }
    }
}
