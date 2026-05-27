package Interfaz;

import Modelo.Lote;
import Modelo.Producto;
import Negocio.GestorInventario;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestorInventario gestor = new GestorInventario();
        boolean salir = false;

        while (!salir) {
            // Ejecución automática de reglas de negocio en cada iteración
            gestor.actualizarEstadosDiarios();

            System.out.println("\nMENU PRINCIPAL");
            System.out.println("1. Registrar Producto");
            System.out.println("2. Registrar Lote");
            System.out.println("3. Consultar Inventario y Alertas");
            System.out.println("4. Procesar Salida PEPS");
            System.out.println("5. Eliminar un producto");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opcion: ");

            int opcion = -1;
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un numero valido.");
                continue;
            }

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese ID del Producto: ");
                    String idProd = scanner.nextLine();
                    System.out.print("Ingrese Nombre del Producto: ");
                    String nombreProd = scanner.nextLine();

                    gestor.registrarProducto(new Producto(idProd, nombreProd));
                    System.out.println("Producto registrado exitosamente.");
                    break;

                case 2:
                    System.out.print("Ingrese ID del Producto existente: ");
                    String idBuscar = scanner.nextLine();
                    Producto prodExistente = gestor.buscarProducto(idBuscar);

                    if (prodExistente != null) {
                        System.out.print("Ingrese ID del Lote: ");
                        String idLote = scanner.nextLine();

                        try {
                            System.out.print("Ingrese Cantidad Inicial: ");
                            int cantidad = Integer.parseInt(scanner.nextLine());
                            System.out.print("Ingrese Fecha de Caducidad (YYYY-MM-DD): ");
                            LocalDate fechaCad = LocalDate.parse(scanner.nextLine());
                            System.out.print("Ingrese dias limite para alerta: ");
                            int alerta = Integer.parseInt(scanner.nextLine());

                            Lote nuevoLote = new Lote(idLote, LocalDate.now(), fechaCad, cantidad, alerta);
                            gestor.registrarLote(prodExistente, nuevoLote);
                            System.out.println("Lote registrado exitosamente.");

                        } catch (DateTimeParseException e) {
                            System.out.println("Error: Formato de fecha incorrecto.");
                        } catch (NumberFormatException e) {
                            System.out.println("Error: El valor debe ser numerico.");
                        }
                    } else {
                        System.out.println("Error: Producto no encontrado.");
                    }
                    break;

                case 3:
                    gestor.generarAlertaCaducidad();
                    System.out.println(gestor.mostrarInventario());
                    break;

                case 4:
                    System.out.print("Ingrese ID del Producto a retirar: ");
                    String idRetiro = scanner.nextLine();

                    try {
                        System.out.print("Ingrese Cantidad a retirar: ");
                        int cantRetiro = Integer.parseInt(scanner.nextLine());
                        gestor.procesarSalida(idRetiro, cantRetiro);
                    } catch (NumberFormatException e) {
                        System.out.println("Error: La cantidad debe ser un numero.");
                    }
                    break;
                case 5:
                    System.out.println("Ingrese el ID del producto a retirar");
                    String P_eliminar = scanner.nextLine();
                    gestor.eliminarProducto(P_eliminar);
                    System.out.println("Producto elimnado con exito");
                    break;
                case 6:
                    salir = true;
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }
        }
        scanner.close();
    }
}