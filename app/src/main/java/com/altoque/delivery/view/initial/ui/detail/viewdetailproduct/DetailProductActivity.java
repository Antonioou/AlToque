package com.altoque.delivery.view.initial.ui.detail.viewdetailproduct;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.altoque.delivery.R;
import com.altoque.delivery.adapter.AggregatesStyleParentPreviewAdapter;
import com.altoque.delivery.api.ApiClient;
import com.altoque.delivery.api.ApiHelper;
import com.altoque.delivery.apiInterface.ApiInterface;
import com.altoque.delivery.data.SessionSP;
import com.altoque.delivery.databinding.ActivityDetailProductBinding;
import com.altoque.delivery.model.AggregatesModel;
import com.altoque.delivery.model.ProductoModel;
import com.altoque.delivery.model.SubAggregatesModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProductActivity extends AppCompatActivity {

    private ApiInterface api;
    SessionSP sessionSP;
    ActivityDetailProductBinding binding;

    String global_idproduct = "";


    List<ProductoModel> listProductsQty;
    List<ProductoModel> listProducts;
    List<AggregatesModel> listAggregatesProduct;
    List<AggregatesModel> listPreviewAggregatesProduct;

    AggregatesStyleParentPreviewAdapter adapterPreviewAggregates;

    private final Integer qtyDefItem = 1;
    String countTemp = "";
    String global_minQuantity = "", global_maxQuantity = "";

    private TextView tv_qtyadd, tv_total;
    private FloatingActionButton fab_positive, fab_negative;

    RecyclerView rv_preview_aggregates;


    private void initView() {

        /*TextView tv_timeopen = (TextView) view.findViewById(R.id.tv_timeopen_businessdata);
        TextView tv_timeclose = (TextView) view.findViewById(R.id.tv_timeclose_businessdata);*/
        TextView tv_address = (TextView) findViewById(R.id.tv_map_businessdata);
        tv_total = (TextView) findViewById(R.id.tv_total_detailproduct);
        tv_qtyadd = (TextView) findViewById(R.id.tv_qtyadd_detailproduct);
        fab_positive = findViewById(R.id.fab_positive_detailproduct);
        fab_negative = findViewById(R.id.fab_negative_detailproduct);

        rv_preview_aggregates = binding.rvPreviewAggregates;


    }

    private void initEvent() {

        fab_negative.setOnClickListener(v -> restQtyProduct());
        fab_positive.setOnClickListener(v -> sumQtyProduct());


        FloatingActionButton fab_back_ = findViewById(R.id.fab_back_detailproduct);
        fab_back_.setOnClickListener(v -> finish());
    }

    private void initResources() {
        api = ApiClient.getApiClient().create(ApiInterface.class);

        sessionSP = SessionSP.get(DetailProductActivity.this);

        listProductsQty = new ArrayList<>();
        listProducts = new ArrayList<>();
        listAggregatesProduct = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        binding = ActivityDetailProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow()
                .setStatusBarColor(ContextCompat.getColor(DetailProductActivity.this, R.color.colorWhite));
        getWindow()
                .getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        initView();
        initResources();
        initEvent();

        if (getIntent().getExtras() != null) {
            String id_prod = getIntent().getStringExtra("value_idproduct");

            String id_prod_def = ((id_prod != null) ? id_prod : "-1");

            if (id_prod_def.isEmpty() || id_prod_def.equals("-1")) {
                Toast.makeText(DetailProductActivity.this, "No se obtuvo el id " + id_prod_def, Toast.LENGTH_SHORT).show();
            } else {
                global_idproduct = id_prod_def;

                getDataProduct(global_idproduct);
            }
        }
    }


    private void getDataProduct(String id) {
        Call<List<ProductoModel>> call = api.getDataProduct("detail_product", id);
        ApiHelper.enqueueWithRetry(call, new Callback<List<ProductoModel>>() {
            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            @Override
            public void onResponse(@NonNull Call<List<ProductoModel>> call, @NonNull Response<List<ProductoModel>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().size() > 0) {
                        String code = response.body().get(0).getCode_server().toString();

                        if (code.equals("221")) {

                            listProducts = response.body();

                            if (listProducts.size() > 0) {
                                setCostProduct(listProducts);
                                setImageProduct(listProducts);
                                setNameProduct(listProducts);
                                setDescriptionProduct(listProducts);
                                setBrandProduct(listProducts);
                                setCategoryProduct(listProducts);
                                //setMeasureProduct(listProducts);
                                getQuantityPermitiedProduct(listProducts.get(0).getIdproducto());
                                //getAggregatesProduct(listProducts.get(0).getIdproducto());
                                getAggregatesProduct("3");
                                tv_total.setText("S/. " + listProducts.get(0).getPrecio_venta_unidad());
                            }


                        } else if (code.equals("010")) {

                            Toast.makeText(DetailProductActivity.this, "Sin Productos por cargar.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(DetailProductActivity.this, "Sin Productos por cargar.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ProductoModel>> call, @NonNull Throwable t) {

            }
        });
    }

    private void getQuantityPermitiedProduct(String id) {
        Call<List<ProductoModel>> call = api.getQuantityPermitiedProduct("max_min_quantity_allowed", id);
        ApiHelper.enqueueWithRetry(call, new Callback<List<ProductoModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<ProductoModel>> call, @NonNull Response<List<ProductoModel>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().size() > 0) {
                        String code = response.body().get(0).getCode_server().toString();

                        if (code.equals("221")) {

                            listProductsQty = response.body();

                            if (listProductsQty.size() > 0) {
                                global_minQuantity = listProductsQty.get(0).getMinimum_quantity().toString();
                                global_maxQuantity = listProductsQty.get(0).getMaximum_quantity().toString();
                                fab_negative.setEnabled(true);
                                fab_positive.setEnabled(true);

                                tv_qtyadd.setText(global_minQuantity);
                            }


                        } else if (code.equals("010")) {

                            Toast.makeText(DetailProductActivity.this, "Sin registro de cantidades.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(DetailProductActivity.this, "Sin registro de cantidades.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ProductoModel>> call, @NonNull Throwable t) {

            }
        });
    }

    private void getAggregatesProduct(String id) {
        Call<List<AggregatesModel>> call = api.getAggregatesProduct("validate_support_product", "3");
        ApiHelper.enqueueWithRetry(call, new Callback<List<AggregatesModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<AggregatesModel>> call,
                                   @NonNull Response<List<AggregatesModel>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().size() > 0) {
                        String code = response.body().get(0).getCode_server().toString();

                        if (code.equals("221")) {

                            listAggregatesProduct = response.body();

                            if (listAggregatesProduct.size() > 0) {

                                extractData(listAggregatesProduct);

                            }
                        } else if (code.equals("010")) {
                            listAggregatesProduct = response.body();
                            Toast.makeText(DetailProductActivity.this, "El item no cuenta con acompañamientos.",
                                    Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(DetailProductActivity.this, "Sin acompañamientos por cargar.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<AggregatesModel>> call, @NonNull Throwable t) {

            }
        });
    }

    private void extractData(List<AggregatesModel> list) {
        //Log.e("error_log_p", "  FIRSTCOUNT: "+list.get(0).toString());
        List<AggregatesModel> listAgg = new ArrayList<>();
        List<SubAggregatesModel> listSubAgg = new ArrayList<>();

        if (list.size() > 0) {

            listAgg = list;

            for (int i = 0; i < list.size(); i++) {
                //Log.e("error_log_p", "" + i+"\nCOUNT: "+list.get(i).toString());

                listSubAgg = list.get(i).getAcompanamiento();

                Log.e("error_log_p", "POSITION: " + i + "\nDATA: " + listSubAgg.toString());
            }

            retrieveDataPreviewAggregates(listAgg, listSubAgg);
        }
    }


    private void retrieveDataPreviewAggregates(List<AggregatesModel> listAgg, List<SubAggregatesModel> listSub) {

        if (listSub.size() > 0) {

            /*LinearLayoutManager layoutManBusinessByProv = new LinearLayoutManager(DetailProductActivity.this,
                    RecyclerView.VERTICAL, false);
            rv_preview_aggregates.setLayoutManager(layoutManBusinessByProv);*/
            rv_preview_aggregates.setHasFixedSize(true);
            rv_preview_aggregates.setItemViewCacheSize(7);
            rv_preview_aggregates.setDrawingCacheEnabled(true);
            rv_preview_aggregates.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

            adapterPreviewAggregates = new AggregatesStyleParentPreviewAdapter(listAgg, listSub);

            rv_preview_aggregates.setAdapter(adapterPreviewAggregates);
            adapterPreviewAggregates.notifyDataSetChanged();

        }

    }

    /***************************/

    private void setImageProduct(List<ProductoModel> list) {
        ImageView imageView = findViewById(R.id.iv_image_detailproduct);
        try {
            if (list.get(0).getImage_prod() != null) {
                String url = String.valueOf(list.get(0).getImage_prod());

                Picasso.get().load(url)
                        .placeholder(R.drawable.second_image)
                        .into(imageView);
            }
        } catch (Exception e) {
            Log.e("Debug_error", "" + e);
        }
    }

    private void setNameProduct(List<ProductoModel> list) {
        TextView textView = findViewById(R.id.tv_name_detailproduct);
        try {
            if (list.get(0).getNom_prod() != null || !list.get(0).getNom_prod().isEmpty()) {
                textView.setText(list.get(0).getNom_prod());
            }
        } catch (Exception e) {
            Log.e("Debug_error", "" + e);
        }
    }

    private void setDescriptionProduct(List<ProductoModel> list) {
        TextView textView = findViewById(R.id.tv_description_detailproduct);
        try {
            if (list.get(0).getDesc_prod() != null || !list.get(0).getDesc_prod().isEmpty()) {
                textView.setText(list.get(0).getDesc_prod());
            }
        } catch (Exception e) {
            Log.e("Debug_error", "" + e);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setCostProduct(List<ProductoModel> list) {
        TextView textView = findViewById(R.id.tv_cost_detailproduct);
        try {
            if (list.get(0).getPrecio_venta_unidad() != null || !list.get(0).getPrecio_venta_unidad().isEmpty()) {
                textView.setText("S/. " + list.get(0).getPrecio_venta_unidad());
            }
        } catch (Exception e) {
            Log.e("Debug_error", "" + e);
        }
    }

    private void setBrandProduct(List<ProductoModel> list) {
        TextView textView = findViewById(R.id.tv_brand_detailproduct);
        try {
            if (list.get(0).getNombre_marca() != null || !list.get(0).getNombre_marca().isEmpty()) {
                textView.setText(list.get(0).getNombre_marca());
            }
        } catch (Exception e) {
            Log.e("Debug_error", "" + e);
        }
    }

    private void setCategoryProduct(List<ProductoModel> list) {
        TextView textView = findViewById(R.id.tv_category_detailproduct);
        try {
            if (list.get(0).getNomb_categoria() != null || !list.get(0).getNomb_categoria().isEmpty()) {
                textView.setText(list.get(0).getNomb_categoria());
            }
        } catch (Exception e) {
            Log.e("Debug_error", "" + e);
        }
    }

    private void setMeasureProduct(List<ProductoModel> list) {
        TextView textView = findViewById(R.id.tv_measure_detailproduct);
        try {
            if (list.get(0).getNom_medida() != null || !list.get(0).getNom_medida().isEmpty()) {
                textView.setText(list.get(0).getNom_medida());
            }
        } catch (Exception e) {
            Log.e("Debug_error", "" + e);
        }
    }

    /****************************/


    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void sumQtyProduct() {

        if (!global_maxQuantity.isEmpty()) {

            Integer maxLocal = Integer.parseInt(global_maxQuantity);

            Integer count = Integer.parseInt(tv_qtyadd.getText().toString());
            int countT = 0;

            if (countTemp.isEmpty()) {
                countT = 0;
            } else {
                countT = Integer.parseInt(countTemp);
            }

            int total = countT + count;
            double price = 0.0;

            if (!listProducts.get(0).getPrecio_venta_unidad().isEmpty()) {
                price = Double.parseDouble(listProducts.get(0).getPrecio_venta_unidad().toString());
                //Log.e("error_log_p", "" + price);
            }

            if (total >= maxLocal) {
                String textToast = "Cantidad máxima (" + maxLocal + ") alcanzada.";
                Toast.makeText(DetailProductActivity.this, "" + textToast, Toast.LENGTH_SHORT).show();
            } else {
                count += qtyDefItem;
                tv_qtyadd.setText(String.valueOf(count));

                if (price != 0.0) {
                    Double sumPrice = price * count;
                    tv_total.setText("S/. " + String.format("%.2f", sumPrice));
                }
            }
        }
    }

    private void restQtyProduct() {

        if (!global_minQuantity.isEmpty()) {
            Integer minLocal = Integer.parseInt(global_minQuantity);

            Integer count = Integer.parseInt(tv_qtyadd.getText().toString());

            double price = 0.0;

            if (!listProducts.get(0).getPrecio_venta_unidad().isEmpty()) {
                price = Double.parseDouble(listProducts.get(0).getPrecio_venta_unidad().toString());
            }

            if (count > minLocal) {
                count -= qtyDefItem;
                tv_qtyadd.setText(String.valueOf(count));

                Double sumPrice = price * count;
                tv_total.setText("S/. " + String.format("%.2f", sumPrice));
            }
        }

    }
}