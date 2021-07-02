package com.altoque.delivery.apiInterface;

import com.altoque.delivery.model.CustomerModel;
import com.altoque.delivery.model.DomicilioModel;
import com.altoque.delivery.model.GeneroModel;
import com.altoque.delivery.model.JoinResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    /**************************** RELACINATED TO USUARIO ********************************/

    @FormUrlEncoded
    @POST("controller/rusuario.php")
    Call<List<JoinResponseModel>> ClientLogin(
            @Field("op") String operation,
            @Field("param_type") String param_type,
            @Field("param_process") String param_process,
            @Field("codigouid") String CodeUID
    );

    @FormUrlEncoded
    @POST("controller/rusuario.php")
    Call<List<CustomerModel>> ClientRegister(
            @Field("op") String operation,
            @Field("param_type") String param_type,
            @Field("param_process") String param_process,
            @Field("codigouid") String CodeUID,
            @Field("celular") String celular,
            @Field("nombre") String nombre,
            @Field("apellidos") String apellidos,
            @Field("foto") String foto,
            @Field("idgenero") String idgenero,
            @Field("token") String token
    );

    @FormUrlEncoded
    @POST("controller/rusuario.php")
    Call<List<CustomerModel>> getClientData(
            @Field("op") String operation,
            @Field("param_type") String param_type,
            @Field("param_process") String param_process,
            @Field("codigouid") String CodeUID
    );


    @FormUrlEncoded
    @POST("controller/rdomicilio.php")
    Call<List<DomicilioModel>> DirectionRegister(
            @Field("op") String operation,
            @Field("param_process") String param_process,
            @Field("direccion") String direccion,
            @Field("piso") String piso,
            @Field("referencia") String referencia,
            @Field("lat") String lat,
            @Field("lng") String lng,
            @Field("iddistrito") String iddistrito,
            @Field("idcliente") String idcliente
    );


    /**************************** RELACINATED TO GENEROS ********************************/

    @FormUrlEncoded
    @POST("controller/rgenero.php")
    Call<List<GeneroModel>> getGenero(
            @Field("op") String operation
    );
}
