package com.altoque.delivery.view.oauth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.altoque.delivery.R;
import com.altoque.delivery.view.initial.InitialActivity;

public class OAuthMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oauth_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow()
                    .setStatusBarColor(ContextCompat.getColor(OAuthMainActivity.this, R.color.colorWhite));
            getWindow()
                    .getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    @Override
    public void finishAfterTransition() {
        super.finishAfterTransition();
        //finishAffinity();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAfterTransition();
    }
}