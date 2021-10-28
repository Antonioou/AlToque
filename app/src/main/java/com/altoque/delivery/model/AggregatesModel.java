package com.altoque.delivery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AggregatesModel {

    @SerializedName("code_server")
    @Expose
    private String code_server;
    @SerializedName("res_server")
    @Expose
    private String res_server;
    @SerializedName("msg_server")
    @Expose
    private String msg_server;
    @SerializedName("idencabezado")
    @Expose
    private String idencabezado;
    @SerializedName("nombre_etiqueta")
    @Expose
    private String nombre_etiqueta;
    @SerializedName("seleccion_multiple_unitaria")
    @Expose
    private String seleccion_multiple_unitaria;
    @SerializedName("valor_seleccion_multiple_unitaria")
    @Expose
    private String valor_seleccion_multiple_unitaria;
    @SerializedName("acompanamiento")
    @Expose
    private ArrayList<SubAggregatesModel> acompanamiento;


    public AggregatesModel() {
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

    public String getIdencabezado() {
        return idencabezado;
    }

    public void setIdencabezado(String idencabezado) {
        this.idencabezado = idencabezado;
    }

    public String getNombre_etiqueta() {
        return nombre_etiqueta;
    }

    public void setNombre_etiqueta(String nombre_etiqueta) {
        this.nombre_etiqueta = nombre_etiqueta;
    }

    public String getSeleccion_multiple_unitaria() {
        return seleccion_multiple_unitaria;
    }

    public void setSeleccion_multiple_unitaria(String seleccion_multiple_unitaria) {
        this.seleccion_multiple_unitaria = seleccion_multiple_unitaria;
    }

    public String getValor_seleccion_multiple_unitaria() {
        return valor_seleccion_multiple_unitaria;
    }

    public void setValor_seleccion_multiple_unitaria(String valor_seleccion_multiple_unitaria) {
        this.valor_seleccion_multiple_unitaria = valor_seleccion_multiple_unitaria;
    }

    public ArrayList<SubAggregatesModel> getAcompanamiento() {
        return acompanamiento;
    }

    public void setAcompanamiento(ArrayList<SubAggregatesModel> acompanamiento) {
        this.acompanamiento = acompanamiento;
    }

    @Override
    public String toString() {
        return "AggregatesModel{" +
                "code_server='" + code_server + '\'' +
                ", res_server='" + res_server + '\'' +
                ", msg_server='" + msg_server + '\'' +
                ", idencabezado='" + idencabezado + '\'' +
                ", nombre_etiqueta='" + nombre_etiqueta + '\'' +
                ", seleccion_multiple_unitaria='" + seleccion_multiple_unitaria + '\'' +
                ", valor_seleccion_multiple_unitaria='" + valor_seleccion_multiple_unitaria + '\'' +
                ", acompanamiento=" + acompanamiento +
                '}';
    }

    public AggregatesModel(String code_server, String res_server, String msg_server, String idencabezado, String nombre_etiqueta, String seleccion_multiple_unitaria, String valor_seleccion_multiple_unitaria, ArrayList<SubAggregatesModel> acompanamiento) {
        this.code_server = code_server;
        this.res_server = res_server;
        this.msg_server = msg_server;
        this.idencabezado = idencabezado;
        this.nombre_etiqueta = nombre_etiqueta;
        this.seleccion_multiple_unitaria = seleccion_multiple_unitaria;
        this.valor_seleccion_multiple_unitaria = valor_seleccion_multiple_unitaria;
        this.acompanamiento = acompanamiento;
    }
}
