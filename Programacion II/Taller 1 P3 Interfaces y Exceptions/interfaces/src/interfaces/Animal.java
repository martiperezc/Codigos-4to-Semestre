package interfaces;

public abstract class Animal implements IAnimal{
    private String nombre;

    public Animal(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public abstract String tipoAnimal();
}
