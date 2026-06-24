package club;

import java.util.Scanner;
import club.Socio.Tipo;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int op;
        Club c = new Club();

        do {
            System.out.println("\n===== CONTROL DE CLUB SOCIAL =====");
            System.out.println("1. Afiliar un socio al club.");
            System.out.println("2. Registrar una persona autorizada por un socio.");
            System.out.println("3. Pagar una factura.");
            System.out.println("4. Registrar un consumo en la cuenta de un socio");
            System.out.println("5. Aumentar fondos de la cuenta de un socio");
            System.out.println("6. Salir");
            System.out.print("Ingrese una opción: ");

            try {
                op = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese únicamente números enteros.");
                op = 0;
                continue;
            }

            // Cada caso de negocio se envuelve en un bloque try-catch para capturar las excepciones lanzadas por las clases
            switch (op) {
                case 1: {
                    try {
                        System.out.print("Ingrese cédula: ");
                        String cedula = sc.nextLine();
                        System.out.print("Ingrese nombre: ");
                        String nombre = sc.nextLine();
                        System.out.print("Tipo (1. VIP, 2. REGULAR): ");
                        int t = Integer.parseInt(sc.nextLine());
                        Tipo tipo = (t == 1) ? Tipo.VIP : Tipo.REGULAR;

                        c.afiliarSocio(cedula, nombre, tipo);
                        System.out.println("¡Socio afiliado exitosamente!");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } break;

                case 2: {
                    try {
                        System.out.print("Ingrese la cédula del socio: ");
                        String cedula = sc.nextLine();
                        System.out.print("Nombre del autorizado: ");
                        String autorizado = sc.nextLine();

                        c.agregarAutorizadoSocio(cedula, autorizado);
                        System.out.println("¡Autorizado registrado con éxito!");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } break;

                case 3: {
                    try {
                        System.out.print("Ingrese la cédula del socio: ");
                        String cedula = sc.nextLine();
                        ArrayList<Factura> facturas = c.darFacturasSocio(cedula);

                        if (facturas.isEmpty()) {
                            System.out.println("El socio no presenta facturas pendientes.");
                        } else {
                            System.out.println("--- Facturas pendientes ---");
                            for (int i = 0; i < facturas.size(); i++) {
                                System.out.println("[" + i + "] " + facturas.get(i));
                            }
                            System.out.print("Seleccione el índice de la factura a pagar: ");
                            int indice = Integer.parseInt(sc.nextLine());

                            c.pagarFacturaSocio(cedula, indice);
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } break;

                case 4: {
                    try {
                        System.out.print("Ingrese la cédula del socio: ");
                        String cedula = sc.nextLine();
                        System.out.print("Nombre de la persona que consume: ");
                        String cliente = sc.nextLine();
                        System.out.print("Concepto del servicio/producto: ");
                        String concepto = sc.nextLine();
                        System.out.print("Valor del consumo: ");
                        double valor = Double.parseDouble(sc.nextLine());

                        c.registrarConsumo(cedula, cliente, concepto, valor);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } break;

                case 5: {
                    try {
                        System.out.print("Ingrese la cédula del socio: ");
                        String cedula = sc.nextLine();
                        System.out.print("Monto a agregar a fondos: ");
                        double monto = Double.parseDouble(sc.nextLine());

                        c.aumentarFondosSocio(cedula, monto);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } break;

                case 6: {
                    System.out.println("¡Gracias por usar el sistema!");
                } break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (op != 6);

        sc.close();
    }
}
