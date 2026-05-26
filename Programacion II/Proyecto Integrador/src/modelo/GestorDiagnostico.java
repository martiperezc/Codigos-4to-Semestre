package modelo;
import modelo.Motor.Motor;
import modelo.Motor.MotorAC110V;
import modelo.Motor.MotorDC;
import modelo.Motor.MotorTrifasico;
import negocio.diagnostico.*;
import modelo.registroMediciones.MedicionesMotor;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class GestorDiagnostico {
    private Scanner scanner;
    private AsistenteDiagnostico asistente;

    public GestorDiagnostico() {
        this.scanner = new Scanner(System.in);
        //Definimos la tolerancia del sistema ante cambios en los valores nominales
        this.asistente = new AsistenteDiagnostico(0.05);
    }

    public void iniciarSistema() {
        boolean continuar = true;
        System.out.println("Sistema de diagnostico en motores electriocs");
        while (continuar) {
            try {
                Motor m1 = configurarMotor();
                if (m1 == null) continue;

                MedicionesMotor medicionActual = registrarMedicion();
                if (medicionActual == null) continue;

                procesarReporte(m1, medicionActual);

                System.out.print("\nQuiere hacer otro diagnostico?(S/N): ");
                String respuesta = scanner.nextLine();
                if (respuesta.equalsIgnoreCase("N")) {
                    continuar = false;
                    System.out.println("Saliendo del sistema");
                }

            } catch (NumberFormatException e) {
                System.out.println("\nIngresar valores numericos validos");
            } catch (Exception e) {
                System.out.println("\nError" + e.getMessage());
            }
        }
        scanner.close();
    }

    private Motor configurarMotor() {
        System.out.println("\nConfiguracion del motor");
        System.out.println("1. Motor DC");
        System.out.println("2. Motor AC Trifásico");
        System.out.println("3. Motor AC 110V Monofásico");
        System.out.print("Seleccione el tipo de motor: ");

        int opcionMotor = Integer.parseInt(scanner.nextLine());

        System.out.print("Código del equipo: ");
        String codigo = scanner.nextLine();
        System.out.print("Ubicación en planta: ");
        String ubicacion = scanner.nextLine();
        System.out.print("Potencia Nominal (W): ");
        double potencia = Double.parseDouble(scanner.nextLine());
        System.out.print("Corriente Nominal (A): ");
        double corrienteNom = Double.parseDouble(scanner.nextLine());
        System.out.print("Voltaje Nominal (V): ");
        double voltajeNom = Double.parseDouble(scanner.nextLine());

        switch (opcionMotor) {
            case 1:
                System.out.print("¿Tiene escobillas? (true/false): ");
                boolean escobillas = Boolean.parseBoolean(scanner.nextLine());
                return new MotorDC(codigo, "Motor DC", potencia, corrienteNom, voltajeNom, ubicacion, escobillas);
            case 2:
                System.out.print("Tipo de conexión en bornera (Estrella, Delta): ");
                String conexion = scanner.nextLine();
                return new MotorTrifasico(codigo, "Trifásico de Inducción", potencia, corrienteNom, voltajeNom, ubicacion, conexion);
            case 3:
                System.out.print("Frecuencia de red (Hz): ");
                double frec = Double.parseDouble(scanner.nextLine());
                System.out.print("Factor de potencia nominal (Ej. 0.85): ");
                double fp = Double.parseDouble(scanner.nextLine());
                return new MotorAC110V(codigo, "AC 110V Estándar", potencia, corrienteNom, voltajeNom, ubicacion, frec, fp);
            default:
                System.out.println("Opción inválida.");
                return null;
        }
    }

    private MedicionesMotor registrarMedicion() {
        System.out.println("\nRegistro de mediciones");
        System.out.println("1. Prueba Eléctrica (Voltaje y Corriente bajo carga)");
        System.out.println("2. Prueba de Aislamiento (Megaómetro)");
        System.out.println("3. Prueba de Temperatura (Camara Termografica, Termocupla");
        System.out.print("Seleccione el tipo de prueba: ");

        int opcionPrueba = Integer.parseInt(scanner.nextLine());
        MedicionesMotor medicion = new MedicionesMotor(new Date(), 0.0, "", "");

        switch (opcionPrueba) {
            case 1:
                System.out.print("Ingrese el Voltaje medido (V): ");
                double vMedido = Double.parseDouble(scanner.nextLine());
                System.out.print("Ingrese la Corriente medida (A): ");
                double iMedida = Double.parseDouble(scanner.nextLine());
                medicion.medirMotorDC(vMedido, iMedida);
                break;
            case 2:
                System.out.print("Ingrese la resistencia de aislamiento obtenida (kOhms): ");
                double aislamiento = Double.parseDouble(scanner.nextLine());
                medicion.pruebaAislamiento(aislamiento);
                break;
            case 3:
                System.out.print("Ingrese la temperatura superficial (carcasa) medida (°C): ");
                double temp = Double.parseDouble(scanner.nextLine());
                medicion.pruebaTemperatura(temp);
                break;
            default:
                System.out.println("Opción de prueba inválida.");
                return null;
        }

        medicion.registrarMedicion();
        return medicion;
    }


    private void procesarReporte(Motor motor, MedicionesMotor medicion) {
        System.out.println("\nIniciando diagnostico");
        ReporteDiagnostico reporte = asistente.evaluarMotor(motor, medicion);

        System.out.println(reporte.mostrarResumen());

        if (reporte.getProximaRevision() == 0) {
            System.out.println("\nMotor en estado emergente");
            List<String> procedimiento = asistente.procedimiento(motor);
            for (String paso : procedimiento) {
                System.out.println(paso);
            }
            System.out.println("-");
        }
    }
}
