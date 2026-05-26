package modelo.Motor;

public class Motor {
    private String codigo;
    private String tipoMotor;
    private double potenciaNominal;
    private double corrienteNominal;
    private double voltajeNominal;
    private String ubicacion;

    // Constructor
    public Motor(String codigo, String tipoMotor, double potenciaNominal, double corrienteNominal, double voltajeNominal, String ubicacion) {
        this.codigo = codigo;
        this.tipoMotor = tipoMotor;
        this.potenciaNominal = potenciaNominal;
        this.corrienteNominal = corrienteNominal;
        this.voltajeNominal= voltajeNominal;
        this.ubicacion = ubicacion;
    }

    // Getters y Setters
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getTipoMotor() { return tipoMotor; }
    public void setTipoMotor(String tipoMotor) { this.tipoMotor = tipoMotor; }

    public double getPotenciaNominal() { return potenciaNominal; }
    public void setPotenciaNominal(double potenciaNominal) { this.potenciaNominal = potenciaNominal; }

    public double getCorrienteNominal() { return corrienteNominal; }
    public void setCorrienteNominal(double corrienteNominal) { this.corrienteNominal = corrienteNominal; }

    public double getVoltajeNominal() {return voltajeNominal;}
    public void setVoltajeNominal(double voltajeNominal) {this.voltajeNominal = voltajeNominal;}


    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    //Metodo para mostrar info
    public String mostrarInfo() {
        return "Codigo: " + codigo +
                ", Tipo: " + tipoMotor +
                ", Potencia: " + potenciaNominal +
                ", Corriente: " + corrienteNominal +
                " ,Voltaje: "+voltajeNominal+
                ", Ubicacion: " + ubicacion;
    }
}