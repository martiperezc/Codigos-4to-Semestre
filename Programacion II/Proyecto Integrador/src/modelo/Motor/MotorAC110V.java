package modelo.Motor;

public class MotorAC110V extends Motor {

    private double frecuencia;
    private double factorPotencia;

    public MotorAC110V(String codigo, String tipoMotor, double potenciaNominal, double corrienteNominal, double voltajeNominal,
                       String ubicacion, double frecuencia, double factorPotencia) {
        super(codigo, tipoMotor, potenciaNominal, corrienteNominal, voltajeNominal, ubicacion);
        this.frecuencia = frecuencia;
        this.factorPotencia = factorPotencia;
    }

    public double getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(double frecuencia) {
        this.frecuencia = frecuencia;
    }

    public double getFactorPotencia() {
        return factorPotencia;
    }

    public void setFactorPotencia(double factorPotencia) {
        this.factorPotencia = factorPotencia;
    }


    @Override
    public String mostrarInfo() {
        return super.mostrarInfo() +
                ", Frecuencia: " + frecuencia +
                ", FactorPotencia: " + factorPotencia;
    }
}