package negocio.diagnostico;

import java.util.ArrayList;
import java.util.List;

public class ReporteDiagnostico {
    private String estadoGeneral;
    private ArrayList<String> alertasGeneradas;
    private int proximaRevision;

    public ReporteDiagnostico(String estadoGeneral, List<String> alertasGeneradas, int proximaRevision) {
        this.estadoGeneral = estadoGeneral;
        this.alertasGeneradas = new ArrayList<>(alertasGeneradas);
        this.proximaRevision = proximaRevision;
    }

    public String mostrarResumen() {
        StringBuilder sb = new StringBuilder();
        sb.append("Reporte de Diagnostico\n");
        sb.append("Estado Operativo: ").append(estadoGeneral).append("\n");
        sb.append("Re-calibración / Revisión: ").append(proximaRevision).append(" días\n");
        sb.append("Alertas generadas:\n");
        if (alertasGeneradas.isEmpty()) {
            sb.append("No hay ninguna alerta, operando bajo condiciones normales\n");
        } else {
            for (String alerta : alertasGeneradas) {
                sb.append("- ").append(alerta).append("\n");
            }
        }
        return sb.toString();
    }

    // Getters y Setters alineados al UML
    public String getEstadoGeneral() { return estadoGeneral; }
    public void setEstadoGeneral(String estado) { this.estadoGeneral = estado; }
    public List<String> getAlertasGeneradas() { return alertasGeneradas; }
    public void setAlertasgeneradas(List<String> alertas) { this.alertasGeneradas = new ArrayList<>(alertas); }
    public int getProximaRevision() { return proximaRevision; }
    public void setProximaRevision(int dias) { this.proximaRevision = dias; }
}