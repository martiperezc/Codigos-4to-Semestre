package negocio.producto;

public class Empleado {
    //Definimos nuestras variables a utilizar
    
    private String nombre,apellido;
    private  double salario;

    //Creamos nuestro constructor
    public Empleado(String nombre, double salario, String apellido) {
        this.nombre = nombre;
        this.salario = salario;
        this.apellido = apellido;
    }

    //Creamos nuestros metodos get y set

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        if(salario>0){
            this.salario = salario;
        }
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
