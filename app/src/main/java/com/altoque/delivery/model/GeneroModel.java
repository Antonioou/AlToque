package com.altoque.delivery.model;

import com.google.gson.annotations.SerializedName;

public class GeneroModel {

    @SerializedName("code_server")
    String code_server;
    @SerializedName("res_server")
    String res_server;
    @SerializedName("msg_server")
    String msg_server;
    @SerializedName("idgenero")
    String idgenero;
    @SerializedName("nombre_genero")
    String nombre_genero;
    @SerializedName("estado_genero")
    String estado_genero;



    public GeneroModel() {
    }

    public GeneroModel(String idgenero, String nombre_genero, String estado_genero, String res_server, String msg_server, String code_server) {
        this.idgenero = idgenero;
        this.nombre_genero = nombre_genero;
        this.estado_genero = estado_genero;
        this.res_server = res_server;
        this.msg_server = msg_server;
        this.code_server = code_server;
    }

    @Override
    public String toString() {
        return "GeneroModel{" +
                "idgenero='" + idgenero + '\'' +
                ", nombre_genero='" + nombre_genero + '\'' +
                ", estado_genero='" + estado_genero + '\'' +
                ", res_server='" + res_server + '\'' +
                ", msg_server='" + msg_server + '\'' +
                ", code_server='" + code_server + '\'' +
                '}';
    }

    public String getIdgenero() {
        return idgenero;
    }

    public void setIdgenero(String idgenero) {
        this.idgenero = idgenero;
    }

    public String getNombre_genero() {
        return nombre_genero;
    }

    public void setNombre_genero(String nombre_genero) {
        this.nombre_genero = nombre_genero;
    }

    public String getEstado_genero() {
        return estado_genero;
    }

    public void setEstado_genero(String estado_genero) {
        this.estado_genero = estado_genero;
    }

    public String getRes_server() {
        return res_server;
    }

    public void setRes_server(String res_server) {
        this.res_server = res_server;
    }

    public String getMsg_server() {
        return msg_server;
    }

    public void setMsg_server(String msg_server) {
        this.msg_server = msg_server;
    }

    public String getCode_server() {
        return code_server;
    }

    public void setCode_server(String code_server) {
        this.code_server = code_server;
    }
}
