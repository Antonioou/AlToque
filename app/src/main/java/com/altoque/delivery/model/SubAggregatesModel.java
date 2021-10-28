package com.altoque.delivery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubAggregatesModel {


    @SerializedName("idacompanamiento")
    @Expose
    private String idacompanamiento;
    @SerializedName("nombre_subproducto")
    @Expose
    private String nombre_subproducto;
    @SerializedName("opcional_obligatorio")
    @Expose
    private String opcional_obligatorio;
    @SerializedName("valor_opcional_obligatorio")
    @Expose
    private String valor_opcional_obligatorio;
    @SerializedName("contable_no_contable")
    @Expose
    private String contable_no_contable;
    @SerializedName("valor_contable_no_contable")
    @Expose
    private String valor_contable_no_contable;
    @SerializedName("limite_maximo")
    @Expose
    private String limite_maximo;
    @SerializedName("pagable_no_pagable")
    @Expose
    private String pagable_no_pagable;
    @SerializedName("valor_pagable_no_pagable")
    @Expose
    private String valor_pagable_no_pagable;
    @SerializedName("precio_acompanamiento")
    @Expose
    private String precio_acompanamiento;


    public SubAggregatesModel() {
    }

    @Override
    public String toString() {
        return "SubAggregatesModel{" +
                "idacompanamiento='" + idacompanamiento + '\'' +
                ", nombre_subproducto='" + nombre_subproducto + '\'' +
                ", opcional_obligatorio='" + opcional_obligatorio + '\'' +
                ", valor_opcional_obligatorio='" + valor_opcional_obligatorio + '\'' +
                ", contable_no_contable='" + contable_no_contable + '\'' +
                ", valor_contable_no_contable='" + valor_contable_no_contable + '\'' +
                ", limite_maximo='" + limite_maximo + '\'' +
                ", pagable_no_pagable='" + pagable_no_pagable + '\'' +
                ", valor_pagable_no_pagable='" + valor_pagable_no_pagable + '\'' +
                ", precio_acompanamiento='" + precio_acompanamiento + '\'' +
                '}';
    }

    public String getIdacompanamiento() {
        return idacompanamiento;
    }

    public void setIdacompanamiento(String idacompanamiento) {
        this.idacompanamiento = idacompanamiento;
    }

    public String getNombre_subproducto() {
        return nombre_subproducto;
    }

    public void setNombre_subproducto(String nombre_subproducto) {
        this.nombre_subproducto = nombre_subproducto;
    }

    public String getOpcional_obligatorio() {
        return opcional_obligatorio;
    }

    public void setOpcional_obligatorio(String opcional_obligatorio) {
        this.opcional_obligatorio = opcional_obligatorio;
    }

    public String getValor_opcional_obligatorio() {
        return valor_opcional_obligatorio;
    }

    public void setValor_opcional_obligatorio(String valor_opcional_obligatorio) {
        this.valor_opcional_obligatorio = valor_opcional_obligatorio;
    }

    public String getContable_no_contable() {
        return contable_no_contable;
    }

    public void setContable_no_contable(String contable_no_contable) {
        this.contable_no_contable = contable_no_contable;
    }

    public String getValor_contable_no_contable() {
        return valor_contable_no_contable;
    }

    public void setValor_contable_no_contable(String valor_contable_no_contable) {
        this.valor_contable_no_contable = valor_contable_no_contable;
    }

    public String getLimite_maximo() {
        return limite_maximo;
    }

    public void setLimite_maximo(String limite_maximo) {
        this.limite_maximo = limite_maximo;
    }

    public String getPagable_no_pagable() {
        return pagable_no_pagable;
    }

    public void setPagable_no_pagable(String pagable_no_pagable) {
        this.pagable_no_pagable = pagable_no_pagable;
    }

    public String getValor_pagable_no_pagable() {
        return valor_pagable_no_pagable;
    }

    public void setValor_pagable_no_pagable(String valor_pagable_no_pagable) {
        this.valor_pagable_no_pagable = valor_pagable_no_pagable;
    }

    public String getPrecio_acompanamiento() {
        return precio_acompanamiento;
    }

    public void setPrecio_acompanamiento(String precio_acompanamiento) {
        this.precio_acompanamiento = precio_acompanamiento;
    }

    public SubAggregatesModel(String idacompanamiento, String nombre_subproducto, String opcional_obligatorio, String valor_opcional_obligatorio, String contable_no_contable, String valor_contable_no_contable, String limite_maximo, String pagable_no_pagable, String valor_pagable_no_pagable, String precio_acompanamiento) {
        this.idacompanamiento = idacompanamiento;
        this.nombre_subproducto = nombre_subproducto;
        this.opcional_obligatorio = opcional_obligatorio;
        this.valor_opcional_obligatorio = valor_opcional_obligatorio;
        this.contable_no_contable = contable_no_contable;
        this.valor_contable_no_contable = valor_contable_no_contable;
        this.limite_maximo = limite_maximo;
        this.pagable_no_pagable = pagable_no_pagable;
        this.valor_pagable_no_pagable = valor_pagable_no_pagable;
        this.precio_acompanamiento = precio_acompanamiento;
    }
}
