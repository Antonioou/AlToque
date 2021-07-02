package com.altoque.delivery.model;

import com.google.gson.annotations.SerializedName;

public class DomicilioModel {
    @SerializedName("code_server")
    String code_server;
    @SerializedName("res_server")
    String res_server;
    @SerializedName("msg_server")
    String msg_server;
    @SerializedName("iddomicilio")
    String iddomicilio;
    @SerializedName("direccion_dom")
    String direccion_dom;
    @SerializedName("piso_dom")
    String piso_dom;
    @SerializedName("referencia_dom")
    String referencia_dom;
    @SerializedName("estado_dom")
    String estado_dom;
    @SerializedName("uso_dom")
    String uso_dom;
    @SerializedName("lat_dom")
    String lat_dom;
    @SerializedName("long_dom")
    String long_dom;
    @SerializedName("iddistrito")
    String iddistrito;
    @SerializedName("idcliente")
    String idcliente;


    public DomicilioModel() {
    }

    @Override
    public String toString() {
        return "DomicilioModel{" +
                "code_server='" + code_server + '\'' +
                ", res_server='" + res_server + '\'' +
                ", msg_server='" + msg_server + '\'' +
                ", iddomicilio='" + iddomicilio + '\'' +
                ", direccion_dom='" + direccion_dom + '\'' +
                ", piso_dom='" + piso_dom + '\'' +
                ", referencia_dom='" + referencia_dom + '\'' +
                ", estado_dom='" + estado_dom + '\'' +
                ", uso_dom='" + uso_dom + '\'' +
                ", lat_dom='" + lat_dom + '\'' +
                ", long_dom='" + long_dom + '\'' +
                ", iddistrito='" + iddistrito + '\'' +
                ", idcliente='" + idcliente + '\'' +
                '}';
    }

    public DomicilioModel(String code_server, String res_server, String msg_server, String iddomicilio, String direccion_dom, String piso_dom, String referencia_dom, String estado_dom, String uso_dom, String lat_dom, String long_dom, String iddistrito, String idcliente) {
        this.code_server = code_server;
        this.res_server = res_server;
        this.msg_server = msg_server;
        this.iddomicilio = iddomicilio;
        this.direccion_dom = direccion_dom;
        this.piso_dom = piso_dom;
        this.referencia_dom = referencia_dom;
        this.estado_dom = estado_dom;
        this.uso_dom = uso_dom;
        this.lat_dom = lat_dom;
        this.long_dom = long_dom;
        this.iddistrito = iddistrito;
        this.idcliente = idcliente;
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

    public String getIddomicilio() {
        return iddomicilio;
    }

    public void setIddomicilio(String iddomicilio) {
        this.iddomicilio = iddomicilio;
    }

    public String getDireccion_dom() {
        return direccion_dom;
    }

    public void setDireccion_dom(String direccion_dom) {
        this.direccion_dom = direccion_dom;
    }

    public String getPiso_dom() {
        return piso_dom;
    }

    public void setPiso_dom(String piso_dom) {
        this.piso_dom = piso_dom;
    }

    public String getReferencia_dom() {
        return referencia_dom;
    }

    public void setReferencia_dom(String referencia_dom) {
        this.referencia_dom = referencia_dom;
    }

    public String getEstado_dom() {
        return estado_dom;
    }

    public void setEstado_dom(String estado_dom) {
        this.estado_dom = estado_dom;
    }

    public String getUso_dom() {
        return uso_dom;
    }

    public void setUso_dom(String uso_dom) {
        this.uso_dom = uso_dom;
    }

    public String getLat_dom() {
        return lat_dom;
    }

    public void setLat_dom(String lat_dom) {
        this.lat_dom = lat_dom;
    }

    public String getLong_dom() {
        return long_dom;
    }

    public void setLong_dom(String long_dom) {
        this.long_dom = long_dom;
    }

    public String getIddistrito() {
        return iddistrito;
    }

    public void setIddistrito(String iddistrito) {
        this.iddistrito = iddistrito;
    }

    public String getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(String idcliente) {
        this.idcliente = idcliente;
    }
}
