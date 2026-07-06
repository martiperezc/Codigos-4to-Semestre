package modelo.activos;

public class MotorDC extends Motor {
    private boolean tieneEscobillas;

    public MotorDC(String codigo, double potenciaNominal, double corrienteNominal, double voltajeNominal, String ubicacion, boolean tieneEscobillas) {
        super(codigo, potenciaNominal, corrienteNominal, voltajeNominal, ubicacion);
        this.tieneEscobillas = tieneEscobillas;
    }

    @Override
    public String obtenerDetallesEspecificos() {
        return "Tiene Escobillas: " + (tieneEscobillas ? "Sí" : "No");
    }

    @Override
    public String getTipoTexto() { return "DC"; }
}