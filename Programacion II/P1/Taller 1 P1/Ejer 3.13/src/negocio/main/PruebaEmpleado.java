package negocio.main;

//Comando psvma para crear el main

import negocio.producto.Empleado;

import javax.swing.*;
import java.util.Scanner;

public class PruebaEmpleado {

    static void main(String[] args) {
        //Instanciamos
        Scanner sc= new Scanner(System.in);
        Empleado e1 = new Empleado();
        Empleado e2 = new Empleado();

        e1.setNombre(JOptionPane.showInputDialog("Ingrese su nombre: "));
        e1.setApellido(JOptionPane.showInputDialog("Ingrese su apellido: "));
        e1.setSalario(Double.parseDouble(JOptionPane.showInputDialog("Ingrese su salario: ")));

        e2.setNombre(JOptionPane.showInputDialog("Ingrese su nombre: "));
        e2.setApellido(JOptionPane.showInputDialog("Ingrese su apellido: "));
        e2.setSalario(Double.parseDouble(JOptionPane.showInputDialog("Ingrese su salario: ")));


        System.out.println("El salario anual de Martin es de "+(e1.getSalario()*12));
        System.out.println("El salario anual de Sebs es de "+(e2.getSalario()*12));


        //Asignamos nuevo salario
        e1.setSalario(e1.getSalario() * 1.10);
        e2.setSalario(e2.getSalario()* 1.10);

        //Imprimir en pantalla nuevo salario anual de martin y de sebs
        System.out.printf("El nuevo salario anual del empleado 1: es de: %.2f%n", e1.getSalario()*12);
        System.out.printf("El nuevo salario anual del empleado 2 es de: %.2f%n", e2.getSalario()*12);

    }

}
