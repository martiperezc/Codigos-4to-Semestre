package modelo.Motor;

public class MotorTrifasico extends Motor {

    private String tipoConexion;


    public MotorTrifasico(String codigo, String tipoMotor, double potenciaNominal, double corrienteNominal,double voltajeNominal,
                          String ubicacion, String tipoConexion) {
        super(codigo, tipoMotor, potenciaNominal, corrienteNominal, voltajeNominal, ubicacion);
        this.tipoConexion = tipoConexion;
    }

    public String getTipoConexion() {
        return tipoConexion;
    }

    public void setTipoConexion(String conexion) {
        this.tipoConexion = conexion;
    }


    @Override
    public String mostrarInfo() {
        return super.mostrarInfo() +
                ", TipoConexion: " + tipoConexion;
    }
}