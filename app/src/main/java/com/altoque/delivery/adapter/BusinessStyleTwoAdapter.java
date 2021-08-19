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
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BusinessStyleTwoAdapter extends RecyclerView.Adapter<BusinessStyleTwoAdapter.ViewHolder>
        implements View.OnClickListener {

    List<NegocioModel> list;
    Context context;
    private View.OnClickListener listener;

    private final float mShrinkAmount = 0.15f;
    private final float mShrinkDistance = 0.9f;

    public BusinessStyleTwoAdapter(List<NegocioModel> list) {
        this.list = list;
    }

    public BusinessStyleTwoAdapter(Context context) {
        this.context = context;
    }

    @NonNull
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

        int pos = position % list.size();

        //name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();

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

        try {
            if (list.get(pos).getFotoNeg() != null) {
                String url = String.valueOf(list.get(pos).getFotoNeg());

                Picasso.get().load(url)
                        .placeholder(R.drawable.second_image)
                        .into(holder.logo);
            }
        } catch (Exception e) {
            Log.e("Debug_error", "" + e);
        }


        try {
            if (list.get(pos).getBannerNeg() != null) {
                String url = String.valueOf(list.get(pos).getBannerNeg());

                Picasso.get().load(url)
                        .placeholder(R.drawable.second_image)
                        .into(holder.banner);
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



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, cost, direction, rate, time;
        ImageView banner;
        CircularImageView logo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_name__business_style_two);
            banner = itemView.findViewById(R.id.iv_banner_business_style_two);
            logo = itemView.findViewById(R.id.iv_logo_business_style_two);
            cost = itemView.findViewById(R.id.tv_cost__business_style_two);
            direction = itemView.findViewById(R.id.tv_direction_business_style_two);
            rate = itemView.findViewById(R.id.tv_rate_business_style_two);
            time = itemView.findViewById(R.id.tv_time_business_style_two);
        }
    }
}
