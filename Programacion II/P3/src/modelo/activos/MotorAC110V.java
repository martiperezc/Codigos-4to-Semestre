package modelo.activos;

public class MotorAC110V extends Motor {
    private double frecuencia;
    private double factorPotencia;

    public MotorAC110V(String codigo, double potenciaNominal, double corrienteNominal, double voltajeNominal, String ubicacion, double frecuencia, double factorPotencia) {
        super(codigo, potenciaNominal, corrienteNominal, voltajeNominal, ubicacion);
        this.frecuencia = frecuencia;
        this.factorPotencia = factorPotencia;
    }

    @Override
    public String obtenerDetallesEspecificos() {
        return "Frecuencia: " + frecuencia + " Hz | FP: " + factorPotencia;
    }

    @Override
    public String getTipoTexto() { return "AC 110V"; }
}