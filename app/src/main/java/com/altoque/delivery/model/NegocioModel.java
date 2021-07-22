package com.altoque.delivery.model;

import com.google.gson.annotations.SerializedName;

public class NegocioModel {
    @SerializedName("code_server")
    String code_server;
    @SerializedName("res_server")
    String res_server;
    @SerializedName("msg_server")
    String msg_server;
    @SerializedName("status")
    private String status;
    @SerializedName("idnegocio")
    private String idnegocio;
    @SerializedName("rsocial_neg")
    private String rsocial_neg;
    @SerializedName("ruc_neg")
    private String ruc_neg;
    @SerializedName("celular_neg")
    private String celular_neg;
    @SerializedName("correo_neg")
    private String correo_neg;
    @SerializedName("clave_neg")
    private String clave_neg;
    @SerializedName("foto_neg")
    private String foto_neg;
    @SerializedName("ainicio_neg")
    private String ainicio_neg;
    @SerializedName("acierre_neg")
    private String acierre_neg;
    @SerializedName("activo_neg")
    private String activo_neg;
    @SerializedName("online_neg")
    private String online_neg;
    @SerializedName("estado_neg")
    private String estado_neg;
    @SerializedName("dir_fiscal_neg")
    private String dir_fiscal_neg;
    @SerializedName("lat_neg")
    private String lat_neg;
    @SerializedName("long_neg")
    private String long_neg;
    @SerializedName("isban")
    private String isban;
    @SerializedName("idrubro")
    private String idrubro;
    @SerializedName("idtarifario")
    private String idtarifario;
    @SerializedName("iddistrito")
    private String iddistrito;
    @SerializedName("nom_rubro")
    private String nom_rubro;
    @SerializedName("foto_rubro")
    private String foto_rubro;
    @SerializedName("estado_rubro")
    private String estado_rubro;
    @SerializedName("costo_envio")
    private String costo_envio;
    @SerializedName("distancia_envio")
    private String distancia_envio;
    @SerializedName("estado_envio")
    private String estado_envio;
    @SerializedName("nombre_distrito")
    private String nombre_distrito;
    @SerializedName("idprovincia")
    private String idprovincia;
    @SerializedName("iddepartamento")
    private String iddepartamento;
    @SerializedName("estado_distrito")
    private String estado_distrito;
    @SerializedName("nombre_prov")
    private String nombre_prov;
    @SerializedName("estado_prov")
    private String estado_prov;
    @SerializedName("nombre_dep")
    private String nombre_dep;
    @SerializedName("estado_dep")
    private String estado_dep;
    @SerializedName("idpais")
    private String idpais;
    @SerializedName("nombre_pais")
    private String nombre_pais;
    @SerializedName("codigopais")
    private String codigopais;
    @SerializedName("codigotel")
    private String codigotel;
    @SerializedName("estado_pais")
    private String estado_pais;
    @SerializedName("rating")
    private String rating;

    // Getter Methods


    public NegocioModel() {
    }

