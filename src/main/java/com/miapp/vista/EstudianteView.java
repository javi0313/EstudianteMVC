package com.miapp.vista;

import com.miapp.controlador.EstudianteController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Vista: JFrame principal del módulo Estudiante.
 *
 * La Vista solamente se encarga de:
 * - Mostrar componentes.
 * - Capturar lo que escribe el usuario.
 * - Llamar al Controlador.
 * - Mostrar los resultados recibidos.
 *
 * La Vista NO contiene lógica de búsqueda ni de ordenamiento.
 */
public class EstudianteView extends JFrame {

    // ── Componentes de búsqueda ─────────────────────────────────────────────

    private JTextField txtNombreBuscar;
private JButton btnBuscar;
private JButton btnMostrarTodos;

    // ── Componentes para registrar estudiante ────────────────────────────────

    private JTextField txtNombre;
    private JTextField txtCarrera;
    private JTextField txtPromedio;

    private JButton btnAgregar;

    // ── Componentes para ordenar ─────────────────────────────────────────────

    private JComboBox<String> comboOrdenar;
    private JButton btnOrdenar;

    // ── Tabla ────────────────────────────────────────────────────────────────

    private JTable tblResultados;
    private DefaultTableModel modeloTabla;

    // ── Mensaje de estado ────────────────────────────────────────────────────

    private JLabel lblEstado;

    // ── Controlador ──────────────────────────────────────────────────────────

    private EstudianteController controlador;

    // ── Constructor ──────────────────────────────────────────────────────────

    public EstudianteView() {

        initComponentes();

        initEventos();
    }

    // ── Inicialización de componentes ────────────────────────────────────────

