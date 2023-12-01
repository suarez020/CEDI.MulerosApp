package com.crystal.mulerosCEDI.retrofit.response.lectura_sku;
import com.crystal.mulerosCEDI.models.Error;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class RespuestaLecturaSKU {
    @SerializedName("Mensaje")
    @Expose
    private String mensaje;
    @SerializedName("Equipo")
    @Expose
    private String equipo;
    @SerializedName("Voz")
    @Expose
    private String voz;
    @SerializedName("Caja")
    @Expose
    private String caja;
    @SerializedName("Error")
    @Expose
    private Error error;

    public RespuestaLecturaSKU(String mensaje, String equipo, String voz, String caja, Error error) {
        this.mensaje = mensaje;
        this.equipo = equipo;
        this.voz = voz;
        this.caja = caja;
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getVoz() {
        return voz;
    }

    public String getCaja() {
        return caja;
    }

    public Error getError() {
        return error;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setVoz(String voz) {
        this.voz = voz;
    }

    public void setCaja(String caja) {
        this.caja = caja;
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
        return "RespuestaLecturaSKU{" +
                "mensaje='" + mensaje + '\'' +
                ", equipo='" + equipo + '\'' +
                ", voz='" + voz + '\'' +
                ", caja='" + caja + '\'' +
                ", error=" + error +
                '}';
    }
}
