package com.altoque.delivery.view.initial.ui.services;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.altoque.delivery.R;
import com.altoque.delivery.adapter.BusinessStyleOneAdapter;
import com.altoque.delivery.adapter.BusinessStyleThreeAdapter;
import com.altoque.delivery.adapter.ServicesStyleOneAdapter;
import com.altoque.delivery.api.ApiClient;
import com.altoque.delivery.api.ApiHelper;
import com.altoque.delivery.apiInterface.ApiInterface;
import com.altoque.delivery.databinding.ActivityListBusinessbyrubroBinding;
import com.altoque.delivery.model.NegocioModel;
import com.altoque.delivery.model.RubroModel;
import com.altoque.delivery.view.initial.ui.detail.DetailBusinessActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListBusinessByRubroActivity extends AppCompatActivity {

    ActivityListBusinessbyrubroBinding binding;
    public ApiInterface apiInterface;

    String global_nameRubro = "", global_idRubro = "", global_photoRubro = "";
    FloatingActionButton fab_back;

    ImageView iv_photo;
    TextView tv_nameRubro, tv_qtylist;

    /* BUSINESS */
    private RecyclerView recview;
    private List<NegocioModel> list;
    private BusinessStyleThreeAdapter adapter;

    private void initResources() {
        iv_photo = binding.ivPhotoListBusinessByRubro;
        tv_nameRubro = binding.tvNameListBusinessByRubro;
        tv_qtylist = binding.tvQtyListBusinessByRubro;
        fab_back = binding.fabBackListBusinessByRubro;

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        recview = binding.rvListBusinessByRubro;

        list = new ArrayList<>();

        LinearLayoutManager layoutManRubro =
                new LinearLayoutManager(ListBusinessByRubroActivity.this,
                        RecyclerView.VERTICAL, false);
        recview.setLayoutManager(layoutManRubro);
        recview.setHasFixedSize(true);
        recview.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
    }


    private void eventListener() {
        fab_back.setOnClickListener(v -> finish());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityListBusinessbyrubroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow()
                    .setStatusBarColor(ContextCompat.
                            getColor(ListBusinessByRubroActivity.this, R.color.colorWhite));
            getWindow()
                    .getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        initResources();

        if (getIntent() != null) {
            global_nameRubro = getIntent().getStringExtra("value_name_rubro");
            global_idRubro = getIntent().getStringExtra("value_id_rubro");
            global_photoRubro = getIntent().getStringExtra("value_photo_rubro");

            Picasso.get()
                    .load(global_photoRubro)
                    .placeholder(R.drawable.second_image)
                    .into(iv_photo);

            tv_nameRubro.setText(global_nameRubro);
        }

        eventListener();
        getList();

    }

    private void getList() {

        try {
            Call<List<NegocioModel>> call = apiInterface.
                    getListBusinessByRubro("list_business_by_heading", "1", global_idRubro);
            ApiHelper.enqueueWithRetry(call, new Callback<List<NegocioModel>>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(@NotNull Call<List<NegocioModel>> call, @NotNull Response<List<NegocioModel>> response) {

                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        Log.e("Error_log_list", "" + response.body().toString());
                        if (response.body().size() > 0) {
                            String code = response.body().get(0).getCodeServer().toString();

                            if (code.equals("221")) {

                                list = response.body();


                                if (list.size() >= 1) {
                                    tv_qtylist.setText(list.size() + " Tienda (s)");

                                    adapter = new BusinessStyleThreeAdapter(list, ListBusinessByRubroActivity.this);
                                    recview.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                    recview.setVisibility(View.VISIBLE);
                                    eventAdapterBusinessList();
                                } else {
                                    tv_qtylist.setTextSize(25);
                                    tv_qtylist.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                    tv_qtylist.setText("No se hallaron tiendas relacionadas.");
                                    tv_qtylist.setMaxLines(6);
                                }

                            } else if (code.equals("010")) {
                                tv_qtylist.setTextSize(25);
                                tv_qtylist.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                tv_qtylist.setText("No se hallaron tiendas relacionadas.");
                                tv_qtylist.setMaxLines(6);
                            }
                        } else {
                            tv_qtylist.setTextSize(25);
                            tv_qtylist.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            tv_qtylist.setText("No se hallaron tiendas relacionadas.");
                            tv_qtylist.setMaxLines(6);
                            //Toast.makeText(ListBusinessByRubroActivity.this, ".", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<NegocioModel>> call, Throwable t) {
                    Log.e("Error_log_serices", "" + t.getMessage().toString());
                }
            });
        } catch (Exception e) {
            Log.e("Error_log_serices", "" + e.getMessage().toString());

        }
    }

    private void eventAdapterBusinessList() {
        adapter.setOnClickListener(v -> {
            int pos = recview.getChildAdapterPosition(v);
            Intent intent = new Intent(ListBusinessByRubroActivity.this, DetailBusinessActivity.class);
            intent.putExtra("value_idnegocio", list.get(pos).getIdnegocio().toString());
            //Toast.makeText(requireContext(), "pos "+pos+"\n id "+listBusinessByProv.get(pos).getIdnegocio(), Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });
    }
}