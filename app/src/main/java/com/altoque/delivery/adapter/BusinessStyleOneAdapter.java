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
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BusinessStyleOneAdapter extends RecyclerView.Adapter<BusinessStyleOneAdapter.ViewHolder>
        implements View.OnClickListener {

    List<NegocioModel> list;
    Context context;
    private View.OnClickListener listener;

    int pos;

    public BusinessStyleOneAdapter(List<NegocioModel> list) {
        this.list = list;
    }

    public BusinessStyleOneAdapter(Context context) {
        this.context = context;
    }

    @NotNull
    @Override
    public BusinessStyleOneAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_list_business_style_one, null, false);
        view.setOnClickListener(this);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessStyleOneAdapter.ViewHolder holder, int position) {

        pos = holder.getAdapterPosition();

        //name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();

        String name = list.get(position).getRsocialNeg();
        holder.name.setText(name);
        String direction = list.get(position).getDirFiscalNeg();
        holder.direction.setText(direction);
        String cost = list.get(position).getCostoEnvio();
        holder.cost.setText(cost);
        String rate = list.get(position).getRating();
        holder.rate.setText(rate);
        String time = list.get(pos).getEstimacionDemora();
        holder.time.setText(time);
        /*String name = list.get(position).getRsocial_neg();
        holder.name.setText(name);*/

        try {
            if (list.get(position).getFotoNeg() != null) {
                String url = String.valueOf(list.get(position).getFotoNeg());

                Picasso.get().load(url)
                        .placeholder(R.drawable.second_image)
                        .into(holder.url);
            }
        } catch (Exception e) {
            Log.e("Debug_error", "" + e);
        }
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
        TextView name, cost, direction, rate, time;
        ImageView url;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_name__business_style_one);
            url = itemView.findViewById(R.id.iv_photo_business_style_one);
            cost = itemView.findViewById(R.id.tv_cost__business_style_one);
            rate = itemView.findViewById(R.id.tv_rate_business_style_one);
            direction = itemView.findViewById(R.id.tv_direction__business_style_one);
            time = itemView.findViewById(R.id.tv_time_business_style_one);
        }
    }
}
