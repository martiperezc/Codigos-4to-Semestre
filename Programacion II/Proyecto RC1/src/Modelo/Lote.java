package Modelo;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Lote {
    private String idLote;
    private LocalDate fechaIngreso;
    private LocalDate fechaCaducidad;
    private int cantidadStock;
    private int diasCaducar;
    private int alertaDias; // Días límite para lanzar la advertencia
    private String estadoLote; // "DISPONIBLE", "AGOTADO", "CADUCADO"

    // Constructor
    public Lote(String idLote, LocalDate fechaIngreso, LocalDate fechaCaducidad, int cantidadStock, int alertaDias) {
        this.idLote = idLote;
        this.fechaIngreso = fechaIngreso;
        this.fechaCaducidad = fechaCaducidad;
        this.cantidadStock = cantidadStock;
        this.alertaDias = alertaDias;

        // Al instanciar, calculamos los días y el estado automáticamente
        calcularDiasCaducar(LocalDate.now());
        actualizarEstado();
    }

    // --- Métodos de Lógica de Negocio Interna ---

    // Calcula la diferencia en días desde la fecha actual hasta la caducidad
    public void calcularDiasCaducar(LocalDate fechaActual) {
        // ChronoUnit calcula días exactos considerando años bisiestos y meses irregulares
        long dias = ChronoUnit.DAYS.between(fechaActual, this.fechaCaducidad);
        this.diasCaducar = (int) dias;
    }

    // Actualiza el estado basado en el stock y la fecha
    public void actualizarEstado() {
        if (this.cantidadStock <= 0) {
                this.estadoLote = "AGOTADO";
        } else if (this.diasCaducar < 0) {
            this.estadoLote = "CADUCADO";
        } else {
            this.estadoLote = "DISPONIBLE";
        }
    }

    // --- Getters y Setters ---

    public String getIdLote() {
        return idLote;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public int getCantidadStock() {
        return cantidadStock;
    }

    // Al modificar el stock, se actualiza automáticamente el estado si llega a 0
    public void setCantidadStock(int cantidadStock) {
        this.cantidadStock = cantidadStock;
        actualizarEstado();
    }

    public int getDiasCaducar() {
        return diasCaducar;
    }

    public int getAlertaDias() {
        return alertaDias;
    }

    public void setAlertaDias(int alertaDias) {
        this.alertaDias = alertaDias;
    }

    public String getEstadoLote() {
        return estadoLote;
    }

}