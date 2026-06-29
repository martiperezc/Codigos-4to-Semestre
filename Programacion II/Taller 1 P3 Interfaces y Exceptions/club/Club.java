package club;

import java.util.ArrayList;
import club.Socio.Tipo;

public class Club {
    public final static int MAXIMO_VIP = 3;
    private ArrayList<Socio> socios;

    public Club() {
        socios = new ArrayList<>();
    }

    public ArrayList<Socio> darSocios() { return socios; }

    // Propaga o genera excepciones según las restricciones globales del club
    public void afiliarSocio(String pCedula, String pNombre, Tipo pTipo) throws Exception {
        if (buscarSocio(pCedula) != null) {
            throw new Exception("Error: Ya existe un socio registrado con la cédula " + pCedula + ".");
        }
        if (pTipo == Tipo.VIP && contarSociosVIP() == MAXIMO_VIP) {
            throw new Exception("Error: El club en el momento no acepta más socios VIP (Cupo máximo de " + MAXIMO_VIP + " alcanzado).");
        }

        Socio nuevoSocio = new Socio(pCedula, pNombre, pTipo);
        socios.add(nuevoSocio);
    }

    public Socio buscarSocio(String pCedulaSocio) {
        for (Socio s : socios) {
            if (s.darCedula().equals(pCedulaSocio)) return s;
        }
        return null;
    }

    public int contarSociosVIP() {
        int conteo = 0;
        for (Socio socio : socios) {
            if (socio.darTipo() == Tipo.VIP) conteo++;
        }
        return conteo;
    }

    public ArrayList<String> darAutorizadosSocio(String pCedulaSocio) throws Exception {
        Socio s = buscarSocio(pCedulaSocio);
        if (s == null) throw new Exception("Error: El socio con cédula " + pCedulaSocio + " no existe.");

        ArrayList<String> autorizados = new ArrayList<>();
        autorizados.add(s.darNombre());
        autorizados.addAll(s.darAutorizados());
        return autorizados;
    }

    public void agregarAutorizadoSocio(String pCedulaSocio, String pNombreAutorizado) throws Exception {
        Socio s = buscarSocio(pCedulaSocio);
        if (s == null) throw new Exception("Error: El socio con cédula " + pCedulaSocio + " no existe.");
        s.agregarAutorizado(pNombreAutorizado);
    }

    public void eliminarAutorizadoSocio(String pCedulaSocio, String pNombreAutorizado) throws Exception {
        Socio s = buscarSocio(pCedulaSocio);
        if (s == null) throw new Exception("Error: El socio con cédula " + pCedulaSocio + " no existe.");
        s.eliminarAutorizado(pNombreAutorizado);
    }

    public void registrarConsumo(String pCedulaSocio, String pNombreCliente, String pConcepto, double pValor) throws Exception {
        Socio s = buscarSocio(pCedulaSocio);
        if (s == null) throw new Exception("Error: El socio con cédula " + pCedulaSocio + " no existe.");

        if (!s.darNombre().equalsIgnoreCase(pNombreCliente) && !s.existeAutorizado(pNombreCliente)) {
            throw new Exception("Error: La persona '" + pNombreCliente + "' no está autorizada para consumir en la cuenta de este socio.");
        }
        s.registrarConsumo(pNombreCliente, pConcepto, pValor);
    }

    public ArrayList<Factura> darFacturasSocio(String pCedulaSocio) throws Exception {
        Socio s = buscarSocio(pCedulaSocio);
        if (s == null) throw new Exception("Error: El socio con cédula " + pCedulaSocio + " no existe.");
        return s.darFacturas();
    }

    public void pagarFacturaSocio(String pCedulaSocio, int pFacturaIndice) throws Exception {
        Socio s = buscarSocio(pCedulaSocio);
        if (s == null) throw new Exception("Error: El socio con cédula " + pCedulaSocio + " no existe.");
        s.pagarFactura(pFacturaIndice);
    }

    public void aumentarFondosSocio(String pCedulaSocio, double pValor) throws Exception {
        Socio s = buscarSocio(pCedulaSocio);
        if (s == null) throw new Exception("Error: El socio con cédula " + pCedulaSocio + " no existe.");
        s.aumentarFondos(pValor);
    }

    // Métodos de Extensión e Ingeniería de Requerimientos
    public String metodo1() { return "respuesta1"; }
    public String metodo2() { return "respuesta2"; }

    public double calcularTotalConsumos(String pCedula) throws Exception {
        Socio s = buscarSocio(pCedula);
        if (s == null) {
            throw new Exception("Condición: No existe el socio solicitado.");
        }
        double total = 0;
        for (Factura f : s.darFacturas()) {
            total += f.darValor();
        }
        return total;
    }

    public String sePuedeEliminarSocio(String pCedula) {
        Socio s = buscarSocio(pCedula);
        if (s == null) return "Caso 1: No existe un socio con la cédula recibida como parámetro.";
        if (s.darTipo() == Tipo.VIP) return "Caso 2: El socio es de tipo VIP. No se pueden eliminar socios de tipo VIP.";
        if (!s.darFacturas().isEmpty()) return "Caso 3: El socio tiene facturas pendientes de pago.";
        if (s.darAutorizados().size() > 1) return "Caso 4: El socio tiene más de un autorizado.";

        return "El socio cumple todos los requisitos para ser eliminado.";
    }
}