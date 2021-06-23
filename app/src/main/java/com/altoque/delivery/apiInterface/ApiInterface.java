package com.altoque.delivery.apiInterface;

import com.altoque.delivery.model.JoinResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    /**************************** RELACINATED TO MENU DAILY ********************************/

    @FormUrlEncoded
    @POST("controller/rusuario.php")
    Call<JoinResponseModel> ClientLogin(
            @Field("op") String operation,
            @Field("codigouid") String CodeUID
    );
}
