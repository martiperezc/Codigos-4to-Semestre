package modelo.activos;

public class MotorTrifasico extends Motor {
    private String tipoConexion;

    public MotorTrifasico(String codigo, double potenciaNominal, double corrienteNominal, double voltajeNominal, String ubicacion, String tipoConexion) {
        super(codigo, potenciaNominal, corrienteNominal, voltajeNominal, ubicacion);
        this.tipoConexion = tipoConexion;
    }

    @Override
    public String obtenerDetallesEspecificos() {
        return "Conexión: " + tipoConexion;
    }

    @Override
    public String getTipoTexto() { return "Trifásico"; }
}