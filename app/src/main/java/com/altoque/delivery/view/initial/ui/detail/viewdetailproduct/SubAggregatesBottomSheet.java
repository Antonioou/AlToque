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

public class SubAggregatesBottomSheet extends BottomSheetDialogFragment {

    View view;

    private BottomSheetBehavior mBehavior;
    private ApiInterface api;
    SessionSP sessionSP;

    private Context mContext;

    String global_idproduct = "";


    private void initView() {


        /*TextView tv_timeclose = (TextView) view.findViewById(R.id.tv_timeclose_businessdata);
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
        view = inflater.inflate(R.layout.bottomsheet_sub_aggregates, null);
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
            String value = getArguments().getString("value");

            TextView state = (TextView) view.findViewById(R.id.statetv);

            /*String id_prod_def = ((id_prod != null) ? id_prod : "-1");

            if (id_prod_def.isEmpty() || id_prod_def.equals("-1")) {
                Toast.makeText(requireContext(), "No se obtuvo el id " + id_prod_def, Toast.LENGTH_SHORT).show();
            } else {
                global_idproduct = id_prod_def;

            }*/
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
