package modelo.operaciones;

import modelo.actores.Tecnico;
import java.util.Date;

public class Mantenimiento {
    private int idMantenimiento;
    private Date fecha;
    private Analisis analisisOrigen;
    private Tecnico tecnicoAsignado;
    private String tareasRealizadas;

    public Mantenimiento(int idMantenimiento, Analisis analisisOrigen, Tecnico tecnicoAsignado, String tareasRealizadas) {
        this.idMantenimiento = idMantenimiento;
        this.fecha = new Date();
        this.analisisOrigen = analisisOrigen;
        this.tecnicoAsignado = tecnicoAsignado;
        this.tareasRealizadas = tareasRealizadas;
    }

    public int getIdMantenimiento() { return idMantenimiento; }
    public Date getFecha() { return fecha; }
    public Analisis getAnalisisOrigen() { return analisisOrigen; }
    public Tecnico getTecnicoAsignado() { return tecnicoAsignado; }
    public String getTareasRealizadas() { return tareasRealizadas; }

    @Override
    public String toString() {
        return "Mant. ID: " + idMantenimiento + " | Basado en Análisis: " + analisisOrigen.getIdAnalisis() + " | Tareas: " + tareasRealizadas;
    }
}