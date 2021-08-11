package com.altoque.delivery.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class AccessResponseModel {
    @SerializedName("code_server")
    private String code_server;
    @SerializedName("res_server")
    private String res_server;
    @SerializedName("msg_server")
    private String msg_server;
    @SerializedName("iddistrito")
    private String iddistrito;
    @SerializedName("nombre_distrito")
    private String nombre_distrito;
    @SerializedName("habilitado_distrito")
    private String habilitado_distrito;

    public AccessResponseModel() {

    }

    public AccessResponseModel(String code_server, String res_server, String msg_server, String iddistrito, String nombre_distrito, String habilitado_distrito) {
        this.code_server = code_server;
        this.res_server = res_server;
        this.msg_server = msg_server;
        this.iddistrito = iddistrito;
        this.nombre_distrito = nombre_distrito;
        this.habilitado_distrito = habilitado_distrito;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessResponseModel that = (AccessResponseModel) o;
        return Objects.equals(code_server, that.code_server) && Objects.equals(res_server, that.res_server) && Objects.equals(msg_server, that.msg_server) && Objects.equals(iddistrito, that.iddistrito) && Objects.equals(nombre_distrito, that.nombre_distrito) && Objects.equals(habilitado_distrito, that.habilitado_distrito);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code_server, res_server, msg_server, iddistrito, nombre_distrito, habilitado_distrito);
    }

    @Override
    public String toString() {
        return "AccessResponseModel{" +
                "code_server='" + code_server + '\'' +
                ", res_server='" + res_server + '\'' +
                ", msg_server='" + msg_server + '\'' +
                ", iddistrito='" + iddistrito + '\'' +
                ", nombre_distrito='" + nombre_distrito + '\'' +
                ", habilitado_distrito='" + habilitado_distrito + '\'' +
                '}';
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

    public String getIddistrito() {
        return iddistrito;
    }

    public void setIddistrito(String iddistrito) {
        this.iddistrito = iddistrito;
    }

    public String getNombre_distrito() {
        return nombre_distrito;
    }

    public void setNombre_distrito(String nombre_distrito) {
        this.nombre_distrito = nombre_distrito;
    }

    public String getHabilitado_distrito() {
        return habilitado_distrito;
    }

    public void setHabilitado_distrito(String habilitado_distrito) {
        this.habilitado_distrito = habilitado_distrito;
    }
}
