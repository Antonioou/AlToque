package com.altoque.delivery.view.initial.ui.services;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.altoque.delivery.R;
import com.altoque.delivery.adapter.BusinessStyleOneAdapter;
import com.altoque.delivery.adapter.BusinessStyleTwoAdapter;
import com.altoque.delivery.adapter.RecentSearchAdapter;
import com.altoque.delivery.data.RecentSearchSP;
import com.altoque.delivery.databinding.ActivitySearchAccessBinding;
import com.altoque.delivery.model.NegocioModel;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchAccessActivity extends AppCompatActivity {

    ActivitySearchAccessBinding binding;
    FloatingActionButton fab_back, fab_Search;
    EditText et_textsearch;
    MaterialButton btn_clearall;
    TextView tv_state;

    List<String> list_save;

    private RecyclerView recview;
    private List<String> list;
    private LinearLayoutManager layoutMan;
    RecentSearchAdapter adapter;

    private void initResources() {

        fab_back = binding.fabBackSearchaccess;
        fab_Search = binding.fabSearchtextSearchaccess;
        et_textsearch = binding.etSearchtextSearchaccess;
        btn_clearall = binding.btnClearallRecentsearch;
        tv_state = binding.state;

        list = new ArrayList<String>();
        list_save = new ArrayList<>();

        recview = findViewById(R.id.rv_recentsearch);

        LinearLayoutManager layoutMan = new LinearLayoutManager(SearchAccessActivity.this,
                RecyclerView.VERTICAL, false);
        recview.setLayoutManager(layoutMan);
        recview.setHasFixedSize(true);

        list_save = RecentSearchSP.get(SearchAccessActivity.this).retrieveRecentRegister();

        if (list_save.size() > 0) {
            list = list_save;
            btn_clearall.setVisibility(View.VISIBLE);
        } else {
            tv_state.setVisibility(View.VISIBLE);
        }
        //Collections.reverse(list);
        adapter = new RecentSearchAdapter(list);
        recview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void eventListener() {

        fab_back.setOnClickListener(v -> {
            finish();
        });

        fab_Search.setOnClickListener(v -> {
            String text = et_textsearch.getText().toString();

            if (list.size() <= 9) {
                if (!text.isEmpty()) {
                    tv_state.setVisibility(View.GONE);
                    list.add(text);

                    RecentSearchSP.get(SearchAccessActivity.this).saveRecentRegister((ArrayList<String>) list);
                    adapter.notifyDataSetChanged();
                    et_textsearch.setText("");
                    if (btn_clearall.getVisibility() == View.INVISIBLE)
                        btn_clearall.setVisibility(View.VISIBLE);
                }
            }else{
                if (!text.isEmpty()) {
                    tv_state.setVisibility(View.GONE);
                    list.remove(0);
                    RecentSearchSP.get(SearchAccessActivity.this).deleteLastRegister();
                    list.add(text);

                    RecentSearchSP.get(SearchAccessActivity.this).saveRecentRegister((ArrayList<String>) list);
                    adapter.notifyDataSetChanged();
                    et_textsearch.setText("");
                }
            }
        });

        adapter.setOnClickListener(v -> {
            int pos = recview.getChildAdapterPosition(v);
            et_textsearch.setText("" + list.get(pos));
        });


        btn_clearall.setOnClickListener(v -> {
            RecentSearchSP.get(SearchAccessActivity.this).clearRecentRegister();
            btn_clearall.setVisibility(View.INVISIBLE);
            tv_state.setVisibility(View.VISIBLE);
            list.removeAll(list);
            adapter.notifyDataSetChanged();
        });

        et_textsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchAccessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow()
                    .setStatusBarColor(ContextCompat.getColor(SearchAccessActivity.this, R.color.colorWhite));
            getWindow()
                    .getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        initResources();
        eventListener();


    }
}