    @Override
    public String toString() {
        return "NegocioModel{" +
                "code_server='" + code_server + '\'' +
                ", res_server='" + res_server + '\'' +
                ", msg_server='" + msg_server + '\'' +
                ", status='" + status + '\'' +
                ", idnegocio='" + idnegocio + '\'' +
                ", rsocial_neg='" + rsocial_neg + '\'' +
                ", ruc_neg='" + ruc_neg + '\'' +
                ", celular_neg='" + celular_neg + '\'' +
                ", correo_neg='" + correo_neg + '\'' +
                ", clave_neg='" + clave_neg + '\'' +
                ", foto_neg='" + foto_neg + '\'' +
                ", ainicio_neg='" + ainicio_neg + '\'' +
                ", acierre_neg='" + acierre_neg + '\'' +
                ", activo_neg='" + activo_neg + '\'' +
                ", online_neg='" + online_neg + '\'' +
                ", estado_neg='" + estado_neg + '\'' +
                ", dir_fiscal_neg='" + dir_fiscal_neg + '\'' +
                ", lat_neg='" + lat_neg + '\'' +
                ", long_neg='" + long_neg + '\'' +
                ", isban='" + isban + '\'' +
                ", idrubro='" + idrubro + '\'' +
                ", idtarifario='" + idtarifario + '\'' +
                ", iddistrito='" + iddistrito + '\'' +
                ", nom_rubro='" + nom_rubro + '\'' +
                ", foto_rubro='" + foto_rubro + '\'' +
                ", estado_rubro='" + estado_rubro + '\'' +
                ", costo_envio='" + costo_envio + '\'' +
                ", distancia_envio='" + distancia_envio + '\'' +
                ", estado_envio='" + estado_envio + '\'' +
                ", nombre_distrito='" + nombre_distrito + '\'' +
                ", idprovincia='" + idprovincia + '\'' +
                ", iddepartamento='" + iddepartamento + '\'' +
                ", estado_distrito='" + estado_distrito + '\'' +
                ", nombre_prov='" + nombre_prov + '\'' +
                ", estado_prov='" + estado_prov + '\'' +
                ", nombre_dep='" + nombre_dep + '\'' +
                ", estado_dep='" + estado_dep + '\'' +
                ", idpais='" + idpais + '\'' +
                ", nombre_pais='" + nombre_pais + '\'' +
                ", codigopais='" + codigopais + '\'' +
                ", codigotel='" + codigotel + '\'' +
                ", estado_pais='" + estado_pais + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }


    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public NegocioModel(String code_server, String res_server, String msg_server, String status, String idnegocio, String rsocial_neg, String ruc_neg, String celular_neg, String correo_neg, String clave_neg, String foto_neg, String ainicio_neg, String acierre_neg, String activo_neg, String online_neg, String estado_neg, String dir_fiscal_neg, String lat_neg, String long_neg, String isban, String idrubro, String idtarifario, String iddistrito, String nom_rubro, String foto_rubro, String estado_rubro, String costo_envio, String distancia_envio, String estado_envio, String nombre_distrito, String idprovincia, String iddepartamento, String estado_distrito, String nombre_prov, String estado_prov, String nombre_dep, String estado_dep, String idpais, String nombre_pais, String codigopais, String codigotel, String estado_pais, String rating) {
        this.code_server = code_server;
       this.res_server = res_server;
        this.msg_server = msg_server;
        this.status = status;
        this.idnegocio = idnegocio;
        this.rsocial_neg = rsocial_neg;
        this.ruc_neg = ruc_neg;
        this.celular_neg = celular_neg;
        this.correo_neg = correo_neg;
        this.clave_neg = clave_neg;
        this.foto_neg = foto_neg;
        this.ainicio_neg = ainicio_neg;
        this.acierre_neg = acierre_neg;
        this.activo_neg = activo_neg;
        this.online_neg = online_neg;
        this.estado_neg = estado_neg;
        this.dir_fiscal_neg = dir_fiscal_neg;
        this.lat_neg = lat_neg;
        this.long_neg = long_neg;
        this.isban = isban;
        this.idrubro = idrubro;
        this.idtarifario = idtarifario;
        this.iddistrito = iddistrito;
        this.nom_rubro = nom_rubro;
        this.foto_rubro = foto_rubro;
        this.estado_rubro = estado_rubro;
        this.costo_envio = costo_envio;
        this.distancia_envio = distancia_envio;
        this.estado_envio = estado_envio;
        this.nombre_distrito = nombre_distrito;
        this.idprovincia = idprovincia;
        this.iddepartamento = iddepartamento;
        this.estado_distrito = estado_distrito;
        this.nombre_prov = nombre_prov;
        this.estado_prov = estado_prov;
        this.nombre_dep = nombre_dep;
        this.estado_dep = estado_dep;
        this.idpais = idpais;
        this.nombre_pais = nombre_pais;
        this.codigopais = codigopais;
        this.codigotel = codigotel;
        this.estado_pais = estado_pais;
        this.rating = rating;
    }

    public String getCode_server() {
        return code_server;
    }

    public String getRes_server() {
        return res_server;
    }

    public String getMsg_server() {
        return msg_server;
    }

    public String getStatus() {
        return status;
    }

    public String getIdnegocio() {
        return idnegocio;
    }

    public String getRsocial_neg() {
        return rsocial_neg;
    }

    public String getRuc_neg() {
        return ruc_neg;
    }

    public String getCelular_neg() {
        return celular_neg;
    }

    public String getCorreo_neg() {
        return correo_neg;
    }

    public String getClave_neg() {
        return clave_neg;
    }

    public String getFoto_neg() {
        return foto_neg;
    }

    public String getAinicio_neg() {
        return ainicio_neg;
    }

    public String getAcierre_neg() {
        return acierre_neg;
    }

    public String getActivo_neg() {
        return activo_neg;
    }

    public String getOnline_neg() {
        return online_neg;
    }

    public String getEstado_neg() {
        return estado_neg;
    }

    public String getDir_fiscal_neg() {
        return dir_fiscal_neg;
    }

    public String getLat_neg() {
        return lat_neg;
    }

    public String getLong_neg() {
        return long_neg;
    }

    public String getIsban() {
        return isban;
    }

    public String getIdrubro() {
        return idrubro;
    }

    public String getIdtarifario() {
        return idtarifario;
    }

    public String getIddistrito() {
        return iddistrito;
    }

    public String getNom_rubro() {
        return nom_rubro;
    }

    public String getFoto_rubro() {
        return foto_rubro;
    }

    public String getEstado_rubro() {
        return estado_rubro;
    }

    public String getCosto_envio() {
        return costo_envio;
    }

    public String getDistancia_envio() {
        return distancia_envio;
    }

    public String getEstado_envio() {
        return estado_envio;
    }

