package negocio.Motor;

public class MotorDC extends Motor {
    private boolean tieneEscobillas;
    
    // El constructor recibe los parámetros del padre + el parámetro propio
    public MotorDC(String codigo, String tipoMotor, double potenciaNominal, double corrienteNominal, String ubicacion, boolean tieneEscobillas) {
        // super() pasa los datos obligatorios al constructor de la clase Motor
        super(codigo, tipoMotor, potenciaNominal, corrienteNominal, ubicacion);
        this.tieneEscobillas = tieneEscobillas;
    }

    // Métodos específicos
    public boolean getTieneEscobillas() { return tieneEscobillas; }
    public void setTieneEscobillas(boolean tieneEscobillas) { this.tieneEscobillas = tieneEscobillas; }
}