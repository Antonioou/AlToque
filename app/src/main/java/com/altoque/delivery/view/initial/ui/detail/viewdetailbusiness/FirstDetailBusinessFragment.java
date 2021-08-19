package com.altoque.delivery.view.initial.ui.detail.viewdetailbusiness;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.altoque.delivery.adapter.BusinessStyleOneAdapter;
import com.altoque.delivery.adapter.BusinessStyleTwoAdapter;
import com.altoque.delivery.adapter.CenterZoomLayoutManager;
import com.altoque.delivery.adapter.ProductStyleOneAdapter;
import com.altoque.delivery.api.ApiClient;
import com.altoque.delivery.api.ApiHelper;
import com.altoque.delivery.apiInterface.ApiInterface;
import com.altoque.delivery.databinding.FragmentFirstDetailBusinessBinding;
import com.altoque.delivery.model.NegocioModel;
import com.altoque.delivery.model.ProductoModel;
import com.altoque.delivery.view.initial.ui.detail.viewdetailproduct.AccessDetailProductBottomSheet;
import com.altoque.delivery.view.initial.ui.detail.viewdetailproduct.DetailProductBottomSheet;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstDetailBusinessFragment extends Fragment {

    private static final String ARG_PARAM_IDCATEGORY = "value_idcategory";
    private static final String ARG_PARAM_IDBUSINESS = "value_idbusiness";

    private String param_idcategory;
    private String param_idBusiness;

    FragmentFirstDetailBusinessBinding binding;
    public ApiInterface apiInterface;

    private RecyclerView recviewProducts;
    private List<ProductoModel> listProducts;
    private ProductStyleOneAdapter adapterProduct;

    private Context mContext;

    public FirstDetailBusinessFragment() {
        // Required empty public constructor
    }


    public static FirstDetailBusinessFragment newInstance(String idCategory, String idBusiness) {
        FirstDetailBusinessFragment fragment = new FirstDetailBusinessFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_IDCATEGORY, idCategory);
        args.putString(ARG_PARAM_IDBUSINESS, idBusiness);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            param_idBusiness = getArguments().getString(ARG_PARAM_IDBUSINESS);
            param_idcategory = getArguments().getString(ARG_PARAM_IDCATEGORY);
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFirstDetailBusinessBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recviewProducts = binding.rvProducts;
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        listProducts = new ArrayList<>();

        LinearLayoutManager layoutMan =
                new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
        recviewProducts.setLayoutManager(layoutMan);

        mContext = mContext.getApplicationContext();

        getItemList();
        return root;
    }

    private void getItemList() {

        try {
            Call<List<ProductoModel>> call = apiInterface.
                    getItemListByCategoryAndBusiness("list_products_business",
                            param_idBusiness, param_idcategory);
            ApiHelper.enqueueWithRetry(call, new Callback<List<ProductoModel>>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onResponse(@NotNull Call<List<ProductoModel>> call,
                                       @NotNull Response<List<ProductoModel>> response) {

                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        if (response.body().size() > 0) {
                            String code = response.body().get(0).getCode_server().toString();

                            if (code.equals("221")) {

                                listProducts = response.body();
                                if (listProducts.size() > 0) {
                                    adapterProduct =
                                            new ProductStyleOneAdapter(listProducts, requireContext(), getChildFragmentManager());
                                    recviewProducts.setAdapter(adapterProduct);
                                    adapterProduct.notifyDataSetChanged();

                                    eventAdapter();
                                } else {
                                    Toast.makeText(mContext, "No se cargó productos.", Toast.LENGTH_SHORT).show();
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
                public void onFailure(@NotNull Call<List<ProductoModel>> call, Throwable t) {

                }
            });
        } catch (Exception ignored) {


        }
    }

    private void eventAdapter() {
        adapterProduct.setOnClickListener(v -> {
            int position = recviewProducts.getChildAdapterPosition(v);

            DetailProductBottomSheet productBottomSheet = new DetailProductBottomSheet();
            Bundle bundle = new Bundle();
            bundle.putString("value_idproduct", listProducts.get(position).getIdproducto().toString());
            productBottomSheet.setArguments(bundle);
            productBottomSheet.show(getChildFragmentManager(), "");
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (mContext == null)
            mContext = context.getApplicationContext();
        mContext = context;
    }

}