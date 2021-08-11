package com.altoque.delivery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NegocioModel {

    @SerializedName("code_server")
    @Expose
    private String codeServer;
    @SerializedName("res_server")
    @Expose
    private String resServer;
    @SerializedName("msg_server")
    @Expose
    private String msgServer;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("idnegocio")
    @Expose
    private String idnegocio;
    @SerializedName("rsocial_neg")
    @Expose
    private String rsocialNeg;
    @SerializedName("foto_neg")
    @Expose
    private String fotoNeg;
    @SerializedName("celular_neg")
    @Expose
    private String celularNeg;
    @SerializedName("correo_neg")
    @Expose
    private String correoNeg;
    @SerializedName("ainicio_neg")
    @Expose
    private String ainicioNeg;
    @SerializedName("acierre_neg")
    @Expose
    private String acierreNeg;
    @SerializedName("dir_fiscal_neg")
    @Expose
    private String dirFiscalNeg;
    @SerializedName("costo_envio")
    @Expose
    private String costoEnvio;
    @SerializedName("distancia_envio")
    @Expose
    private String distanciaEnvio;
    @SerializedName("banner_neg")
    @Expose
    private String bannerNeg;
    @SerializedName("nom_rubro")
    @Expose
    private String nomRubro;
    @SerializedName("foto_rubro")
    @Expose
    private String fotoRubro;
    @SerializedName("iddistrito")
    @Expose
    private String iddistrito;
    @SerializedName("nombre_distrito")
    @Expose
    private String nombreDistrito;
    @SerializedName("idprovincia")
    @Expose
    private String idprovincia;
    @SerializedName("iddepartamento")
    @Expose
    private String iddepartamento;
    @SerializedName("estado_distrito")
    @Expose
    private String estadoDistrito;
    @SerializedName("idcodigo_postal")
    @Expose
    private String idcodigoPostal;
    @SerializedName("habilitado_distrito")
    @Expose
    private String habilitadoDistrito;
    @SerializedName("nombre_prov")
    @Expose
    private String nombreProv;
    @SerializedName("estado_prov")
    @Expose
    private String estadoProv;
    @SerializedName("nombre_dep")
    @Expose
    private String nombreDep;
    @SerializedName("estado_dep")
    @Expose
    private String estadoDep;
    @SerializedName("idpais")
    @Expose
    private String idpais;
    @SerializedName("nombre_pais")
    @Expose
    private String nombrePais;
    @SerializedName("codigopais")
    @Expose
    private String codigopais;
    @SerializedName("codigotel")
    @Expose
    private String codigotel;
    @SerializedName("estado_pais")
    @Expose
    private String estadoPais;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("estimacion_demora")
    @Expose
    private String estimacionDemora;
    @SerializedName("calificadores")
    @Expose
    private String calificadores;

    /**
     * No args constructor for use in serialization
     *
     */
    public NegocioModel() {
    }

    /**
     *
     * @param calificadores
     * @param estadoPais
     * @param fotoRubro
     * @param costoEnvio
     * @param nombreDep
     * @param rating
     * @param estadoProv
     * @param acierreNeg
     * @param codeServer
     * @param idpais
     * @param nombreDistrito
     * @param fotoNeg
     * @param estimacionDemora
     * @param idcodigoPostal
     * @param nomRubro
     * @param resServer
     * @param iddistrito
     * @param idnegocio
     * @param rsocialNeg
     * @param nombreProv
     * @param ainicioNeg
     * @param celularNeg
     * @param idprovincia
     * @param estadoDistrito
     * @param bannerNeg
     * @param correoNeg
     * @param habilitadoDistrito
     * @param msgServer
     * @param codigotel
     * @param dirFiscalNeg
     * @param distanciaEnvio
     * @param iddepartamento
     * @param estadoDep
     * @param codigopais
     * @param status
     * @param nombrePais
     */
    public NegocioModel(String codeServer, String resServer, String msgServer, String status, String idnegocio, String rsocialNeg, String fotoNeg, String celularNeg, String correoNeg, String ainicioNeg, String acierreNeg, String dirFiscalNeg, String costoEnvio, String distanciaEnvio, String bannerNeg, String nomRubro, String fotoRubro, String iddistrito, String nombreDistrito, String idprovincia, String iddepartamento, String estadoDistrito, String idcodigoPostal, String habilitadoDistrito, String nombreProv, String estadoProv, String nombreDep, String estadoDep, String idpais, String nombrePais, String codigopais, String codigotel, String estadoPais, String rating, String estimacionDemora, String calificadores) {
        super();
        this.codeServer = codeServer;
        this.resServer = resServer;
        this.msgServer = msgServer;
        this.status = status;
        this.idnegocio = idnegocio;
        this.rsocialNeg = rsocialNeg;
        this.fotoNeg = fotoNeg;
        this.celularNeg = celularNeg;
        this.correoNeg = correoNeg;
        this.ainicioNeg = ainicioNeg;
        this.acierreNeg = acierreNeg;
        this.dirFiscalNeg = dirFiscalNeg;
        this.costoEnvio = costoEnvio;
        this.distanciaEnvio = distanciaEnvio;
        this.bannerNeg = bannerNeg;
        this.nomRubro = nomRubro;
        this.fotoRubro = fotoRubro;
        this.iddistrito = iddistrito;
        this.nombreDistrito = nombreDistrito;
        this.idprovincia = idprovincia;
        this.iddepartamento = iddepartamento;
        this.estadoDistrito = estadoDistrito;
        this.idcodigoPostal = idcodigoPostal;
        this.habilitadoDistrito = habilitadoDistrito;
        this.nombreProv = nombreProv;
        this.estadoProv = estadoProv;
        this.nombreDep = nombreDep;
        this.estadoDep = estadoDep;
        this.idpais = idpais;
        this.nombrePais = nombrePais;
        this.codigopais = codigopais;
        this.codigotel = codigotel;
        this.estadoPais = estadoPais;
        this.rating = rating;
        this.estimacionDemora = estimacionDemora;
        this.calificadores = calificadores;
    }

    public String getCodeServer() {
        return codeServer;
    }

    public void setCodeServer(String codeServer) {
        this.codeServer = codeServer;
    }

    public NegocioModel withCodeServer(String codeServer) {
        this.codeServer = codeServer;
        return this;
    }

    public String getResServer() {
        return resServer;
    }

    public void setResServer(String resServer) {
        this.resServer = resServer;
    }

    public NegocioModel withResServer(String resServer) {
        this.resServer = resServer;
        return this;
    }

    public String getMsgServer() {
        return msgServer;
    }

    public void setMsgServer(String msgServer) {
        this.msgServer = msgServer;
    }

    public NegocioModel withMsgServer(String msgServer) {
        this.msgServer = msgServer;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public NegocioModel withStatus(String status) {
        this.status = status;
        return this;
    }

    public String getIdnegocio() {
        return idnegocio;
    }

    public void setIdnegocio(String idnegocio) {
        this.idnegocio = idnegocio;
    }

    public NegocioModel withIdnegocio(String idnegocio) {
        this.idnegocio = idnegocio;
        return this;
    }

    public String getRsocialNeg() {
        return rsocialNeg;
    }

    public void setRsocialNeg(String rsocialNeg) {
        this.rsocialNeg = rsocialNeg;
    }

    public NegocioModel withRsocialNeg(String rsocialNeg) {
        this.rsocialNeg = rsocialNeg;
        return this;
    }

    public String getFotoNeg() {
        return fotoNeg;
    }

    public void setFotoNeg(String fotoNeg) {
        this.fotoNeg = fotoNeg;
    }

    public NegocioModel withFotoNeg(String fotoNeg) {
        this.fotoNeg = fotoNeg;
        return this;
    }

    public String getCelularNeg() {
        return celularNeg;
    }

    public void setCelularNeg(String celularNeg) {
        this.celularNeg = celularNeg;
    }

    public NegocioModel withCelularNeg(String celularNeg) {
        this.celularNeg = celularNeg;
        return this;
    }

    public String getCorreoNeg() {
        return correoNeg;
    }

    public void setCorreoNeg(String correoNeg) {
        this.correoNeg = correoNeg;
    }

    public NegocioModel withCorreoNeg(String correoNeg) {
        this.correoNeg = correoNeg;
        return this;
    }

    public String getAinicioNeg() {
        return ainicioNeg;
    }

    public void setAinicioNeg(String ainicioNeg) {
        this.ainicioNeg = ainicioNeg;
    }

    public NegocioModel withAinicioNeg(String ainicioNeg) {
        this.ainicioNeg = ainicioNeg;
        return this;
    }

    public String getAcierreNeg() {
        return acierreNeg;
    }

    public void setAcierreNeg(String acierreNeg) {
        this.acierreNeg = acierreNeg;
    }

    public NegocioModel withAcierreNeg(String acierreNeg) {
        this.acierreNeg = acierreNeg;
        return this;
    }

    public String getDirFiscalNeg() {
        return dirFiscalNeg;
    }

    public void setDirFiscalNeg(String dirFiscalNeg) {
        this.dirFiscalNeg = dirFiscalNeg;
    }

    public NegocioModel withDirFiscalNeg(String dirFiscalNeg) {
        this.dirFiscalNeg = dirFiscalNeg;
        return this;
    }

    public String getCostoEnvio() {
        return costoEnvio;
    }

    public void setCostoEnvio(String costoEnvio) {
        this.costoEnvio = costoEnvio;
    }

    public NegocioModel withCostoEnvio(String costoEnvio) {
        this.costoEnvio = costoEnvio;
        return this;
    }

    public String getDistanciaEnvio() {
        return distanciaEnvio;
    }

    public void setDistanciaEnvio(String distanciaEnvio) {
        this.distanciaEnvio = distanciaEnvio;
    }

    public NegocioModel withDistanciaEnvio(String distanciaEnvio) {
        this.distanciaEnvio = distanciaEnvio;
        return this;
    }

    public String getBannerNeg() {
        return bannerNeg;
    }

    public void setBannerNeg(String bannerNeg) {
        this.bannerNeg = bannerNeg;
    }

    public NegocioModel withBannerNeg(String bannerNeg) {
        this.bannerNeg = bannerNeg;
        return this;
    }

    public String getNomRubro() {
        return nomRubro;
    }

    public void setNomRubro(String nomRubro) {
        this.nomRubro = nomRubro;
    }

    public NegocioModel withNomRubro(String nomRubro) {
        this.nomRubro = nomRubro;
        return this;
    }

    public String getFotoRubro() {
        return fotoRubro;
    }

    public void setFotoRubro(String fotoRubro) {
        this.fotoRubro = fotoRubro;
    }

    public NegocioModel withFotoRubro(String fotoRubro) {
        this.fotoRubro = fotoRubro;
        return this;
    }

    public String getIddistrito() {
        return iddistrito;
    }

    public void setIddistrito(String iddistrito) {
        this.iddistrito = iddistrito;
    }

    public NegocioModel withIddistrito(String iddistrito) {
        this.iddistrito = iddistrito;
        return this;
    }

    public String getNombreDistrito() {
        return nombreDistrito;
    }

    public void setNombreDistrito(String nombreDistrito) {
        this.nombreDistrito = nombreDistrito;
    }

    public NegocioModel withNombreDistrito(String nombreDistrito) {
        this.nombreDistrito = nombreDistrito;
        return this;
    }

    public String getIdprovincia() {
        return idprovincia;
    }

    public void setIdprovincia(String idprovincia) {
        this.idprovincia = idprovincia;
    }

    public NegocioModel withIdprovincia(String idprovincia) {
        this.idprovincia = idprovincia;
        return this;
    }

    public String getIddepartamento() {
        return iddepartamento;
    }

    public void setIddepartamento(String iddepartamento) {
        this.iddepartamento = iddepartamento;
    }

    public NegocioModel withIddepartamento(String iddepartamento) {
        this.iddepartamento = iddepartamento;
        return this;
    }

    public String getEstadoDistrito() {
        return estadoDistrito;
    }

    public void setEstadoDistrito(String estadoDistrito) {
        this.estadoDistrito = estadoDistrito;
    }

    public NegocioModel withEstadoDistrito(String estadoDistrito) {
        this.estadoDistrito = estadoDistrito;
        return this;
    }

    public String getIdcodigoPostal() {
        return idcodigoPostal;
    }

    public void setIdcodigoPostal(String idcodigoPostal) {
        this.idcodigoPostal = idcodigoPostal;
    }

    public NegocioModel withIdcodigoPostal(String idcodigoPostal) {
        this.idcodigoPostal = idcodigoPostal;
        return this;
    }

    public String getHabilitadoDistrito() {
        return habilitadoDistrito;
    }

    public void setHabilitadoDistrito(String habilitadoDistrito) {
        this.habilitadoDistrito = habilitadoDistrito;
    }

    public NegocioModel withHabilitadoDistrito(String habilitadoDistrito) {
        this.habilitadoDistrito = habilitadoDistrito;
        return this;
    }

    public String getNombreProv() {
        return nombreProv;
    }

    public void setNombreProv(String nombreProv) {
        this.nombreProv = nombreProv;
    }

    public NegocioModel withNombreProv(String nombreProv) {
        this.nombreProv = nombreProv;
        return this;
    }

    public String getEstadoProv() {
        return estadoProv;
    }

    public void setEstadoProv(String estadoProv) {
        this.estadoProv = estadoProv;
    }

    public NegocioModel withEstadoProv(String estadoProv) {
        this.estadoProv = estadoProv;
        return this;
    }

    public String getNombreDep() {
        return nombreDep;
    }

    public void setNombreDep(String nombreDep) {
        this.nombreDep = nombreDep;
    }

    public NegocioModel withNombreDep(String nombreDep) {
        this.nombreDep = nombreDep;
        return this;
    }

    public String getEstadoDep() {
        return estadoDep;
    }

    public void setEstadoDep(String estadoDep) {
        this.estadoDep = estadoDep;
    }

    public NegocioModel withEstadoDep(String estadoDep) {
        this.estadoDep = estadoDep;
        return this;
    }

    public String getIdpais() {
        return idpais;
    }

    public void setIdpais(String idpais) {
        this.idpais = idpais;
    }

    public NegocioModel withIdpais(String idpais) {
        this.idpais = idpais;
        return this;
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }

    public NegocioModel withNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
        return this;
    }

    public String getCodigopais() {
        return codigopais;
    }

    public void setCodigopais(String codigopais) {
        this.codigopais = codigopais;
    }

    public NegocioModel withCodigopais(String codigopais) {
        this.codigopais = codigopais;
        return this;
    }

    public String getCodigotel() {
        return codigotel;
    }

    public void setCodigotel(String codigotel) {
        this.codigotel = codigotel;
    }

    public NegocioModel withCodigotel(String codigotel) {
        this.codigotel = codigotel;
        return this;
    }

    public String getEstadoPais() {
        return estadoPais;
    }

    public void setEstadoPais(String estadoPais) {
        this.estadoPais = estadoPais;
    }

    public NegocioModel withEstadoPais(String estadoPais) {
        this.estadoPais = estadoPais;
        return this;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public NegocioModel withRating(String rating) {
        this.rating = rating;
        return this;
    }

    public String getEstimacionDemora() {
        return estimacionDemora;
    }

    public void setEstimacionDemora(String estimacionDemora) {
        this.estimacionDemora = estimacionDemora;
    }

    public NegocioModel withEstimacionDemora(String estimacionDemora) {
        this.estimacionDemora = estimacionDemora;
        return this;
    }

    public String getCalificadores() {
        return calificadores;
    }

    public void setCalificadores(String calificadores) {
        this.calificadores = calificadores;
    }

    public NegocioModel withCalificadores(String calificadores) {
        this.calificadores = calificadores;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(NegocioModel.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("codeServer");
        sb.append('=');
        sb.append(((this.codeServer == null)?"<null>":this.codeServer));
        sb.append(',');
        sb.append("resServer");
        sb.append('=');
        sb.append(((this.resServer == null)?"<null>":this.resServer));
        sb.append(',');
        sb.append("msgServer");
        sb.append('=');
        sb.append(((this.msgServer == null)?"<null>":this.msgServer));
        sb.append(',');
        sb.append("status");
        sb.append('=');
        sb.append(((this.status == null)?"<null>":this.status));
        sb.append(',');
        sb.append("idnegocio");
        sb.append('=');
        sb.append(((this.idnegocio == null)?"<null>":this.idnegocio));
        sb.append(',');
        sb.append("rsocialNeg");
        sb.append('=');
        sb.append(((this.rsocialNeg == null)?"<null>":this.rsocialNeg));
        sb.append(',');
        sb.append("fotoNeg");
        sb.append('=');
        sb.append(((this.fotoNeg == null)?"<null>":this.fotoNeg));
        sb.append(',');
        sb.append("celularNeg");
        sb.append('=');
        sb.append(((this.celularNeg == null)?"<null>":this.celularNeg));
        sb.append(',');
        sb.append("correoNeg");
        sb.append('=');
        sb.append(((this.correoNeg == null)?"<null>":this.correoNeg));
        sb.append(',');
        sb.append("ainicioNeg");
        sb.append('=');
        sb.append(((this.ainicioNeg == null)?"<null>":this.ainicioNeg));
        sb.append(',');
        sb.append("acierreNeg");
        sb.append('=');
        sb.append(((this.acierreNeg == null)?"<null>":this.acierreNeg));
        sb.append(',');
        sb.append("dirFiscalNeg");
        sb.append('=');
        sb.append(((this.dirFiscalNeg == null)?"<null>":this.dirFiscalNeg));
        sb.append(',');
        sb.append("costoEnvio");
        sb.append('=');
        sb.append(((this.costoEnvio == null)?"<null>":this.costoEnvio));
        sb.append(',');
        sb.append("distanciaEnvio");
        sb.append('=');
        sb.append(((this.distanciaEnvio == null)?"<null>":this.distanciaEnvio));
        sb.append(',');
        sb.append("bannerNeg");
        sb.append('=');
        sb.append(((this.bannerNeg == null)?"<null>":this.bannerNeg));
        sb.append(',');
        sb.append("nomRubro");
        sb.append('=');
        sb.append(((this.nomRubro == null)?"<null>":this.nomRubro));
        sb.append(',');
        sb.append("fotoRubro");
        sb.append('=');
        sb.append(((this.fotoRubro == null)?"<null>":this.fotoRubro));
        sb.append(',');
        sb.append("iddistrito");
        sb.append('=');
        sb.append(((this.iddistrito == null)?"<null>":this.iddistrito));
        sb.append(',');
        sb.append("nombreDistrito");
        sb.append('=');
        sb.append(((this.nombreDistrito == null)?"<null>":this.nombreDistrito));
        sb.append(',');
        sb.append("idprovincia");
        sb.append('=');
        sb.append(((this.idprovincia == null)?"<null>":this.idprovincia));
        sb.append(',');
        sb.append("iddepartamento");
        sb.append('=');
        sb.append(((this.iddepartamento == null)?"<null>":this.iddepartamento));
        sb.append(',');
        sb.append("estadoDistrito");
        sb.append('=');
        sb.append(((this.estadoDistrito == null)?"<null>":this.estadoDistrito));
        sb.append(',');
        sb.append("idcodigoPostal");
        sb.append('=');
        sb.append(((this.idcodigoPostal == null)?"<null>":this.idcodigoPostal));
        sb.append(',');
        sb.append("habilitadoDistrito");
        sb.append('=');
        sb.append(((this.habilitadoDistrito == null)?"<null>":this.habilitadoDistrito));
        sb.append(',');
        sb.append("nombreProv");
        sb.append('=');
        sb.append(((this.nombreProv == null)?"<null>":this.nombreProv));
        sb.append(',');
        sb.append("estadoProv");
        sb.append('=');
        sb.append(((this.estadoProv == null)?"<null>":this.estadoProv));
        sb.append(',');
        sb.append("nombreDep");
        sb.append('=');
        sb.append(((this.nombreDep == null)?"<null>":this.nombreDep));
        sb.append(',');
        sb.append("estadoDep");
        sb.append('=');
        sb.append(((this.estadoDep == null)?"<null>":this.estadoDep));
        sb.append(',');
        sb.append("idpais");
        sb.append('=');
        sb.append(((this.idpais == null)?"<null>":this.idpais));
        sb.append(',');
        sb.append("nombrePais");
        sb.append('=');
        sb.append(((this.nombrePais == null)?"<null>":this.nombrePais));
        sb.append(',');
        sb.append("codigopais");
        sb.append('=');
        sb.append(((this.codigopais == null)?"<null>":this.codigopais));
        sb.append(',');
        sb.append("codigotel");
        sb.append('=');
        sb.append(((this.codigotel == null)?"<null>":this.codigotel));
        sb.append(',');
        sb.append("estadoPais");
        sb.append('=');
        sb.append(((this.estadoPais == null)?"<null>":this.estadoPais));
        sb.append(',');
        sb.append("rating");
        sb.append('=');
        sb.append(((this.rating == null)?"<null>":this.rating));
        sb.append(',');
        sb.append("estimacionDemora");
        sb.append('=');
        sb.append(((this.estimacionDemora == null)?"<null>":this.estimacionDemora));
        sb.append(',');
        sb.append("calificadores");
        sb.append('=');
        sb.append(((this.calificadores == null)?"<null>":this.calificadores));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }
}