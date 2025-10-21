package modelo;

import java.time.LocalDateTime;
import java.time.Duration;

public class RegistroAtencion {
    private Cliente cliente;
    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;
    private String tipoAtencion;

    public RegistroAtencion(Cliente cliente, String tipoAtencion) {
        this.cliente = cliente;
        this.horaInicio = LocalDateTime.now();
        this.tipoAtencion = tipoAtencion;
    }

    public void finalizarAtencion() {
        this.horaFin = LocalDateTime.now();
    }

    public long calcularDuracion() {
        if (horaFin == null) return 0;
        return Duration.between(horaInicio, horaFin).toMinutes();
    }

    public Cliente getCliente() { return cliente; }
    public LocalDateTime getHoraInicio() { return horaInicio; }
    public LocalDateTime getHoraFin() { return horaFin; }
    public String getTipoAtencion() { return tipoAtencion; }
}