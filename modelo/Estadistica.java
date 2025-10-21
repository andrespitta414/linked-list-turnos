package modelo;

public class Estadistica {
    private int atendidosNormales;
    private int atendidosUrgentes;
    private int maxCola;
    private int maxPila;
    private Turno clienteActual;

    public void actualizar(GestorTurnos gestor) {
        this.atendidosNormales = gestor.getAtendidosNormales();
        this.atendidosUrgentes = gestor.getAtendidosUrgentes();
        this.maxCola = gestor.getMaxCola();
        this.maxPila = gestor.getMaxPila();
        this.clienteActual = gestor.getClienteActual();
    }

    public String resumen() {
        String actual = (clienteActual != null) ? clienteActual.toString() : "Ninguno";
        return "Estadísticas:\n" +
                "Atendidos Normales: " + atendidosNormales + "\n" +
                "Atendidos Urgentes: " + atendidosUrgentes + "\n" +
                "Máx. Cola: " + maxCola + "\n" +
                "Máx. Pila: " + maxPila + "\n" +
                "Cliente Actual: " + actual;
    }
}