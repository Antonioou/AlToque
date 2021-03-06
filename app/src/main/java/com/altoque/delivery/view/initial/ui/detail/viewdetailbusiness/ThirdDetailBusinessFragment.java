package com.altoque.delivery.view.initial.ui.detail.viewdetailbusiness;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.altoque.delivery.adapter.ProductStyleOneAdapter;
import com.altoque.delivery.api.ApiClient;
import com.altoque.delivery.api.ApiHelper;
import com.altoque.delivery.apiInterface.ApiInterface;
import com.altoque.delivery.databinding.FragmentThirdDetailBusinessBinding;
import com.altoque.delivery.model.ProductoModel;
import com.altoque.delivery.view.initial.ui.detail.viewdetailproduct.DetailProductActivity;
import com.altoque.delivery.view.initial.ui.detail.viewdetailproduct.DetailProductBottomSheet;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThirdDetailBusinessFragment extends Fragment {

    private static final String ARG_PARAM_IDCATEGORY = "value_idcategory";
    private static final String ARG_PARAM_IDBUSINESS = "value_idbusiness";

    private String param_idcategory;
    private String param_idBusiness;

    FragmentThirdDetailBusinessBinding binding;
    public ApiInterface apiInterface;

    private RecyclerView recviewProducts;
    private List<ProductoModel> listProducts;
    private ProductStyleOneAdapter adapterProduct;

    private Context mContext;

    public ThirdDetailBusinessFragment() {
        // Required empty public constructor
    }


    public static FourthDetailBusinessFragment newInstance(String idCategory, String idBusiness) {
        FourthDetailBusinessFragment fragment = new FourthDetailBusinessFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentThirdDetailBusinessBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recviewProducts = binding.rvProducts;
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        listProducts = new ArrayList<>();

        LinearLayoutManager layoutMan =
                new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
        recviewProducts.setLayoutManager(layoutMan);

        getItemList();
        return root;
    }

    private void getItemList() {

        try {
            Call<List<ProductoModel>> call = apiInterface.
                    getItemListByCategoryAndBusiness("list_products_business",
                            param_idBusiness, param_idcategory);
            ApiHelper.enqueueWithRetry(call, new Callback<List<ProductoModel>>() {
                @Override
                public void onResponse(@NotNull Call<List<ProductoModel>> call,
                                       @NotNull Response<List<ProductoModel>> response) {

                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        if (response.body().size() > 0) {
                            String code = response.body().get(0).getCode_server().toString();

                            if (code.equals("221")) {

                                listProducts = response.body();
                                adapterProduct =
                                        new ProductStyleOneAdapter(listProducts, requireContext(), getChildFragmentManager());
                                recviewProducts.setAdapter(adapterProduct);
                                adapterProduct.notifyDataSetChanged();
                                eventAdapter();
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
        } catch (Exception e) {


        }
    }

    private void eventAdapter() {
        adapterProduct.setOnClickListener(v -> {
            int position = recviewProducts.getChildAdapterPosition(v);

            String state = listProducts.get(position).getStatus().toLowerCase().trim().toString();

            if (state.equals("agotado")) {
                Toast.makeText(requireContext(), "Item no disponible", Toast.LENGTH_SHORT).show();
            }else {
                Intent intent = new Intent(mContext, DetailProductActivity.class);
                intent.putExtra("value_idproduct", listProducts.get(position).getIdproducto().toString());
                startActivity(intent);
            }
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