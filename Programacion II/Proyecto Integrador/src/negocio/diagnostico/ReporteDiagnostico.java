package negocio.diagnostico;

import java.util.ArrayList;
import java.util.List;

public class ReporteDiagnostico {
    private String estadoGeneral;
    private ArrayList<String> alertasGeneradas;
    private int proximaRevision;

    // Constructor adaptado al diagrama UML
    public ReporteDiagnostico(String estadoGeneral, List<String> alertasGeneradas, int proximaRevision) {
        this.estadoGeneral = estadoGeneral;
        this.alertasGeneradas = new ArrayList<>(alertasGeneradas);
        this.proximaRevision = proximaRevision;
    }

    // Método solicitado para impresión rápida en pantalla
    public String mostrarResumen() {
        StringBuilder sb = new StringBuilder();
        sb.append("=========================================\n");
        sb.append("         RESUMEN DE DIAGNÓSTICO          \n");
        sb.append("=========================================\n");
        sb.append("Estado General: ").append(estadoGeneral).append("\n");
        sb.append("Próxima Revisión Sugerida: ").append(proximaRevision).append(" días\n");
        sb.append("Alertas de Seguridad:\n");
        if (alertasGeneradas.isEmpty()) {
            sb.append(" -> Ninguna. Operación bajo parámetros nominales.\n");
        } else {
            for (String alerta : alertasGeneradas) {
                sb.append(" -> ").append(alerta).append("\n");
            }
        }
        sb.append("=========================================");
        return sb.toString();
    }

    // Getters y Setters respetando firmas UML
    public String getEstadoGeneral() { return estadoGeneral; }
    public void setEstadoGeneral(String estado) { this.estadoGeneral = estado; }

    public List<String> getAlertasGeneradas() { return alertasGeneradas; }
    public void setAlertasgeneradas(List<String> alertas) { this.alertasGeneradas = new ArrayList<>(alertas); }

    public int getProximaRevision() { return proximaRevision; }
    public void setProximaRevision(int dias) { this.proximaRevision = dias; }
}