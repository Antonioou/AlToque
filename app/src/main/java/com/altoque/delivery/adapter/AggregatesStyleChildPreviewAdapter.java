package com.altoque.delivery.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.altoque.delivery.R;
import com.altoque.delivery.model.SubAggregatesModel;
import com.google.android.material.checkbox.MaterialCheckBox;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AggregatesStyleChildPreviewAdapter extends RecyclerView.Adapter<AggregatesStyleChildPreviewAdapter.ViewHolder>
        implements View.OnClickListener {

    List<SubAggregatesModel> listSub;
    String seleccionMultiple;
    //List<AggregatesModel> listAgg;
    Context context;
    private View.OnClickListener listener;

    public FragmentManager fragmentManager;

    public AggregatesStyleChildPreviewAdapter(List<SubAggregatesModel> listSub, String seleccionMultiple) {
        this.listSub = listSub;
        this.seleccionMultiple = seleccionMultiple;
        notifyDataSetChanged();
    }

    /*public AggregatesStyleChildPreviewAdapter(List<AggregatesModel> listAgg, List<SubAggregatesModel> listSub) {
        this.listSub = listSub;
        this.listAgg = listAgg;
    }*/

    /*public AggregatesStyleChildPreviewAdapter(List<AggregatesModel> listAgg, List<SubAggregatesModel> listSub, Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.listAgg = listAgg;
        this.listSub = listSub;
    }*/

    @NotNull
    @Override
    public AggregatesStyleChildPreviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_list_preview_child_aggregates, null, false);
        view.setOnClickListener(this);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AggregatesStyleChildPreviewAdapter.ViewHolder holder, int position) {

        SubAggregatesModel item = listSub.get(position);

        if (item != null) {

            String name_sub = item.getNombre_subproducto();

            if (seleccionMultiple.equals("0")) {

                holder.chb_subagg.setVisibility(View.GONE);
                holder.tv_subagg.setVisibility(View.VISIBLE);
                holder.tv_subagg.setText(name_sub);

            } else {

                holder.chb_subagg.setText(name_sub);
                holder.chb_subagg.setVisibility(View.VISIBLE);
                holder.tv_subagg.setVisibility(View.GONE);

            }

            String valor_opcional = item.getOpcional_obligatorio();
            String valor_pagable = item.getPagable_no_pagable();

            holder.tv_option.setText(""+item.getValor_opcional_obligatorio());


            if (valor_opcional.equals("1")){

            }

            if (valor_pagable.equals("1")){
                holder.tv_cost.setVisibility(View.VISIBLE);
                holder.tv_cost.setText("S/. "+item.getPrecio_acompanamiento());
                holder.itemView.findViewById(R.id.tv_separate).setVisibility(View.VISIBLE);
            }else{
                holder.tv_cost.setVisibility(View.GONE);
                holder.itemView.findViewById(R.id.tv_separate).setVisibility(View.GONE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return listSub.size();
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

        TextView tv_subagg, tv_option, tv_cost;
        MaterialCheckBox chb_subagg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_subagg = itemView.findViewById(R.id.tv_subaggregates_child);
            chb_subagg = itemView.findViewById(R.id.chb_subaggregates_child);
            tv_cost = itemView.findViewById(R.id.tv_cost_subagg_child);
            tv_option = itemView.findViewById(R.id.tv_option_subagg_child);

        }
    }
}

