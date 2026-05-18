package interfaz;

import negocio.Motor.MotorTrifasico;
import negocio.diagnostico.AsistenteDiagnostico;
import negocio.diagnostico.ReporteDiagnostico;

import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) {
        // Instancia del Asistente con un margen de tolerancia del 10% (0.10)
        AsistenteDiagnostico asistente = new AsistenteDiagnostico(0.10);

        // Instancia de prueba: Motor Trifásico (Corriente nominal de 20.0 Amperios)
        MotorTrifasico motorPlanta = new MotorTrifasico("MOT-TRI-04", "Trifásico Inducción", 15.0, 20.0, "Área de Compresores", "Delta");

        // Simulación de los datos estructurados que entregaría el Módulo 2 (Fuera de rango)
        ArrayList<String> medicionesSimuladas = new ArrayList<>();
        medicionesSimuladas.add("corriente: 23.5"); // Supera los 22.0A permitidos por la tolerancia (20 * 1.10)
        medicionesSimuladas.add("temperatura: 89.2"); // Supera el límite térmico configurado

        // Ejecución de RF-05
        ReporteDiagnostico reporte = asistente.evaluarMotor(motorPlanta, medicionesSimuladas);
        System.out.println(reporte.mostrarResumen());

        // Condicional lógico derivado: Si hay fallas, el asistente activa dinámicamente RF-06
        if (reporte.getProximaRevision() == 0) {
            System.out.println("\n[SISTEMA]: Alertas detectadas. Generando secuencia procedimental obligatoria...");
            List<String> guiaMantenimiento = asistente.procedimiento(motorPlanta);

            for (String paso : guiaMantenimiento) {
                System.out.println(paso);
            }
        }
    }
}