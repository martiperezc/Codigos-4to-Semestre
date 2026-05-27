package Negocio;

import Modelo.Lote;
import Modelo.Producto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GestorInventario {

    private List<Producto> productos;

    // Constructor
    public GestorInventario() {
        productos = new ArrayList<>();
    }

    // Registrar producto
    public void registrarProducto(Producto producto) {
        productos.add(producto);
    }

    // Registrar lote
    public void registrarLote(Producto producto, Lote lote) {
        producto.agregarLote(lote);
    }

    // Buscar producto por ID
    public Producto buscarProducto(String idProducto) {
        for (Producto p : productos) {
            if (p.getIdProducto().equals(idProducto)) {
                return p;
            }
        }
        return null; // Retorna null si no lo encuentra
    }

    // Eliminar producto
    public void eliminarProducto(String idProducto) {
        productos.removeIf(p -> p.getIdProducto().equals(idProducto));
    }

    // Procesar salida (PEPS)
    public void procesarSalida(String idProducto, int cantidad) {
        Producto producto = buscarProducto(idProducto);

        if (producto == null) {
            System.out.println("Error: Producto no encontrado.");
            return;
        }

        // Ordenar lotes por fecha de caducidad (Lógica PEPS)
        producto.getLotes().sort(Comparator.comparing(Lote::getFechaCaducidad));

        int restante = cantidad;

        for (Lote lote : producto.getLotes()) {
            if (restante <= 0) break; // Si ya cubrimos la cantidad, salimos del ciclo

            // Saltar estrictamente los lotes que ya no sirven
            if (lote.getEstadoLote().equals("CADUCADO") || lote.getEstadoLote().equals("AGOTADO")) {
                continue;
            }

            int stock = lote.getCantidadStock();

            // Lógica de sustracción
            if (stock >= restante) {
                lote.setCantidadStock(stock - restante);
                restante = 0;
            } else {
                restante -= stock;
                lote.setCantidadStock(0);
            }
        }

        // Validación final de la transacción
        if (restante > 0) {
            System.out.println("Advertencia: Stock insuficiente para cubrir la demanda. Faltaron: " + restante + " unidades.");
        } else {
            System.out.println("Salida procesada con éxito.");
        }
    }

    // Mostrar inventario completo
    public String mostrarInventario() {
        StringBuilder sb = new StringBuilder();

        for (Producto p : productos) {
            sb.append(p.mostrarInfo()).append("\n");

            for (Lote l : p.getLotes()) {
                sb.append("  Lote: ").append(l.getIdLote())
                        .append(" | Stock: ").append(l.getCantidadStock())
                        .append(" | Estado: ").append(l.getEstadoLote())
                        .append(" | Caduca en: ").append(l.getDiasCaducar()).append(" días\n");
            }
        }
        return sb.toString();
    }

    // Actualizar estados diarios de todos los lotes
    public void actualizarEstadosDiarios() {
        LocalDate hoy = LocalDate.now();
        for (Producto p : productos) {
            for (Lote l : p.getLotes()) {
                l.calcularDiasCaducar(hoy);
                l.actualizarEstado();
            }
        }
    }

    // Generar reporte de alertas de caducidad
    public void generarAlertaCaducidad() {
        System.out.println("\n=== ALERTAS DE CADUCIDAD ===");
        boolean hayAlertas = false;

        for (Producto p : productos) {
            for (Lote l : p.getLotes()) {
                // Solo alertar si está disponible y los días son menores o iguales al límite
                if (l.getEstadoLote().equals("DISPONIBLE") && l.getDiasCaducar() <= l.getAlertaDias()) {
                    System.out.println("ALERTA: Producto " + p.getNombreProducto() +
                            " | Lote: " + l.getIdLote() +
                            " | Caduca en " + l.getDiasCaducar() + " días.");
                    hayAlertas = true;
                }
            }
        }

        if (!hayAlertas) {
            System.out.println("Todo en orden. No hay lotes próximos a caducar.");
        }
        System.out.println("============================\n");
    }
}