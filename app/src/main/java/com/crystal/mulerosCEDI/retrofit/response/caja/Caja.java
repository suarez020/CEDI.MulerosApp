package com.crystal.mulerosCEDI.retrofit.response.caja;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Caja {

    @SerializedName("SKU")
    @Expose
    private String sku;
    @SerializedName("Cantidad")
    @Expose
    private String cantidad;

    public Caja(String sku, String cantidad) {
        this.sku = sku;
        this.cantidad = cantidad;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Caja{" +
                "sku='" + sku + '\'' +
                ", cantidad='" + cantidad + '\'' +
                '}';
    }
}
