package com.fpoly.suppermannh.ui.account.history.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.api.Server;
import com.fpoly.suppermannh.lisenner.HistoryLisenner;
import com.fpoly.suppermannh.model.History;
import com.fpoly.suppermannh.untils.FormatUtils;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    int ITEM = 1;
    int LOAD_MORE = 2;
    private boolean onLoadMore = true;
    private Context context;
    private List<History> list;
    private HistoryLisenner historyLisenner;

    public HistoryAdapter(Context context, List<History> list,HistoryLisenner historyLisenner) {
        this.context = context;
        this.list = list;
        this.historyLisenner = historyLisenner;
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
                    .inflate(R.layout.item_history, parent, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.loadmore, parent, false);
            return new LoadHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            final History history = list.get(position);
                ((ViewHolder) holder).tv_ban_histoty.setText("Bàn : "+history.getTables());
            ((ViewHolder) holder).tv_date_history.setText(history.getDate());
            ((ViewHolder) holder).tv_price_history.setTextColor(0xFFFF0000);
            ((ViewHolder) holder).tv_price_history
                    .setText(FormatUtils.convertEstimatedPrice(Double.valueOf(history.getPrice()))+" VNĐ");
            Glide.with(context)
                    .load(Server.duongdananh+history.getImages())
                    .placeholder(R.drawable.img_hinhcho)
                    .error(R.drawable.img_hinhcho)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontTransform()
                    .dontAnimate()
                    .into(((ViewHolder) holder).img_history);
            holder.itemView.setOnClickListener(view -> {
                historyLisenner.history(list.get(position));
            });
        } else if (holder instanceof LoadHolder){

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_history;
        TextView tv_ban_histoty,tv_date_history,tv_price_history;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_history = itemView.findViewById(R.id.img_history);
            tv_ban_histoty = itemView.findViewById(R.id.tv_ban_history);
            tv_date_history = itemView.findViewById(R.id.tv_date_history);
            tv_price_history = itemView.findViewById(R.id.tv_price_history);
        }
    }

    public class LoadHolder extends RecyclerView.ViewHolder {
        public LoadHolder(View view) {
            super(view);
        }
    }

}
