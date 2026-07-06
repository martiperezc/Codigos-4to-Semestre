package modelo.actores;

public class Tecnico {
    private String cedula;
    private String nombre;
    private String specialty;
    private String password; //

    public Tecnico(String cedula, String nombre, String specialty, String password) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.specialty = specialty;
        this.password = password;
    }

    public String getCedula() { return cedula; }
    public String getNombre() { return nombre; }
    public String getEspecialidad() { return specialty; }
    public String getPassword() { return password; }

    @Override
    public String toString() {
        return nombre + " (CI: " + cedula + ") - Esp: " + specialty;
    }
}