    public String getNombre_distrito() {
        return nombre_distrito;
    }

    public String getIdprovincia() {
        return idprovincia;
    }

    public String getIddepartamento() {
        return iddepartamento;
    }

    public String getEstado_distrito() {
        return estado_distrito;
    }

    public String getNombre_prov() {
        return nombre_prov;
    }

    public String getEstado_prov() {
        return estado_prov;
    }

    public String getNombre_dep() {
        return nombre_dep;
    }

    public String getEstado_dep() {
        return estado_dep;
    }

    public String getIdpais() {
        return idpais;
    }

    public String getNombre_pais() {
        return nombre_pais;
    }

    public String getCodigopais() {
        return codigopais;
    }

    public String getCodigotel() {
        return codigotel;
    }

    public String getEstado_pais() {
        return estado_pais;
    }

    // Setter Methods

    public void setCode_server(String code_server) {
        this.code_server = code_server;
    }

    public void setRes_server(String res_server) {
        this.res_server = res_server;
    }

    public void setMsg_server(String msg_server) {
        this.msg_server = msg_server;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setIdnegocio(String idnegocio) {
        this.idnegocio = idnegocio;
    }

    public void setRsocial_neg(String rsocial_neg) {
        this.rsocial_neg = rsocial_neg;
    }

    public void setRuc_neg(String ruc_neg) {
        this.ruc_neg = ruc_neg;
    }

    public void setCelular_neg(String celular_neg) {
        this.celular_neg = celular_neg;
    }

    public void setCorreo_neg(String correo_neg) {
        this.correo_neg = correo_neg;
    }

    public void setClave_neg(String clave_neg) {
        this.clave_neg = clave_neg;
    }

    public void setFoto_neg(String foto_neg) {
        this.foto_neg = foto_neg;
    }

    public void setAinicio_neg(String ainicio_neg) {
        this.ainicio_neg = ainicio_neg;
    }

    public void setAcierre_neg(String acierre_neg) {
        this.acierre_neg = acierre_neg;
    }

    public void setActivo_neg(String activo_neg) {
        this.activo_neg = activo_neg;
    }

    public void setOnline_neg(String online_neg) {
        this.online_neg = online_neg;
    }

    public void setEstado_neg(String estado_neg) {
        this.estado_neg = estado_neg;
    }

    public void setDir_fiscal_neg(String dir_fiscal_neg) {
        this.dir_fiscal_neg = dir_fiscal_neg;
    }

    public void setLat_neg(String lat_neg) {
        this.lat_neg = lat_neg;
    }

    public void setLong_neg(String long_neg) {
        this.long_neg = long_neg;
    }

    public void setIsban(String isban) {
        this.isban = isban;
    }

    public void setIdrubro(String idrubro) {
        this.idrubro = idrubro;
    }

    public void setIdtarifario(String idtarifario) {
        this.idtarifario = idtarifario;
    }

    public void setIddistrito(String iddistrito) {
        this.iddistrito = iddistrito;
    }

    public void setNom_rubro(String nom_rubro) {
        this.nom_rubro = nom_rubro;
    }

    public void setFoto_rubro(String foto_rubro) {
        this.foto_rubro = foto_rubro;
    }

    public void setEstado_rubro(String estado_rubro) {
        this.estado_rubro = estado_rubro;
    }

    public void setCosto_envio(String costo_envio) {
        this.costo_envio = costo_envio;
    }

    public void setDistancia_envio(String distancia_envio) {
        this.distancia_envio = distancia_envio;
    }

    public void setEstado_envio(String estado_envio) {
        this.estado_envio = estado_envio;
    }

    public void setNombre_distrito(String nombre_distrito) {
        this.nombre_distrito = nombre_distrito;
    }

    public void setIdprovincia(String idprovincia) {
        this.idprovincia = idprovincia;
    }

    public void setIddepartamento(String iddepartamento) {
        this.iddepartamento = iddepartamento;
    }

    public void setEstado_distrito(String estado_distrito) {
        this.estado_distrito = estado_distrito;
    }

    public void setNombre_prov(String nombre_prov) {
        this.nombre_prov = nombre_prov;
    }

    public void setEstado_prov(String estado_prov) {
        this.estado_prov = estado_prov;
    }

    public void setNombre_dep(String nombre_dep) {
        this.nombre_dep = nombre_dep;
    }

    public void setEstado_dep(String estado_dep) {
        this.estado_dep = estado_dep;
    }

    public void setIdpais(String idpais) {
        this.idpais = idpais;
    }

    public void setNombre_pais(String nombre_pais) {
        this.nombre_pais = nombre_pais;
    }

    public void setCodigopais(String codigopais) {
        this.codigopais = codigopais;
    }

    public void setCodigotel(String codigotel) {
        this.codigotel = codigotel;
    }

    public void setEstado_pais(String estado_pais) {
        this.estado_pais = estado_pais;
    }
}