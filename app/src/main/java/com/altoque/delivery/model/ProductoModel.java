package com.altoque.delivery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class ProductoModel {

    @SerializedName("code_server")
    @Expose
    private String code_server;
    @SerializedName("res_server")
    @Expose
    private String res_server;
    @SerializedName("msg_server")
    @Expose
    private String msg_server;
    @SerializedName("idproducto")
    @Expose
    private String idproducto;
    @SerializedName("nom_prod")
    @Expose
    private String nom_prod;
    @SerializedName("desc_prod")
    @Expose
    private String desc_prod;
    @SerializedName("precio_compraprod")
    @Expose
    private String precio_compraprod;
    @SerializedName("precio_ventaprod")
    @Expose
    private String precio_ventaprod;
    @SerializedName("precio_venta_unidad")
    @Expose
    private String precio_venta_unidad;
    @SerializedName("image_prod")
    @Expose
    private String image_prod;
    @SerializedName("disponibilidad_prod")
    @Expose
    private String disponibilidad_prod;
    @SerializedName("nomb_categoria")
    @Expose
    private String nomb_categoria;
    @SerializedName("nom_medida")
    @Expose
    private String nom_medida;
    @SerializedName("nombre_marca")
    @Expose
    private String nombre_marca;
    @SerializedName("imagen_marca")
    @Expose
    private String imagen_marca;
    @SerializedName("precio_descuento")
    @Expose
    private String precio_descuento;
    @SerializedName("precio_unid")
    @Expose
    private String precio_unid;
    @SerializedName("stock_actual")
    @Expose
    private String stock_actual;
    @SerializedName("stock_minimo")
    @Expose
    private String stock_minimo;
    @SerializedName("stock_maximo")
    @Expose
    private String stock_maximo;
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("minimum_quantity")
    @Expose
    private String minimum_quantity;
    @SerializedName("maximum_quantity")
    @Expose
    private String maximum_quantity;


    public ProductoModel() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductoModel that = (ProductoModel) o;
        return Objects.equals(code_server, that.code_server) && Objects.equals(res_server, that.res_server) && Objects.equals(msg_server, that.msg_server) && Objects.equals(idproducto, that.idproducto) && Objects.equals(nom_prod, that.nom_prod) && Objects.equals(desc_prod, that.desc_prod) && Objects.equals(precio_compraprod, that.precio_compraprod) && Objects.equals(precio_ventaprod, that.precio_ventaprod) && Objects.equals(precio_venta_unidad, that.precio_venta_unidad) && Objects.equals(image_prod, that.image_prod) && Objects.equals(disponibilidad_prod, that.disponibilidad_prod) && Objects.equals(nomb_categoria, that.nomb_categoria) && Objects.equals(nom_medida, that.nom_medida) && Objects.equals(nombre_marca, that.nombre_marca) && Objects.equals(imagen_marca, that.imagen_marca) && Objects.equals(precio_descuento, that.precio_descuento) && Objects.equals(precio_unid, that.precio_unid) && Objects.equals(stock_actual, that.stock_actual) && Objects.equals(stock_minimo, that.stock_minimo) && Objects.equals(stock_maximo, that.stock_maximo) && Objects.equals(status, that.status) && Objects.equals(minimum_quantity, that.minimum_quantity) && Objects.equals(maximum_quantity, that.maximum_quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code_server, res_server, msg_server, idproducto, nom_prod, desc_prod, precio_compraprod, precio_ventaprod, precio_venta_unidad, image_prod, disponibilidad_prod, nomb_categoria, nom_medida, nombre_marca, imagen_marca, precio_descuento, precio_unid, stock_actual, stock_minimo, stock_maximo, status, minimum_quantity, maximum_quantity);
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

    public String getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(String idproducto) {
        this.idproducto = idproducto;
    }

    public String getNom_prod() {
        return nom_prod;
    }

    public void setNom_prod(String nom_prod) {
        this.nom_prod = nom_prod;
    }

    public String getDesc_prod() {
        return desc_prod;
    }

    public void setDesc_prod(String desc_prod) {
        this.desc_prod = desc_prod;
    }

    public String getPrecio_compraprod() {
        return precio_compraprod;
    }

    public void setPrecio_compraprod(String precio_compraprod) {
        this.precio_compraprod = precio_compraprod;
    }

    public String getPrecio_ventaprod() {
        return precio_ventaprod;
    }

    public void setPrecio_ventaprod(String precio_ventaprod) {
        this.precio_ventaprod = precio_ventaprod;
    }

    public String getPrecio_venta_unidad() {
        return precio_venta_unidad;
    }

    public void setPrecio_venta_unidad(String precio_venta_unidad) {
        this.precio_venta_unidad = precio_venta_unidad;
    }

    public String getImage_prod() {
        return image_prod;
    }

    public void setImage_prod(String image_prod) {
        this.image_prod = image_prod;
    }

    public String getDisponibilidad_prod() {
        return disponibilidad_prod;
    }

    public void setDisponibilidad_prod(String disponibilidad_prod) {
        this.disponibilidad_prod = disponibilidad_prod;
    }

    public String getNomb_categoria() {
        return nomb_categoria;
    }

    public void setNomb_categoria(String nomb_categoria) {
        this.nomb_categoria = nomb_categoria;
    }

    public String getNom_medida() {
        return nom_medida;
    }

    public void setNom_medida(String nom_medida) {
        this.nom_medida = nom_medida;
    }

    public String getNombre_marca() {
        return nombre_marca;
    }

    public void setNombre_marca(String nombre_marca) {
        this.nombre_marca = nombre_marca;
    }

    public String getImagen_marca() {
        return imagen_marca;
    }

    public void setImagen_marca(String imagen_marca) {
        this.imagen_marca = imagen_marca;
    }

    public String getPrecio_descuento() {
        return precio_descuento;
    }

    public void setPrecio_descuento(String precio_descuento) {
        this.precio_descuento = precio_descuento;
    }

    public String getPrecio_unid() {
        return precio_unid;
    }

    public void setPrecio_unid(String precio_unid) {
        this.precio_unid = precio_unid;
    }

    public String getStock_actual() {
        return stock_actual;
    }

    public void setStock_actual(String stock_actual) {
        this.stock_actual = stock_actual;
    }

    public String getStock_minimo() {
        return stock_minimo;
    }

    public void setStock_minimo(String stock_minimo) {
        this.stock_minimo = stock_minimo;
    }

    public String getStock_maximo() {
        return stock_maximo;
    }

    public void setStock_maximo(String stock_maximo) {
        this.stock_maximo = stock_maximo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMinimum_quantity() {
        return minimum_quantity;
    }

    public void setMinimum_quantity(String minimum_quantity) {
        this.minimum_quantity = minimum_quantity;
    }

    public String getMaximum_quantity() {
        return maximum_quantity;
    }

    public void setMaximum_quantity(String maximum_quantity) {
        this.maximum_quantity = maximum_quantity;
    }

    @Override
    public String toString() {
        return "ProductoModel{" +
                "code_server='" + code_server + '\'' +
                ", res_server='" + res_server + '\'' +
                ", msg_server='" + msg_server + '\'' +
                ", idproducto='" + idproducto + '\'' +
                ", nom_prod='" + nom_prod + '\'' +
                ", desc_prod='" + desc_prod + '\'' +
                ", precio_compraprod='" + precio_compraprod + '\'' +
                ", precio_ventaprod='" + precio_ventaprod + '\'' +
                ", precio_venta_unidad='" + precio_venta_unidad + '\'' +
                ", image_prod='" + image_prod + '\'' +
                ", disponibilidad_prod='" + disponibilidad_prod + '\'' +
                ", nomb_categoria='" + nomb_categoria + '\'' +
                ", nom_medida='" + nom_medida + '\'' +
                ", nombre_marca='" + nombre_marca + '\'' +
                ", imagen_marca='" + imagen_marca + '\'' +
                ", precio_descuento='" + precio_descuento + '\'' +
                ", precio_unid='" + precio_unid + '\'' +
                ", stock_actual='" + stock_actual + '\'' +
                ", stock_minimo='" + stock_minimo + '\'' +
                ", stock_maximo='" + stock_maximo + '\'' +
                ", status='" + status + '\'' +
                ", minimum_quantity='" + minimum_quantity + '\'' +
                ", maximum_quantity='" + maximum_quantity + '\'' +
                '}';
    }

    public ProductoModel(String code_server, String res_server, String msg_server, String idproducto, String nom_prod, String desc_prod, String precio_compraprod, String precio_ventaprod, String precio_venta_unidad, String image_prod, String disponibilidad_prod, String nomb_categoria, String nom_medida, String nombre_marca, String imagen_marca, String precio_descuento, String precio_unid, String stock_actual, String stock_minimo, String stock_maximo, String status, String minimum_quantity, String maximum_quantity) {
        this.code_server = code_server;
        this.res_server = res_server;
        this.msg_server = msg_server;
        this.idproducto = idproducto;
        this.nom_prod = nom_prod;
        this.desc_prod = desc_prod;
        this.precio_compraprod = precio_compraprod;
        this.precio_ventaprod = precio_ventaprod;
        this.precio_venta_unidad = precio_venta_unidad;
        this.image_prod = image_prod;
        this.disponibilidad_prod = disponibilidad_prod;
        this.nomb_categoria = nomb_categoria;
        this.nom_medida = nom_medida;
        this.nombre_marca = nombre_marca;
        this.imagen_marca = imagen_marca;
        this.precio_descuento = precio_descuento;
        this.precio_unid = precio_unid;
        this.stock_actual = stock_actual;
        this.stock_minimo = stock_minimo;
        this.stock_maximo = stock_maximo;
        this.status = status;
        this.minimum_quantity = minimum_quantity;
        this.maximum_quantity = maximum_quantity;
    }
}
