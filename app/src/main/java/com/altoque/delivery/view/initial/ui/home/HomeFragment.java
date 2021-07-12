package com.altoque.delivery.view.initial.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.altoque.delivery.MainActivity;
import com.altoque.delivery.R;
import com.altoque.delivery.adapter.BusinessStyleOneAdapter;
import com.altoque.delivery.api.ApiClient;
import com.altoque.delivery.api.ApiHelper;
import com.altoque.delivery.apiInterface.ApiInterface;
import com.altoque.delivery.data.SessionSP;
import com.altoque.delivery.databinding.FragmentHomeBinding;
import com.altoque.delivery.model.DomicilioModel;
import com.altoque.delivery.model.NegocioModel;
import com.altoque.delivery.view.oauth.OAuthActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;
    public ApiInterface apiInterface;

    TextView tv_username;


    /** DIRECCION **/
    TextView tv_namedirection;
    ProgressBar pb_load_direction;

    /* UBICATION BUSINESS */
    private RecyclerView recviewBusinessByProv;
    private List<NegocioModel> listBusinessByProv;
    private BusinessStyleOneAdapter adapterBusinessByProv;
    private LinearLayoutManager layoutManBusinessByProv;


    private void initView() {

        tv_namedirection = binding.tvNameDirectionHome;
        pb_load_direction = binding.pbLoadDirectionuserHome;

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        recviewBusinessByProv = binding.rvBusinessByProv;

    }

    private void initResources(){
        listBusinessByProv = new ArrayList<>();

        layoutManBusinessByProv = new LinearLayoutManager(this.getContext(),
                RecyclerView.VERTICAL, false);
        recviewBusinessByProv.setLayoutManager(layoutManBusinessByProv);
        recviewBusinessByProv.setHasFixedSize(true);
        recviewBusinessByProv.setItemViewCacheSize(10);
        recviewBusinessByProv.setDrawingCacheEnabled(true);
        recviewBusinessByProv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        tv_username = binding.logout;

        tv_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionSP.get(requireContext()).saveStateLogin("no");

                FirebaseAuth mAuthent = FirebaseAuth.getInstance();
                mAuthent.signOut();

                SessionSP.get(requireContext()).logout();
                startActivity(new Intent(requireContext(), MainActivity.class));
                getActivity().finish();

            }
        });

        setUserName();

        initView();
        initResources();
        getDataDirections();
        getBusinessListByProv();

        return root;
    }

    private void getDataDirections(){

        String idclient = SessionSP.get(requireContext()).getIdClientSessSp();
        Log.e("Home_log", "OnFailure " + idclient);

        try {
            Call<List<DomicilioModel>> call = apiInterface.
                    getDataDirection("insert_select_adress", "3", idclient);
            ApiHelper.enqueueWithRetry(call, new Callback<List<DomicilioModel>>() {
                @Override
                public void onResponse(Call<List<DomicilioModel>> call, Response<List<DomicilioModel>> response) {
                    pb_load_direction.setVisibility(View.GONE);

                    if (response.isSuccessful()) {
                        String code = response.body().get(0).getCode_server().toString();
                        String namedir = response.body().get(0).getDireccion_dom().toString();

                        Log.e("Home_log", "OnFailure " + response.body().toString());

                        if (code.equals("221")) {
                            tv_namedirection.setText(namedir);
                        }
                        else if (code.equals("010")) {
                            tv_namedirection.setText("No se cargó correctamente");
                            tv_namedirection.setTextColor(getResources().getColor(R.color.colorRedMatiz3));
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<DomicilioModel>> call, Throwable t) {
                    pb_load_direction.setVisibility(View.GONE);
                    tv_namedirection.setText("No se cargó correctamente");
                    Log.e("Home_log", "OnFailure " + t.getMessage());
                    tv_namedirection.setTextColor(getResources().getColor(R.color.colorRedMatiz3));
                }
            });
        }catch (Exception e){
            pb_load_direction.setVisibility(View.GONE);
            tv_namedirection.setText("No se cargó correctamente");
            tv_namedirection.setTextColor(getResources().getColor(R.color.colorRedMatiz3));
        }
    }


    private void getBusinessListByProv(){

        try {
            Call<List<NegocioModel>> call = apiInterface.
                    getBusinessListByProv("list_business_by_province", "134");
            ApiHelper.enqueueWithRetry(call, new Callback<List<NegocioModel>>() {
                @Override
                public void onResponse(Call<List<NegocioModel>> call, Response<List<NegocioModel>> response) {
                    pb_load_direction.setVisibility(View.GONE);

                    if (response.isSuccessful()) {
                        String code = response.body().get(0).getCode_server().toString();

                        if (code.equals("221")) {

                            listBusinessByProv = response.body();
                            adapterBusinessByProv = new BusinessStyleOneAdapter(listBusinessByProv);
                            recviewBusinessByProv.setAdapter(adapterBusinessByProv);
                            adapterBusinessByProv.notifyDataSetChanged();
                            recviewBusinessByProv.setVisibility(View.VISIBLE);

                        }
                        else if (code.equals("010")) {

                        }
                    }
                }

                @Override
                public void onFailure(Call<List<NegocioModel>> call, Throwable t) {

                }
            });
        }catch (Exception e){
            pb_load_direction.setVisibility(View.GONE);
            tv_namedirection.setText("No se cargó correctamente");
            tv_namedirection.setTextColor(getResources().getColor(R.color.colorRedMatiz3));
        }
    }




    private void setUserName(){
        tv_username.setText("Hola, "+SessionSP.get(requireContext()).getNameSessSp().toString().trim());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}