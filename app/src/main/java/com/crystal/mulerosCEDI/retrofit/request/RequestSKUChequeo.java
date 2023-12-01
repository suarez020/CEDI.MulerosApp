package com.crystal.mulerosCEDI.retrofit.request;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class RequestSKUChequeo {
    @SerializedName("Cedula")
    @Expose
    private String cedula;
    @SerializedName("Equipo")
    @Expose
    private String equipo;
    @SerializedName("SKU")
    @Expose
    private String sku;

    public RequestSKUChequeo(String cedula, String equipo, String sku) {
        this.cedula = cedula;
        this.equipo = equipo;
        this.sku = sku;
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

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Override
    public String toString() {
        return "RequestSKUChequeo{" +
                "cedula='" + cedula + '\'' +
                ", equipo='" + equipo + '\'' +
                ", sku='" + sku + '\'' +
                '}';
    }
}
