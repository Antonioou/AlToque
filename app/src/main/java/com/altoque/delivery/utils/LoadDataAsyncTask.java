package com.altoque.delivery.utils;

import android.os.AsyncTask;

import com.altoque.delivery.model.NegocioModel;

import java.lang.ref.WeakReference;

public class LoadDataAsyncTask extends AsyncTask<NegocioModel, Integer, Void> {

    private WeakReference<NegocioModel> myObjectWeakReference;

    @Override
    protected Void doInBackground(NegocioModel... params) {

        return null;
    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

    }

}
