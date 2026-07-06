package interfaz;

import negocio.GestorMantenimiento;
import modelo.activos.*;
import modelo.actores.Tecnico;
import modelo.operaciones.Analisis;
import modelo.operaciones.Mantenimiento;
import modelo.excepciones.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static GestorMantenimiento gestor = new GestorMantenimiento();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Precarga básica para pruebas rápidas
        try {
            gestor.registrarTecnico(new Tecnico("1754280269", "Martin Perez", "Automatizacion", "morde110"));
            gestor.registrarMotor(new MotorTrifasico("101", 1.760, 8.0, 220.0, "A1", "Delta"));
        } catch (Exception ignored) {}

        boolean terminarPrograma = false;
        while (!terminarPrograma) {
            System.out.println("\n=== CONTROL DE ACCESO INDUSTRIAL ===");
            System.out.println("1. Iniciar Sesión (Técnico o Admin)");
            System.out.println("2. Registrar Nuevo Técnico");
            System.out.println("3. Salir del Programa");
            System.out.print("Seleccione una opción: ");

            String opAcceso = sc.nextLine();
            switch (opAcceso) {
                case "1":
                    manejarLogin();
                    break;
                case "2":
                    manejarRegistroTecnico();
                    break;
                case "3":
                    terminarPrograma = true;
                    System.out.println("Cerrando el sistema central. ¡Buen día!");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private static void manejarLogin() {
        System.out.print("\nIngrese su Usuario / Cédula: ");
        String usuario = sc.nextLine().trim();
        System.out.print("Ingrese su Contraseña: ");
        String password = sc.nextLine().trim();

        // Validación Admin por defecto: admin / admin123
        if ("admin".equalsIgnoreCase(usuario) && "admin123".equals(password)) {
            System.out.println("✔ Sesión iniciada como ADMINISTRADOR.");
            menuAdministrador();
        } else {
            // Validación del Técnico
            if (gestor.iniciarSesionTecnico(usuario, password)) {
                System.out.println("✔ Sesión iniciada. Bienvenido Técnico: " + gestor.getTecnicoLogueado().getNombre());
                menuTecnico();
            } else {
                System.out.println("Error: Credenciales incorrectas o usuario no registrado.");
            }
        }
    }

    private static void manejarRegistroTecnico() {
        List<String> errores = new ArrayList<>();
        System.out.println("\n--- REGISTRO DE NUEVO TÉCNICO ---");
        System.out.print("Cédula (10 dígitos exactos): "); String ced = sc.nextLine().trim();
        System.out.print("Nombre Completo (solo letras): "); String nom = sc.nextLine().trim();
        System.out.print("Especialidad (solo letras): "); String esp = sc.nextLine().trim();
        System.out.print("Defina una Contraseña para ingresar: "); String pass = sc.nextLine().trim();

        if (!ced.matches("\\d+")) errores.add("- La cédula debe contener solo números positivos.");
        if (ced.length() != 10) errores.add("- La cédula debe tener exactamente 10 dígitos.");
        if (!nom.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) errores.add("- El nombre debe contener solo letras.");
        if (!esp.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) errores.add("- La especialidad debe contener solo letras.");
        if (pass.isEmpty()) errores.add("- La contraseña no puede estar vacía.");

        if (!errores.isEmpty()) {
            System.out.println("\n⚠️ ERRORES DE VALIDACIÓN DETECTADOS:");
            errores.forEach(System.out::println);
            return;
        }

        try {
            gestor.registrarTecnico(new Tecnico(ced, nom, esp, pass));
            System.out.println("Técnico dado de alta con éxito. Pruebe a iniciar sesión.");
        } catch (DatoInvalidoException e) {
            System.out.println("Error de Negocio: " + e.getMessage());
        }
    }

    private static void menuAdministrador() {
        boolean salirAdmin = false;
        while (!salirAdmin) {
            System.out.println("\n=== MENÚ ADMINISTRADOR ===");
            System.out.println("1. Gestión de Motores (Registrar)");
            System.out.println("2. Ver Lista de Técnicos Registrados");
            System.out.println("3. Ver Estado y Condición Actual de Motores");
            System.out.println("4. Ver Historial Integral de un Motor");
            System.out.println("5. Cerrar Sesión");
            System.out.print("Seleccione una opción: ");

            String op = sc.nextLine();
            switch (op) {
                case "1":
                    registrarMotorAdmin();
                    break;
                case "2":
                    System.out.println("\n--- TÉCNICOS REGISTRADOS ---");
                    gestor.getCatalogoTecnicos().forEach(System.out::println);
                    break;
                case "3":
                    registrarAnalisisAdmin();
                    break;
                case "4":
                    try {
                        System.out.print("\nIngrese el Código del Motor: ");
                        String cod = sc.nextLine().trim();
                        Motor m = gestor.buscarMotor(cod);
                        System.out.println(m.getHistorial().obtenerHistorialCompleto());
                    } catch (EntidadNoEncontradaException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case "5":
                    salirAdmin = true;
                    System.out.println("Sesión de administrador finalizada.");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void menuTecnico() {
        boolean salirTecnico = false;
        while (!salirTecnico) {
            System.out.println("\n=== MENÚ TÉCNICO (EXCLUSIVO MANTENIMIENTO) ===");
            System.out.println("1. Ver Motores con Análisis (Preventivos y Correctivos)");
            System.out.println("2. Ejecutar y Registrar Mantenimiento");
            System.out.println("3. Ver Estado y Condición Actual de Motores");
            System.out.println("4. Cerrar Sesión");
            System.out.print("Seleccione una opción: ");

            String op = sc.nextLine();
            switch (op) {
                case "1":
                    System.out.println("\n--- MOTORES EN ESTADO PREVENTIVO ---");
                    gestor.getTodosLosAnalisis().stream()
                            .filter(a -> "Preventivo".equals(a.getEstadoFinal()))
                            .forEach(a -> System.out.println("Motor Código: " + a.getMotor().getCodigo()));

                    System.out.println("\n--- MOTORES EN ESTADO CORRECTIVO ---");
                    gestor.getTodosLosAnalisis().stream()
                            .filter(a -> "Correctivo".equals(a.getEstadoFinal()))
                            .forEach(a -> System.out.println("Motor Código: " + a.getMotor().getCodigo()));
                    break;

                case "2":
                    try {
                        System.out.println("\n--- REGISTRAR TRABAJO DE MANTENIMIENTO ---");
                        System.out.print("Ingrese Código del Motor a revisar: ");
                        String codM = sc.nextLine().trim();
                        System.out.print("Escriba las tareas realizadas detalladamente: ");
                        String tareas = sc.nextLine().trim();

                        Mantenimiento mant = gestor.ejecutarMantenimiento(codM, tareas);
                        System.out.println("Mantenimiento registrado con éxito. ID Secuencial Asignado: " + mant.getIdMantenimiento());
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case "3":
                    mostrarEstadoMotoresConsola();
                    break;
                case "4":
                    gestor.cerrarSesion();
                    salirTecnico = true;
                    System.out.println("Sesión de técnico finalizada.");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void registrarMotorAdmin() {
        List<String> errores = new ArrayList<>();
        System.out.println("\n--- REGISTRO DE MOTOR ---");
        System.out.println("Seleccione Tipo: 1. DC | 2. Trifásico | 3. AC 110V");
        String tipo = sc.nextLine().trim();

        System.out.print("Código (Solo números positivos): "); String cod = sc.nextLine().trim();
        System.out.print("Potencia Nominal (kW): "); String potS = sc.nextLine().trim();
        System.out.print("Corriente Nominal (A): "); String corrS = sc.nextLine().trim();
        System.out.print("Voltaje Nominal (V): "); String voltS = sc.nextLine().trim();
        System.out.print("Ubicación (Debe iniciar con letra): "); String ubi = sc.nextLine().trim();

        if (!cod.matches("\\d+")) errores.add("- El código debe contener solo números positivos.");
        if (!potS.matches("\\d+(\\.\\d+)?")) errores.add("- Potencia debe ser un número positivo.");
        if (!corrS.matches("\\d+(\\.\\d+)?")) errores.add("- Corriente debe ser un número positivo.");
        if (!voltS.matches("\\d+(\\.\\d+)?")) errores.add("- Voltaje debe ser un número positivo.");
        if (!ubi.matches("^[a-zA-Z].*")) errores.add("- La ubicación debe iniciar con una letra.");

        double p = 0, c = 0, v = 0;
        try { p = Double.parseDouble(potS); c = Double.parseDouble(corrS); v = Double.parseDouble(voltS); } catch (Exception ignored) {}

        try {
            Motor m = null;
            if ("1".equals(tipo)) {
                System.out.print("¿Tiene Escobillas? (true/false): "); boolean esc = Boolean.parseBoolean(sc.nextLine());
                if (!errores.isEmpty()) { DesplegarErrores(errores); return; }
                m = new MotorDC(cod, p, c, v, ubi, esc);
            } else if ("2".equals(tipo)) {
                System.out.print("Conexión (Estrella/Delta): "); String con = sc.nextLine().trim();
                if (!errores.isEmpty()) { DesplegarErrores(errores); return; }
                m = new MotorTrifasico(cod, p, c, v, ubi, con);
            } else if ("3".equals(tipo)) {
                System.out.print("Frecuencia (Hz): "); String frecS = sc.nextLine().trim();
                System.out.print("Factor de Potencia (FP): "); String fpS = sc.nextLine().trim();
                if (!frecS.matches("\\d+(\\.\\d+)?")) errores.add("- Frecuencia inválida.");
                if (!fpS.matches("\\d+(\\.\\d+)?")) errores.add("- FP inválido.");
                if (!errores.isEmpty()) { DesplegarErrores(errores); return; }
                m = new MotorAC110V(cod, p, c, v, ubi, Double.parseDouble(frecS), Double.parseDouble(fpS));
            } else {
                System.out.println("Tipo de motor no válido.");
                return;
            }

            gestor.registrarMotor(m);
            System.out.println("Motor añadido correctamente.");
        } catch (DatoInvalidoException e) {
            System.out.println("Error en Catálogo: " + e.getMessage());
        }
    }

    private static void registrarAnalisisAdmin() {
        // Para simular el requerimiento de que el técnico hace el análisis, la consola usará temporalmente el primero del catálogo
        if (gestor.getCatalogoTecnicos().isEmpty()) {
            System.out.println("No se pueden hacer análisis si no existen técnicos dados de alta para firmar.");
            return;
        }

        List<String> errores = new ArrayList<>();
        System.out.println("\n--- REGISTRAR DIAGNÓSTICO EN ACTIVO ---");
        System.out.print("Código del Motor: "); String cod = sc.nextLine().trim();
        System.out.print("Voltaje Leído (V): "); String vS = sc.nextLine().trim();
        System.out.print("Corriente Leída (A): "); String cS = sc.nextLine().trim();
        System.out.print("Temperatura (°C): "); String tS = sc.nextLine().trim();
        System.out.print("Resistencia Aislamiento (MΩ): "); String aS = sc.nextLine().trim();

        if (cod.isEmpty()) errores.add("- El código del motor no puede estar vacío.");
        if (!vS.matches("\\d+(\\.\\d+)?")) errores.add("- Voltaje incorrecto.");
        if (!cS.matches("\\d+(\\.\\d+)?")) errores.add("- Corriente incorrecta.");
        if (!tS.matches("\\d+(\\.\\d+)?")) errores.add("- Temperatura incorrecta.");
        if (!aS.matches("\\d+(\\.\\d+)?")) errores.add("- Aislamiento incorrecto.");

        if (!errores.isEmpty()) {
            DesplegarErrores(errores);
            return;
        }

        try {
            // Simulamos el inicio de sesión del analista con el técnico por defecto de la precarga
            gestor.iniciarSesionTecnico(gestor.getCatalogoTecnicos().get(0).getCedula(), gestor.getCatalogoTecnicos().get(0).getPassword());

            Analisis an = gestor.generarAnalisisMotor(cod,
                    Double.parseDouble(vS), Double.parseDouble(cS),
                    Double.parseDouble(tS), Double.parseDouble(aS));

            System.out.println("Análisis registrado con éxito.");
            System.out.println("ID Asignado Automáticamente: " + an.getIdAnalisis());
            System.out.println("Resultado de Estado Técnico: " + an.getEstadoFinal());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void mostrarEstadoMotoresConsola() {
        System.out.println("\n--- ESTADO DE LOS MOTORES REGISTRADOS (CONDIClÓN REAL) ---");
        for (Motor m : gestor.getCatalogoMotores()) {
            String condicion = gestor.obtenerEstadoActualMotor(m.getCodigo());
            System.out.println("Motor [" + m.getCodigo() + "] - Tipo: " + m.getTipoTexto() + " | Estado: " + condicion);
        }
    }

    private static void DesplegarErrores(List<String> errores) {
        System.out.println("\n⚠️ ERRORES ENCONTRADOS:");
        errores.forEach(System.out::println);
    }
}