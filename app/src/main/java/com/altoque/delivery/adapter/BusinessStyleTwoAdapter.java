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

public class BusinessStyleTwoAdapter extends RecyclerView.Adapter<BusinessStyleTwoAdapter.ViewHolder>
        implements View.OnClickListener {

    List<NegocioModel> list;
    Context context;
    private View.OnClickListener listener;

    int pos;

    private final float mShrinkAmount = 0.15f;
    private final float mShrinkDistance = 0.9f;

    public BusinessStyleTwoAdapter(List<NegocioModel> list) {
        this.list = list;
    }

    public BusinessStyleTwoAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BusinessStyleTwoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_list_business_style_two, null, false);
        view.setOnClickListener(this);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessStyleTwoAdapter.ViewHolder holder, int position) {

        pos = position % list.size();

        //name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();

        String name = list.get(pos).getRsocial_neg();
        holder.name.setText(name);
        String direction = list.get(pos).getDir_fiscal_neg();
        holder.direction.setText(direction);
        String cost = list.get(pos).getCosto_envio();
        holder.cost.setText(cost);
        String rate = list.get(position).getRating();
        holder.rate.setText(rate);

        try {
            if (list.get(pos).getFoto_neg() != null) {
                String url = String.valueOf(list.get(pos).getFoto_neg());

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
        return list == null ? 0 : list.size() * 2;
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
        TextView name, cost, direction, rate;
        ImageView url;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_name__business_style_two);
            url = itemView.findViewById(R.id.iv_photo_business_style_two);
            cost = itemView.findViewById(R.id.tv_cost__business_style_two);
            direction = itemView.findViewById(R.id.tv_direction_business_style_two);
            rate = itemView.findViewById(R.id.tv_rate_business_style_two);
        }
    }
}
