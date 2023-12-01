package com.crystal.mulerosCEDI.retrofit.response.inicio;

import com.crystal.mulerosCEDI.models.Error;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RespuestaInicio{
    @SerializedName("Matriculado")
    @Expose
    private Matriculado matriculado;

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

    public RespuestaInicio(Matriculado matriculado, String mensaje, String equipo, String voz, Error error) {
        this.matriculado = matriculado;
        this.mensaje = mensaje;
        this.equipo = equipo;
        this.voz = voz;
        this.error = error;
    }

    public Matriculado getMatriculado() {
        return matriculado;
    }

    public void setMatriculado(Matriculado matriculado) {
        this.matriculado = matriculado;
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

    @Override
    public String toString() {
        return "RespuestaInicio{" +
                "matriculado=" + matriculado +
                ", mensaje='" + mensaje + '\'' +
                ", equipo='" + equipo + '\'' +
                ", voz='" + voz + '\'' +
                ", error=" + error +
                '}';
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }
}
