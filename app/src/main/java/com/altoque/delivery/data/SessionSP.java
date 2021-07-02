package com.altoque.delivery.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.altoque.delivery.model.CustomerModel;

public class SessionSP {

    private static SessionSP INSTANCE;
    private static Context contextGlobal;

    public static final String PREFS_DATA = "DATA_SESSION_SP";
    public static final String PREFS_SESSION = "STATE_SESSION_SP";

    public static final String IDCLIENT_SESS_SP = "IDCLIENT_SP";
    public static final String NAME_SESS_SP = "NAME_SP";
    public static final String LNAME_SESS_SP = "LNAME_SP";
    public static final String PHONE_SESS_SP = "PHONE_SP";
    public static final String UID_SESS_SP = "UID_SP";
    public static final String PHOTO_SESS_SP = "PHOTO_SP";
    public static final String TARGET_RESULT = "TARGET_RESULT_RESERVATION";
    public static final String IDGENERO_SESS_SP = "IDGENERO_SP";
    public static final String VALOR_STATE_SESS_SP = null;

    SharedPreferences dataSP;
    SharedPreferences loggedSP = null;


    public static SessionSP get(Context contextLocal) {
        if (INSTANCE == null) {
            INSTANCE = new SessionSP(contextLocal);
        }
        return INSTANCE;
    }

    private SessionSP(Context context) {

        loggedSP = context.getApplicationContext()
                .getSharedPreferences(PREFS_SESSION, Context.MODE_PRIVATE);
        dataSP = context.getApplicationContext()
                .getSharedPreferences(PREFS_DATA, Context.MODE_PRIVATE);
    }

    public void saveStateLogin(String valor) {
        SharedPreferences.Editor editor1 = loggedSP.edit();
        editor1.putString(VALOR_STATE_SESS_SP, valor);
        editor1.apply();
    }

    public String getStateLogin() {
        return loggedSP.getString(VALOR_STATE_SESS_SP, "");
    }

    public void saveDataCustomer(CustomerModel customer) {
        if (customer != null) {
            SharedPreferences.Editor editor = dataSP.edit();
            editor.putString(IDCLIENT_SESS_SP, customer.getIdcliente());
            editor.putString(NAME_SESS_SP, customer.getNombrecli());
            editor.putString(LNAME_SESS_SP, customer.getApellidos_cli());
            editor.putString(UID_SESS_SP, customer.getCodigoUID_cli());
            editor.putString(PHOTO_SESS_SP, customer.getFoto_cli());
            editor.putString(IDGENERO_SESS_SP, customer.getIdgenero());
            editor.apply();
        }
    }

    public void setPhoneSessSp(String numberPhone) {
        SharedPreferences.Editor editor = dataSP.edit();
        editor.putString(PHONE_SESS_SP, numberPhone);
        editor.apply();
    }

    public void setTargetResultReservation(String target) {
        if (target != null) {
            SharedPreferences.Editor editor = dataSP.edit();
            editor.putString(TARGET_RESULT, target);
            editor.apply();
        }
    }

    //public String getIdSessSp() {
    //  return dataSP.getString(ID_SESS_SP, "");
    //}
    public String getIdClientSessSp() {
        return dataSP.getString(IDCLIENT_SESS_SP, "");
    }

    public String getNameSessSp() {
        return dataSP.getString(NAME_SESS_SP, "");
    }

    public String getLnameSessSp() {
        return dataSP.getString(LNAME_SESS_SP, "");
    }

    public String getPhoneSessSp() {
        return dataSP.getString(PHONE_SESS_SP, "");
    }

    public String getUidSessSp() {
        return dataSP.getString(UID_SESS_SP, "");
    }

    public String getPhotoSessSp() {
        return dataSP.getString(PHOTO_SESS_SP, "");
    }

    public String getIdGeneroSessSp() {
        return dataSP.getString(IDGENERO_SESS_SP, "");
    }

    public String getTargetResultReservation() {
        return dataSP.getString(TARGET_RESULT, "");
    }


    public void logout() {
        SharedPreferences.Editor editor1 = dataSP.edit();
        editor1.putString(IDCLIENT_SESS_SP, null);
        editor1.putString(NAME_SESS_SP, null);
        editor1.putString(LNAME_SESS_SP, null);
        editor1.putString(UID_SESS_SP, null);
        editor1.putString(PHONE_SESS_SP, null);
        editor1.putString(PHOTO_SESS_SP, null);
        editor1.putString(IDGENERO_SESS_SP, null);
        editor1.apply();
        SharedPreferences.Editor editor = loggedSP.edit();
        editor.putString(VALOR_STATE_SESS_SP, "no");
        editor.apply();
    }


}
