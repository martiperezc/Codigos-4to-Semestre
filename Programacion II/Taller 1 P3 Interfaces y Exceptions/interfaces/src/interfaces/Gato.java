package interfaces;

public class Gato extends Animal {
    public Gato(String nombre) {
        super(nombre);
    }

    @Override
    public String tipoAnimal(){
        return "gato";

    }
    @Override
    public String comunicarse(){
        return "Miau dijo el perro";
    }
}
