package com.crystal.mulerosCEDI.retrofit.response.lectura_sku;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Caja {
    @SerializedName("CantidadLeida")
    @Expose
    private String cantidadLeida;

    public Caja(String cantidadLeida) {this.cantidadLeida = cantidadLeida;}

    public String getCantidadLeida() {return cantidadLeida;}

    public void setCantidadLeida(String cantidadLeida) {
        this.cantidadLeida = cantidadLeida;
    }
    @Override
    public String toString() {
        return "Caja{" +
                "cantidadLeida='" + cantidadLeida + '\'' +
                '}';
    }
}
