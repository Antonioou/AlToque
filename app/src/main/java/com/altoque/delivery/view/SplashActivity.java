package com.altoque.delivery.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.altoque.delivery.MainActivity;
import com.altoque.delivery.R;
import com.altoque.delivery.data.SessionSP;
import com.altoque.delivery.utils.ConnectivityReceiver;
import com.altoque.delivery.utils.initClass;
import com.altoque.delivery.view.direction.DirectionClientActivity;
import com.altoque.delivery.view.oauth.RegisterActivity;
import com.altoque.delivery.view.initial.InitialActivity;

public class SplashActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReciverListener {

    private TextView tv_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //requestWindowFeature(1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow()
                    .setStatusBarColor(ContextCompat.getColor(SplashActivity.this, R.color.colorPrimary));
        }

        tv_state = findViewById(R.id.tv_load_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        ConnectivityReceiver connectivityReceiver = new ConnectivityReceiver();
        registerReceiver(connectivityReceiver, intentFilter);
        initClass.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanger(boolean isConnected) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isConnected) {
                    Toast.makeText(SplashActivity.this, "Sin conexión a internet.", Toast.LENGTH_SHORT).show();
                    tv_state.setText("Esperando conexión a internet...");
                } else {
                    nextActivity();
                }
            }
        }, 1300);


    }

    private void nextActivity() {
        if (SessionSP.get(SplashActivity.this).getStateLogin().equals("yes")) {
            startActivity(new Intent(SplashActivity.this, InitialActivity.class));
        }else if (SessionSP.get(SplashActivity.this).getStateLogin().equals("register")) {
            Intent intent = new Intent(SplashActivity.this, DirectionClientActivity.class);
            intent.putExtra("action", "register_data");
            startActivity(intent);
        }else if (SessionSP.get(SplashActivity.this).getStateLogin().equals("dirclient")) {
            startActivity(new Intent(SplashActivity.this, DirectionClientActivity.class));
        }
        else {
            //final ImageView iv_logo = findViewById(R.id.iv_logo_splash);
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            /*ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(SplashActivity.this,
                            iv_logo,
                            "go_main_transition");
                            , options.toBundle()*/
            startActivity(intent);
        }
        finish();
    }
}