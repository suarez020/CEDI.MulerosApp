package com.crystal.mulerosCEDI.retrofit.response;

import com.crystal.mulerosCEDI.models.Error;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RespuestaBase {
    @SerializedName("Mensaje")
    @Expose
    private String mensaje;
    @SerializedName("Voz")
    @Expose
    private String voz;
    @SerializedName("Equipo")
    @Expose
    private String equipo;
    @SerializedName("Error")
    @Expose
    private Error error;

    public RespuestaBase(String mensaje, String voz, String equipo, Error error) {
        this.mensaje = mensaje;
        this.voz = voz;
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
        return "RespuestaBase{" +
                "mensaje='" + mensaje + '\'' +
                ", voz='" + voz + '\'' +
                ", equipo='" + equipo + '\'' +
                ", error=" + error +
                '}';
    }
}
