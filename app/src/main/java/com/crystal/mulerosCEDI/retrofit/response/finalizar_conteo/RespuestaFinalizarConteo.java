package com.crystal.mulerosCEDI.retrofit.response.finalizar_conteo;

import com.crystal.mulerosCEDI.models.Error;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RespuestaFinalizarConteo {
    @SerializedName("Mensaje")
    @Expose
    private String mensaje;
    @SerializedName("Equipo")
    @Expose
    private String equipo;
    @SerializedName("Voz")
    @Expose
    private String voz;
    @SerializedName("Error")
    @Expose
    private Error error;

    public RespuestaFinalizarConteo(String mensaje, String equipo, String voz, Error error) {
        this.mensaje = mensaje;
        this.equipo = equipo;
        this.voz = voz;
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

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    @Override
    public String toString() {
        return "RespuestaFinalizarConteo{" +
                "mensaje='" + mensaje + '\'' +
                ", equipo='" + equipo + '\'' +
                ", voz='" + voz + '\'' +
                ", error=" + error +
                '}';
    }
}
