package com.altoque.delivery.model;

import com.google.gson.annotations.SerializedName;

public class CustomerModel {
    @SerializedName("code_server")
    String code_server;
    @SerializedName("res_server")
    String res_server;
    @SerializedName("msg_server")
    String msg_server;
    @SerializedName("idcliente")
    String idcliente;
    @SerializedName("cel_cli")
    String cel_cli;
    @SerializedName("nombrecli")
    String nombrecli;
    @SerializedName("apellidos_cli")
    String apellidos_cli;
    @SerializedName("foto_cli")
    String foto_cli;
    @SerializedName("estado_cli")
    String estado_cli;
    @SerializedName("idgenero")
    String idgenero;
    @SerializedName("nombre_genero")
    String nombre_genero;
    @SerializedName("codigoUID_cli")
    String codigoUID_cli;
    @SerializedName("token_cli")
    String token_cli;

    public CustomerModel() {
    }

    public String getNombre_genero() {
        return nombre_genero;
    }

    public void setNombre_genero(String nombre_genero) {
        this.nombre_genero = nombre_genero;
    }


    @Override
    public String toString() {
        return "CustomerModel{" +
                "code_server='" + code_server + '\'' +
                ", res_server='" + res_server + '\'' +
                ", msg_server='" + msg_server + '\'' +
                ", idcliente='" + idcliente + '\'' +
                ", cel_cli='" + cel_cli + '\'' +
                ", nombrecli='" + nombrecli + '\'' +
                ", apellidos_cli='" + apellidos_cli + '\'' +
                ", foto_cli='" + foto_cli + '\'' +
                ", estado_cli='" + estado_cli + '\'' +
                ", idgenero='" + idgenero + '\'' +
                ", nombre_genero='" + nombre_genero + '\'' +
                ", codigoUID_cli='" + codigoUID_cli + '\'' +
                ", token_cli='" + token_cli + '\'' +
                '}';
    }

    public CustomerModel(String code_server, String res_server, String msg_server, String idcliente, String cel_cli, String nombrecli, String apellidos_cli, String foto_cli, String estado_cli, String idgenero, String nombre_genero, String codigoUID_cli, String token_cli) {
        this.code_server = code_server;
        this.res_server = res_server;
        this.msg_server = msg_server;
        this.idcliente = idcliente;
        this.cel_cli = cel_cli;
        this.nombrecli = nombrecli;
        this.apellidos_cli = apellidos_cli;
        this.foto_cli = foto_cli;
        this.estado_cli = estado_cli;
        this.idgenero = idgenero;
        this.nombre_genero = nombre_genero;
        this.codigoUID_cli = codigoUID_cli;
        this.token_cli = token_cli;
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

    public String getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(String idcliente) {
        this.idcliente = idcliente;
    }

    public String getCel_cli() {
        return cel_cli;
    }

    public void setCel_cli(String cel_cli) {
        this.cel_cli = cel_cli;
    }

    public String getNombrecli() {
        return nombrecli;
    }

    public void setNombrecli(String nombrecli) {
        this.nombrecli = nombrecli;
    }

    public String getApellidos_cli() {
        return apellidos_cli;
    }

    public void setApellidos_cli(String apellidos_cli) {
        this.apellidos_cli = apellidos_cli;
    }

    public String getFoto_cli() {
        return foto_cli;
    }

    public void setFoto_cli(String foto_cli) {
        this.foto_cli = foto_cli;
    }

    public String getEstado_cli() {
        return estado_cli;
    }

    public void setEstado_cli(String estado_cli) {
        this.estado_cli = estado_cli;
    }

    public String getIdgenero() {
        return idgenero;
    }

    public void setIdgenero(String idgenero) {
        this.idgenero = idgenero;
    }

    public String getCodigoUID_cli() {
        return codigoUID_cli;
    }

    public void setCodigoUID_cli(String codigoUID_cli) {
        this.codigoUID_cli = codigoUID_cli;
    }

    public String getToken_cli() {
        return token_cli;
    }

    public void setToken_cli(String token_cli) {
        this.token_cli = token_cli;
    }
}
