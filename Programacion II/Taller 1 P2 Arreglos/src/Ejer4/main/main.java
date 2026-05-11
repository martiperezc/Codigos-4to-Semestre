package Ejer4.main;
import Ejer4.SensorTemperatura.SensorTemperatura;
import java.util.Scanner;

public class main {
    static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        // Definimos un arreglo para 2 sensores
        SensorTemperatura[] sensores = new SensorTemperatura[2];

        // Llenado secuencial del arreglo
        for (int i = 0; i < sensores.length; i++) {
            System.out.println("Configuración del sensor en la posición " + i);

            System.out.print("Ingrese el ID del sensor: ");
            String id = teclado.nextLine();

            System.out.print("Ingrese el valor de temperatura actual: ");
            double valor = teclado.nextDouble();
            teclado.nextLine(); // Limpiar el buffer

            System.out.print("Ingrese la unidad (C/F/K): ");
            String unidad = teclado.nextLine();

            // Creación del objeto dentro del arreglo
            sensores[i] = new SensorTemperatura(id, valor, unidad);
        }

        // Acceso a un elemento específico por índice
        System.out.print("\n¿Qué índice de sensor desea consultar? (0 a " + (sensores.length - 1) + "): ");
        int indiceBusqueda = teclado.nextInt();

        if (indiceBusqueda >= 0 && indiceBusqueda < sensores.length) {
            System.out.println("Resultado de la consulta individual:");
            sensores[indiceBusqueda].mostrarLectura();
        } else {
            System.out.println("El índice solicitado no existe en el arreglo.");
        }

        // Recorrido completo del arreglo para reporte final
        System.out.println("\nListado general de todos los sensores registrados:");
        for (int i = 0; i < sensores.length; i++) {
            sensores[i].mostrarLectura();
        }

    }
}
