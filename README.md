# Práctica 1 - Bucket Sort with threads - Guía de uso para el alumnado (PCA)

## 1. ¿Qué es este proyecto y para qué sirve?

Este repositorio es el **entorno de trabajo** para las prácticas de *Programación Concurrente y Avanzada*.

Aquí tenéis:
- Un proyecto Maven ya configurado.
- Una **librería** (`lib/Practicas_PCA_P1_P2.jar`) que proporciona:
  - La interfaz que vuestro algoritmo debe implementar.
  - Un sistema de **carga de instancias** (ficheros con números).
  - Un **tester** para ejecutar experimentos y generar resultados.
- Una clase de ejemplo (`PruebaUso`) que muestra cómo lanzar una evaluación.

El objetivo es que implementéis un algoritmo de ordenación paralelo (**Bucket Sort**) y lo evaluéis en distintas instancias y con distintos números de hilos.

---

## 2. Cómo empezar (clonar y abrir en IntelliJ)

Este apartado explica el flujo típico para empezar la práctica desde el repositorio de GitLab de la escuela.

### 2.1. Clonar el repositorio

Tenéis dos opciones habituales:

**Opción A — Desde IntelliJ (recomendado si no domináis Git por terminal)**
1. Abrir IntelliJ IDEA.
2. En la pantalla inicial: **Get from VCS**.
3. En **Version control** elegir **Git**.
4. Pegar la URL del repositorio de GitLab (la que os da la escuela).
5. Elegir una carpeta de destino y pulsar **Clone**.

**Opción B — Con Git en terminal**
1. Abrir una terminal.
2. Ejecutar:

```powershell
git clone <URL_DEL_REPOSITORIO_GITLAB>
```

3. Entrar en la carpeta clonada.

> Nota: si GitLab os pide credenciales, usad el método indicado por la escuela (token, SSO, etc.).

### 2.2. Abrir el proyecto como proyecto Maven

1. En IntelliJ: **File → Open…** y seleccionar la carpeta del repositorio clonado.
2. Aseguraos de que IntelliJ detecta el fichero `pom.xml`.
3. Si aparece un aviso para **Load Maven project**, aceptadlo.

### 2.3. Configurar el JDK del proyecto

El proyecto está configurado con un nivel de Java alto (ver `pom.xml`). Para evitar errores:
1. Ir a **File → Project Structure → Project**.
2. Seleccionar un **Project SDK** compatible (por ejemplo, el JDK instalado en vuestro equipo).
3. Si IntelliJ indica problemas de lenguaje/bytecode, revisad:
   - `Project language level`
   - `Maven` → `Importer` (si aplica)

> Si vuestra máquina no tiene el JDK requerido, instalad uno compatible y seleccionadlo en IntelliJ.

### 2.4. Comprobar que todo compila antes de tocar nada

Antes de implementar nada, comprobad que el proyecto abre y compila:

- Desde IntelliJ: usar **Build → Build Project**.
- Desde el panel **Maven** de IntelliJ (recomendado):
  1. Abrir la ventana: **View → Tool Windows → Maven**.
  2. En **Lifecycle**, ejecutar **`package`** (o **`compile`** si solo queréis compilar).

> Nota: en esta asignatura priorizamos el uso de IntelliJ para ejecutar Maven. Si en tu máquina tienes Maven instalado y en PATH, también podrías compilar desde terminal, pero no es el flujo recomendado.

Si esto falla, no sigáis con la práctica hasta resolverlo (JDK, Maven/IntelliJ, o rutas del proyecto).

---

## 3. Estructura del proyecto

- `src/main/java/`
  - Vuestras implementaciones de algoritmos.
  - `PruebaUso.java`: punto de entrada para lanzar pruebas.
  - `BucketSortList.java`: plantilla donde debéis implementar el algoritmo de ordenación BucketSort con *threads*.

- `lib/Practicas_PCA_P1_P2.jar`
  - Librería de la asignatura con las clases de soporte (tester, carga de instancias, gráficas, etc.).

---

## 4. Instancias de prueba

Debido al **tamaño** de las instancias, estas **no se incluyen directamente en el repositorio**.
Se distribuyen a través del **Package Registry** del proyecto.

