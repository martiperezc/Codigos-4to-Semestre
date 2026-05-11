package Ejer1.main;

import Ejer1.RegistroIP.RegistroIP;

public class main {
    public static void main(String[] args) {

        // Creamos nuestro arreglo
        RegistroIP[] listaRegistros = new RegistroIP[2];

        // Instanciación de los objetos directamente en el arreglo
        listaRegistros[0] = new RegistroIP("192.168.1.50", "08:30 AM", true);
        listaRegistros[1] = new RegistroIP("10.0.0.12", "11:45 PM", false);

        // Recorrido del arreglo, para imprimir resultados
        System.out.println("Reporte de Accesos de Red");
        for (RegistroIP registro : listaRegistros) {
            registro.mostrarEstado();
        }
    }
}
