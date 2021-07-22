package com.altoque.delivery.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.altoque.delivery.R;
import com.altoque.delivery.model.NegocioModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ServicesStyleOneAdapter extends RecyclerView.Adapter<ServicesStyleOneAdapter.ViewHolder>
        implements View.OnClickListener {

    List<NegocioModel> list;
    Context context;
    private View.OnClickListener listener;

    int pos;

    public ServicesStyleOneAdapter(List<NegocioModel> list) {
        this.list = list;
    }

    public ServicesStyleOneAdapter(Context context) {
        this.context = context;
    }

    @NotNull
    @Override
    public ServicesStyleOneAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_list_services_style_one, null, false);
        view.setOnClickListener(this);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesStyleOneAdapter.ViewHolder holder, int position) {

        pos = position;

        //name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();

        String name = list.get(position).getRsocial_neg();
        holder.name.setText(name);
        //String icon = list.get(position).getDir_fiscal_neg();
        //holder.direction.setText(direction);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        FloatingActionButton url;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_name_services_style_one);
            url = itemView.findViewById(R.id.fab_icon_services_style_one);

        }
    }
}
