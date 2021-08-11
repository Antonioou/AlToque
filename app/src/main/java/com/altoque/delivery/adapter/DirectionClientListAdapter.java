package com.altoque.delivery.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.altoque.delivery.api.ApiClient;
import com.altoque.delivery.api.ApiHelper;
import com.altoque.delivery.apiInterface.ApiInterface;
import com.altoque.delivery.data.SessionSP;
import com.altoque.delivery.model.DomicilioModel;
import com.altoque.delivery.view.direction.DirectionClientActivity;
import com.altoque.delivery.view.direction.DirectionListBottomSheet;
import com.altoque.delivery.view.direction.DirectionStaticActivity;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DirectionClientListAdapter extends RecyclerView.Adapter<DirectionClientListAdapter.ViewHolder>
        implements View.OnClickListener {

    List<DomicilioModel> list;
    Context context;
    private View.OnClickListener listener;
    DirectionListBottomSheet bottomSheet;

    ApiInterface apiInterface;

    String value_use = "";

    int pos;

    public DirectionClientListAdapter(List<DomicilioModel> list, Context context,
                                      DirectionListBottomSheet bottomSheet) {
        this.list = list;
        this.context = context;
        this.bottomSheet = bottomSheet;
    }

    @NonNull
    @Override
    public DirectionClientListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_list_direction_client, null, false);
        view.setOnClickListener(this);
        context = parent.getContext();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DirectionClientListAdapter.ViewHolder holder, int position) {

        pos = holder.getAdapterPosition();

        String name = list.get(position).getDireccion_dom();
        holder.name.setText(name);
        String reference = list.get(position).getReferencia_dom();
        holder.reference.setText(reference);
        /*String numberfloat = list.get(position).getPiso_dom();
        holder.numberfloat.setText(numberfloat);*/

        String iddom = list.get(position).getIddomicilio();
        value_use = list.get(position).getUso_dom();

        if (list.size()==1) holder.fab_delete.setVisibility(View.GONE);

        switch (value_use) {
            case "1":
                value_use = "1";
                holder.fab_setuse.setImageResource(R.drawable.ic_baseline_done_24);
                holder.cv_section.setStrokeWidth(3);
                holder.cv_section.setStrokeColor(context.getResources().getColor(R.color.colorPrimary));
                holder.fab_delete.setVisibility(View.GONE);
                break;
            case "0":
                value_use = "0";
                holder.fab_setuse.setImageResource(R.drawable.ic_baseline_where_to_vote_24);
                break;
            default:
                holder.fab_setuse.setImageResource(R.drawable.ic_baseline_where_to_vote_24);
                break;
        }

        holder.fab_view.setOnClickListener(view -> {
            context.startActivity(new Intent(context, DirectionStaticActivity.class));

        });

        holder.fab_edit.setOnClickListener(v -> {
            //Log.e("DirectionClient_log", "iddom adapter " + iddom);
            Intent intent = new Intent(context, DirectionClientActivity.class);
            intent.putExtra("action", "edit_data");
            intent.putExtra("value_id", iddom);
            context.startActivity(intent);
        });

        holder.fab_setuse.setOnClickListener(v -> {
            if (list.get(position).getUso_dom().equals("0")) {
                MaterialAlertDialogBuilder mat = new MaterialAlertDialogBuilder(context);
                mat.create();
                mat.setMessage("¿Seguro que desea cambiar de dirección predeterminada?")
                        .setPositiveButton("CAMBIAR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String idclient = SessionSP.get(context).getIdClientSessSp();
                                setUseDirection("1", idclient, iddom);
                            }
                        })
                        .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
            }
        });

        holder.fab_delete.setOnClickListener(view -> {

            MaterialAlertDialogBuilder mat = new MaterialAlertDialogBuilder(context);
            mat.create();
            mat.setTitle("Confirmar");
            mat.setMessage("¿Seguro que desea eliminar la dirección?")
                    .setPositiveButton("ELIMINAR", (dialogInterface, i) -> {

                        String idclient = SessionSP.get(context).getIdClientSessSp();

                        Call<List<DomicilioModel>> call = apiInterface.
                                deleteClientDirection("insert_select_adress",
                                        "4", iddom, idclient);
                        ApiHelper.enqueueWithRetry(call, new Callback<List<DomicilioModel>>() {
                            @Override
                            public void onResponse(@NonNull Call<List<DomicilioModel>> call, @NonNull Response<List<DomicilioModel>> response) {
                                if (response.isSuccessful()) {
                                    assert response.body() != null;
                                    //Log.e("Error_log_adapter", "pos: "+iddom+"\n"+ "idcli: "+idclient+"\n"+response.body().toString());
                                    if (response.body().get(0).getCode_server().equals("221")) {
                                        list.remove(pos);
                                        notifyDataSetChanged();
                                        notifyItemRemoved(pos);
                                        Toast.makeText(context, "Eliminado correctamente.", Toast.LENGTH_SHORT).show();
                                    } else if (response.body().get(0).getCode_server().equals("010")) {
                                        Toast.makeText(context, "No se logró eliminar.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<List<DomicilioModel>> call, @NonNull Throwable t) {

                            }
                        });
                    }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            mat.show();

        });

    }

    private void setUseDirection(String use_state, String idcliente, String iddom) {

        Call<List<DomicilioModel>> call = apiInterface.setDirectionNewUse("insert_select_adress",
                "7", use_state, iddom, idcliente);

        ApiHelper.enqueueWithRetry(call, new Callback<List<DomicilioModel>>() {
            @Override
            public void onResponse(@NotNull Call<List<DomicilioModel>> call,
                                   @NotNull Response<List<DomicilioModel>> response) {
                if (response.isSuccessful()) {
                    Log.e("DirectionClient_log", "body: " + response.body());
                    assert response.body() != null;
                    switch (response.body().get(0).getCode_server()) {
                        case "221":
                            Toast.makeText(context, "Se cambió la dirección correctamente.", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                            bottomSheet.refreshRecyclerView();

                            break;
                        case "110":
                            Toast.makeText(context,
                                    "Error de parametros. Intente mas tarde.", Toast.LENGTH_LONG).show();
                            break;
                        case "010":
                            break;
                    }
                } else {
                    Toast.makeText(context, "No se logró registrar.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<DomicilioModel>> call, @NonNull Throwable t) {
                Log.e("adapterDirClient_error", "error: " + t.getMessage());

            }
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
        TextView name, reference;
        ImageView url;
        FloatingActionButton fab_view, fab_delete, fab_edit, fab_setuse;
        MaterialCardView cv_section;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_name_item_direction_list);
            reference = itemView.findViewById(R.id.tv_reference_item_direction_list);
            //numberfloat numberfloat = itemView.findViewById(R.id.tv_numberfloat_item_direction_list);
            fab_view = itemView.findViewById(R.id.fab_view_item_direction_list);
            fab_delete = itemView.findViewById(R.id.fab_delete_item_direction_list);
            fab_edit = itemView.findViewById(R.id.fab_edit_item_direction_list);
            fab_setuse = itemView.findViewById(R.id.fab_setuse_item_direction_list);
            cv_section = itemView.findViewById(R.id.cv_section_item_direction_list);
        }
    }
}
