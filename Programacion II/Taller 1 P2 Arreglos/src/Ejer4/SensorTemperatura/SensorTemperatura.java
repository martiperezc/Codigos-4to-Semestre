package Ejer4.SensorTemperatura;

public class SensorTemperatura {
    private String idSensor;
    private double valorActual;
    private String unidad;

    // Constructor para inicializar todos los atributos
    public SensorTemperatura(String idSensor, double valorActual, String unidad) {
        this.idSensor = idSensor;
        this.valorActual = valorActual;
        this.unidad = unidad;
    }

    // Getters y Setters para valorActual y unidad
    public double getValorActual() {
        return valorActual;
    }

    public void setValorActual(double valorActual) {
        this.valorActual = valorActual;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getIdSensor() {
        return idSensor;
    }

    // Método para imprimir la lectura en consola
    public void mostrarLectura() {
        System.out.println("Sensor ID: " + idSensor + " | Lectura: " + valorActual + " " + unidad);
    }
}   
