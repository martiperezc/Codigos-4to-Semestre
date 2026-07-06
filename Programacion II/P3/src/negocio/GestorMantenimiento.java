package negocio;

import modelo.activos.*;
import modelo.actores.Tecnico;
import modelo.operaciones.Analisis;
import modelo.operaciones.Mantenimiento;
import modelo.excepciones.*;

import java.util.ArrayList;
import java.util.List;

public class GestorMantenimiento {
    private List<Motor> catalogoMotores = new ArrayList<>();
    private List<Tecnico> catalogoTecnicos = new ArrayList<>();
    private List<Analisis> todosLosAnalisis = new ArrayList<>();

    private int contadorAnalisis = 0;
    private int contadorMantenimiento = 0;
    private Tecnico tecnicoLogueado = null;

    public void registrarMotor(Motor motor) throws DatoInvalidoException {
        for (Motor m : catalogoMotores) {
            if (m.getCodigo().equals(motor.getCodigo())) {
                throw new DatoInvalidoException("El código del motor ya se encuentra registrado.");
            }
        }
        catalogoMotores.add(motor);
    }

    public void registrarTecnico(Tecnico tecnico) throws DatoInvalidoException {
        for (Tecnico t : catalogoTecnicos) {
            if (t.getCedula().equals(tecnico.getCedula())) {
                throw new DatoInvalidoException("La cédula de este técnico ya está registrada.");
            }
        }
        catalogoTecnicos.add(tecnico);
    }

    public boolean iniciarSesionTecnico(String cedula, String password) {
        for (Tecnico t : catalogoTecnicos) {
            if (t.getCedula().equals(cedula) && t.getPassword().equals(password)) {
                this.tecnicoLogueado = t;
                return true;
            }
        }
        return false;
    }

    public Motor buscarMotor(String codigo) throws EntidadNoEncontradaException {
        for (Motor m : catalogoMotores) {
            if (m.getCodigo().equals(codigo)) return m;
        }
        throw new EntidadNoEncontradaException("El motor con código '" + codigo + "' no está registrado.");
    }

    public Analisis generarAnalisisMotor(String codMotor, double v, double c, double t, double a)
            throws EntidadNoEncontradaException, DatoInvalidoException {
        Motor motor = buscarMotor(codMotor);
        contadorAnalisis++;
        Analisis nuevo = new Analisis(contadorAnalisis, motor, tecnicoLogueado, v, c, t, a);
        motor.getHistorial().agregarAnalisis(nuevo);
        todosLosAnalisis.add(nuevo);
        return nuevo;
    }

    public Mantenimiento ejecutarMantenimiento(String codMotor, String tareas)
            throws EntidadNoEncontradaException, DatoInvalidoException {
        Motor motor = buscarMotor(codMotor);
        Analisis ultimoAnalisis = null;

        for (int i = todosLosAnalisis.size() - 1; i >= 0; i--) {
            if (todosLosAnalisis.get(i).getMotor().getCodigo().equals(codMotor)) {
                ultimoAnalisis = todosLosAnalisis.get(i);
                break;
            }
        }

        if (ultimoAnalisis == null) {
            throw new DatoInvalidoException("No se puede hacer un mantenimiento si el motor no posee análisis previos.");
        }

        contadorMantenimiento++;
        Mantenimiento mant = new Mantenimiento(contadorMantenimiento, ultimoAnalisis, tecnicoLogueado, tareas);
        motor.getHistorial().agregarMantenimiento(mant);
        return mant;
    }

    public String obtenerEstadoActualMotor(String codigoMotor) {
        Analisis ultimoAnalisis = null;
        // Buscamos el análisis más reciente para este motor
        for (int i = todosLosAnalisis.size() - 1; i >= 0; i--) {
            if (todosLosAnalisis.get(i).getMotor().getCodigo().equals(codigoMotor)) {
                ultimoAnalisis = todosLosAnalisis.get(i);
                break;
            }
        }

        // Si no tiene análisis, está en su estado nominal por defecto
        if (ultimoAnalisis == null) {
            return "Buen Estado (Sin Novedad)";
        }

        // Si el análisis decía "Buen Estado", se queda así
        if ("Buen Estado".equalsIgnoreCase(ultimoAnalisis.getEstadoFinal())) {
            return "Buen Estado";
        }

        // Si requería atención (Preventivo/Correctivo), verificamos si ya se le hizo mantenimiento después
        boolean tieneMantenimientoPosterior = false;
        try {
            Motor m = buscarMotor(codigoMotor);
            if (!m.getHistorial().getListaMantenimientos().isEmpty()) {
                // Si la lista de mantenimientos no está vacía, asumimos que la última alerta fue atendida
                tieneMantenimientoPosterior = true;
            }
        } catch (Exception ignored) {}

        if (tieneMantenimientoPosterior) {
            return "Buen Estado (Mantenimiento Ejecutado)";
        } else {
            return "Necesita Mantenimiento (" + ultimoAnalisis.getEstadoFinal() + ")";
        }
    }


    public List<Motor> getCatalogoMotores() { return catalogoMotores; }
    public List<Tecnico> getCatalogoTecnicos() { return catalogoTecnicos; }
    public List<Analisis> getTodosLosAnalisis() { return todosLosAnalisis; }
    public Tecnico getTecnicoLogueado() { return tecnicoLogueado; }
    public void cerrarSesion() { this.tecnicoLogueado = null; }
}