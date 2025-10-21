package modelo;

import java.util.LinkedList;

public class GestorTurnos {
    private LinkedList<Turno> colaTurnos;
    private LinkedList<Turno> pilaUrgentes;
    private int atendidosNormales;
    private int atendidosUrgentes;
    private int maxCola;
    private int maxPila;
    private Turno clienteActual;

    public GestorTurnos() {
        this.colaTurnos = new LinkedList<>();
        this.pilaUrgentes = new LinkedList<>();
        this.atendidosNormales = 0;
        this.atendidosUrgentes = 0;
        this.maxCola = 0;
        this.maxPila = 0;
        this.clienteActual = null;
    }

    public void agregarClienteNormal(Turno turno) {
        colaTurnos.offer(turno);
        if (colaTurnos.size() > maxCola) maxCola = colaTurnos.size();
    }

    public void agregarClienteUrgente(Turno turno) {
        pilaUrgentes.push(turno);
        if (pilaUrgentes.size() > maxPila) maxPila = pilaUrgentes.size();
    }

    public Turno atenderSiguiente() {
        if (!pilaUrgentes.isEmpty()) {
            clienteActual = pilaUrgentes.pop();
            atendidosUrgentes++;
        } else if (!colaTurnos.isEmpty()) {
            clienteActual = colaTurnos.poll();
            atendidosNormales++;
        } else {
            clienteActual = null;
        }
        if (clienteActual != null) {
            clienteActual.setHoraAtencion(java.time.LocalDateTime.now());
        }
        return clienteActual;
    }

    public void moverUltimoAFrente() {
        if (!colaTurnos.isEmpty()) {
            Turno ultimo = colaTurnos.removeLast();
            colaTurnos.addFirst(ultimo);
        }
    }

    public void limpiar() {
        colaTurnos.clear();
        pilaUrgentes.clear();
        atendidosNormales = 0;
        atendidosUrgentes = 0;
        maxCola = 0;
        maxPila = 0;
        clienteActual = null;
    }


    public void offerFirst(Turno turno) { colaTurnos.offerFirst(turno); }
    public void offerLast(Turno turno) { colaTurnos.offerLast(turno); }
    public Turno peekFirst() { return colaTurnos.peekFirst(); }
    public Turno peekLast() { return colaTurnos.peekLast(); }
    public Turno pollFirst() { return colaTurnos.pollFirst(); }
    public Turno pollLast() { return colaTurnos.pollLast(); }
    public void addFirst(Turno turno) { colaTurnos.addFirst(turno); }
    public void addLast(Turno turno) { colaTurnos.addLast(turno); }
    public Turno removeFirst() { return colaTurnos.removeFirst(); }
    public Turno removeLast() { return colaTurnos.removeLast(); }
    public Turno getFirst() { return colaTurnos.getFirst(); }
    public Turno getLast() { return colaTurnos.getLast(); }


    public void offerFirstUrgente(Turno turno) { pilaUrgentes.offerFirst(turno); }
    public void offerLastUrgente(Turno turno) { pilaUrgentes.offerLast(turno); }
    public Turno peekFirstUrgente() { return pilaUrgentes.peekFirst(); }
    public Turno peekLastUrgente() { return pilaUrgentes.peekLast(); }
    public Turno pollFirstUrgente() { return pilaUrgentes.pollFirst(); }
    public Turno pollLastUrgente() { return pilaUrgentes.pollLast(); }
    public void addFirstUrgente(Turno turno) { pilaUrgentes.addFirst(turno); }
    public void addLastUrgente(Turno turno) { pilaUrgentes.addLast(turno); }
    public Turno removeFirstUrgente() { return pilaUrgentes.removeFirst(); }
    public Turno removeLastUrgente() { return pilaUrgentes.removeLast(); }
    public Turno getFirstUrgente() { return pilaUrgentes.getFirst(); }
    public Turno getLastUrgente() { return pilaUrgentes.getLast(); }


    public LinkedList<Turno> getColaTurnos() { return colaTurnos; }
    public LinkedList<Turno> getPilaUrgentes() { return pilaUrgentes; }
    public Turno getClienteActual() { return clienteActual; }
    public int getAtendidosNormales() { return atendidosNormales; }
    public int getAtendidosUrgentes() { return atendidosUrgentes; }
    public int getMaxCola() { return maxCola; }
    public int getMaxPila() { return maxPila; }
}