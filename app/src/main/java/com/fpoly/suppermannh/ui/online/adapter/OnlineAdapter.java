package com.fpoly.suppermannh.ui.online.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.lisenner.OnlineLisenner;
import com.fpoly.suppermannh.model.Menu;
import com.fpoly.suppermannh.model.Time;
import com.fpoly.suppermannh.untils.StringUtils;

import java.util.Calendar;
import java.util.List;

public class OnlineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Time> times;
    Menu menu;
    Calendar calendar;
    private boolean onLoad = true;
    private OnlineLisenner lisenner;

    public OnlineAdapter(Context context, List<Time> times, Menu menu, Calendar calendar,OnlineLisenner lisenner) {
        this.context = context;
        this.times = times;
        this.menu = menu;
        this.calendar = calendar;
        this.lisenner = lisenner;
    }

    public void setOnLoad(boolean onLoad) {
        this.onLoad = onLoad;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_time,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Time time = times.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        if (onLoad){

        if (calendar.get(Calendar.HOUR_OF_DAY) >= time.getTime()){
            viewHolder.tvTime.setVisibility(View.GONE);
        }else {

            if (StringUtils.isEmpty(menu.getDates())){
                return;
            }

            switch (menu.getDates()){
                case "day":
                        viewHolder.tvTime.setVisibility(View.VISIBLE);
                        viewHolder.tvTime.setText(time.getTime()+":00");
                    break;
                case "lunch":
                    if (time.getTime() > 13 || time.getTime() < 8){
                        viewHolder.tvTime.setVisibility(View.GONE);
                    }else {
                        viewHolder.tvTime.setVisibility(View.VISIBLE);
                        viewHolder.tvTime.setText(time.getTime()+":00");
                    }
                    break;
                case "dinner":
                    if (time.getTime() > 21 || time.getTime() < 16){
                        viewHolder.tvTime.setVisibility(View.GONE);
                    }else {
                        viewHolder.tvTime.setVisibility(View.VISIBLE);
                        viewHolder.tvTime.setText(time.getTime()+":00");
                    }
                    break;
            }
        }
        }
        else {
            switch (menu.getDates()){
                case "day":
                    viewHolder.tvTime.setVisibility(View.VISIBLE);
                    viewHolder.tvTime.setText(time.getTime()+":00");
                    break;
                case "lunch":
                    if (time.getTime() > 13 || time.getTime() < 8){
                        viewHolder.tvTime.setVisibility(View.GONE);
                    }else {
                        viewHolder.tvTime.setVisibility(View.VISIBLE);
                        viewHolder.tvTime.setText(time.getTime()+":00");
                    }
                    break;
                case "dinner":
                    if (time.getTime() > 21 || time.getTime() < 16){
                        viewHolder.tvTime.setVisibility(View.GONE);
                    }else {
                        viewHolder.tvTime.setVisibility(View.VISIBLE);
                        viewHolder.tvTime.setText(time.getTime()+":00");
                    }
                    break;
            }
        }

        viewHolder.itemView.setOnClickListener(view -> {
            lisenner.onclick(times.get(position).getTime());
        });
    }

    @Override
    public int getItemCount() {
        return times.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_item_time_online);
        }
    }
}
