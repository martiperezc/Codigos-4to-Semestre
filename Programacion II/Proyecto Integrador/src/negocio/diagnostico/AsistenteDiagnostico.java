package negocio.diagnostico;
import negocio.Motor.MotoAC110V;
import negocio.Motor.Motor;
import negocio.Motor.MotorDC;
import negocio.Motor.MotorTrifasico;

import java.util.ArrayList;
import java.util.List;

public class AsistenteDiagnostico {
    private double margenTolerancia;

    public AsistenteDiagnostico(double margenTolerancia) {
        this.margenTolerancia = margenTolerancia;
    }

    //Logica detras de RF-05
    public ReporteDiagnostico evaluarMotor(Motor motor, ArrayList<String> mediciones){
        ArrayList<String> alertas = new ArrayList<>();
        String estadoGeneral;
        String estagoGeneral= "Valores dentro de los rangos seguros";
        int proximaRevision= 30;
        double corrienteMedida= 0.0;
        double temperaturaMedida = 0.0;

        //Comparacion con las mediciones realizadas en el modulo 2

        for(String medicion : mediciones){
            String [] partes =  medicion.split(":"); //Funcion.split se encarga de dividir la lista de String principal, en substrings mostrando ":" cada vez que adquiere nueva info
            if(partes.length ==2){
                String tipo = partes[0].trim().toLowerCase(); //Funcion Trim para eliminar espacios no deseados
                double valor = Double.parseDouble(partes[1].trim());

                if(tipo.equals("corriente")){
                    corrienteMedida = valor;
                } else if(tipo.equals("temperatura")){
                    temperaturaMedida = valor;
                }

            }
        }
        // Regla de negocio 1: Validar sobrecorriente usando el margen de tolerancia
        double limiteCorriente = motor.getCorrienteNominal() * (1 + this.margenTolerancia);
        if (corrienteMedida > limiteCorriente) {
            alertas.add("CRÍTICO: Corriente medida (" + corrienteMedida + "A) supera el límite nominal tolerado de " + limiteCorriente + "A.");
        }

        // Regla de negocio 2: Validar temperatura (Parámetro general de seguridad industrial)
        if (temperaturaMedida > 85.0) {
            alertas.add("ADVERTENCIA: Temperatura elevada detectada (" + temperaturaMedida + "°C). Riesgo de sobrecalentamiento.");
        }

        // Evaluación final del reporte informativo (RF-05)
        if (!alertas.isEmpty()) {
            estadoGeneral = "Mantenimiento Correctivo Requerido / Ejecutar RF-06";
            proximaRevision = 0; // Requiere atención inmediata
        } else {
            estadoGeneral = "Operación Normal. Valores óptimos.";
        }

        return new ReporteDiagnostico(estadoGeneral, alertas, proximaRevision);

    }

    //Logica detras de RF-06
    /**
     * RF-06: Generación dinámica de procedimientos.
     * Evalúa dinámicamente el tipo de instancia del objeto Motor para desplegar su guía técnica específica.
     */
    public List<String> procedimiento(Motor motor) {
        List<String> pasos = new ArrayList<>();
        pasos.add("--- PROCEDIMIENTO DE DIAGNÓSTICO ESTANDARIZADO ---");
        pasos.add("Código de Equipo: " + motor.getCodigo());
        pasos.add("Ubicación en Planta: " + motor.getUbicacion());

        // Identificación dinámica mediante polimorfismo/evaluación de instancia
        if (motor instanceof MotorDC) {
            MotorDC mDC = (MotorDC) motor;
            pasos.add("Tipo de Actuador: Motor de Corriente Continua (DC)");
            pasos.add("1. Desenergizar el circuito de alimentación DC principal.");
            pasos.add("2. Inspeccionar físicamente el estado de las delgas del colector.");
            if (mDC.getTieneEscobillas()) {
                pasos.add("3. ALERTA DE MANTENIMIENTO: Verificar desgaste de escobillas de carbón y presión de los resortes.");
            } else {
                pasos.add("3. Verificar la conmutación electrónica y el correcto switcheo de los controladores estáticos.");
            }
        }
        else if (motor instanceof MotorTrifasico) {
            MotorTrifasico mTrifasico = (MotorTrifasico) motor;
            pasos.add("Tipo de Actuador: Motor de Corriente Alterna Trifásico");
            pasos.add("1. Desconectar los interruptores termomagnéticos de la línea de potencia.");
            pasos.add("2. Validar el acoplamiento mecánico y la caja de bornes configurada en conexión: " + mTrifasico.getTipoConexion());
            pasos.add("3. Emplear el megaómetro para realizar pruebas de aislamiento de fase a fase y fase a tierra.");
        }
        else if (motor instanceof MotoAC110V) {
            MotoAC110V mAC = (MotoAC110V) motor;
            pasos.add("Tipo de Actuador: Motor Monofásico AC 110V");
            pasos.add("1. Medir el voltaje de línea para asegurar estabilidad en los 110V nominales.");
            pasos.add("2. Verificar la frecuencia de la red (Frecuencia de diseño: " + mAC.getFrecuencia() + " Hz).");
            pasos.add("3. Medir desfasaje para constatar el Factor de Potencia nominal asignado (" + mAC.getFactorPotencia() + ").");
        }
        else {
            pasos.add("Tipo de Actuador: Motor Genérico no identificado.");
            pasos.add("1. Revisar el manual general del fabricante para parámetros no estandarizados.");
        }

        return pasos;
    }

    // Getters y Setters de control
    public double getMargenTolerancia() {
        return margenTolerancia;
    }

    public void setMargenTolerancia(double margenTolerancia) {
        this.margenTolerancia = margenTolerancia;
    }





}
