package com.altoque.delivery.adapter;

import android.annotation.SuppressLint;
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
import com.altoque.delivery.model.RubroModel;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ServicesStyleTwoAdapter extends RecyclerView.Adapter<ServicesStyleTwoAdapter.ViewHolder>
        implements View.OnClickListener {

    List<RubroModel> list;
    Context context;
    private View.OnClickListener listener;

    int pos;

    public ServicesStyleTwoAdapter(List<RubroModel> list) {
        this.list = list;
    }

    public ServicesStyleTwoAdapter(Context context) {
        this.context = context;
    }

    @NotNull
    @Override
    public ServicesStyleTwoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_list_services_style_two, null, false);
        view.setOnClickListener(this);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesStyleTwoAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        pos = position;

        //name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();

        String name = list.get(position).getNom_rubro();
        holder.name.setText(name);
        //String icon = list.get(position).getDir_fiscal_neg();
        //holder.direction.setText(direction);

        try {
            if (list.get(pos).getFoto_rubro() != null) {
                String url = String.valueOf(list.get(pos).getFoto_rubro());

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
        TextView name;
        ImageView url;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_name_services_style_two);
            url = itemView.findViewById(R.id.iv_imagen_services_style_two);

        }
    }
}
