package com.altoque.delivery.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.altoque.delivery.R;
import com.altoque.delivery.model.NegocioModel;
import com.altoque.delivery.model.ProductoModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProductStyleOneAdapter extends RecyclerView.Adapter<ProductStyleOneAdapter.ViewHolder>
        implements View.OnClickListener {

    List<ProductoModel> list;
    Context context;
    private View.OnClickListener listener;

    int pos;

    public ProductStyleOneAdapter(List<ProductoModel> list) {
        this.list = list;
    }

    public ProductStyleOneAdapter(Context context) {
        this.context = context;
    }

    @NotNull
    @Override
    public ProductStyleOneAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_list_product_style_one, null, false);
        view.setOnClickListener(this);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductStyleOneAdapter.ViewHolder holder, int position) {

        pos = holder.getAdapterPosition();

        String name = list.get(pos).getNom_prod();
        holder.name.setText(name);
        String detail = list.get(pos).getDesc_prod();
        holder.detail.setText(detail);
        String cost = list.get(pos).getPrecio_ventaprod();
        holder.cost.setText("S/. " + cost);

        Log.e("Debug_error", "" + list.get(pos).toString());

        try {
            if (list.get(pos).getImage_prod() != null) {
                String url = String.valueOf(list.get(pos).getImage_prod());

                Picasso.get().load(url)
                        .placeholder(R.drawable.second_image)
                        .into(holder.url);
            }
        } catch (Exception e) {
            Log.e("Debug_error", "" + e);
        }

        String state = list.get(pos).getStatus().toLowerCase().trim().toString();

        if (state.equals("agotado")) {
            holder.add.setEnabled(false);
            holder.cost.setText("Agotado");
            holder.cost.setTextColor(Color.RED);
        }

        holder.add.setOnClickListener(v -> {
            Toast.makeText(context, "add", Toast.LENGTH_SHORT).show();
        });

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
        TextView name, detail, cost;
        ImageView url;
        FloatingActionButton add;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_name_product_style_one);
            url = itemView.findViewById(R.id.iv_photo_product_style_one);
            detail = itemView.findViewById(R.id.tv_detail_product_style_one);
            cost = itemView.findViewById(R.id.tv_cost_product_style_one);
            add = itemView.findViewById(R.id.fab_add_product_style_one);
        }
    }
}
