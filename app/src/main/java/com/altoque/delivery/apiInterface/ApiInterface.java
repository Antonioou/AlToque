package com.altoque.delivery.apiInterface;

import com.altoque.delivery.model.AccessResponseModel;
import com.altoque.delivery.model.CategoriaModel;
import com.altoque.delivery.model.CustomerModel;
import com.altoque.delivery.model.DomicilioModel;
import com.altoque.delivery.model.GeneroModel;
import com.altoque.delivery.model.JoinResponseModel;
import com.altoque.delivery.model.NegocioModel;
import com.altoque.delivery.model.ProductoModel;
import com.altoque.delivery.model.RubroModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("controller/rubigeo.php")
    Call<List<AccessResponseModel>> getResponseAccess(
            @Field("op") String operation,
            @Field("cod_postal") String codePostal
    );

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
    Call<List<DomicilioModel>> setDirectionRegister(
            @Field("op") String operation,
            @Field("param_process") String param_process,
            @Field("direccion") String direccion,
            @Field("piso") String piso,
            @Field("referencia") String referencia,
            @Field("lat") String lat,
            @Field("lng") String lng,
            @Field("idcliente") String idcliente,
            @Field("estado_uso") String estado_uso
    );

    @FormUrlEncoded
    @POST("controller/rdomicilio.php")
    Call<List<DomicilioModel>> setDirectionUpdate(
            @Field("op") String operation,
            @Field("param_process") String param_process,
            @Field("direccion") String direccion,
            @Field("piso") String piso,
            @Field("referencia") String referencia,
            @Field("lat") String lat,
            @Field("lng") String lng,
            @Field("idcliente") String idcliente,
            @Field("iddom") String iddom
    );

    @FormUrlEncoded
    @POST("controller/rdomicilio.php")
    Call<List<DomicilioModel>> getDataDirection(
            @Field("op") String operation,
            @Field("param_process") String param_process,
            @Field("idcliente") String idcliente
    );

    @FormUrlEncoded
    @POST("controller/rdomicilio.php")
    Call<List<DomicilioModel>> getListDirection(
            @Field("op") String operation,
            @Field("param_process") String param_process,
            @Field("idcliente") String idcliente
    );

    @FormUrlEncoded
    @POST("controller/rdomicilio.php")
    Call<List<DomicilioModel>> deleteClientDirection(
            @Field("op") String operation,
            @Field("param_process") String param_process,
            @Field("iddom") String iddom,
            @Field("idcliente") String idcliente
    );

    @FormUrlEncoded
    @POST("controller/rdomicilio.php")
    Call<List<DomicilioModel>> getDataByIdDirection(
            @Field("op") String operation,
            @Field("param_process") String param_process,
            @Field("iddom") String iddom,
            @Field("idcliente") String idcliente
    );

    @FormUrlEncoded
    @POST("controller/rdomicilio.php")
    Call<List<DomicilioModel>> setDirectionNewUse(
            @Field("op") String operation,
            @Field("param_process") String param_process,
            @Field("estado_uso") String uso_dom,
            @Field("iddom") String iddom,
            @Field("idcliente") String idcliente
    );

    /**************************** RELACINATED TO GENEROS ********************************/

    @FormUrlEncoded
    @POST("controller/rgenero.php")
    Call<List<GeneroModel>> getGenero(
            @Field("op") String operation
    );

    /**************************** RELACINATED TO BUSINESS ********************************/

    @FormUrlEncoded
    @POST("controller/rnegocio.php")
    Call<List<NegocioModel>> getBusinessListByProv(
            @Field("op") String operation,
            @Field("iddistrito") String iddistrito
    );

    @FormUrlEncoded
    @POST("controller/rnegocio.php")
    Call<List<NegocioModel>> getBusinessListByRate(
            @Field("op") String operation,
            @Field("iddistrito") String iddistrito
    );

    @FormUrlEncoded
    @POST("controller/rnegocio.php")
    Call<List<NegocioModel>> getSuggestionShortList(
            @Field("op") String operation
    );

    @FormUrlEncoded
    @POST("controller/rnegocio.php")
    Call<List<NegocioModel>> getListBusinessByRubro(
            @Field("op") String operation,
            @Field("iddistrito") String iddistrito,
            @Field("idrubro") String idrubro
    );

    @FormUrlEncoded
    @POST("controller/rrubro.php")
    Call<List<RubroModel>> getRubroList(
            @Field("op") String operation
    );

    @FormUrlEncoded
    @POST("controller/rproducto.php")
    Call<List<CategoriaModel>> getListCategories(
            @Field("op") String operation,
            @Field("idnegocio") String idnegocio
    );

    @FormUrlEncoded
    @POST("controller/rproducto.php")
    Call<List<NegocioModel>> getDataBusiness(
            @Field("op") String operation,
            @Field("idnegocio") String idnegocio
    );

    @FormUrlEncoded
    @POST("controller/rproducto.php")
    Call<List<ProductoModel>> getItemListByCategoryAndBusiness(
            @Field("op") String operation,
            @Field("idnegocio") String idnegocio,
            @Field("idcategoria") String idcategoria
    );
}
