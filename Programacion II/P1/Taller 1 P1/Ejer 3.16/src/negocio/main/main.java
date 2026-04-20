package negocio.main;

import negocio.producto.FrecuenciasCardiacas;

import java.util.Scanner;


public class main {
    static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        FrecuenciasCardiacas p1 = new FrecuenciasCardiacas();
        System.out.println("Ingrese su nombre: ");
        p1.setNombre(sc.next());
        System.out.println("Ingrese su apellido: ");
        p1.setApellido(sc.next());
        System.out.println("Ingrese su dia de nacimiento: ");
        p1.setDia(sc.nextInt());
        System.out.println("Ingrese su mes de nacimiento: ");
        p1.setMes(sc.nextInt());
        System.out.println("Ingrese su año de nacimiento: ");
        p1.setAnio(sc.nextInt());

        int edad = p1.calcEdad(p1.getAnio(),p1.getMes());
        System.out.println("Su edad es de: "+edad);
        int freq = p1.calcfreq_max(edad);
        System.out.println("Su frecuencia cardiaca maxima es de: "+freq);

        System.out.println("Frecuencia cardiaca esperada = " + (p1.calcfreq_esperada(freq)));


    }
}
