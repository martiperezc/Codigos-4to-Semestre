package club;

import java.util.ArrayList;

public class Socio {
    public final static double FONDOS_INICIALES_REGULARES = 50;
    public final static double FONDOS_INICIALES_VIP = 100;
    public final static double MONTO_MAXIMO_REGULARES = 1000;
    public final static double MONTO_MAXIMO_VIP = 5000;

    private String cedula;
    private String nombre;
    private double fondos;
    private Tipo tipoSubscripcion;
    private ArrayList<Factura> facturas;
    private ArrayList<String> autorizados;

    public enum Tipo { VIP, REGULAR }

    public Socio(String pCedula, String pNombre, Tipo pTipo) {
        cedula = pCedula;
        nombre = pNombre;
        tipoSubscripcion = pTipo;

        switch (tipoSubscripcion) {
            case VIP:
                fondos = FONDOS_INICIALES_VIP;
                break;
            default:
                fondos = FONDOS_INICIALES_REGULARES;
        }
        facturas = new ArrayList<>();
        autorizados = new ArrayList<>();
    }

    public String darNombre() { return nombre; }
    public String darCedula() { return cedula; }
    public double darFondos() { return fondos; }
    public Tipo darTipo() { return tipoSubscripcion; }
    public ArrayList<Factura> darFacturas() { return facturas; }
    public ArrayList<String> darAutorizados() { return autorizados; }

    public boolean existeAutorizado(String pNombreAutorizado) {
        for (String a : autorizados) {
            if (a.equalsIgnoreCase(pNombreAutorizado)) return true;
        }
        return false;
    }

    public boolean tieneFacturaAsociada(String pNombreAutorizado) {
        for (Factura f : facturas) {
            if (f.darNombre().equalsIgnoreCase(pNombreAutorizado)) return true;
        }
        return false;
    }

    // Lanza una excepción si se superan los montos máximos permitidos
    public void aumentarFondos(double pFondos) throws Exception {
        if (tipoSubscripcion == Tipo.VIP && pFondos + fondos > MONTO_MAXIMO_VIP) {
            throw new Exception("Error: Con este monto se excederían los fondos máximos de un socio VIP ($" + MONTO_MAXIMO_VIP + ").");
        } else if (tipoSubscripcion == Tipo.REGULAR && pFondos + fondos > MONTO_MAXIMO_REGULARES) {
            throw new Exception("Error: Con este monto se excederían los fondos máximos de un socio regular ($" + MONTO_MAXIMO_REGULARES + ").");
        }
        fondos += pFondos;
    }

    // Lanza una excepción si no hay dinero suficiente
    public void registrarConsumo(String pNombre, String pConcepto, double pValor) throws Exception {
        if (pValor > fondos) {
            throw new Exception("Error: El socio no posee fondos suficientes para este consumo (Saldo actual: $" + fondos + ").");
        }
        Factura nuevaFactura = new Factura(pNombre, pConcepto, pValor);
        facturas.add(nuevaFactura);
    }

    // Lanza excepciones si no cumple con las precondiciones de negocio
    public void agregarAutorizado(String pNombreAutorizado) throws Exception {
        if (pNombreAutorizado.equalsIgnoreCase(darNombre())) {
            throw new Exception("Error: No puede agregar al socio principal como un autorizado.");
        }
        if (fondos == 0) {
            throw new Exception("Error: El socio no tiene fondos en su cuenta para financiar autorizados.");
        }
        if (existeAutorizado(pNombreAutorizado)) {
            throw new Exception("Error: El autorizado '" + pNombreAutorizado + "' ya existe en la lista.");
        }
        autorizados.add(pNombreAutorizado);
    }

    // Lanza una excepción si tiene deudas pendientes
    public void eliminarAutorizado(String pNombreAutorizado) throws Exception {
        if (!existeAutorizado(pNombreAutorizado)) {
            throw new Exception("Error: No se encontró la persona autorizada en la lista.");
        }
        if (tieneFacturaAsociada(pNombreAutorizado)) {
            throw new Exception("Error: " + pNombreAutorizado + " tiene una factura sin pagar. No se puede eliminar.");
        }

        for (int i = 0; i < autorizados.size(); i++) {
            if (autorizados.get(i).equalsIgnoreCase(pNombreAutorizado)) {
                autorizados.remove(i);
                break;
            }
        }
    }

    // Lanza excepciones en base a fondos o índices erróneos
    public void pagarFactura(int pIndiceFactura) throws Exception {
        if (pIndiceFactura < 0 || pIndiceFactura >= facturas.size()) {
            throw new Exception("Error: El índice de factura seleccionado no es válido.");
        }

        Factura factura = facturas.get(pIndiceFactura);
        if (factura.darValor() > fondos) {
            throw new Exception("Error: El socio no posee fondos suficientes para pagar esta factura de $" + factura.darValor() + ".");
        }
        fondos -= factura.darValor();
        facturas.remove(pIndiceFactura);
    }

    @Override
    public String toString() {
        return cedula + " - " + nombre;
    }
}