package modelo;

import java.util.concurrent.atomic.AtomicInteger;

public class Cliente {
    private static final AtomicInteger contadorId = new AtomicInteger(1);
    private int id;
    private String nombre;
    private String email;
    private String telefono;

    public Cliente(String nombre, String email, String telefono) {
        this.id = contadorId.getAndIncrement();
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }

    @Override
    public String toString() {
        return "Cliente{id=" + id + ", nombre='" + nombre + "', email='" + email + "', telefono='" + telefono + "'}";
    }
}