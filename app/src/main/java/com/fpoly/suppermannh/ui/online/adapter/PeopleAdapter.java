package com.fpoly.suppermannh.ui.online.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.lisenner.PeopleLisenner;
import com.fpoly.suppermannh.model.People;

import java.util.List;

public class PeopleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<People> peoples;
    PeopleLisenner peopleLisenner;

    public PeopleAdapter(Context context, List<People> people,PeopleLisenner peopleLisenner) {
        this.context = context;
        this.peoples = people;
        this.peopleLisenner = peopleLisenner;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_people,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        People people = peoples.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;

        if (people.getPeople() == 1){
            viewHolder.view.setVisibility(View.GONE);
        }else {
            viewHolder.view.setVisibility(View.VISIBLE);
        }
        viewHolder.textView.setText(""+people.getPeople());
        viewHolder.itemView.setOnClickListener(view -> {
            peopleLisenner.people(peoples.get(position).getPeople());
        });
    }

    @Override
    public int getItemCount() {
        return peoples.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.view_item_people);
            textView = itemView.findViewById(R.id.tv_item_people);
        }
    }
}
