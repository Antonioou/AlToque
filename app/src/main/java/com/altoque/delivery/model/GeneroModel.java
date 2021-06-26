package com.altoque.delivery.model;

import com.google.gson.annotations.SerializedName;

public class GeneroModel {

    @SerializedName("codigo")
    String codigo;
    @SerializedName("resultado")
    String resultado;
    @SerializedName("mensaje")
    String mensaje;
    @SerializedName("idgenero")
    String idgenero;
    @SerializedName("nombre_genero")
    String nombre_genero;
    @SerializedName("estado_genero")
    String estado_genero;



    public GeneroModel() {
    }

    public GeneroModel(String idgenero, String nombre_genero, String estado_genero, String resultado, String mensaje, String codigo) {
        this.idgenero = idgenero;
        this.nombre_genero = nombre_genero;
        this.estado_genero = estado_genero;
        this.resultado = resultado;
        this.mensaje = mensaje;
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "GeneroModel{" +
                "idgenero='" + idgenero + '\'' +
                ", nombre_genero='" + nombre_genero + '\'' +
                ", estado_genero='" + estado_genero + '\'' +
                ", resultado='" + resultado + '\'' +
                ", mensaje='" + mensaje + '\'' +
                ", codigo='" + codigo + '\'' +
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
