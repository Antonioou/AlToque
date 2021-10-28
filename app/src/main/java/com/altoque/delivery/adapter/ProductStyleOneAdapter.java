package com.altoque.delivery.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.altoque.delivery.R;
import com.altoque.delivery.model.ProductoModel;
import com.altoque.delivery.view.initial.ui.detail.viewdetailproduct.AccessDetailProductBottomSheet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProductStyleOneAdapter extends RecyclerView.Adapter<ProductStyleOneAdapter.ViewHolder>
        implements View.OnClickListener {

    List<ProductoModel> list;
    Context context;
    private View.OnClickListener listener;

    public FragmentManager fragmentManager;

    public ProductStyleOneAdapter(List<ProductoModel> list) {
        this.list = list;
    }

    public ProductStyleOneAdapter(List<ProductoModel> list, Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.list = list;
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



        String name = list.get(position).getNom_prod();
        holder.name.setText(name);
        String detail = list.get(position).getDesc_prod();
        holder.detail.setText(detail);
        String cost = list.get(position).getPrecio_venta_unidad();
        holder.cost.setText("S/. " + cost);

        //Log.e("Debug_error", "" + list.get(pos).toString());

        try {
            if (list.get(position).getImage_prod() != null) {
                String url = String.valueOf(list.get(position).getImage_prod());

                Picasso.get().load(url)
                        .placeholder(R.drawable.second_image)
                        .into(holder.url);
            }
        } catch (Exception e) {
            Log.e("Debug_error", "" + e);
        }

        String state = list.get(position).getStatus().toLowerCase().trim().toString();

        if (state.equals("agotado")) {
            //holder.add.setVisibility(View.GONE);
            holder.cost.setText("Agotado");
            holder.cost.setTextColor(Color.RED);
        }

        /*holder.add.setOnClickListener(v -> {

            AccessDetailProductBottomSheet accessBottomSheet = new AccessDetailProductBottomSheet();
            Bundle bundle = new Bundle();
            bundle.putString("value_idproduct", list.get(position).getIdproducto().toString());
            accessBottomSheet.setArguments(bundle);
            accessBottomSheet.show(fragmentManager, "");
        });*/

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
        //FloatingActionButton add;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_name_product_style_one);
            url = itemView.findViewById(R.id.iv_photo_product_style_one);
            detail = itemView.findViewById(R.id.tv_detail_product_style_one);
            cost = itemView.findViewById(R.id.tv_cost_product_style_one);
            //add = itemView.findViewById(R.id.fab_add_product_style_one);
        }
    }
}
