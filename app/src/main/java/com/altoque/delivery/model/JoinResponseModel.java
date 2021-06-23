package com.altoque.delivery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JoinResponseModel {
    @SerializedName("resultado")
    @Expose
    String resultado;
    @SerializedName("mensaje")
    @Expose
    String mensaje;

    public JoinResponseModel() {
    }

    @Override
    public String toString() {
        return "JoinResponseModel{" +
                "resultado='" + resultado + '\'' +
                ", mensaje='" + mensaje + '\'' +
                '}';
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
}
