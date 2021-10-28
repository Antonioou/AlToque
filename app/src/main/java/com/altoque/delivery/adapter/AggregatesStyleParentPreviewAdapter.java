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
import com.altoque.delivery.model.AggregatesModel;
import com.altoque.delivery.model.SubAggregatesModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AggregatesStyleParentPreviewAdapter extends RecyclerView.Adapter<AggregatesStyleParentPreviewAdapter.ViewHolder>
        implements View.OnClickListener {

    List<SubAggregatesModel> listSub;
    List<AggregatesModel> listAgg;
    Context context;
    private View.OnClickListener listener;

    public FragmentManager fragmentManager;

    public AggregatesStyleParentPreviewAdapter(List<SubAggregatesModel> listSub) {
        this.listSub = listSub;
        notifyDataSetChanged();
    }

    public AggregatesStyleParentPreviewAdapter(List<AggregatesModel> listAgg, List<SubAggregatesModel> listSub) {
        this.listSub = listSub;
        this.listAgg = listAgg;
    }

    public AggregatesStyleParentPreviewAdapter(List<AggregatesModel> listAgg, List<SubAggregatesModel> listSub, Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.listAgg = listAgg;
        this.listSub = listSub;
    }

    @NotNull
    @Override
    public AggregatesStyleParentPreviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_list_preview_parent_aggregates, null, false);
        view.setOnClickListener(this);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AggregatesStyleParentPreviewAdapter.ViewHolder holder, int position) {

        AggregatesModel item = listAgg.get(position);

        if (item != null) {

            holder.tv_agg.setText("" + item.getNombre_etiqueta());

            Integer size = 0;
            size = (listSub.size() >0) ? listSub.size() : 0;

            holder.tv_agg_size.setText(""+size);

            AggregatesStyleChildPreviewAdapter adapter;
            adapter = new AggregatesStyleChildPreviewAdapter(item.getAcompanamiento(), item.getSeleccion_multiple_unitaria());
            holder.rv_data.setAdapter(adapter);




        }



        /*String name_sub = listSub.get(position).getNombre_subproducto();
        holder.tv_subagg.setText(name_sub);*/


        //Log.e("Debug_error", "" + list.get(pos).toString());

        /*try {
            if (list.get(position).get() != null) {
                String url = String.valueOf(list.get(position).getImage_prod());

                Picasso.get().load(url)
                        .placeholder(R.drawable.second_image)
                        .into(holder.url);
            }
        } catch (Exception e) {
            Log.e("Debug_error", "" + e);
        }*/

        /*String state = list.get(position).getStatus().toLowerCase().trim().toString();

        if (state.equals("agotado")) {
            //holder.add.setVisibility(View.GONE);
            holder.cost.setText("Agotado");
            holder.cost.setTextColor(Color.RED);
        }/

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
        return (listAgg.size() >0 ? listAgg.size() : 0);
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
        TextView tv_agg, tv_agg_size;

        RecyclerView rv_data;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_agg = itemView.findViewById(R.id.tv_aggregates_parent);
            tv_agg_size = itemView.findViewById(R.id.tv_aggregates_size_parent);
            rv_data = itemView.findViewById(R.id.recyclerview_child_subaggregates);
        }
    }
}

