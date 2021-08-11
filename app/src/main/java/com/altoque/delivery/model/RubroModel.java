package com.altoque.delivery.model;

import com.google.gson.annotations.SerializedName;

public class RubroModel {

    @SerializedName("code_server")
    String code_server;
    @SerializedName("res_server")
    String res_server;
    @SerializedName("msg_server")
    String msg_server;
    @SerializedName("idrubro")
    String idrubro;
    @SerializedName("nom_rubro")
    String nom_rubro;
    @SerializedName("foto_rubro")
    String foto_rubro;
    @SerializedName("estado_rubro")
    String estado_rubro;


    public RubroModel() {
    }

    public String getCode_server() {
        return code_server;
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

    public String getIdrubro() {
        return idrubro;
    }

    public void setIdrubro(String idrubro) {
        this.idrubro = idrubro;
    }

    public String getNom_rubro() {
        return nom_rubro;
    }

    public void setNom_rubro(String nom_rubro) {
        this.nom_rubro = nom_rubro;
    }

    public String getFoto_rubro() {
        return foto_rubro;
    }

    public void setFoto_rubro(String foto_rubro) {
        this.foto_rubro = foto_rubro;
    }

    public String getEstado_rubro() {
        return estado_rubro;
    }

    public void setEstado_rubro(String estado_rubro) {
        this.estado_rubro = estado_rubro;
    }

    public RubroModel(String code_server, String res_server, String msg_server, String idrubro, String nom_rubro, String foto_rubro, String estado_rubro) {
        this.code_server = code_server;
        this.res_server = res_server;
        this.msg_server = msg_server;
        this.idrubro = idrubro;
        this.nom_rubro = nom_rubro;
        this.foto_rubro = foto_rubro;
        this.estado_rubro = estado_rubro;
    }
}