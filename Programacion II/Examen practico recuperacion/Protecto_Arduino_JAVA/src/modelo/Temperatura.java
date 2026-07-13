package modelo;

public class Temperatura {
    private float valorActual;

    // Encapsulamiento del estado
    public void setValorActual(float nuevaTemp) {
        this.valorActual = nuevaTemp;
    }

    public float getValorActual() {
        return this.valorActual;
    }

    public String evaluarLogica() {
        if (this.valorActual > 30.0f) {
            System.out.println("Estado: Alerta " + this.valorActual + "°C - Led rojo");
            return "H"; //Mandamos estado a arduino
        } else {
            System.out.println("Estado: Normal " + this.valorActual + "°C - Led verde");
            return "L";
        }
    }
}