import practicas_pca.TesterPracticas;

/**
 * Punto de entrada de ejemplo para ejecutar el tester de la asignatura.
 *
 * Objetivo:
 * - Mostrar cómo se lanza una ejecución/experimento desde IntelliJ.
 * - Permitir cambiar (1) el algoritmo, (2) el tamaño de instancia y (3) el número de hilos.
 *
 * Nota: esta clase es una ayuda de uso. No forma parte de la implementación del algoritmo.
 */
public class PruebaUso {

    public static void main(String[] args) {
        // 1) Elegir el algoritmo a evaluar
        // Sustituye la clase que se pasa al constructor para probar otra práctica.
        TesterPracticas TP = new TesterPracticas(new BucketSortList());

        // 2) Elegir la instancia (tamaño del problema)
        // Estas constantes seleccionan el fichero de entrada que cargará la librería.
        // Empieza por tamaños pequeños para depurar y sube progresivamente.
        TesterPracticas.Instancias instancia = TesterPracticas.Instancias.NUMBER_25000000;

        // 3) Elegir el número de hilos solicitados al algoritmo
        // Para comparar rendimiento, probad 1, 2, 4, 8... según vuestra máquina.
        int numThreads = 4;

        // Ejecuta una evaluación concreta (instancia + hilos).
        //TP.evaluarPractica(instancia, numThreads);

        // --- Ejemplos opcionales (tiradas más completas) ---
        // Ejecutar todas las prácticas/instancias disponibles.
        // TP.evaluarPracticasALL();

        // Comparación entre prácticas (requiere haber ejecutado evaluaciones).
        // TP.graficarResultadosComparacion(TP.evaluarPracticasALL());

        // Comparación por número de hilos sobre una instancia dada.
        // TP.graficarResultadosComparacionThreads(TP.evaluarPracticasThreads(TesterPracticas.Instancias.NUMBER_2500000));

        // Gráficas de mejora/speedup (depende de lo que ofrezca la librería).
        // TP.graficarResultadosMejora(TP.evaluarPracticasALL());
        // TP.graficarResultadosMejoraThreads(TP.evaluarPracticasThreads(TesterPracticas.Instancias.NUMBER_2500000));
    }
}
