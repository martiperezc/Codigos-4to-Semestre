package Modelo;
import java.util.ArrayList;
import java.util.List;

public class Producto {
    private String idProducto;
    private String nombreProducto;
    private List<Lote> lotes;

    // Constructor
    public Producto(String idProducto, String nombreProducto) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.lotes = new ArrayList<>();
    }

    // Getters y setters
    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public List<Lote> getLotes() {
        return lotes;
    }

    // Agregar lote al producto
    public void agregarLote(Lote lote) {
        lotes.add(lote);
    }

    // Calcular stock total del producto
    public int calcularStockTotal() {
        int total = 0;
        for (Lote lote : lotes) {
            total += lote.getCantidadStock();
        }
        return total;
    }

    // Mostrar información
    public String mostrarInfo() {
        return "Producto: " + nombreProducto +
                " | ID: " + idProducto +
                " | Stock total: " + calcularStockTotal();
    }
}
