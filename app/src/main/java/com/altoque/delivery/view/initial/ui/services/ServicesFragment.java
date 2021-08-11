package com.altoque.delivery.view.initial.ui.services;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.altoque.delivery.R;
import com.altoque.delivery.adapter.BusinessStyleOneAdapter;
import com.altoque.delivery.adapter.ServicesStyleTwoAdapter;
import com.altoque.delivery.api.ApiClient;
import com.altoque.delivery.api.ApiHelper;
import com.altoque.delivery.apiInterface.ApiInterface;
import com.altoque.delivery.databinding.FragmentHomeBinding;
import com.altoque.delivery.databinding.FragmentServicesBinding;
import com.altoque.delivery.model.NegocioModel;
import com.altoque.delivery.model.RubroModel;
import com.altoque.delivery.model.RubroModel;
import com.altoque.delivery.view.initial.ui.detail.DetailBusinessActivity;
import com.google.android.material.card.MaterialCardView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ServicesFragment extends Fragment {

    View root;

    private FragmentServicesBinding binding;
    public ApiInterface apiInterface;

    /* UBICATION BUSINESS */
    private RecyclerView recviewRubro;
    private List<RubroModel> listRubro;
    private ServicesStyleTwoAdapter adapterRubro;

    /*  BUSINESS */
    private RecyclerView recviewSuggestion;
    private List<NegocioModel> listSuggestion;
    private BusinessStyleOneAdapter adapterSuggestion;

    public ServicesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void initView() {

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        recviewRubro = binding.rvServicesOrderList;
        recviewSuggestion = binding.rvSuggestionShortList;

        MaterialCardView cv_search = binding.cvSearchService;
        cv_search.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), SearchAccessActivity.class));
        });

    }

    private void initResources() {
        listRubro = new ArrayList<>();

        LinearLayoutManager layoutManRubro = new LinearLayoutManager(this.getContext(),
                RecyclerView.HORIZONTAL, false);
        recviewRubro.setLayoutManager(layoutManRubro);
        recviewRubro.setHasFixedSize(true);
        recviewRubro.setItemViewCacheSize(5);
        recviewRubro.setDrawingCacheEnabled(true);
        recviewRubro.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        listSuggestion = new ArrayList<>();

        LinearLayoutManager layoutManSuggestion = new LinearLayoutManager(this.getContext(),
                RecyclerView.VERTICAL, false);
        recviewSuggestion.setLayoutManager(layoutManSuggestion);
        recviewSuggestion.setHasFixedSize(true);
        recviewSuggestion.setItemViewCacheSize(7);
        recviewSuggestion.setDrawingCacheEnabled(true);
        recviewSuggestion.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //root = inflater.inflate(R.layout.fragment_services, container, false);

        binding = FragmentServicesBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        initView();
        initResources();

        getRubroList();
        getSuggestionShortList();

        return root;
    }

    private void getRubroList() {

        try {
            Call<List<RubroModel>> call = apiInterface.
                    getRubroList("select_heading_business");
            ApiHelper.enqueueWithRetry(call, new Callback<List<RubroModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<RubroModel>> call, @NonNull Response<List<RubroModel>> response) {

                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        if (response.body().size() > 0) {
                            String code = response.body().get(0).getCode_server().toString();

                            if (code.equals("221")) {

                                listRubro = response.body();
                                adapterRubro = new ServicesStyleTwoAdapter(listRubro);
                                recviewRubro.setAdapter(adapterRubro);
                                adapterRubro.notifyDataSetChanged();
                                recviewRubro.setVisibility(View.VISIBLE);
                                eventAdapterRubroList();
                            } else if (code.equals("010")) {

                            }
                        } else {
                            Toast.makeText(requireContext(), "Sin servicios por cargar.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<RubroModel>> call, Throwable t) {

                }
            });
        } catch (Exception e) {


        }
    }

    private void getSuggestionShortList() {

        try {
            Call<List<NegocioModel>> call = apiInterface.
                    getSuggestionShortList("list_suggested_businesses");
            ApiHelper.enqueueWithRetry(call, new Callback<List<NegocioModel>>() {
                @Override
                public void onResponse(@NotNull Call<List<NegocioModel>> call, @NotNull Response<List<NegocioModel>> response) {

                    if (response.isSuccessful()) {
                        //Log.e("Error_log_serices", ""+response.body().toString());
                        assert response.body() != null;
                        if (response.body().size() > 0) {
                            String code = response.body().get(0).getCodeServer().toString();

                            if (code.equals("221")) {

                                listSuggestion = response.body();
                                adapterSuggestion = new BusinessStyleOneAdapter(listSuggestion);
                                recviewSuggestion.setAdapter(adapterSuggestion);
                                adapterSuggestion.notifyDataSetChanged();
                                recviewSuggestion.setVisibility(View.VISIBLE);

                                eventAdapterBusinessList();
                            } else if (code.equals("010")) {

                            }
                        } else {
                            Toast.makeText(requireContext(), "Sin servicios por cargar.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<NegocioModel>> call, @NonNull Throwable t) {
                    Log.e("Error_log_serices", "" + t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.e("Error_log_serices", "" + e.getMessage());

        }
    }


    private void eventAdapterRubroList() {
        adapterRubro.setOnClickListener(v -> {
            //Toast.makeText(requireContext(), "click", Toast.LENGTH_SHORT).show();
            int pos = recviewRubro.getChildAdapterPosition(v);
            Intent intent = new Intent(requireContext(), ListBusinessByRubroActivity.class);
            intent.putExtra("value_name_rubro", listRubro.get(pos).getNom_rubro());
            intent.putExtra("value_id_rubro", listRubro.get(pos).getIdrubro());
            intent.putExtra("value_photo_rubro", listRubro.get(pos).getFoto_rubro());
            startActivity(intent);
        });
    }

    private void eventAdapterBusinessList() {
        adapterSuggestion.setOnClickListener(v -> {
            int pos = recviewSuggestion.getChildAdapterPosition(v);
            Intent intent = new Intent(requireContext(), DetailBusinessActivity.class);
            intent.putExtra("value_idnegocio", listSuggestion.get(pos).getIdnegocio().toString());
            startActivity(intent);
        });
    }
}