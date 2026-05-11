package Ejer2.SistemaSeguridad;

public class SistemaSeguridad {
    private String nombreSistema;
    private int intentosFallidos;

    public SistemaSeguridad(String nombreSistema) {
        this.nombreSistema = nombreSistema;
        this.intentosFallidos = 0;
    }

    public String getNombreSistema() {
        return nombreSistema;
    }

    public void setNombreSistema(String nombreSistema) {
        this.nombreSistema = nombreSistema;
    }

    public int getIntentosFallidos() {
        return intentosFallidos;
    }

    // Encapsulamiento: se omite el método setIntentosFallidos para evitar alteraciones arbitrarias
    public void registrarIntentoFallido() {
        this.intentosFallidos++;
    }

    public void reiniciarContador() {
        this.intentosFallidos = 0;
    }

    public void mostrarEstado() {
        boolean bloqueado = intentosFallidos >= 3;
        String estado = bloqueado ? "bloqueado" : "operativo";
        System.out.println("El sistema " + nombreSistema + " registra " + intentosFallidos + " intentos fallidos y actualmente está " + estado + ".");
    }
}
