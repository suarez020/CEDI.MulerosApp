package com.crystal.mulerosCEDI.retrofit.response.logout;
import com.crystal.mulerosCEDI.models.Error;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class RespuestaLogout{
    @SerializedName("logout")
    @Expose
    private Logout logout;

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

    public RespuestaLogout(Logout logout, String mensaje, String equipo, String voz, Error error) {
        this.logout = logout;
        this.mensaje = mensaje;
        this.equipo = equipo;
        this.voz = voz;
        this.error = error;
    }


    public Logout getLogout() {
        return logout;
    }

    public void setLogout(Logout logout) {
        this.logout = logout;
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
        return "RespuestaLogout{" +
                "logout=" + logout +
                ", mensaje='" + mensaje + '\'' +
                ", equipo='" + equipo + '\'' +
                ", voz='" + voz + '\'' +
                ", error=" + error +
                '}';
    }
}