    private void initComponentes() {

        setTitle(
                "Búsqueda de Estudiantes — MVC NetBeans"
        );

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setSize(900, 600);

        setLocationRelativeTo(null);

        setLayout(
                new BorderLayout(10, 10)
        );

        // Color utilizado para los botones

        Color verdeOliva = new Color(
                107,
                142,
                35
        );

        // =========================================================
        // PANEL SUPERIOR
        // =========================================================

        JPanel panelSuperior = new JPanel();

        panelSuperior.setLayout(
                new BoxLayout(
                        panelSuperior,
                        BoxLayout.Y_AXIS
                )
        );

        panelSuperior.setBorder(
                BorderFactory.createEmptyBorder(
                        5,
                        10,
                        5,
                        10
                )
        );

        // =========================================================
        // PANEL BUSCAR
        // =========================================================

        JPanel panelBusqueda = new JPanel(
                new FlowLayout(
                        FlowLayout.LEFT,
                        10,
                        10
                )
        );

        panelBusqueda.setBorder(
                BorderFactory.createTitledBorder(
                        "Buscar estudiante"
                )
        );

        JLabel lblBuscar = new JLabel(
                "Nombre:"
        );

        txtNombreBuscar = new JTextField(30);

        btnBuscar = new JButton(
                "Buscar"
        );

        btnBuscar.setBackground(
                verdeOliva
        );

        btnBuscar.setForeground(
                Color.WHITE
        );

        btnBuscar.setFocusPainted(
        false
);

btnMostrarTodos = new JButton(
        "Mostrar todos"
);

btnMostrarTodos.setBackground(
        verdeOliva
);

btnMostrarTodos.setForeground(
        Color.WHITE
);

btnMostrarTodos.setFocusPainted(
        false
);

panelBusqueda.add(
        lblBuscar
);

        panelBusqueda.add(
                txtNombreBuscar
        );

       panelBusqueda.add(
        btnBuscar
);

panelBusqueda.add(
        btnMostrarTodos
);

        // =========================================================
        // PANEL REGISTRAR
        // =========================================================

        JPanel panelAgregar = new JPanel(
                new FlowLayout(
                        FlowLayout.LEFT,
                        10,
                        10
                )
        );

        panelAgregar.setBorder(
                BorderFactory.createTitledBorder(
                        "Registrar nuevo estudiante"
                )
        );

        JLabel lblNombre = new JLabel(
                "Nombre:"
        );

        txtNombre = new JTextField(18);

        JLabel lblCarrera = new JLabel(
                "Carrera:"
        );

        txtCarrera = new JTextField(18);

        JLabel lblPromedio = new JLabel(
                "Promedio:"
        );

        txtPromedio = new JTextField(8);

        btnAgregar = new JButton(
                "Agregar"
        );

        btnAgregar.setBackground(
                verdeOliva
        );

        btnAgregar.setForeground(
                Color.WHITE
        );

        btnAgregar.setFocusPainted(
                false
        );

        panelAgregar.add(
                lblNombre
        );

        panelAgregar.add(
                txtNombre
        );

        panelAgregar.add(
                lblCarrera
        );

        panelAgregar.add(
                txtCarrera
        );

        panelAgregar.add(
                lblPromedio
        );

        panelAgregar.add(
                txtPromedio
        );

        panelAgregar.add(
                btnAgregar
        );

        // =========================================================
        // PANEL ORDENAR
        // =========================================================

        JPanel panelOrdenar = new JPanel(
                new FlowLayout(
                        FlowLayout.LEFT,
                        10,
                        5
                )
        );

        panelOrdenar.setBorder(
                BorderFactory.createTitledBorder(
                        "Ordenar resultados"
                )
        );

        JLabel lblOrdenar = new JLabel(
                "Ordenar por:"
        );

        comboOrdenar = new JComboBox<>(
                new String[]{
                    "Nombre",
                    "Promedio"
                }
        );

        btnOrdenar = new JButton(
                "Ordenar"
        );

        btnOrdenar.setBackground(
                verdeOliva
        );

        btnOrdenar.setForeground(
                Color.WHITE
        );

        btnOrdenar.setFocusPainted(
                false
        );

        panelOrdenar.add(
                lblOrdenar
        );

        panelOrdenar.add(
                comboOrdenar
        );

        panelOrdenar.add(
                btnOrdenar
        );

        // =========================================================
        // AGREGAR PANELES AL PANEL SUPERIOR
        // =========================================================

        panelSuperior.add(
                panelBusqueda
        );

        panelSuperior.add(
                panelAgregar
        );

        panelSuperior.add(
                panelOrdenar
        );

        // =========================================================
        // TABLA
        // =========================================================

        String[] columnas = {
            "ID",
            "Nombre",
            "Carrera",
            "Promedio"
        };

        modeloTabla = new DefaultTableModel(
                columnas,
                0
        ) {

            @Override
            public boolean isCellEditable(
                    int row,
                    int col) {

                return false;
            }
        };

        tblResultados = new JTable(
                modeloTabla
        );

        tblResultados.setRowHeight(
                24
        );

        tblResultados
                .getTableHeader()
                .setReorderingAllowed(
                        false
                );

        tblResultados.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scroll = new JScrollPane(
                tblResultados
        );

        scroll.setBorder(
                BorderFactory.createTitledBorder(
                        "Resultados"
                )
        );

        // =========================================================
        // ESTADO
        // =========================================================

        lblEstado = new JLabel(
                "Ingrese un nombre y presione Buscar."
        );

        lblEstado.setBorder(
                BorderFactory.createEmptyBorder(
                        4,
                        10,
                        4,
                        10
                )
        );

        lblEstado.setForeground(
                verdeOliva
        );

        // =========================================================
        // AGREGAR AL FRAME
        // =========================================================

        add(
                panelSuperior,
                BorderLayout.NORTH
        );

        add(
                scroll,
                BorderLayout.CENTER
        );

        add(
                lblEstado,
                BorderLayout.SOUTH
        );
    }

    // ── Eventos ─────────────────────────────────────────────────────────────

