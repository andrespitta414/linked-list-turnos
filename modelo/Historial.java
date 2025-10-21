package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Historial {
    private List<RegistroAtencion> registros;
    private int totalAtenciones;
    private LocalDateTime ultimaActualizacion;

    public Historial() {
        this.registros = new ArrayList<>();
        this.totalAtenciones = 0;
        this.ultimaActualizacion = LocalDateTime.now();
    }

    public void agregarRegistro(RegistroAtencion registro) {
        registros.add(registro);
        totalAtenciones++;
        ultimaActualizacion = LocalDateTime.now();
    }

    public List<RegistroAtencion> buscarPorCliente(String nombre) {
        return registros.stream()
                .filter(r -> r.getCliente().getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .collect(Collectors.toList());
    }

    public void limpiar() {
        registros.clear();
        totalAtenciones = 0;
        ultimaActualizacion = LocalDateTime.now();
    }

    public void mostrarHistorial() {
        System.out.println("Historial de Atenciones:");
        for (RegistroAtencion r : registros) {
            System.out.println(r.getCliente().getNombre() + " - " + r.getTipoAtencion() + " - Duración: " + r.calcularDuracion() + " min");
        }
    }

    public List<RegistroAtencion> getRegistros() { return registros; }
    public int getTotalAtenciones() { return totalAtenciones; }
    public LocalDateTime getUltimaActualizacion() { return ultimaActualizacion; }
}