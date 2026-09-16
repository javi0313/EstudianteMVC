package com.miapp.controlador;

import com.miapp.modelo.Estudiante;
import com.miapp.vista.EstudianteView;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

/**
 * Controlador: gestiona la lógica entre la Vista y el Modelo.
 *
 * El Controlador es el encargado de:
 * - Buscar estudiantes.
 * - Agregar estudiantes.
 * - Guardar los últimos resultados mostrados.
 * - Ordenar por nombre o promedio.
 * - Alternar entre orden ascendente y descendente.
 *
 * La Vista no conoce directamente al Modelo.
 */
public class EstudianteController {

    // ── Vista ────────────────────────────────────────────────────────────────

    private EstudianteView vista;

    // ── Array de estudiantes ────────────────────────────────────────────────

    private ArrayList<Estudiante> estudiantes;

    // ── Últimos resultados mostrados ─────────────────────────────────────────

    private List<Estudiante> ultimosResultados;

    // ── Control del orden ascendente / descendente ───────────────────────────

    private boolean ordenAscendente = true;

    // ── Constructor ─────────────────────────────────────────────────────────

    public EstudianteController(EstudianteView vista) {

        this.vista = vista;

        this.vista.setControlador(this);

        cargarDatos();
    }

    // ── Carga de datos iniciales ─────────────────────────────────────────────

    private void cargarDatos() {

        estudiantes = new ArrayList<>();

        ultimosResultados = new ArrayList<>();

        estudiantes.add(
                new Estudiante(
                        1,
                        "Javier Isaac",
                        "Ingeniería de Sistemas",
                        4.5
                )
        );

        estudiantes.add(
                new Estudiante(
                        2,
                        "Carlos López",
                        "Ingeniería Civil",
                        3.8
                )
        );

        estudiantes.add(
                new Estudiante(
                        3,
                        "María Rodríguez",
                        "Medicina",
                        4.9
                )
        );

        estudiantes.add(
                new Estudiante(
                        4,
                        "José Martínez",
                        "Derecho",
                        3.5
                )
        );

        estudiantes.add(
                new Estudiante(
                        5,
                        "Laura Sánchez",
                        "Administración",
                        4.1
                )
        );

        estudiantes.add(
                new Estudiante(
                        6,
                        "Andrés Torres",
                        "Ingeniería de Sistemas",
                        3.9
                )
        );

        estudiantes.add(
                new Estudiante(
                        7,
                        "Valentina Gómez",
                        "Psicología",
                        4.3
                )
        );

        estudiantes.add(
                new Estudiante(
                        8,
                        "Luis Herrera",
                        "Economía",
                        3.7
                )
        );

        estudiantes.add(
                new Estudiante(
                        9,
                        "Sofía Díaz",
                        "Ingeniería Civil",
                        4.6
                )
        );

        estudiantes.add(
                new Estudiante(
                        10,
                        "Juliana Morales",
                        "Medicina",
                        4.8
                )
        );

        estudiantes.add(
                new Estudiante(
                        11,
                        "Ana Milena Ruiz",
                        "Derecho",
                        4.0
                )
        );

        estudiantes.add(
                new Estudiante(
                        12,
                        "Carlos Andrés Paz",
                        "Administración",
                        3.6
                )
        );
    }

    // ── Búsqueda de estudiantes ─────────────────────────────────────────────

    public void buscarEstudiante(String criterio) {

        // Validar que el usuario haya escrito algo

        if (criterio == null || criterio.isEmpty()) {

            vista.mostrarError(
                    "Por favor ingrese un nombre para buscar."
            );

            return;
        }

        // Lista donde guardaremos los resultados

        List<Estudiante> resultados = new ArrayList<>();

        String criterioBajo = criterio.toLowerCase();

        // Recorrer todos los estudiantes

        for (Estudiante e : estudiantes) {

            if (e.getNombre().toLowerCase().contains(criterioBajo)) {

                resultados.add(e);
            }
        }

        // Guardamos los últimos resultados encontrados

        ultimosResultados = new ArrayList<>(resultados);

        // Una nueva búsqueda comienza nuevamente en ascendente

        ordenAscendente = true;

        // Mostrar resultados

        if (resultados.isEmpty()) {

            vista.mostrarEstudiantes(
                    new ArrayList<>()
            );

        } else if (resultados.size() == 1) {

            vista.mostrarEstudiante(
                    convertirAFila(resultados.get(0))
            );

        } else {

            vista.mostrarEstudiantes(
                    convertirAFilas(resultados)
            );
        }
    }

