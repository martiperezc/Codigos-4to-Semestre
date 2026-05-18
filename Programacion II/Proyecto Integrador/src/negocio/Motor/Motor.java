package negocio.Motor;

public class Motor {
    // Atributos privados (encapsulamiento)
    private String codigo;
    private String tipoMotor;
    private double potenciaNominal;
    private double corrienteNominal;
    private String ubicacion;

    // Constructor completo
    public Motor(String codigo, String tipoMotor, double potenciaNominal, double corrienteNominal, String ubicacion) {
        this.codigo = codigo;
        this.tipoMotor = tipoMotor;
        this.potenciaNominal = potenciaNominal;
        this.corrienteNominal = corrienteNominal;
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

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    // Método para mostrar información base
    public String mostrarInfo() {
        return "Código: " + codigo + ", Tipo: " + tipoMotor +
                ", Potencia: " + potenciaNominal + " kW, Corriente: " + corrienteNominal + " A" +
                ", Ubicación: " + ubicacion;
    }
}