    private void initEventos() {

        // =========================================================
        // BOTÓN BUSCAR
        // =========================================================

        btnBuscar.addActionListener(
                (ActionEvent e) -> {

                    if (controlador != null) {

                        controlador.buscarEstudiante(
                                txtNombreBuscar
                                        .getText()
                                        .trim()
                        );
                    }
                }
        );
        
        btnMostrarTodos.addActionListener(
        (ActionEvent e) -> {

            if (controlador != null) {

                controlador.mostrarTodos();
            }
        }
);

        // Permitir buscar presionando ENTER

        txtNombreBuscar.addActionListener(
                (ActionEvent e) ->
                        btnBuscar.doClick()
        );

        // =========================================================
        // BOTÓN AGREGAR
        // =========================================================

        btnAgregar.addActionListener(
                (ActionEvent e) -> {

                    if (controlador != null) {

                        String nombre =
                                txtNombre
                                        .getText()
                                        .trim();

                        String carrera =
                                txtCarrera
                                        .getText()
                                        .trim();

                        double promedio;

                        try {

                            promedio =
                                    Double.parseDouble(
                                            txtPromedio
                                                    .getText()
                                                    .trim()
                                    );

                        } catch (
                                NumberFormatException ex) {

                            mostrarError(
                                    "El promedio debe ser "
                                    + "un número válido."
                            );

                            return;
                        }

                        controlador.agregarEstudiante(
                                nombre,
                                carrera,
                                promedio
                        );
                    }
                }
        );

        // =========================================================
        // BOTÓN ORDENAR
        // =========================================================

        btnOrdenar.addActionListener(
                (ActionEvent e) -> {

                    if (controlador != null) {

                        String criterio =
                                comboOrdenar
                                        .getSelectedItem()
                                        .toString();

                        controlador.ordenarPor(
                                criterio
                        );
                    }
                }
        );
    }

    // ── Métodos que utiliza el Controlador ───────────────────────────────────

    /**
     * Muestra un único estudiante.
     */
    public void mostrarEstudiante(
            Object[] fila) {

        limpiarTabla();

        agregarFila(fila);

        setEstado(
                "Se encontró 1 estudiante."
        );
    }

    /**
     * Muestra varios estudiantes.
     */
    public void mostrarEstudiantes(
            List<Object[]> filas) {

        limpiarTabla();

        if (filas == null
                || filas.isEmpty()) {

            setEstado(
                    "No se encontraron estudiantes "
                    + "con ese criterio."
            );

            return;
        }

        for (Object[] fila : filas) {

            agregarFila(fila);
        }

        setEstado(
                "Se encontraron "
                + filas.size()
                + " estudiante(s)."
        );
    }

    /**
     * Muestra un mensaje de error.
     */
    public void mostrarError(
            String mensaje) {

        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );

        setEstado(
                "Error: " + mensaje
        );
    }

    /**
     * Muestra una confirmación.
     */
    public void mostrarConfirmacion(
            String mensaje) {

        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Registro exitoso",
                JOptionPane.INFORMATION_MESSAGE
        );

        setEstado(
                mensaje
        );
    }

    /**
     * Limpia el formulario de registro.
     */
    public void limpiarFormulario() {

        txtNombre.setText("");

        txtCarrera.setText("");

        txtPromedio.setText("");

        txtNombre.requestFocus();
    }

    /**
     * Devuelve el nombre escrito en el campo de búsqueda.
     */
    public String getNombreBuscado() {

        return txtNombreBuscar
                .getText()
                .trim();
    }

    // ── Setter del Controlador ───────────────────────────────────────────────

    public void setControlador(
            EstudianteController controlador) {

        this.controlador = controlador;
    }

    // ── Métodos auxiliares ──────────────────────────────────────────────────

    private void agregarFila(
            Object[] fila) {

        modeloTabla.addRow(fila);
    }

    private void limpiarTabla() {

        modeloTabla.setRowCount(0);
    }

    private void setEstado(
            String texto) {

        lblEstado.setText(texto);
    }
}