    // ── Agregar estudiante ──────────────────────────────────────────────────

    public void agregarEstudiante(
            String nombre,
            String carrera,
            double promedio) {

        // Validar nombre

        if (nombre == null || nombre.trim().isEmpty()) {

            vista.mostrarError(
                    "El nombre no puede estar vacío."
            );

            return;
        }

        // Validar promedio

        if (promedio < 0.0 || promedio > 5.0) {

            vista.mostrarError(
                    "El promedio debe estar entre 0.0 y 5.0."
            );

            return;
        }

        // Crear nuevo ID

        int nuevoId = estudiantes.size() + 1;

        // Crear estudiante

        Estudiante nuevoEstudiante = new Estudiante(
                nuevoId,
                nombre,
                carrera,
                promedio
        );

        // Agregar al ArrayList

        estudiantes.add(nuevoEstudiante);

        // Mostrar confirmación

        vista.mostrarConfirmacion(
                "El estudiante "
                + nombre
                + " fue agregado correctamente."
        );

        // Limpiar formulario

        vista.limpiarFormulario();

        // Mostrar todos los estudiantes

        ultimosResultados = new ArrayList<>(estudiantes);

        ordenAscendente = true;

        vista.mostrarEstudiantes(
                convertirAFilas(estudiantes)
        );
    }

    // ── Ordenar resultados ──────────────────────────────────────────────────

    /**
     * Ordena los últimos resultados mostrados.
     *
     * @param criterio puede ser "Nombre" o "Promedio"
     */
    public void ordenarPor(String criterio) {

        // Verificar si existen resultados anteriores

        if (ultimosResultados == null
                || ultimosResultados.isEmpty()) {

            vista.mostrarError(
                    "No hay resultados para ordenar. "
                    + "Primero realice una búsqueda."
            );

            return;
        }

        // Crear Comparator

        Comparator<Estudiante> comparador;

        // ─────────────────────────────────────────────────────────
        // ORDENAR POR NOMBRE
        // ─────────────────────────────────────────────────────────

        if (criterio.equals("Nombre")) {

            comparador = Comparator.comparing(
                    Estudiante::getNombre,
                    String.CASE_INSENSITIVE_ORDER
            );

        // ─────────────────────────────────────────────────────────
        // ORDENAR POR PROMEDIO
        // ─────────────────────────────────────────────────────────

        } else if (criterio.equals("Promedio")) {

            comparador = Comparator.comparingDouble(
                    Estudiante::getPromedio
            );

        // ─────────────────────────────────────────────────────────
        // CRITERIO NO VÁLIDO
        // ─────────────────────────────────────────────────────────

        } else {

            vista.mostrarError(
                    "Criterio de ordenamiento no válido."
            );

            return;
        }

        // ─────────────────────────────────────────────────────────
        // ASCENDENTE / DESCENDENTE
        // ─────────────────────────────────────────────────────────

        if (!ordenAscendente) {

            comparador = comparador.reversed();
        }

        // Aplicar ordenamiento

        ultimosResultados.sort(comparador);

        // Mostrar nuevamente los resultados ordenados

        vista.mostrarEstudiantes(
                convertirAFilas(ultimosResultados)
        );

        // Cambiar el sentido para el próximo clic

        ordenAscendente = !ordenAscendente;
    }

    // ── Convertir Estudiante → fila ──────────────────────────────────────────

    private Object[] convertirAFila(Estudiante e) {

        return new Object[]{
            e.getId(),
            e.getNombre(),
            e.getCarrera(),
            String.format(
                    "%.2f",
                    e.getPromedio()
            )
        };
    }

    // ── Convertir lista de Estudiante → filas ───────────────────────────────

    private List<Object[]> convertirAFilas(
            List<Estudiante> lista) {

        List<Object[]> filas = new ArrayList<>();

        for (Estudiante e : lista) {

            filas.add(
                    convertirAFila(e)
            );
        }

        return filas;
    }
}