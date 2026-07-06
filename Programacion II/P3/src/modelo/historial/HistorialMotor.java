package modelo.historial;


import modelo.activos.Motor;
import modelo.operaciones.Analisis;
import modelo.operaciones.Mantenimiento;
import java.util.ArrayList;
import java.util.List;

public class HistorialMotor {
    private Motor motorAsociado;
    private List<Analisis> listaAnalisis;
    private List<Mantenimiento> listaMantenimientos;

    public HistorialMotor(Motor motorAsociado) {
        this.motorAsociado = motorAsociado;
        this.listaAnalisis = new ArrayList<>();
        this.listaMantenimientos = new ArrayList<>();
    }

    public void agregarAnalisis(Analisis a) { this.listaAnalisis.add(a); }
    public void agregarMantenimiento(Mantenimiento m) { this.listaMantenimientos.add(m); }

    public List<Analisis> getListaAnalisis() { return listaAnalisis; }
    public List<Mantenimiento> getListaMantenimientos() { return listaMantenimientos; }

    public String obtenerHistorialCompleto() {
        StringBuilder sb = new StringBuilder();
        sb.append("==================================================\n");
        sb.append("HISTORIAL TÉCNICO DEL ACTIVO: ").append(motorAsociado.getCodigo()).append("\n");
        sb.append("==================================================\n");
        sb.append("--- REGISTROS DE ANÁLISIS ---\n");
        for (Analisis a : listaAnalisis) {
            sb.append(a.toString()).append("\n");
        }
        sb.append("\n--- REGISTROS DE MANTENIMIENTO ---\n");
        for (Mantenimiento m : listaMantenimientos) {
            sb.append("Mant. ID: ").append(m.getIdMantenimiento()).append("\n")
                    .append("Fecha: ").append(m.getFecha()).append("\n")
                    .append("Técnico: ").append(m.getTecnicoAsignado().toString()).append("\n")
                    .append("Trabajo: ").append(m.getTareasRealizadas()).append("\n-----------------\n");
        }
        return sb.toString();
    }
}