package negocio.diagnostico;

import modelo.Motor.Motor;
import modelo.Motor.MotorAC110V;
import modelo.Motor.MotorDC;
import modelo.Motor.MotorTrifasico;
import modelo.registroMediciones.MedicionesMotor;
import java.util.ArrayList;
import java.util.List;

public class AsistenteDiagnostico {
    private double margenTolerancia;

    //Constructor
    public AsistenteDiagnostico(double margenTolerancia) {
        this.margenTolerancia = margenTolerancia;
    }

    //Metodo que compara los valores nominales del motor frente a los medidos en modulo 2
    public ReporteDiagnostico evaluarMotor(Motor motor, MedicionesMotor medicion) {
        ArrayList<String> alertas = new ArrayList<>();
        String estadoGeneral = "Valores normales y dentro de los rangos seguros";
        int proximaRevision = 30;

    //Lamamos a validaciones de rango de modulo 2.
        if (!medicion.validarRango()) {
            alertas.add("El valor ingresado es incorrecto, o fuera de rango");
            return new ReporteDiagnostico("Medición no valida", alertas, 0);
        }

    //Segun el tipo de prueba tomamos el valor.
        String tipoPrueba = medicion.getTipoPrueba() != null ? medicion.getTipoPrueba().toLowerCase() : "";
        double valor = medicion.getValorObtenido();


        if (tipoPrueba.contains("motor dc") || tipoPrueba.contains("electrica")) {
            double limiteCorriente = motor.getCorrienteNominal() * (1 + this.margenTolerancia);
            if (valor > (motor.getVoltajeNominal() * limiteCorriente)) {
                alertas.add("La potencia calculada (" + valor + " W) excede la capacidad nominal del motor.");
            }
        }

        if (tipoPrueba.contains("aislamiento")) {
            // Normativa IEEE: Un aislamiento menor a 1 MegaOhm (1,000,000 Ohm) es crítico
            if (valor < 1000.0) {
                alertas.add("Asilamiento critico (" + valor + " Ohm). Posible riesgo de cortocircuito, Se recomienda urgentemente realizar un rebobinado.");
            } else if (valor < 2000.0) {
                alertas.add("Asilamiento bajo (" + valor + " Ohm). Realizar mantenimiento preventivo.");
            }
        }
        if (tipoPrueba.contains("temperatura")) {
            if (valor > 85.0) {
                alertas.add("La temperatura esta por encima de lo normal(" + valor + " °C). Revisar velocidad de operacion, carga colocada en el rotor, consumo actual");
            }
        }
        if (!alertas.isEmpty()) {
            estadoGeneral = "Se requiere un mantenimiento correctivo, a continuacion se detallan los pasos a seguir";
            proximaRevision = 0;
        } else {
            estadoGeneral = "Operación Nominal, el motor se encuentra en perfectas condiciones";
        }

        return new ReporteDiagnostico(estadoGeneral, alertas, proximaRevision);
    }

    //Pasos sugeridos para mantenimiento
    public List<String> procedimiento(Motor motor) {
        List<String> pasos = new ArrayList<>();
        pasos.add("Procedimiento de diagnostico");
        pasos.add("Informacion del motor: " + motor.mostrarInfo());

        //Segun que tipo de instancia es, sugerimos las recomendaciones
        if (motor instanceof MotorDC) {
            MotorDC mDC = (MotorDC) motor;
            pasos.add("Motor de corriente continua DC, con voltaje nominal de: " + mDC.getVoltajeNominal() + "V DC)");
            pasos.add("1. Desenergizar las líneas de alimentación y descargar bancos de capacitores si existen.");
            if (mDC.getTieneEscobillas()) {
                pasos.add("Se requiere mantenimiento fisico y desamblaje de la unidad, primeramente medir longitud de las escobillas" +
                        "Se debe de realizar una limpieza entre los carbones y el contacto metalico de las escobillas");
            } else {
                pasos.add("Mantenimiento avanzado requerido, verificar señales PWM/Procedimiento del fabricante");
            }
        }
        else if (motor instanceof MotorTrifasico) {
            MotorTrifasico mTrifasico = (MotorTrifasico) motor;
            pasos.add("Motor Trifasico con voltaje nominal: " + mTrifasico.getVoltajeNominal() + "V AC");
            pasos.add("1. Desenergizar completamente el equipo y verificar desbalance entre fases");
            pasos.add("2. Verificar simetría de resistencias en los devanados de la caja de bornes configurada en: " + mTrifasico.getTipoConexion());
            pasos.add("3. Realizar prueba de aislamiento con el megaómetro acoplado a bornes.");
        }
        else if (motor instanceof MotorAC110V) {
            MotorAC110V mAC = (MotorAC110V) motor;
            pasos.add("Motor monofasico AC 110VAC (Voltaje Nominal: " + mAC.getVoltajeNominal() + "V AC)");
            pasos.add("1. Verificar el capacitor de arranque/marcha, desconectarlo completamente y medir Capacitancia");
            pasos.add("2. Comprobación de interruptor centrifugo, realizar prueba de continuidad entre contactos, de ser necesario usar limpiador de contactos");
        }

        return pasos;
    }

    // Getters y Setters
    public double getMargenTolerancia() { return margenTolerancia; }
    public void setMargenTolerancia(double margenTolerancia) { this.margenTolerancia = margenTolerancia; }
}
