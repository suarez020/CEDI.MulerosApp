package com.crystal.mulerosCEDI.retrofit.response.caja;

import com.crystal.mulerosCEDI.models.Error;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RespuestaCaja {
    @SerializedName("Mensaje")
    @Expose
    private String mensaje;
    @SerializedName("Voz")
    @Expose
    private String voz;
    @SerializedName("Caja")
    @Expose
    private Caja caja;
    @SerializedName("Equipo")
    @Expose
    private String equipo;
    @SerializedName("Error")
    @Expose
    private Error error;

    public RespuestaCaja(String mensaje, String voz, Caja caja, String equipo, Error error) {
        this.mensaje = mensaje;
        this.voz = voz;
        this.caja = caja;
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

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
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
        return "RespuestaCaja{" +
                "mensaje='" + mensaje + '\'' +
                ", voz='" + voz + '\'' +
                ", caja=" + caja +
                ", equipo='" + equipo + '\'' +
                ", error=" + error +
                '}';
    }
}
