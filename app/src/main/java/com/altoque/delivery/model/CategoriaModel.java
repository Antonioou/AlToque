package com.altoque.delivery.model;

import com.google.gson.annotations.SerializedName;

public class CategoriaModel {
    @SerializedName("code_server")
    String code_server;
    @SerializedName("res_server")
    String res_server;
    @SerializedName("msg_server")
    String msg_server;
    @SerializedName("idcategoria")
    String idcategoria;
    @SerializedName("nomb_categoria")
    String nomb_categoria;

    public CategoriaModel() {
    }

    public CategoriaModel(String code_server, String res_server, String msg_server, String idcategoria, String nomb_categoria) {
        this.code_server = code_server;
        this.res_server = res_server;
        this.msg_server = msg_server;
        this.idcategoria = idcategoria;
        this.nomb_categoria = nomb_categoria;
    }

    public String getCode_server() {
        return code_server;
    }

    @Override
    public String toString() {
        return "CategoriaModel{" +
                "code_server='" + code_server + '\'' +
                ", res_server='" + res_server + '\'' +
                ", msg_server='" + msg_server + '\'' +
                ", idcategoria='" + idcategoria + '\'' +
                ", nomb_categoria='" + nomb_categoria + '\'' +
                '}';
    }

    public void setCode_server(String code_server) {
        this.code_server = code_server;
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

    public String getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(String idcategoria) {
        this.idcategoria = idcategoria;
    }

    public String getNomb_categoria() {
        return nomb_categoria;
    }

    public void setNomb_categoria(String nomb_categoria) {
        this.nomb_categoria = nomb_categoria;
    }
}
