package com.altoque.delivery.view.initial.ui.detail.viewdetailproduct;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.altoque.delivery.R;
import com.altoque.delivery.api.ApiClient;
import com.altoque.delivery.api.ApiHelper;
import com.altoque.delivery.apiInterface.ApiInterface;
import com.altoque.delivery.data.SessionSP;
import com.altoque.delivery.model.AggregatesModel;
import com.altoque.delivery.model.ProductoModel;
import com.altoque.delivery.model.SubAggregatesModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProductBottomSheet extends BottomSheetDialogFragment {

    View view;

    private BottomSheetBehavior mBehavior;
    private ApiInterface api;
    SessionSP sessionSP;


    String global_idproduct = "";
    private Context mContext;

    List<ProductoModel> listProductsQty;
    List<ProductoModel> listProducts;
    List<AggregatesModel> listAggregatesProduct;

    private final Integer qtyDefItem = 1;
    String countTemp = "";
    String global_minQuantity = "", global_maxQuantity = "";

    private TextView tv_qtyadd, tv_total;
    private FloatingActionButton fab_positive, fab_negative;

    private void initView() {

        /*TextView tv_timeopen = (TextView) view.findViewById(R.id.tv_timeopen_businessdata);
        TextView tv_timeclose = (TextView) view.findViewById(R.id.tv_timeclose_businessdata);*/
        TextView tv_address = (TextView) view.findViewById(R.id.tv_map_businessdata);
        tv_total = (TextView) view.findViewById(R.id.tv_total_detailproduct);
        tv_qtyadd = (TextView) view.findViewById(R.id.tv_qtyadd_detailproduct);
        fab_positive = view.findViewById(R.id.fab_positive_detailproduct);
        fab_negative = view.findViewById(R.id.fab_negative_detailproduct);

    }

    private void initEvent() {

        fab_negative.setOnClickListener(v -> restQtyProduct());
        fab_positive.setOnClickListener(v -> sumQtyProduct());
    }

    private void initResources() {
        api = ApiClient.getApiClient().create(ApiInterface.class);

        sessionSP = SessionSP.get(requireContext());

        mContext = mContext.getApplicationContext();
        listProductsQty = new ArrayList<>();
        listProducts = new ArrayList<>();
        listAggregatesProduct = new ArrayList<>();
    }

    private DialogInterface.OnDismissListener onDismissListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(@NotNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater)
                mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.bottomsheet_product_detail, null);
        dialog.setContentView(view);

        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        mBehavior.setPeekHeight(0);

        /*requireActivity().getWindow()
                .setStatusBarColor(ContextCompat.getColor(requireContext(), R.color.transparent));
        requireActivity().getWindow()
                .getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);*/

        initView();
        initResources();
        initEvent();
        setupStatesBS();

        if (getArguments() != null) {
            String id_prod = getArguments().getString("value_idproduct");

            String id_prod_def = ((id_prod != null) ? id_prod : "-1");

            if (id_prod_def.isEmpty() || id_prod_def.equals("-1")) {
                Toast.makeText(requireContext(), "No se obtuvo el id " + id_prod_def, Toast.LENGTH_SHORT).show();
            } else {
                global_idproduct = id_prod_def;

                getDataProduct(global_idproduct);
            }
        }


        return dialog;
    }


    private void setupStatesBS() {
        mBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                    case BottomSheetBehavior.STATE_COLLAPSED:
                    case BottomSheetBehavior.STATE_HALF_EXPANDED:

                        mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                    case BottomSheetBehavior.STATE_SETTLING:
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
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

                            Toast.makeText(mContext, "Sin Productos por cargar.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, "Sin Productos por cargar.",
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

                            Toast.makeText(mContext, "Sin registro de cantidades.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, "Sin registro de cantidades.",
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
                            Toast.makeText(mContext, "El item no cuenta con acompa??amientos.",
                                    Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(mContext, "Sin acompa??amientos por cargar.",
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

        for (int i = 0; i<list.size(); i++){
            //Log.e("error_log_p", "" + i+"\nCOUNT: "+list.get(i).toString());

            List<SubAggregatesModel> listSubAgg = new ArrayList<>();

            listSubAgg  = list.get(i).getAcompanamiento();

            Log.e("error_log_p", "POSITION: " + i+"\nDATA: "+listSubAgg.toString());
        }
    }


    /***************************/

    private void setImageProduct(List<ProductoModel> list) {
        ImageView imageView = view.findViewById(R.id.iv_image_detailproduct);
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
        TextView textView = view.findViewById(R.id.tv_name_detailproduct);
        try {
            if (list.get(0).getNom_prod() != null || !list.get(0).getNom_prod().isEmpty()) {
                textView.setText(list.get(0).getNom_prod());
            }
        } catch (Exception e) {
            Log.e("Debug_error", "" + e);
        }
    }

    private void setDescriptionProduct(List<ProductoModel> list) {
        TextView textView = view.findViewById(R.id.tv_description_detailproduct);
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
        TextView textView = view.findViewById(R.id.tv_cost_detailproduct);
        try {
            if (list.get(0).getPrecio_venta_unidad() != null || !list.get(0).getPrecio_venta_unidad().isEmpty()) {
                textView.setText("S/. " + list.get(0).getPrecio_venta_unidad());
            }
        } catch (Exception e) {
            Log.e("Debug_error", "" + e);
        }
    }

    private void setBrandProduct(List<ProductoModel> list) {
        TextView textView = view.findViewById(R.id.tv_brand_detailproduct);
        try {
            if (list.get(0).getNombre_marca() != null || !list.get(0).getNombre_marca().isEmpty()) {
                textView.setText(list.get(0).getNombre_marca());
            }
        } catch (Exception e) {
            Log.e("Debug_error", "" + e);
        }
    }

    private void setCategoryProduct(List<ProductoModel> list) {
        TextView textView = view.findViewById(R.id.tv_category_detailproduct);
        try {
            if (list.get(0).getNomb_categoria() != null || !list.get(0).getNomb_categoria().isEmpty()) {
                textView.setText(list.get(0).getNomb_categoria());
            }
        } catch (Exception e) {
            Log.e("Debug_error", "" + e);
        }
    }

    private void setMeasureProduct(List<ProductoModel> list) {
        TextView textView = view.findViewById(R.id.tv_measure_detailproduct);
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
                String textToast = "Cantidad m??xima (" + maxLocal + ") alcanzada.";
                Toast.makeText(requireContext(), "" + textToast, Toast.LENGTH_SHORT).show();
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


    /******************************/
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (mContext == null)
            mContext = context.getApplicationContext();
        mContext = context;
    }

    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
    }


}
