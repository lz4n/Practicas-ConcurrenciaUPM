import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import practicas_pca.TesterRun;

/**
 * Implementación de Bucket Sort usando hilos simples.
 * Implementa la interfaz TesterRun (definida en el JAR externo).
 *
 * Idea general:
 * - Dividir el rango de valores en `num_threads` buckets (cubetas).
 * - Repartir los números en las cubetas según su valor.
 * - Lanzar un hilo por cubeta para ordenar cada una (Collections.sort).
 * - Esperar a la finalización de todos los hilos y concatenar los resultados.
 *
 * Notas:
 * - El cálculo de `amp` usa Integer.MIN_VALUE / MAX_VALUE; esta implementación asume
 *   que los números están distribuidos en el rango entero completo, lo que puede provocar
 *   buckets vacíos o índices fuera de rango si los datos no están dentro del rango esperado.
 * - El método imprime información por cada hilo (puede eliminarse en ejecuciones de rendimiento).
 */
public class BucketSortList implements TesterRun {

    @Override
    public ArrayList<Integer> parallelSort(List<Integer> numbers, int num_threads) {

        // El algoritmo asume num_threads >= 2; para un solo hilo debe usarse ordenación secuencial
        if (num_threads == 1) {
            Collections.sort(numbers);
            return new ArrayList<>(numbers);
        }

        // Rango teórico de valores (mínimo y máximo del tipo int)
        double max = Integer.MAX_VALUE; // Valor máximo del rango
        double min = Integer.MIN_VALUE; // Valor mínimo del rango
        // 'amp' representa el ancho (amplitud) de cada bucket; se distribuye el rango completo entre num_threads-1 divisiones
        // Si num_threads == 1, esto puede provocar división por cero; la práctica asume num_threads >= 2.
        // Se divide el rango (max - min) entre (num_threads - 1) para asegurar que el valor máximo
        // se asigne al último bucket (índice num_threads - 1) al calcular:
        //     bucket = floor((number + |min|) / amp)
        // De este modo se evita que x == max genere un índice fuera de rango.
        double amp = (max - min) / (num_threads - 1) /* A COMPLETAR POR EL ALUMNO */;

        // Resultado final que contendrá la concatenación de todos los buckets ordenados
        ArrayList<Integer> numbersSort = new ArrayList<>();

        // Lista de buckets: cada bucket es una ArrayList<Integer>
        ArrayList<ArrayList<Integer>> listaBuckets = new ArrayList<>();
        for (int i = 0; i < num_threads; i++) {
            listaBuckets.add(new ArrayList<>());
        }

        // Distribuir cada número en su bucket correspondiente
        // Índice calculado mediante: índice = floor((number + |min|) / amp)
        // Atención: el cálculo debe quedar dentro de [0, num_threads-1]. Si hay valores extremos o amp==0, puede fallar.
        for (Integer number : numbers) {
            int bucketIndex = (int) Math.floor((number + Math.abs(min)) / amp);

            listaBuckets.get(bucketIndex).add(number);
        }

        
        // Array de hilos, uno por bucket
        Thread[] hilos = new Thread[num_threads];

        int i = 0;
        // Para cada bucket lanzar un hilo que lo ordene
        for (ArrayList<Integer> bucket : listaBuckets) {
            // Cada Tarea ordena la cubeta actual
            Runnable tarea = () -> {
                // Ordenar la cubeta actual
                System.out.println("Ordenando, bb");
                Collections.sort(bucket);
            };



            // Crear y arrancar el hilo para la cubeta
            Thread hilo = new Thread(tarea);
            hilo.start();

            hilos[i] = hilo;

            i++;
        }

        // Esperar a que todos los hilos terminen y controlar la Excepcion InterruptedException
        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                throw new RuntimeException("Unable to short the bucket: " + e.getMessage());
            }
        }

        // Concatenar los buckets ordenados en el resultado final
        // Limpiar el bucket para liberar memoria más rápidamente (no estrictamente necesario)
        for (ArrayList<Integer> bucket : listaBuckets) {
            numbersSort.addAll(bucket);

            bucket.clear();
        }

        // Limpiar la lista de buckets (buena práctica antes de devolver el resultado)
        listaBuckets.clear();

        return numbersSort;
    }
}
