package negocio.main;

import negocio.producto.Empleado;

import java.util.Scanner;

//Comando psvma para crear el main

public class PruebaEmpleado {

    static void main(String[] args) {
        //Instanciamos

        Scanner entrada = new Scanner(System.in);

        Empleado e3 = new Empleado();

        System.out.println("Ingrese el nombre del empleado: ");
        e3.setNombre(entrada.next());
        System.out.println("Ingrese el apellido del empleado: ");
        e3.setApellido(entrada.next());
        System.out.println("Ingrese el sueldo del empleado: ");
        e3.setSalario(entrada.nextDouble());

        System.out.println("El salario anual de e3 es de "+(e3.getSalario()*12));


        /*
        Empleado e1 = new Empleado("Martin", 800, "Perez");
        Empleado e2 = new Empleado("Sebs", 500, "Perez");

        System.out.println("El salario anual de Martin es de "+(e1.getSalario()*12));
        System.out.println("El salario anual de Sebs es de "+(e2.getSalario()*12));


        //Asignamos nuevo salario a martin  y a sebs
        e1.setSalario(e1.getSalario() * 1.10);
        e2.setSalario(e2.getSalario()*1.10);

        //Imprimir en pantalla nuevo salario anual de martin y de sebs
        System.out.println("El nuevo salario anaul de Martin es de: "+(e1.getSalario()*12));
        System.out.println("El nuevo salario anual de Sebs es de: "+(e2.getSalario()*12));


         */
    }

}
