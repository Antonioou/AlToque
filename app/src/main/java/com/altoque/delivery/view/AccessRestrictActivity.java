package com.altoque.delivery.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.altoque.delivery.R;
import com.altoque.delivery.databinding.ActivityAccessRestrictBinding;
import com.altoque.delivery.view.initial.ui.services.SearchAccessActivity;

public class AccessRestrictActivity extends AppCompatActivity {

    //ActivityAccessRestrictBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_restrict);
        //setContentView(binding.getRoot());

        getWindow()
                .setStatusBarColor(ContextCompat.getColor(AccessRestrictActivity.this, R.color.colorWhite));
        getWindow()
                .getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

    }
}