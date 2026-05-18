package negocio.Motor;

public class MotoAC110V extends Motor {
    private double frecuencia;
    private double factorPotencia;

    public MotoAC110V(String codigo, String tipoMotor, double potenciaNominal, double corrienteNominal, String ubicacion, double frecuencia, double factorPotencia) {
        super(codigo, tipoMotor, potenciaNominal, corrienteNominal, ubicacion);
        this.frecuencia = frecuencia;
        this.factorPotencia = factorPotencia;
    }

    // Getters y Setters
    public double getFrecuencia() { return frecuencia; }
    public void setFrecuencia(double frecuencia) { this.frecuencia = frecuencia; }

    public double getFactorPotencia() { return factorPotencia; }
    public void setFactorPotencia(double factorPotencia) { this.factorPotencia = factorPotencia; }
}