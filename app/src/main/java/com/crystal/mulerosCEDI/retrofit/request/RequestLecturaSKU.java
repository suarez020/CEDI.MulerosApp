package com.crystal.mulerosCEDI.retrofit.request;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class RequestLecturaSKU {
    @SerializedName("Cedula")
    @Expose
    private String cedula;

    @SerializedName("Equipo")
    @Expose
    private String equipo;
    @SerializedName("SKU")
    @Expose
    private String sku;

    public RequestLecturaSKU(String cedula, String equipo, String sku) {
        this.cedula = cedula;
        this.equipo = equipo;
        this.sku = sku;
    }

    @Override
    public String toString() {
        return "RequestLecturaSKU{" +
                "cedula='" + cedula + '\'' +
                ", equipo='" + equipo + '\'' +
                ", sku='" + sku + '\'' +
                '}';
    }

    public String getCedula() {
        return cedula;
    }

    public String getEquipo() {
        return equipo;
    }

    public String getSku() {
        return sku;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}
