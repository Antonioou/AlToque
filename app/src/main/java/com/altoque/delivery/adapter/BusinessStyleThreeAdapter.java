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
import com.squareup.picasso.Picasso;

import java.util.List;

public class BusinessStyleThreeAdapter extends RecyclerView.Adapter<BusinessStyleThreeAdapter.ViewHolder>
        implements View.OnClickListener {

    List<NegocioModel> list;
    Context context;
    private View.OnClickListener listener;

    int pos;

    public BusinessStyleThreeAdapter(List<NegocioModel> list, Context context) {
        this.list = list;
        this.context = context;
    }



    @Override
    public BusinessStyleThreeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_list_business_style_three, null, false);
        view.setOnClickListener(this);

        context = parent.getContext();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessStyleThreeAdapter.ViewHolder holder, int position) {

        pos = holder.getAdapterPosition();

        //name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        //Log.e("Error_log_serices", "ADAPTER: "+list.get(position).toString());

        String name = list.get(pos).getRsocialNeg();
        holder.name.setText(name);
        String direction = list.get(pos).getDirFiscalNeg();
        holder.direction.setText(direction);
        String cost = list.get(pos).getCostoEnvio();
        holder.cost.setText(cost);
        String rate = list.get(pos).getRating();
        holder.rate.setText(rate);
        String time = list.get(pos).getEstimacionDemora();
        holder.time.setText(time);
        String type = list.get(pos).getNomRubro();
        holder.type.setText(type);

        try {
            if (list.get(pos).getFotoNeg() != null) {
                String url = String.valueOf(list.get(pos).getFotoNeg());

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



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, cost, direction, rate, time, type;
        ImageView url;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_name__business_style_three);
            url = itemView.findViewById(R.id.iv_photo_business_style_three);
            cost = itemView.findViewById(R.id.tv_cost_business_style_three);
            direction = itemView.findViewById(R.id.tv_direction_business_style_three);
            rate = itemView.findViewById(R.id.tv_rate_business_style_three);
            time = itemView.findViewById(R.id.tv_time_business_style_three);
            type = itemView.findViewById(R.id.tv_type_business_style_three);
        }
    }
}