> Nota: es necesario estar autenticado en **GitLab ETSISI-UPM** para poder descargar el archivo.

### 4.1. Descarga de las instancias

1. Inicia sesión en GitLab ETSISI-UPM.
2. Descarga el archivo ZIP desde este enlace:

   https://gitlab.etsisi.upm.es/api/v4/projects/11819/packages/generic/instancias/1.0/instancias_P1_P2_PCA.zip

3. Copia el archivo descargado `instancias_P1_P2_PCA.zip` a la **carpeta raíz** del proyecto clonado.

### 4.2. Descompresión (PowerShell)

Desde la terminal **PowerShell** (por ejemplo, la terminal integrada de IntelliJ) y situándote en la carpeta raíz del proyecto:

```powershell
Expand-Archive .\instancias_P1_P2_PCA.zip -DestinationPath . -Force
```

Tras descomprimir, los ficheros `numeros_*.txt` quedarán disponibles para que el tester y `PruebaUso` puedan cargarlos.

---

## 5. Restricciones de concurrencia (muy importante)

Para esta práctica, el desarrollo debe hacerse **usando únicamente la clase `Thread`** (y mecanismos básicos asociados como `join()` / `InterruptedException`).

No se deben utilizar otras abstracciones o utilidades de concurrencia de alto nivel, por ejemplo:
- `ExecutorService`, `Future`, `Callable`, `ForkJoinPool`
- `parallelStream()`
- Frameworks externos de concurrencia

El objetivo docente es que practiquéis:
- Creación controlada de hilos.
- Sincronización básica (espera con `join`).
- Diseño de un reparto de trabajo que respete el parámetro `num_threads`.

---

## 6. Conceptos clave que aporta la librería

### 6.1. `TesterRun` (lo que vosotros implementáis)

La librería define una interfaz llamada `TesterRun`. Vuestro algoritmo debe **implementar esa interfaz**.

Conceptualmente, el tester lo que hace es:
1. Cargar una instancia (lista de números).
2. Llamar a vuestro método de ordenación “paralela”, pasándole:
   - la lista de números,
   - el número de hilos a utilizar.
3. Medir tiempos y comparar resultados.

**Importante:** el tester espera que devolváis la lista **ordenada**.

### 6.2. `TesterPracticas` (el evaluador)

`TesterPracticas` es la clase “controlador” que se encarga de ejecutar experimentos.

Sus responsabilidades públicas típicas son:
- Elegir y cargar instancias.
- Ejecutar vuestro algoritmo con un cierto número de hilos.
- Medir el rendimiento.
- (Opcional) Ejecutar baterías completas y generar gráficas.

Vosotros **no modificáis** esta clase, solo la usáis.

### 6.3. `TesterPracticas.Instancias` (selección de tamaños)

La librería incluye un enumerado `TesterPracticas.Instancias` con constantes del estilo:
- `NUMBER_2500000`
- `NUMBER_5000000`
- `NUMBER_12500000`
- `NUMBER_25000000`

Cada constante representa un **tamaño de instancia** (número de elementos) y permite cargar el fichero correspondiente.

#### ¿Cómo elegir la instancia adecuada?
- Empieza con instancias pequeñas (por ejemplo `NUMBER_2500000`) para depurar.
- Sube progresivamente según la capacidad de tu máquina.
- En máquinas con poca RAM o CPU limitada, las instancias más grandes pueden tardar mucho o no caber en memoria.

---

## 7. Clase de ejemplo: `PruebaUso`

`PruebaUso` es un **ejemplo mínimo** de cómo lanzar una evaluación desde `main`.

A nivel de uso (sin entrar en detalles de implementación):
1. Se crea un `TesterPracticas` indicando qué algoritmo vais a evaluar.
2. Se llama a un método de evaluación indicando:
   - la instancia (`TesterPracticas.Instancias...`),
   - el número de hilos.

### ¿Qué parámetros son importantes?
- **Instancia**: determina el tamaño del problema.
- **Número de hilos**: determina el grado de paralelismo (típicamente 1, 2, 4, 8…).

Recomendación docente:
- Probad siempre con **1 o 2 hilos** para validar corrección.
- Luego comparad 2, 4, 8… y analizad escalabilidad.

