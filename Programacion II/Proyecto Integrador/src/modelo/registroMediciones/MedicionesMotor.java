package modelo.registroMediciones;
import java.util.Date;

public class MedicionesMotor {
    private Date fechaMedicion;
    private double valorObtenido;
    private String unitMedida;
    private String tipoPrueba;
    private double voltaje;
    private double corriente;
    private double resistenciaAislamiento;
    private double temperatura;

    // Constructor
    public MedicionesMotor(Date fechaMedicion, double valorObtenido, String unitMedida, String tipoPrueba) {
        this.fechaMedicion = fechaMedicion;
        this.valorObtenido = valorObtenido;
        this.unitMedida = unitMedida;
        this.tipoPrueba = tipoPrueba;
    }

    public boolean validarRango() {
        if (voltaje < 0 || corriente < 0 || resistenciaAislamiento < 0 || temperatura < -40) {
            return false;
        }
        return true;
    }

    public void medirMotorDC(double voltaje, double corriente) {
        this.voltaje = voltaje;
        this.corriente = corriente;
        this.tipoPrueba = "Motor DC / Eléctrica";
        this.unitMedida = "Watts";
        // Calculamos la potencia consumida para que el Asistente (Módulo 3) pueda auditarla
        this.valorObtenido = voltaje * corriente;
    }

    public void pruebaAislamiento(double resistencia) {
        this.resistenciaAislamiento = resistencia;
        this.tipoPrueba = "Aislamiento";
        this.unitMedida = "kOhms";
        this.valorObtenido = resistencia; // Crucial para que el Módulo 3 lea el valor correcto
    }

    public void pruebaTemperatura(double temperatura) {
        this.temperatura = temperatura;
        this.tipoPrueba = "Temperatura";
        this.unitMedida = "°C";
        this.valorObtenido = temperatura; // Crucial para que el Módulo 3 lea el valor correcto
    }

    //Metodo para registro de datos en modulo 4
    public void registrarMedicion() {
        System.out.println("Medición registrada con éxito para en prueba de: " + this.tipoPrueba);
    }

    @Override
    public String toString() {
        return "Medicion [" + fechaMedicion + "] Tipo: " + tipoPrueba + " | Valor: " + valorObtenido + " " + unitMedida;
    }

    // =========================================================================
    // GETTERS Y SETTERS COMPLETO (Obligatorios para el acoplamiento sin errores)
    // =========================================================================
    public Date getFechaMedicion() { return fechaMedicion; }
    public void setFechaMedicion(Date fechaMedicion) { this.fechaMedicion = fechaMedicion; }

    public double getValorObtenido() { return valorObtenido; }
    public void setValorObtenido(double valorObtenido) { this.valorObtenido = valorObtenido; }

    public String getUnitMedida() { return unitMedida; }
    public void setUnitMedida(String unitMedida) { this.unitMedida = unitMedida; }

    public String getTipoPrueba() { return tipoPrueba; }
    public void setTipoPrueba(String tipoPrueba) { this.tipoPrueba = tipoPrueba; }

    public double getVoltaje() { return voltaje; }
    public double getCorriente() { return corriente; }
    public double getResistenciaAislamiento() { return resistenciaAislamiento; }
    public double getTemperatura() { return temperatura; }
}