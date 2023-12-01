package com.crystal.mulerosCEDI.retrofit.request;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class RequestFinalizarConteo {
    @SerializedName("Cedula")
    @Expose
    private String cedula;
    @SerializedName("Equipo")
    @Expose
    private String equipo;

    public RequestFinalizarConteo(String cedula, String equipo) {
        this.cedula = cedula;
        this.equipo = equipo;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    @Override
    public String toString() {
        return "RequestFinalizarConteo{" +
                "cedula='" + cedula + '\'' +
                ", equipo='" + equipo + '\'' +
                '}';
    }
}
