package com.altoque.delivery.view.direction;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.altoque.delivery.R;
import com.altoque.delivery.adapter.BusinessStyleOneAdapter;
import com.altoque.delivery.adapter.BusinessStyleTwoAdapter;
import com.altoque.delivery.adapter.CenterZoomLayoutManager;
import com.altoque.delivery.adapter.DirectionClientListAdapter;
import com.altoque.delivery.api.ApiClient;
import com.altoque.delivery.api.ApiHelper;
import com.altoque.delivery.apiInterface.ApiInterface;
import com.altoque.delivery.data.SessionSP;
import com.altoque.delivery.databinding.FragmentHomeBinding;
import com.altoque.delivery.model.DomicilioModel;
import com.altoque.delivery.model.NegocioModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DirectionListBottomSheet extends BottomSheetDialogFragment implements View.OnClickListener {

    View view;

    private BottomSheetBehavior mBehavior;
    private ApiInterface api;
    SessionSP sessionSP;

    /* UBICATION*/
    private RecyclerView recviewBDiretion;
    private List<DomicilioModel> listDirection;
    private DirectionClientListAdapter adapterDirection;
    private LinearLayoutManager layoutManDirection;

    MaterialButton btn_add_direction;

    private void bindView() {

        recviewBDiretion = view.findViewById(R.id.rv_DirectionList);
        btn_add_direction = view.findViewById(R.id.btn_add_directionList);

    }

    private void eventListener() {

        addDirection();
    }

    private void addDirection() {
        btn_add_direction.setOnClickListener(v->{
            Intent intent = new Intent(requireContext(), DirectionClientActivity.class);
            intent.putExtra("action", "register_data");
            intent.putExtra("state_use", "0");
            startActivity(intent);

        });
    }

    private void initResources() {

        api = ApiClient.getApiClient().create(ApiInterface.class);
        sessionSP = SessionSP.get(requireContext());

        listDirection = new ArrayList<>();

        layoutManDirection = new LinearLayoutManager(this.getContext(),
                RecyclerView.VERTICAL, false);
        recviewBDiretion.setLayoutManager(layoutManDirection);
        recviewBDiretion.setHasFixedSize(true);
        recviewBDiretion.setItemViewCacheSize(3);
        recviewBDiretion.setDrawingCacheEnabled(true);
        recviewBDiretion.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

    }

    private DialogInterface.OnDismissListener onDismissListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getWindow()
                .setStatusBarColor(ContextCompat.getColor(requireContext(), R.color.colorWhite));
        requireActivity().getWindow()
                .getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        view = View.inflate(getContext(), R.layout.bottomsheet_direction_list, null);
        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        mBehavior.setPeekHeight(0);
        bindView();
        initResources();
        eventListener();
        setupStatesBS();

        getListDirections();


        return dialog;
    }

    private void getListDirections(){

        String idclient = SessionSP.get(requireContext()).getIdClientSessSp();

        try {
            Call<List<DomicilioModel>> call = api.
                    getListDirection("insert_select_adress", "1", idclient);
            ApiHelper.enqueueWithRetry(call, new Callback<List<DomicilioModel>>() {
                @Override
                public void onResponse(@NotNull Call<List<DomicilioModel>> call, @NotNull Response<List<DomicilioModel>> response) {
                    //pb_load_direction.setVisibility(View.GONE);

                    if (response.isSuccessful()) {

                        String code = response.body().get(0).getCode_server().toString();

                        if (code.equals("221")) {
                            listDirection = response.body();
                            if (listDirection.size()>4) btn_add_direction.setVisibility(View.GONE);
                            adapterDirection = new DirectionClientListAdapter(listDirection, requireContext(), DirectionListBottomSheet.this);
                            recviewBDiretion.setAdapter(adapterDirection);
                            adapterDirection.notifyDataSetChanged();
                            recviewBDiretion.setVisibility(View.VISIBLE);
                        }
                        else if (code.equals("010")) {
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<List<DomicilioModel>> call, @NotNull Throwable t) {
                    //pb_load_direction.setVisibility(View.GONE);

                }
            });
        }catch (Exception e){
            //pb_load_direction.setVisibility(View.GONE);
        }
    }

    public void refreshRecyclerView(){
        if (listDirection.size()>0){
            getListDirections();
            adapterDirection.notifyDataSetChanged();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
    }

    @Override
    public void onResume() {
        super.onResume();
        getListDirections();
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
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }


    @Override
    public void onDetach() {
        super.onDetach();
    }
}
