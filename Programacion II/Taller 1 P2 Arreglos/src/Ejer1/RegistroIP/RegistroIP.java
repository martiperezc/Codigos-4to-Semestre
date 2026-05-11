package Ejer1.RegistroIP;
public class RegistroIP {
    // Definimos nuestros Atributos privados
    private String direccionIP;
    private String horaAcceso;
    private boolean permitida;


    // Constructor que inicializa todos los atributos
    public RegistroIP(String direccionIP, String horaAcceso, boolean permitida) {
        this.direccionIP = direccionIP;
        this.horaAcceso = horaAcceso;
        this.permitida = permitida;
    }

    //Metodos Getter and Setter
    public String getDireccionIP() {
        return direccionIP;
    }

    public void setDireccionIP(String direccionIP) {
        this.direccionIP = direccionIP;
    }

    public String getHoraAcceso() {
        return horaAcceso;
    }

    public void setHoraAcceso(String horaAcceso) {
        this.horaAcceso = horaAcceso;
    }

    public boolean isPermitida() {
        return permitida;
    }

    public void setPermitida(boolean permitida) {
        this.permitida = permitida;
    }

    // Método utilitario para mostrar el estado de la IP
    public void mostrarEstado() {
        String estado = permitida ? "ACCESO PERMITIDO" : "ACCESO DENEGADO";
        System.out.println("IP: " + direccionIP + " | Hora: " + horaAcceso + " | Estado: " + estado);
    }
}
