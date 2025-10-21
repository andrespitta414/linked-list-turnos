package modelo;

import java.time.LocalDateTime;

public class Turno {
    private static int contadorTurno = 1;
    private int numeroTurno;
    private Cliente cliente;
    private LocalDateTime horaLlegada;
    private LocalDateTime horaAtencion;
    private String tipo;

    public Turno(Cliente cliente, String tipo) {
        this.numeroTurno = contadorTurno++;
        this.cliente = cliente;
        this.horaLlegada = LocalDateTime.now();
        this.tipo = tipo;
    }

    public int getNumeroTurno() { return numeroTurno; }
    public Cliente getCliente() { return cliente; }
    public LocalDateTime getHoraLlegada() { return horaLlegada; }
    public LocalDateTime getHoraAtencion() { return horaAtencion; }
    public void setHoraAtencion(LocalDateTime horaAtencion) { this.horaAtencion = horaAtencion; }
    public String getTipo() { return tipo; }

    @Override
    public String toString() {
        return "Turno{numero=" + numeroTurno + ", cliente=" + cliente.getNombre() + ", tipo=" + tipo + ", llegada=" + horaLlegada + "}";
    }
}