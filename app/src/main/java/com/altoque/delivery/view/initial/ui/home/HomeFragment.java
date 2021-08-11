package com.altoque.delivery.view.initial.ui.home;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.altoque.delivery.MainActivity;
import com.altoque.delivery.R;
import com.altoque.delivery.adapter.BusinessStyleThreeAdapter;
import com.altoque.delivery.adapter.BusinessStyleTwoAdapter;
import com.altoque.delivery.adapter.CenterZoomLayoutManager;
import com.altoque.delivery.api.ApiClient;
import com.altoque.delivery.api.ApiHelper;
import com.altoque.delivery.apiInterface.ApiInterface;
import com.altoque.delivery.data.SessionSP;
import com.altoque.delivery.databinding.FragmentHomeBinding;
import com.altoque.delivery.model.DomicilioModel;
import com.altoque.delivery.model.NegocioModel;
import com.altoque.delivery.view.direction.DirectionListBottomSheet;
import com.altoque.delivery.view.initial.ui.detail.DetailBusinessActivity;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;
    public ApiInterface apiInterface;

    TextView tv_username;
    CircularImageView civ_picprofile;


    /**
     * DIRECCION
     **/
    TextView tv_namedirection;
    ProgressBar pb_load_direction;
    MaterialCardView cv_directionuser;

    /* UBICATION BUSINESS */
    private RecyclerView recviewBusinessByRate;
    private List<NegocioModel> listBusinessByRate;
    private BusinessStyleTwoAdapter adapterBusinessByRate;
    private CenterZoomLayoutManager layoutManBusinessByRate;

    /* UBICATION BUSINESS */
    private RecyclerView recviewBusinessByProv;
    private List<NegocioModel> listBusinessByProv;
    private BusinessStyleThreeAdapter adapterBusinessByProv;
    private LinearLayoutManager layoutManBusinessByProv;


    private void initView() {

        tv_namedirection = binding.tvNameDirectionHome;
        pb_load_direction = binding.pbLoadDirectionuserHome;
        civ_picprofile = binding.civPicprofileHome;
        cv_directionuser = binding.cvDirectionuserHome;

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        recviewBusinessByProv = binding.rvBusinessByProv;
        recviewBusinessByRate = binding.rvBusinessByRate;

    }

    private void initResources() {
        listBusinessByRate = new ArrayList<>();
        listBusinessByProv = new ArrayList<>();

        layoutManBusinessByRate = new CenterZoomLayoutManager(this.getContext(),
                RecyclerView.HORIZONTAL, false);
        recviewBusinessByRate.setLayoutManager(layoutManBusinessByRate);
        recviewBusinessByRate.setHasFixedSize(true);
        recviewBusinessByRate.setItemViewCacheSize(5);
        recviewBusinessByRate.setDrawingCacheEnabled(true);
        recviewBusinessByRate.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recviewBusinessByRate.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstItemVisible = layoutManBusinessByRate.findFirstVisibleItemPosition();
                if (firstItemVisible != 0 && firstItemVisible % listBusinessByRate.size() == 0) {
                    Objects.requireNonNull(recyclerView.getLayoutManager()).scrollToPosition(0);
                }
            }
        });

        layoutManBusinessByProv = new LinearLayoutManager(this.getContext(),
                RecyclerView.VERTICAL, false);
        recviewBusinessByProv.setLayoutManager(layoutManBusinessByProv);
        recviewBusinessByProv.setHasFixedSize(true);
        recviewBusinessByProv.setItemViewCacheSize(7);
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
                requireActivity().finish();

            }
        });


        initView();
        initResources();

        setUserName();
        setPictureProfile();
        showDirections();

        getDataDirections();
        getBusinessListByRate();
        getBusinessListByProv();


        return root;
    }







    /****************************************************************************/

    public void autoScroll() {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                recviewBusinessByRate.scrollBy(2, 0);
                handler.postDelayed(this, 30);
            }
        };
        handler.postDelayed(runnable, 30);
    }

    private void getDataDirections() {

        String idclient = SessionSP.get(requireContext()).getIdClientSessSp();
        //Log.e("Home_log", "OnFailure " + idclient);

        try {
            Call<List<DomicilioModel>> call = apiInterface.
                    getDataDirection("insert_select_adress", "3", idclient);
            ApiHelper.enqueueWithRetry(call, new Callback<List<DomicilioModel>>() {
                @Override
                public void onResponse(@NotNull Call<List<DomicilioModel>> call, @NotNull Response<List<DomicilioModel>> response) {
                    pb_load_direction.setVisibility(View.GONE);

                    if (response.isSuccessful()) {
                        if (response.body().size() > 0) {
                            String code = response.body().get(0).getCode_server().toString();
                            String namedir = response.body().get(0).getDireccion_dom().toString();

                            //Log.e("Home_log", "OnFailure " + response.body().toString());

                            if (code.equals("221")) {
                                tv_namedirection.setText(namedir);
                            } else if (code.equals("010")) {
                                tv_namedirection.setText("No se cargó correctamente");
                                tv_namedirection.setTextColor(getResources().getColor(R.color.colorRedMatiz3));
                            }
                        } else {
                            tv_namedirection.setText("No se obtuvo sus direcciones");
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<List<DomicilioModel>> call, @NotNull Throwable t) {
                    pb_load_direction.setVisibility(View.GONE);
                    tv_namedirection.setText("No se cargó correctamente");
                    //Log.e("Home_log", "OnFailure " + t.getMessage());
                    tv_namedirection.setTextColor(getResources().getColor(R.color.colorRedMatiz3));
                }
            });
        } catch (Exception e) {
            pb_load_direction.setVisibility(View.GONE);
            tv_namedirection.setText("No se cargó correctamente");
            tv_namedirection.setTextColor(getResources().getColor(R.color.colorRedMatiz3));
        }
    }

    private void showDirections() {
        cv_directionuser.setOnClickListener(v -> {
            DirectionListBottomSheet res = new DirectionListBottomSheet();
            res.show(getChildFragmentManager(), "tag");
            res.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    String id_cust = "";
                    //id_cust = sessionSP.getIdSessSp();
                    //getReservations(id_cust);
                }
            });
        });


    }


    private void getBusinessListByRate() {

        try {
            Call<List<NegocioModel>> call = apiInterface.
                    getBusinessListByRate("list_business_by_rating_district", "1");
            ApiHelper.enqueueWithRetry(call, new Callback<List<NegocioModel>>() {
                @Override
                public void onResponse(@NotNull Call<List<NegocioModel>> call, Response<List<NegocioModel>> response) {

                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        if (response.body().size() > 0) {
                            String code = response.body().get(0).getCodeServer().toString();

                            if (code.equals("221")) {

                                listBusinessByRate = response.body();
                                adapterBusinessByRate = new BusinessStyleTwoAdapter(listBusinessByRate);
                                recviewBusinessByRate.setAdapter(adapterBusinessByRate);
                                adapterBusinessByRate.notifyDataSetChanged();
                                recviewBusinessByRate.setVisibility(View.VISIBLE);
                                eventAdapterBusinessListByRate();
                            } else if (code.equals("010")) {

                            }
                        } else {
                            Toast.makeText(requireContext(), "Sin Negocios por cargar.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<List<NegocioModel>> call, Throwable t) {

                }
            });
        } catch (Exception e) {


        }
    }

    private void getBusinessListByProv() {

        try {
            Call<List<NegocioModel>> call = apiInterface.
                    getBusinessListByProv("list_business_by_district", "2");
            ApiHelper.enqueueWithRetry(call, new Callback<List<NegocioModel>>() {
                @Override
                public void onResponse(@NotNull Call<List<NegocioModel>> call, @NotNull Response<List<NegocioModel>> response) {

                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        if (response.body().size() > 0) {
                            String code = response.body().get(0).getCodeServer().toString();

                            if (code.equals("221")) {

                                listBusinessByProv = response.body();
                                adapterBusinessByProv = new BusinessStyleThreeAdapter(listBusinessByProv, requireContext());
                                recviewBusinessByProv.setAdapter(adapterBusinessByProv);
                                adapterBusinessByProv.notifyDataSetChanged();
                                recviewBusinessByProv.setVisibility(View.VISIBLE);
                                eventAdapterBusinessListByProv();
                            } else if (code.equals("010")) {

                            }
                        }else {
                            Toast.makeText(requireContext(), "No se logró cargar correctamente.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<List<NegocioModel>> call, Throwable t) {

                }
            });
        } catch (Exception ignored) {


        }
    }

    private void setPictureProfile() {
        String photoUrl = SessionSP.get(requireContext()).getPhotoSessSp().toString();
        //Log.e("Error_log_home", photoUrl);
        if (!photoUrl.isEmpty()) {
            Picasso.get()
                    .load(photoUrl)
                    .into(civ_picprofile, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            Toast.makeText(requireContext(),
                                    "No se logró cargar la foto.", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(requireContext(),
                    "No se logró cargar la foto.", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void setUserName() {
        tv_username.setText("Hola, " + SessionSP.get(requireContext()).getNameSessSp().toString().trim());
    }

    private void eventAdapterBusinessListByRate() {
        adapterBusinessByRate.setOnClickListener(v -> {
            int pos = recviewBusinessByRate.getChildAdapterPosition(v);
            Intent intent = new Intent(requireContext(), DetailBusinessActivity.class);
            intent.putExtra("value_idnegocio", listBusinessByRate.get(pos).getIdnegocio().toString());
            //Toast.makeText(requireContext(), "pos "+pos+"\n id "+listBusinessByRate.get(pos).getIdnegocio(), Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });
    }

    private void eventAdapterBusinessListByProv() {
        adapterBusinessByProv.setOnClickListener(v -> {
            int pos = recviewBusinessByProv.getChildAdapterPosition(v);
            Intent intent = new Intent(requireContext(), DetailBusinessActivity.class);
            intent.putExtra("value_idnegocio", listBusinessByProv.get(pos).getIdnegocio().toString());
            //Toast.makeText(requireContext(), "pos "+pos+"\n id "+listBusinessByProv.get(pos).getIdnegocio(), Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}