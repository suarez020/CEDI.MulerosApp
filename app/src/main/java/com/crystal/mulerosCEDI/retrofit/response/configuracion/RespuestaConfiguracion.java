package com.crystal.mulerosCEDI.retrofit.response.configuracion;

import com.crystal.mulerosCEDI.models.Error;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RespuestaConfiguracion {
    @SerializedName("Mensaje")
    @Expose
    private String mensaje;
    @SerializedName("Voz")
    @Expose
    private String voz;
    @SerializedName("configuracion")
    @Expose
    private Configuracion configuracion;
    @SerializedName("Equipo")
    @Expose
    private String equipo;
    @SerializedName("Error")
    @Expose
    private Error error;

    public RespuestaConfiguracion(String mensaje, String voz, Configuracion configuracion, String equipo, Error error) {
        this.mensaje = mensaje;
        this.voz = voz;
        this.configuracion = configuracion;
        this.equipo = equipo;
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    public String getVoz() {
        return voz;
    }
    public void setVoz(String voz) {
        this.voz = voz;
    }
    public Configuracion getConfiguracion() {
        return configuracion;
    }
    public void setConfiguracion(Configuracion configuracion) {
        this.configuracion = configuracion;
    }
    public String getEquipo() {
        return equipo;
    }
    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }
    public Error getError() {
        return error;
    }
    public void setError(Error error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "RespuestaConfiguracion{" +
                "mensaje='" + mensaje + '\'' +
                ", voz='" + voz + '\'' +
                ", configuracion=" + configuracion +
                ", equipo='" + equipo + '\'' +
                ", error=" + error +
                '}';
    }
}