---

## 8. Lo que tenéis que implementar (Bucket Sort)

En esta práctica vais a implementar una clase de ordenación basada en **Bucket Sort**.

### 8.1. ¿Qué es Bucket Sort (idea conceptual)?

Bucket Sort ordena una lista distribuyendo primero los elementos en “cubetas” (**buckets**):

1. **Se divide el rango de valores** en varios intervalos.
2. Cada número se asigna a un bucket según el intervalo al que pertenezca.
3. Se ordena cada bucket por separado.
4. Se concatenan los buckets en orden para obtener el resultado final.

### 8.2. ¿Cómo se paraleliza?

La paralelización típica consiste en:
- Crear varios buckets.
- Ordenar **cada bucket en paralelo** usando varios hilos (`Thread`).

Si la distribución es buena, el trabajo queda aproximadamente repartido.

### 8.3. ¿Qué debéis entregar exactamente?

- Una clase `BucketSortList` que implemente la interfaz `TesterRun`.
- Debe ser capaz de:
  - Ordenar correctamente.
  - Utilizar el parámetro `num_threads` para controlar cuántos hilos usa.

**La plantilla que se os proporciona tendrá comentarios** y vosotros completaréis la implementación siguiendo esas indicaciones.

---

## 9. Recomendaciones para la práctica (metodología)

1. **Corrección antes que rendimiento**
   - Verificad que el resultado está ordenado.
   - Comprobad casos pequeños.

2. **Escalabilidad y overhead**
   - Más hilos no siempre implica más velocidad.
   - Para tamaños pequeños, crear hilos puede ser más caro que ordenar secuencialmente.

3. **Elección de buckets**
   - El número de buckets y cómo se reparten los valores afecta al equilibrio de carga.
   - Si muchos valores caen en el mismo bucket, ese hilo será el cuello de botella.

4. **Experimentación controlada**
   - Repetid mediciones.
   - Comparad 1 vs 2 vs 4 vs 8 hilos.
   - Indicad máquina, JDK y configuración (para reproducibilidad).

---

## 10. Cómo compilar y ejecutar

### Compilar con Maven (desde IntelliJ)

1. Abrir la ventana: **View → Tool Windows → Maven**.
2. En el árbol Maven, abrir **Lifecycle**.
3. Ejecutar **`package`** (también vale **`compile`**).

### Ejecutar `PruebaUso`

- En IntelliJ, ejecutar directamente `PruebaUso` como aplicación Java.

> Alternativa (solo si la necesitáis): ejecución por consola con `java -cp ...`.

```powershell
java -cp "target/classes;lib/Practicas_PCA_P1_P2.jar" PruebaUso
```

---

## 11. Cómo se evalúa la práctica (criterios)

Esta práctica **NO ES EVALUABLE** pero te ayudará a preparar la siguiente práctica que sí lo es.

La forma de evaluación de las prácticas se centra principalmente en:

- **Corrección / funcionalidad:** el algoritmo devuelve la lista ordenada, y el programa ejecuta las pruebas sin fallos.
- **Cumplimiento de requisitos:** se usa `Thread` (y no otras abstracciones) y se respeta el parámetro `num_threads`.

Además, hay aspectos que se recomienda cuidar y que pueden considerarse como valor añadido, pero **no son estrictamente puntuables** si el enunciado no lo especifica:
- **Uso adecuado de concurrencia** (respeto del número de hilos solicitado y ausencia de errores típicos).
- **Rendimiento** y **escalabilidad** razonables.
- **Calidad de memoria/robustez** (no explota en instancias grandes por decisiones evitables).

---

## 12. Preguntas típicas

**¿Qué instancia uso para mi portátil?**
Empieza por `NUMBER_2500000` y sube. Si tu equipo se queda sin memoria o tarda demasiado, baja el tamaño.

**¿Cuántos hilos pongo?**
Prueba 1, 2, 4 y 8 (si tu CPU lo soporta). El mejor número depende del equipo.

**¿Tengo que modificar la librería?**
No. La librería se usa tal cual. Tu trabajo está en implementar el algoritmo (por ejemplo `BucketSortList`) y ejecutar experimentos.

---

¡Buena suerte con la práctica!