package com.altoque.delivery.data;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionSP {

    private static SessionSP INSTANCE;
    private static Context contextGlobal;

    public static final String PREFS_DATA = "DATA_SESSION_SP";
    public static final String PREFS_SESSION = "STATE_SESSION_SP";

    public static final String ID_SESS_SP = "ID_SP";
    public static final String NAME_SESS_SP = "NAME_SP";
    public static final String LNAME_SESS_SP = "LNAME_SP";
    public static final String PHONE_SESS_SP = "PHONE_SP";
    public static final String EMAIL_SESS_SP = "PHONE_SP";
    public static final String PHOTO_SESS_SP = "PHOTO_SP";
    public static final String TARGET_RESULT = "TARGET_RESULT_RESERVATION";

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

    /*public void saveDataCustomer(CustomerModel customer) {
        if (customer != null) {
            SharedPreferences.Editor editor = dataSP.edit();
            editor.putString(ID_SESS_SP, customer.getId_customer());
            editor.putString(NAME_SESS_SP, customer.getFirstname_customer());
            editor.putString(LNAME_SESS_SP, customer.getLastname_customer());
            editor.putString(EMAIL_SESS_SP, customer.getEmail_customer());
            editor.putInt(PHONE_SESS_SP, customer.getPhone_customer());
            editor.putString(PHOTO_SESS_SP, customer.getPhoto_customer());
            editor.apply();
        }
    }*/

    public void setTargetResultReservation(String target) {
        if (target != null) {
            SharedPreferences.Editor editor = dataSP.edit();
            editor.putString(TARGET_RESULT, target);
            editor.apply();
        }
    }

    public String getIdSessSp() {
        return dataSP.getString(ID_SESS_SP, "");
    }

    public String getNameSessSp() {
        return dataSP.getString(NAME_SESS_SP, "");
    }

    public String getLnameSessSp() {
        return dataSP.getString(LNAME_SESS_SP, "");
    }

    public Integer getPhoneSessSp() {
        return dataSP.getInt(PHONE_SESS_SP, 0);
    }

    public String getEmailSessSp() {
        return dataSP.getString(EMAIL_SESS_SP, "");
    }

    public String getPhotoSessSp() {
        return dataSP.getString(PHOTO_SESS_SP, "");
    }

    public String getTargetResultReservation() {
        return dataSP.getString(TARGET_RESULT, "");
    }


    public void logout() {
        SharedPreferences.Editor editor1 = dataSP.edit();
        editor1.putString(ID_SESS_SP, null);
        editor1.putString(NAME_SESS_SP, null);
        editor1.putString(LNAME_SESS_SP, null);
        editor1.putString(EMAIL_SESS_SP, null);
        editor1.putInt(PHONE_SESS_SP, 0);
        editor1.putString(PHOTO_SESS_SP, null);
        editor1.apply();
        SharedPreferences.Editor editor = loggedSP.edit();
        editor.putString(VALOR_STATE_SESS_SP, "no");
        editor.apply();
    }


}
