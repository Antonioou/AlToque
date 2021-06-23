package com.altoque.delivery.view.OAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.altoque.delivery.MainActivity;
import com.altoque.delivery.R;
import com.altoque.delivery.databinding.ActivityRegisterBinding;
import com.altoque.delivery.view.initial.InitialActivity;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    Button btn_next;


    private void initView(){

        btn_next = findViewById(R.id.btn_next_register);

    }

    private void eventListener(){

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, InitialActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow()
                    .setStatusBarColor(ContextCompat.getColor(RegisterActivity.this, R.color.colorPrimary));
        }

        initView();
        eventListener();

        /*if (getIntent().getExtras() != null) {

            String valor = getIntent().getExtras().getString("usuario");

            if (!valor.isEmpty() ) {

            }
        }*/


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK) {
            Toast.makeText(this, "Por favor finalice su registro.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}