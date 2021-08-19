package com.altoque.delivery.view.initial.ui.detail.viewdetailbusiness;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.altoque.delivery.R;
import com.altoque.delivery.api.ApiClient;
import com.altoque.delivery.apiInterface.ApiInterface;
import com.altoque.delivery.data.SessionSP;
import com.altoque.delivery.model.NegocioModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class DataBusinessBottomSheet extends BottomSheetDialogFragment {

    View view;

    private BottomSheetBehavior mBehavior;
    private ApiInterface api;
    SessionSP sessionSP;

    private ExtendedFloatingActionButton feb_call, feb_wha, feb_msg;

    private void bindView() {

        /*feb_call = view.findViewById(R.id.feb_call_contactinfo);
        feb_wha = view.findViewById(R.id.feb_whatsapp_contactinfo);
        feb_msg = view.findViewById(R.id.feb_msg_contactinfo);*/

    }

    private void eventListener() {

        /*feb_call.setOnClickListener(this::onClick);
        feb_wha.setOnClickListener(this::onClick);
        feb_msg.setOnClickListener(this::onClick);*/

    }

    private void initResources() {
        api = ApiClient.getApiClient().create(ApiInterface.class);

        sessionSP = SessionSP.get(requireContext());

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

        view = View.inflate(getContext(), R.layout.bottomsheet_business_data, null);
        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        mBehavior.setPeekHeight(0);
        requireActivity().getWindow()
                .setStatusBarColor(ContextCompat.getColor(requireContext(), R.color.colorWhite));
        requireActivity().getWindow()
                .getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        bindView();
        initResources();
        eventListener();
        setupStatesBS();

        TextView tv_timeopen = (TextView) view.findViewById(R.id.tv_timeopen_businessdata);
        TextView tv_timeclose = (TextView) view.findViewById(R.id.tv_timeclose_businessdata);
        TextView tv_address = (TextView) view.findViewById(R.id.tv_map_businessdata);
        TextView tv_phone = (TextView) view.findViewById(R.id.tv_phone_businessdata);
        TextView tv_mail = (TextView) view.findViewById(R.id.tv_mail_businessdata);

        if (getArguments() != null) {
            String phone = getArguments().getString("value_phone");
            String mail = getArguments().getString("value_mail");
            String address = getArguments().getString("value_address");
            String closetime = getArguments().getString("value_close_timeservice");
            String opentime = getArguments().getString("value_open_timeservice");


            String timeo = ((opentime != null) ? "Hora de apertura: "+opentime : "Sin hora");
            tv_timeopen.setText(timeo);
            String timec = ((closetime != null) ? "Hora de cierre: "+closetime : "Sin hora");
            tv_timeclose.setText(timec);

            String addressv = ((address != null) ? address : "Sin datos");
            tv_address.setText(addressv);

            String phonev = ((phone != null) ? phone : "Sin datos");
            tv_phone.setText(phonev);

            String mailv = ((mail != null) ? mail : "Sin datos");
            tv_mail.setText(mailv);

        }


        return dialog;
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
    public void onAttach(Context context) {
        super.onAttach(context);

    }


    @Override
    public void onDetach() {
        super.onDetach();
    }
}
