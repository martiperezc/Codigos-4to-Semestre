package negocio.Motor;

public class MotorTrifasico extends Motor {
    private String tipoConexion;

    public MotorTrifasico(String codigo, String tipoMotor, double potenciaNominal, double corrienteNominal, String ubicacion, String tipoConexion) {
        super(codigo, tipoMotor, potenciaNominal, corrienteNominal, ubicacion);
        this.tipoConexion = tipoConexion;
    }

    // Getters y Setters
    public String getTipoConexion() { return tipoConexion; }
    public void setTipoConexion(String tipoConexion) { this.tipoConexion = tipoConexion; }
}