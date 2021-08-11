package com.altoque.delivery.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RecentSearchSP {

    private static RecentSearchSP INSTANCE;
    private static Context contextGlobal;

    public static final String PREFS_DATA = "RECENTREGISTER_SP";

    SharedPreferences dataSP;

    ArrayList<String> set = new ArrayList<String>();

    public static RecentSearchSP get(Context contextLocal) {
        if (INSTANCE == null) {
            INSTANCE = new RecentSearchSP(contextLocal);
        }
        return INSTANCE;
    }

    private RecentSearchSP(Context context) {
        dataSP = context.getApplicationContext()
                .getSharedPreferences(PREFS_DATA, Context.MODE_PRIVATE);
    }

    public void saveRecentRegister(ArrayList<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        SharedPreferences.Editor editor1 = dataSP.edit();
        editor1.putString("List_recentRegister", json);
        editor1.apply();
    }

    public void clearRecentRegister() {
        SharedPreferences.Editor editor1 = dataSP.edit();
        editor1.putString("List_recentRegister", "");
        editor1.apply();
    }

    public ArrayList<String> retrieveRecentRegister() {
        Gson gson = new Gson();
        String json = dataSP.getString("List_recentRegister", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        set = gson.fromJson(json, type);
        if (set == null) {
            set = new ArrayList<>();
        }
        return set;
    }

    public void deleteLastRegister() {
        Gson gson = new Gson();
        String json = dataSP.getString("List_recentRegister", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();

        set = gson.fromJson(json, type);

        if (set == null) {
            set = new ArrayList<>();
        }

        set.remove(0);

        String jsonN = gson.toJson(set);
        SharedPreferences.Editor editor1 = dataSP.edit();
        editor1.putString("List_recentRegister", jsonN);
        editor1.apply();
    }

}
