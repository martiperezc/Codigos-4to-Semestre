package modelo.operaciones;

import modelo.activos.Motor;
import modelo.actores.Tecnico;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Analisis {
    private int idAnalisis;
    private Date fecha;
    private Motor motor;
    private Tecnico tecnico;
    private double voltajeMedido;
    private double corrienteMedida;
    private double temperatura;
    private double aislamiento;
    private String estadoFinal;
    private List<String> alertas;
    private List<String> sugerencias;

    public Analisis(int idAnalisis, Motor motor, Tecnico tecnico, double voltajeMedido, double corrienteMedida, double temperatura, double aislamiento) {
        this.idAnalisis = idAnalisis;
        this.fecha = new Date();
        this.motor = motor;
        this.tecnico = tecnico;
        this.voltajeMedido = voltajeMedido;
        this.corrienteMedida = corrienteMedida;
        this.temperatura = temperatura;
        this.aislamiento = aislamiento;
        this.alertas = new ArrayList<>();
        this.sugerencias = new ArrayList<>();
        ejecutarEvaluacion();
    }

    private void ejecutarEvaluacion() {
        boolean problema = false;
        if (corrienteMedida > motor.getCorrienteNominal() * 1.10) {
            alertas.add("Corriente alta detectada");
            sugerencias.add("Revisar posible sobrecarga mecánica en el eje");
            problema = true;
        }
        if (temperatura > 85.0) {
            alertas.add("Sobrecalentamiento crítico");
            sugerencias.add("Verificar sistema de ventilación o rodamiento obstruido");
            problema = true;
        }
        if (aislamiento < 1000.0) {
            alertas.add("Aislamiento en estado crítico (Correctivo)");
            sugerencias.add("Se sugiere rebobinado urgente o parada del activo");
            problema = true;
        } else if (aislamiento < 2000.0) {
            alertas.add("Aislamiento preventivo bajo (Preventivo)");
            sugerencias.add("Programar mantenimiento preventivo de limpieza y secado");
            problema = true;
        }
        this.estadoFinal = problema ? (aislamiento < 1000.0 ? "Correctivo" : "Preventivo") : "Buen Estado";
    }

    public int getIdAnalisis() { return idAnalisis; }
    public Motor getMotor() { return motor; }
    public Tecnico getTecnico() { return tecnico; }
    public String getEstadoFinal() { return estadoFinal; }
    public List<String> getAlertas() { return alertas; }
    public List<String> getSugerencias() { return sugerencias; }

    @Override
    public String toString() {
        return "Análisis ID: " + idAnalisis + " | Estado: " + estadoFinal + " | Alertas: " + alertas;
    }
}