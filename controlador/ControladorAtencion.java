package controlador;

import modelo.*;
import vista.VentanaPrincipal;
import java.util.List;

public class ControladorAtencion {
    private GestorTurnos gestor;
    private VentanaPrincipal vista;
    private Estadistica estadistica;
    private Historial historial;

    public ControladorAtencion(VentanaPrincipal vista) {
        this.gestor = new GestorTurnos();
        this.vista = vista;
        this.estadistica = new Estadistica();
        this.historial = new Historial();
    }

    public void agregarCliente(String nombre, String email, String telefono, boolean urgente) {
        try {
            Cliente cliente = new Cliente(nombre, email, telefono);
            Turno turno = new Turno(cliente, urgente ? "URGENTE" : "NORMAL");
            if (urgente) {
                gestor.agregarClienteUrgente(turno);
            } else {
                gestor.agregarClienteNormal(turno);
            }
            actualizarVista();
        } catch (Exception e) {
            vista.mostrarError("Error al agregar cliente: " + e.getMessage());
        }
    }

    public void atender() {
        try {
            Turno atendido = gestor.atenderSiguiente();
            if (atendido != null) {
                RegistroAtencion registro = new RegistroAtencion(atendido.getCliente(), atendido.getTipo());
                registro.finalizarAtencion();
                historial.agregarRegistro(registro);
                vista.agregarRegistroHistorial(registro);
            }
            actualizarVista();
        } catch (Exception e) {
            vista.mostrarError("Error al atender: " + e.getMessage());
        }
    }

    public void moverUltimoAFrente() {
        try {
            gestor.moverUltimoAFrente();
            actualizarVista();
        } catch (Exception e) {
            vista.mostrarError("Error al mover: " + e.getMessage());
        }
    }

    public void limpiar() {
        try {
            gestor.limpiar();
            historial.limpiar();
            actualizarVista();
        } catch (Exception e) {
            vista.mostrarError("Error al limpiar: " + e.getMessage());
        }
    }

    public void actualizarVista() {
        estadistica.actualizar(gestor);
        List<Turno> cola = gestor.getColaTurnos();
        List<Turno> pila = gestor.getPilaUrgentes();
        Turno actual = gestor.getClienteActual();
        vista.mostrarEstado(cola, pila, actual);
        vista.actualizarMetricas(gestor.getPilaUrgentes().size(), gestor.getColaTurnos().size(), gestor.getMaxCola(), gestor.getMaxPila());
    }

    public Historial getHistorial() {
        return historial;
    }
}