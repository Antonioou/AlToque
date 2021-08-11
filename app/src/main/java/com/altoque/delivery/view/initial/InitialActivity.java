package com.altoque.delivery.view.initial;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.altoque.delivery.R;
import com.altoque.delivery.databinding.ActivityInitialBinding;
import com.altoque.delivery.nav.KeepStateNavigator;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

public class InitialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityInitialBinding binding = ActivityInitialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        getWindow()
                .setStatusBarColor(ContextCompat.getColor(InitialActivity.this, R.color.colorWhite));
        getWindow()
                .getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_initial);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_initial);
        assert navHostFragment != null;
        KeepStateNavigator navigator = new KeepStateNavigator(this, navHostFragment.getChildFragmentManager(), R.id.nav_host_fragment_activity_initial);
        navController.getNavigatorProvider().addNavigator(navigator);
        navController.setGraph(R.navigation.mobile_navigation);
        setUpNavBottom(navHostFragment);
    }

    private void setUpNavBottom(NavHostFragment hostFragment) {
        BottomNavigationView navMenu = findViewById(R.id.nav_view);
        if (hostFragment != null) {
            NavController navController = hostFragment.getNavController();
            NavigationUI.setupWithNavController(navMenu, navController);
        }
    }

}