package Ejer2.main;
import Ejer2.SistemaSeguridad.SistemaSeguridad;

public class main {
    static void main(String[] args) {
        SistemaSeguridad[] sistemas = new SistemaSeguridad[2];

        sistemas[0] = new SistemaSeguridad("Puerta Principal");
        sistemas[1] = new SistemaSeguridad("Base de Datos");

        sistemas[0].registrarIntentoFallido();

        sistemas[1].registrarIntentoFallido();
        sistemas[1].registrarIntentoFallido();
        sistemas[1].registrarIntentoFallido();

        for (SistemaSeguridad sistema : sistemas) {
            sistema.mostrarEstado();
        }

        System.out.println("\nSe ejecutó un restablecimiento en los sistemas afectados.\n");
        sistemas[1].reiniciarContador();

        for (SistemaSeguridad sistema : sistemas) {
            sistema.mostrarEstado();
        }
    }
}
