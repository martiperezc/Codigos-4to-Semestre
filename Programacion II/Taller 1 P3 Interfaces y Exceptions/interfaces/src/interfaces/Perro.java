package interfaces;

public class Perro extends Animal{
    public Perro(String nombre) {
        super(nombre);
    }

    @Override
    public String tipoAnimal(){
        return "perro";
    }

    @Override
    public String comunicarse() {
        return "Guau dijo el perro";
    }
}
