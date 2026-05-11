package Ejer3.main;
import Ejer3.Usuario.Usuario;
import java.util.Scanner;

public class main {
    static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        Usuario[] listaUsuarios = new Usuario[2];
        // Llenado del arreglo mientras exista espacio disponible
        for (int i = 0; i < listaUsuarios.length; i++) {
            System.out.println("--- Registro del Usuario " + (i + 1) + " ---");
            Usuario user = new Usuario();

            System.out.print("Ingrese nombre de usuario: ");
            user.setNombreUsuario(entrada.nextLine());

            System.out.print("Ingrese correo electrónico: ");
            user.setCorreo(entrada.nextLine());

            System.out.print("Ingrese contraseña: ");
            user.setContrasena(entrada.nextLine());

            listaUsuarios[i] = user;
        }

        // Consultar un elemento específico por su índice
        System.out.print("\nIngrese el número de índice (0 a " + (listaUsuarios.length - 1) + ") para ver los datos: ");
        int indice = entrada.nextInt();

        if (indice >= 0 && indice < listaUsuarios.length) {
            System.out.println("Datos del usuario seleccionado:");
            listaUsuarios[indice].mostrarDatos();
        } else {
            System.out.println("El índice ingresado no es válido.");
        }

        // Listado completo de usuarios registrados usando for
        System.out.println("\nResumen total de registros:");
        for (int i = 0; i < listaUsuarios.length; i++) {
            listaUsuarios[i].mostrarDatos();
        }

    }
}
