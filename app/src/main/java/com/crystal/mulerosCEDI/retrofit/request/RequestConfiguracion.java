package com.crystal.mulerosCEDI.retrofit.request;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class RequestConfiguracion {
    @SerializedName("MAC")
    @Expose
    private String mac;
    @SerializedName("Equipo")
    @Expose
    private String equipo;

    public RequestConfiguracion(String mac, String equipo) {
        this.mac = mac;
        this.equipo = equipo;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    @Override
    public String toString() {
        return "RequestConfiguracion{" +
                "mac='" + mac + '\'' +
                ", equipo='" + equipo + '\'' +
                '}';
    }
}

