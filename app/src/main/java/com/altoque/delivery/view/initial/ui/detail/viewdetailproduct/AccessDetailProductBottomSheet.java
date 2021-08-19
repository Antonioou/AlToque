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
import androidx.core.content.ContextCompat;

import com.altoque.delivery.R;
import com.altoque.delivery.adapter.ProductStyleOneAdapter;
import com.altoque.delivery.api.ApiClient;
import com.altoque.delivery.api.ApiHelper;
import com.altoque.delivery.apiInterface.ApiInterface;
import com.altoque.delivery.data.SessionSP;
import com.altoque.delivery.model.ProductoModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccessDetailProductBottomSheet extends BottomSheetDialogFragment {

    View view;

    private BottomSheetBehavior mBehavior;
    private ApiInterface api;
    SessionSP sessionSP;

    private Context mContext;

    String global_idproduct = "";


    private void initView() {

        /*TextView tv_timeopen = (TextView) view.findViewById(R.id.tv_timeopen_businessdata);
        TextView tv_timeclose = (TextView) view.findViewById(R.id.tv_timeclose_businessdata);
        TextView tv_address = (TextView) view.findViewById(R.id.tv_map_businessdata);
        TextView tv_phone = (TextView) view.findViewById(R.id.tv_phone_businessdata);
        TextView tv_mail = (TextView) view.findViewById(R.id.tv_mail_businessdata);*/

    }

    private void initEvent() {


    }

    private void initResources() {
        api = ApiClient.getApiClient().create(ApiInterface.class);

        sessionSP = SessionSP.get(requireContext());

        mContext = mContext.getApplicationContext();
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

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater)
                mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.bottomsheet_product_detail_access, null);
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
                        mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                    case BottomSheetBehavior.STATE_DRAGGING:
                    case BottomSheetBehavior.STATE_SETTLING:
                    case BottomSheetBehavior.STATE_HALF_EXPANDED:
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
            @Override
            public void onResponse(@NonNull Call<List<ProductoModel>> call, @NonNull Response<List<ProductoModel>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().size() > 0) {
                        String code = response.body().get(0).getCode_server().toString();

                        if (code.equals("221")) {

                            List<ProductoModel> listProducts = new ArrayList<>();
                            listProducts = response.body();

                            if (listProducts.size() > 0) {
                                setCostProduct(listProducts);
                                setImageProduct(listProducts);
                                setNameProduct(listProducts);
                                setDescriptionProduct(listProducts);
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

    private void setImageProduct(List<ProductoModel> list) {
        ImageView imageView = view.findViewById(R.id.iv_image_access_detailproduct);
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
        TextView textView = view.findViewById(R.id.tv_name_access_detailproduct);
        try {
            if (list.get(0).getNom_prod() != null || !list.get(0).getNom_prod().isEmpty()) {
                textView.setText(list.get(0).getNom_prod());
            }
        } catch (Exception e) {
            Log.e("Debug_error", "" + e);
        }
    }

    private void setDescriptionProduct(List<ProductoModel> list) {
        TextView textView = view.findViewById(R.id.tv_description_access_detailproduct);
        try {
            if (list.get(0).getDesc_prod() != null || !list.get(0).getDesc_prod().isEmpty()) {
                textView.setText(list.get(0).getDesc_prod());
            }
        } catch (Exception e) {
            Log.e("Debug_error", "" + e);
        }
    }

    private void setCostProduct(List<ProductoModel> list) {
        TextView textView = view.findViewById(R.id.tv_cost_access_detailproduct);
        try {
            if (list.get(0).getPrecio_ventaprod() != null || !list.get(0).getPrecio_ventaprod().isEmpty()) {
                textView.setText("S/. " + list.get(0).getPrecio_ventaprod());
            }
        } catch (Exception e) {
            Log.e("Debug_error", "" + e);
        }
    }



    private void calculateCostProduct(List<ProductoModel> list) {
        TextView tv_total = view.findViewById(R.id.tv_total_access_detailproduct);
        try {
            if (list.get(0).getPrecio_ventaprod() != null || !list.get(0).getPrecio_ventaprod().isEmpty()) {
                tv_total.setText(list.get(0).getPrecio_ventaprod());
            }


        } catch (Exception e) {
            Log.e("Debug_error", "" + e);
        }
    }


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
