package com.crystal.mulerosCEDI.retrofit.request;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class RequestCaja {

    @SerializedName("Cedula")
    @Expose
    private String cedula;
    @SerializedName("Equipo")
    @Expose
    private String equipo;
    @SerializedName("Caja")
    @Expose
    private String caja;

    public RequestCaja(String cedula, String equipo, String caja, String proceso) {
        this.cedula = cedula;
        this.equipo = equipo;
        this.caja = caja;
        this.proceso = proceso;
    }

    @SerializedName("Proceso")
    @Expose
    private String proceso;

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

    public String getCaja() {
        return caja;
    }

    public void setCaja(String caja) {
        this.caja = caja;
    }

    @Override
    public String toString() {
        return "RequestCaja{" +
                "cedula='" + cedula + '\'' +
                ", equipo='" + equipo + '\'' +
                ", caja='" + caja + '\'' +
                ", proceso='" + proceso + '\'' +
                '}';
    }
}
