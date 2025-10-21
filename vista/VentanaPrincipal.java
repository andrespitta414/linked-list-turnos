package vista;

import controlador.ControladorAtencion;
import modelo.Turno;
import modelo.RegistroAtencion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaPrincipal extends JFrame {
    private ControladorAtencion controlador;


    private JLabel lblEstado;
    private JTextArea txtCola, txtPila;
    private JTable tblHistorial;
    private DefaultTableModel modeloHistorial;
    private JLabel lblUrgentes, lblCola, lblMaxCola, lblMaxPila;


    private DialogoCaptura dialogoCaptura;

    public VentanaPrincipal() {
        controlador = new ControladorAtencion(this);
        dialogoCaptura = new DialogoCaptura(this);
        inicializarComponentes();
        setTitle("Simulador de Turnos (LinkedList) — MVC");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        controlador.actualizarVista();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout());

        setJMenuBar(crearMenuBar());

        add(crearToolBar(), BorderLayout.NORTH);

        lblEstado = new JLabel("Atendiendo ahora: (sin atención en curso)");
        lblEstado.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        add(lblEstado, BorderLayout.SOUTH);

        JSplitPane splitPrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPrincipal.setLeftComponent(crearPanelCola());
        splitPrincipal.setRightComponent(crearPanelPila());
        splitPrincipal.setDividerLocation(500);
        splitPrincipal.setResizeWeight(0.5);

        JSplitPane splitInferior = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitInferior.setLeftComponent(crearPanelMetricas());
        splitInferior.setRightComponent(crearPanelHistorial());
        splitInferior.setDividerLocation(300);
        splitInferior.setResizeWeight(0.5);

        JSplitPane splitVertical = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitVertical.setTopComponent(splitPrincipal);
        splitVertical.setBottomComponent(splitInferior);
        splitVertical.setDividerLocation(400);
        splitVertical.setResizeWeight(0.6);

        add(splitVertical, BorderLayout.CENTER);
    }

    private JMenuBar crearMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuCliente = new JMenu("Cliente");
        JMenu menuAtencion = new JMenu("Atención");
        menuBar.add(menuCliente);
        menuBar.add(menuAtencion);
        return menuBar;
    }

    private JToolBar crearToolBar() {
        JToolBar toolBar = new JToolBar();
        JButton btnCapturar = new JButton("Capturar datos...");
        btnCapturar.setIcon(UIManager.getIcon("FileView.fileIcon"));
        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setIcon(UIManager.getIcon("FileView.computerIcon"));
        JButton btnAtender = new JButton("Atender");
        btnAtender.setIcon(UIManager.getIcon("FileView.hardDriveIcon"));
        JButton btnMover = new JButton("Mover último→frente");
        btnMover.setIcon(UIManager.getIcon("FileView.floppyDriveIcon"));
        JButton btnHistorial = new JButton("Historial");
        btnHistorial.setIcon(UIManager.getIcon("FileView.detailsViewIcon"));
        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setIcon(UIManager.getIcon("FileView.homeFolderIcon"));
        JButton btnSalir = new JButton("Salir");
        btnSalir.setIcon(UIManager.getIcon("InternalFrame.closeIcon"));

        toolBar.add(btnCapturar);
        toolBar.add(btnRegistrar);
        toolBar.add(btnAtender);
        toolBar.add(btnMover);
        toolBar.add(btnHistorial);
        toolBar.add(btnLimpiar);
        toolBar.add(btnSalir);

        btnCapturar.addActionListener(e -> dialogoCaptura.setVisible(true));
        btnRegistrar.addActionListener(e -> registrarCliente());
        btnAtender.addActionListener(e -> controlador.atender());
        btnMover.addActionListener(e -> controlador.moverUltimoAFrente());
        btnHistorial.addActionListener(e -> mostrarHistorialCompleto());
        btnLimpiar.addActionListener(e -> controlador.limpiar());
        btnSalir.addActionListener(e -> System.exit(0));

        return toolBar;
    }

    private JPanel crearPanelCola() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("COLA (head → tail)"));
        txtCola = new JTextArea();
        txtCola.setEditable(false);
        txtCola.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        panel.add(new JScrollPane(txtCola), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelPila() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("PILA (top → …)"));
        txtPila = new JTextArea();
        txtPila.setEditable(false);
        txtPila.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        panel.add(new JScrollPane(txtPila), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelMetricas() {
        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.setBorder(BorderFactory.createTitledBorder("Métricas en vivo"));
        panel.add(new JLabel("Urgentes:"));
        lblUrgentes = new JLabel("0");
        panel.add(lblUrgentes);
        panel.add(new JLabel("Cola:"));
        lblCola = new JLabel("0");
        panel.add(lblCola);
        panel.add(new JLabel("Máx COLA:"));
        lblMaxCola = new JLabel("0");
        panel.add(lblMaxCola);
        panel.add(new JLabel("Máx PILA:"));
        lblMaxPila = new JLabel("0");
        panel.add(lblMaxPila);
        return panel;
    }

    private JPanel crearPanelHistorial() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Historial de atenciones (en vivo)"));
        modeloHistorial = new DefaultTableModel(new String[]{"Fecha/Hora", "Tipo", "Cliente"}, 0);
        tblHistorial = new JTable(modeloHistorial);
        tblHistorial.setEnabled(false);
        panel.add(new JScrollPane(tblHistorial), BorderLayout.CENTER);
        return panel;
    }

    private void registrarCliente() {
        String nombre = dialogoCaptura.getNombre();
        String email = dialogoCaptura.getEmail();
        String telefono = dialogoCaptura.getTelefono();
        boolean urgente = dialogoCaptura.isUrgente();

        if (nombre.isEmpty() || email.isEmpty() || telefono.isEmpty()) {
            mostrarError("Todos los campos son obligatorios.");
            return;
        }

        controlador.agregarCliente(nombre, email, telefono, urgente);
        dialogoCaptura.limpiarCampos();
    }

    public void mostrarEstado(List<Turno> cola, List<Turno> pila, Turno actual) {
        StringBuilder sbCola = new StringBuilder();
        if (cola.isEmpty()) {
            sbCola.append("[vacía]");
        } else {
            for (Turno t : cola) {
                sbCola.append(t.getCliente().getNombre()).append(" (").append(t.getTipo()).append(")\n");
            }
        }
        txtCola.setText(sbCola.toString());

        StringBuilder sbPila = new StringBuilder();
        if (pila.isEmpty()) {
            sbPila.append("[vacía]");
        } else {
            for (Turno t : pila) {
                sbPila.append(t.getCliente().getNombre()).append(" (").append(t.getTipo()).append(")\n");
            }
        }
        txtPila.setText(sbPila.toString());

        lblEstado.setText("Atendiendo ahora: " + (actual != null ? actual.getCliente().getNombre() : "(sin atención en curso)"));
    }

    public void actualizarMetricas(int urgentes, int cola, int maxCola, int maxPila) {
        lblUrgentes.setText(String.valueOf(urgentes));
        lblCola.setText(String.valueOf(cola));
        lblMaxCola.setText(String.valueOf(maxCola));
        lblMaxPila.setText(String.valueOf(maxPila));
    }

    public void agregarRegistroHistorial(RegistroAtencion registro) {
        modeloHistorial.addRow(new Object[]{
            registro.getHoraInicio().toString(),
            registro.getTipoAtencion(),
            registro.getCliente().getNombre()
        });
    }

    private void mostrarHistorialCompleto() {
        JDialog dialog = new JDialog(this, "Historial Completo", true);
        dialog.setLayout(new BorderLayout());
        JTable table = new JTable(modeloHistorial);
        dialog.add(new JScrollPane(table), BorderLayout.CENTER);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private static class DialogoCaptura extends JDialog {
        private JTextField txtNombre, txtEmail, txtTelefono;
        private JCheckBox chkUrgente;
        private JButton btnAceptar, btnCancelar;

        public DialogoCaptura(JFrame parent) {
            super(parent, "Capturar Datos del Cliente", true);
            inicializar();
            setSize(300, 250);
            setLocationRelativeTo(parent);
        }

        private void inicializar() {
            setLayout(new GridLayout(5, 2));
            add(new JLabel("Nombre:"));
            txtNombre = new JTextField();
            add(txtNombre);
            add(new JLabel("Email:"));
            txtEmail = new JTextField();
            add(txtEmail);
            add(new JLabel("Teléfono:"));
            txtTelefono = new JTextField();
            add(txtTelefono);
            add(new JLabel("Urgente:"));
            chkUrgente = new JCheckBox();
            add(chkUrgente);

            btnAceptar = new JButton("Aceptar");
            btnCancelar = new JButton("Cancelar");
            add(btnAceptar);
            add(btnCancelar);

            btnAceptar.addActionListener(e -> setVisible(false));
            btnCancelar.addActionListener(e -> {
                limpiarCampos();
                setVisible(false);
            });
        }

        public String getNombre() { return txtNombre.getText().trim(); }
        public String getEmail() { return txtEmail.getText().trim(); }
        public String getTelefono() { return txtTelefono.getText().trim(); }
        public boolean isUrgente() { return chkUrgente.isSelected(); }

        public void limpiarCampos() {
            txtNombre.setText("");
            txtEmail.setText("");
            txtTelefono.setText("");
            chkUrgente.setSelected(false);
        }
    }
}