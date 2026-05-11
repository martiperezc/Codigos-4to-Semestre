package Ejer3.Usuario;
public class Usuario {
    private String nombreUsuario;
    private String correo;
    private String contrasena;

    // Métodos Getter y Setter
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        // Validación de longitud de caracteres
        if (contrasena.length() >= 8) {
            this.contrasena = contrasena;
        } else {
            System.out.println("Advertencia: La contraseña es demasiado corta. Debe tener al menos 8 caracteres.");
            this.contrasena = "No definida";
        }
    }

    public void mostrarDatos() {
        System.out.println("Usuario: " + nombreUsuario + " | Correo: " + correo);
    }
}
