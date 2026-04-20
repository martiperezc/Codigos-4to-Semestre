package negocio.producto;

public class FrecuenciasCardiacas {
    private String nombre,apellido;
    private int dia,mes,anio;


    //Constructor de frecuencias cardiacas.


    public FrecuenciasCardiacas() {
        this.nombre = "nombre";
        this.apellido = "apellido";
        this.dia = 0;
        this.mes = 0;
        this.anio = 0;
    }

    public FrecuenciasCardiacas(String nombre, String apellido, int dia, int mes, int anio_n) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dia = dia;
        this.mes = mes;
        this.anio = anio_n;
    }


    //Definimos metodos que nos pide el ejercicio

    public int calcEdad(int año, int mes){
        int edad= 2026-año;
        if(mes>4){
            edad -= 1;
        }
        return edad;
    }

    public int calcfreq_max(int edad){
        int freq= 220 - edad;
        return freq;
    }

    public double calcfreq_esperada(int freq){
        double freq_esperada = freq * 0.65;
        return freq_esperada;
    }


    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }
}
