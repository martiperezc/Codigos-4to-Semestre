package interfaz;

import modelo.GestorDiagnostico;

public class main {
    public static void main(String[] args) {

        // Instanciamos el controlador central
        GestorDiagnostico gestor = new GestorDiagnostico();

        // Arrancamos el flujo del sistema
        gestor.iniciarSistema();
    }
}