package com.altoque.delivery.model;

import com.google.gson.annotations.SerializedName;

public class CustomerModel {
    @SerializedName("codigo")
    String codigo;
    @SerializedName("resultado")
    String resultado;
    @SerializedName("mensaje")
    String mensaje;
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
    @SerializedName("codigoUID_cli")
    String codigoUID_cli;
    @SerializedName("token_cli")
    String token_cli;

    public CustomerModel() {
    }

    public CustomerModel(String codigo, String resultado, String mensaje, String idcliente, String cel_cli, String nombrecli, String apellidos_cli, String foto_cli, String estado_cli, String idgenero, String codigoUID_cli, String token_cli) {
        this.codigo = codigo;
        this.resultado = resultado;
        this.mensaje = mensaje;
        this.idcliente = idcliente;
        this.cel_cli = cel_cli;
        this.nombrecli = nombrecli;
        this.apellidos_cli = apellidos_cli;
        this.foto_cli = foto_cli;
        this.estado_cli = estado_cli;
        this.idgenero = idgenero;
        this.codigoUID_cli = codigoUID_cli;
        this.token_cli = token_cli;
    }

    @Override
    public String toString() {
        return "CustomerModel{" +
                "codigo='" + codigo + '\'' +
                ", resultado='" + resultado + '\'' +
                ", mensaje='" + mensaje + '\'' +
                ", idcliente='" + idcliente + '\'' +
                ", cel_cli='" + cel_cli + '\'' +
                ", nombrecli='" + nombrecli + '\'' +
                ", apellidos_cli='" + apellidos_cli + '\'' +
                ", foto_cli='" + foto_cli + '\'' +
                ", estado_cli='" + estado_cli + '\'' +
                ", idgenero='" + idgenero + '\'' +
                ", codigoUID_cli='" + codigoUID_cli + '\'' +
                ", token_cli='" + token_cli + '\'' +
                '}';
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
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
