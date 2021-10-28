package com.altoque.delivery.view.initial.ui.detail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;


import com.altoque.delivery.api.ApiClient;
import com.altoque.delivery.api.ApiHelper;
import com.altoque.delivery.apiInterface.ApiInterface;
import com.altoque.delivery.data.SessionSP;
import com.altoque.delivery.databinding.ActivityDetailBusinessBinding;
import com.altoque.delivery.model.CategoriaModel;
import com.altoque.delivery.model.DomicilioModel;
import com.altoque.delivery.model.NegocioModel;
import com.altoque.delivery.view.initial.InitialActivity;
import com.altoque.delivery.view.initial.ui.detail.viewdetailbusiness.DataBusinessBottomSheet;
import com.altoque.delivery.view.initial.ui.detail.viewdetailbusiness.FirstDetailBusinessFragment;
import com.altoque.delivery.view.initial.ui.detail.viewdetailbusiness.FiveDetailBusinessFragment;
import com.altoque.delivery.view.initial.ui.detail.viewdetailbusiness.FourthDetailBusinessFragment;
import com.altoque.delivery.view.initial.ui.detail.viewdetailbusiness.SecondDetailBusinessFragment;
import com.altoque.delivery.view.initial.ui.detail.viewdetailbusiness.ThirdDetailBusinessFragment;
import com.altoque.delivery.view.initial.ui.detail.viewdetailbusiness.ViewPagerAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.altoque.delivery.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailBusinessActivity extends AppCompatActivity {

    private ActivityDetailBusinessBinding binding;

    public ApiInterface apiInterface;

    private TabLayout tabLayout;
    CollapsingToolbarLayout toolBarLayout;
    private ViewPager viewPager;
    private MaterialCardView cv_about;

    Integer value_qty_tabs = 1;

    ViewPagerAdapter adapter;

    List<CategoriaModel> listCateg;
    List<NegocioModel> listBusiness;

    TextView tv_time, tv_rate, tv_cost, tv_name, tv_state, tv_info;
    ImageView iv_banner, iv_logo;
    //ImageView view_gradient;

    String global_idnegocio = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailBusinessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        View view = binding.getRoot();

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        toolBarLayout = binding.toolbarLayout;
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);
        actionBar.setDisplayHomeAsUpEnabled(true);

        getWindow()
                .setStatusBarColor(ContextCompat.getColor(DetailBusinessActivity.this, R.color.colorWhite));
        getWindow()
                .getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);



        /*getWindow()
                .setStatusBarColor(ContextCompat.getColor(DetailBusinessActivity.this, R.color.colorWhite));
        getWindow()
                .getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);*/

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        listCateg = new ArrayList<>();
        listBusiness = new ArrayList<>();

        //view_gradient = binding.viewGradientDetailbusiness;

        viewPager = findViewById(R.id.viewPager_detailbusinness);
        tabLayout = findViewById(R.id.tabLayout_detailbusinness);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        tv_cost = binding.tvCostDetailbusiness;
        tv_name = binding.tvNameDetailbusiness;
        tv_time = binding.tvTimeDetailbusiness;
        tv_rate = binding.tvRateDetailbusiness;
        iv_logo = binding.ivLogoDetailbusiness;
        iv_banner = binding.ivBannerDetailbusiness;
        tv_state = view.findViewById(R.id.tv_state_detailbusiness);
        tv_info = view.findViewById(R.id.tv_infobusiness_detailbusiness);
        cv_about = view.findViewById(R.id.cv_about_detailbusiness);


        if (getIntent() != null) {
            String idnegocio = getIntent().getExtras().getString("value_idnegocio");

            if (!idnegocio.isEmpty()) {
                global_idnegocio = idnegocio;
                getDataBusiness(global_idnegocio);
                getListCategories(global_idnegocio);
            } else {
                Toast.makeText(this, "Id no capturado.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void eventListener(List<NegocioModel> list) {


        if (list.size() > 0) {
            cv_about.setOnClickListener(v -> {

                //Log.e("log_error_dataNeee", ""+listBusiness.toString());
                DataBusinessBottomSheet data = new DataBusinessBottomSheet();
                Bundle bundle = new Bundle();
                bundle.putString("value_address", list.get(0).getDirFiscalNeg());
                bundle.putString("value_phone", list.get(0).getCelularNeg());
                bundle.putString("value_mail", list.get(0).getCorreoNeg());
                bundle.putString("value_close_timeservice", list.get(0).getAcierreNeg());
                bundle.putString("value_open_timeservice", list.get(0).getAinicioNeg());
                data.setArguments(bundle);
                data.show(getSupportFragmentManager(), "tag");
            });
        }
    }


    private void getDataBusiness(String id) {

        try {
            Call<List<NegocioModel>> call = apiInterface.
                    getDataBusiness("list_detail_business", id);
            ApiHelper.enqueueWithRetry(call, new Callback<List<NegocioModel>>() {
                @Override
                public void onResponse(@NotNull Call<List<NegocioModel>> call,
                                       @NotNull Response<List<NegocioModel>> response) {

                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        if (response.body().size() > 0) {
                            String code = response.body().get(0).getCodeServer().toString();
                            Log.e("Home_log", "OnFailure " + response.body().toString());
                            if (code.equals("221")) {
                                listBusiness = response.body();

                                tv_time.setText(listBusiness.get(0).getEstimacionDemora());
                                tv_rate.setText(listBusiness.get(0).getRating());
                                tv_cost.setText(listBusiness.get(0).getCostoEnvio());
                                tv_name.setText(listBusiness.get(0).getRsocialNeg());
                                tv_state.setText(listBusiness.get(0).getStatus());

                                /*List<String> list = new ArrayList<>(listBusiness.size());
                                for (Object object : listBusiness) {
                                    list.add(Objects.toString(object, null));
                                }*/

                                eventListener(listBusiness);
                                scrollListener(listBusiness.get(0).getRsocialNeg());
                                try {
                                    Picasso.get()
                                            .load(listBusiness.get(0).getFotoNeg())
                                            .placeholder(R.drawable.second_image)
                                            .into(iv_logo);
                                } catch (Exception ignored) {}

                                try {
                                    Picasso.get()
                                            .load(listBusiness.get(0).getBannerNeg())
                                            .placeholder(R.drawable.second_image)
                                            .into(iv_banner);


                                } catch (Exception ignored) {}



                            } else if (code.equals("010")) {
                                Toast.makeText(DetailBusinessActivity.this, "No se cargó los datos del negocio.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(DetailBusinessActivity.this, "Error al cargar los datos. Intente nuevamente.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<List<NegocioModel>> call, @NotNull Throwable t) {

                }
            });
        } catch (Exception ignored) {

        }
    }

    private void scrollListener(String title){
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (Math.abs(verticalOffset)-appBarLayout.getTotalScrollRange() == 0)
                {
                    //  Collapsed
                    toolBarLayout.setTitle(title);
                    toolBarLayout.setCollapsedTitleTextColor(Color.BLACK);
                }/*if (verticalOffset == 0) {
                    //Expanded
                    toolBarLayout.setTitle("");
                }*/
                else
                { toolBarLayout.setTitle("");
                }
            }
        });
    }



    private void getListCategories(String id) {

        try {
            Call<List<CategoriaModel>> call = apiInterface.
                    getListCategories("list_service_category_business", id);
            ApiHelper.enqueueWithRetry(call, new Callback<List<CategoriaModel>>() {
                @Override
                public void onResponse(@NotNull Call<List<CategoriaModel>> call, @NotNull Response<List<CategoriaModel>> response) {

                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        if (response.body().size() > 0) {
                            String code = response.body().get(0).getCode_server().toString();
                            //Log.e("Home_log", "OnFailure " + response.body().toString());
                            if (code.equals("221")) {
                                listCateg = response.body();

                                ArrayList<String> listName = new ArrayList<>();
                                ArrayList<String> listId = new ArrayList<>();

                                for (int i = 0; i < listCateg.size(); i++) {
                                    listName.add(listCateg.get(i).getNomb_categoria().toString());
                                    listId.add(listCateg.get(i).getIdcategoria().toString());
                                }

                                choseTabs(listName.size(), listName, listId);



                            } else if (code.equals("010")) {
                                Toast.makeText(DetailBusinessActivity.this, "Sin categorias", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(DetailBusinessActivity.this, "Sin categorias", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<List<CategoriaModel>> call, @NotNull Throwable t) {

                }
            });
        } catch (Exception e) {

        }
    }


    private void choseTabs(Integer qty_tabs, ArrayList<String> title, ArrayList<String> id) {
        Log.e("Debug_error", "ID NEG: " + global_idnegocio+"\nTITLE: "+title.get(0));
        switch (qty_tabs) {
            case 1:
                adapter.AddFragment(new FirstDetailBusinessFragment().newInstance(id.get(0), global_idnegocio), title.get(0));
                break;
            case 2:
                adapter.AddFragment(new FirstDetailBusinessFragment().newInstance(id.get(0), global_idnegocio), title.get(0));
                adapter.AddFragment(new SecondDetailBusinessFragment().newInstance(id.get(1), global_idnegocio), title.get(1));

                break;
            case 3:
                adapter.AddFragment(new FirstDetailBusinessFragment().newInstance(id.get(0), global_idnegocio), title.get(0));
                adapter.AddFragment(new SecondDetailBusinessFragment().newInstance(id.get(1), global_idnegocio), title.get(1));
                adapter.AddFragment(new ThirdDetailBusinessFragment().newInstance(id.get(2), global_idnegocio), title.get(2));
                break;
            case 4:
                adapter.AddFragment(new FirstDetailBusinessFragment().newInstance(id.get(0), global_idnegocio), title.get(0));
                adapter.AddFragment(new SecondDetailBusinessFragment().newInstance(id.get(1), global_idnegocio), title.get(1));
                adapter.AddFragment(new ThirdDetailBusinessFragment().newInstance(id.get(2), global_idnegocio), title.get(2));
                adapter.AddFragment(new FourthDetailBusinessFragment().newInstance(id.get(3), global_idnegocio), title.get(3));
                break;
            case 5:
                adapter.AddFragment(new FirstDetailBusinessFragment().newInstance(id.get(0), global_idnegocio), title.get(0));
                adapter.AddFragment(new SecondDetailBusinessFragment().newInstance(id.get(1), global_idnegocio), title.get(1));
                adapter.AddFragment(new ThirdDetailBusinessFragment().newInstance(id.get(2), global_idnegocio), title.get(2));
                adapter.AddFragment(new FourthDetailBusinessFragment().newInstance(id.get(3), global_idnegocio), title.get(3));
                adapter.AddFragment(new FiveDetailBusinessFragment().newInstance(id.get(4), global_idnegocio), title.get(4));
                break;
            case 0:
                adapter.AddFragment(new FirstDetailBusinessFragment(), "Title");
                break;

        }

        addFragments(adapter);
    }

    private void addFragments(ViewPagerAdapter adapter) {
        tabLayout.setupWithViewPager(viewPager, true);
        viewPager.setOffscreenPageLimit(adapter.getCount() - 1);
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
