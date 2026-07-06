package modelo.activos;

import modelo.historial.HistorialMotor;

public abstract class Motor {
    private String codigo;
    private double potenciaNominal;
    private double corrienteNominal;
    private double voltajeNominal;
    private String ubicacion;
    private HistorialMotor historial;

    public Motor(String codigo, double potenciaNominal, double corrienteNominal, double voltajeNominal, String ubicacion) {
        this.codigo = codigo;
        this.potenciaNominal = potenciaNominal;
        this.corrienteNominal = corrienteNominal;
        this.voltajeNominal = voltajeNominal;
        this.ubicacion = ubicacion;
        this.historial = new HistorialMotor(this);
    }

    public String getCodigo() { return codigo; }
    public double getPotenciaNominal() { return potenciaNominal; }
    public double getCorrienteNominal() { return corrienteNominal; }
    public double getVoltajeNominal() { return voltajeNominal; }
    public String getUbicacion() { return ubicacion; }
    public HistorialMotor getHistorial() { return historial; }

    public abstract String obtenerDetallesEspecificos();
    public abstract String getTipoTexto();

    public String mostrarInfo() {
        return "Código: " + codigo + " | Tipo: " + getTipoTexto() + " | Ubicación: " + ubicacion + " | Potencia: " + potenciaNominal +
                " kW | Corriente Nom: " + corrienteNominal + " A | Voltaje Nom: " + voltajeNominal + " V" +
                " | " + obtenerDetallesEspecificos();
    }
}