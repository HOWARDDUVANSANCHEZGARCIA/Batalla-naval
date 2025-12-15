# Batalla Naval

![Lenguaje: Java](https://img.shields.io/badge/language-Java-blue) ![Estado: Completado](https://img.shields.io/badge/status-Completado-green)

Descripción
-----------
"Batalla Naval" es una implementación en Java del clásico juego de Hundir la Flota (Battleship). Permite colocar barcos en un tablero y jugar turnos para intentar hundir los barcos del oponente. El proyecto es mayoritariamente Java con algo de CSS para cualquier interfaz web/estética (si aplica).

Composición de lenguajes
------------------------
- Java: 94.1%  
- CSS: 5.9%

Tabla de contenidos
------------------
- [Características](#características)
- [Requisitos](#requisitos)
- [Instalación](#instalación)
- [Construir y ejecutar](#construir-y-ejecutar)
- [Ejecutar desde IDE](#ejecutar-desde-ide)
- [Estructura del proyecto](#estructura-del-proyecto)
- [Cómo jugar](#cómo-jugar)
- [Pruebas](#pruebas)
- [Contribuir](#contribuir)
- [Reportar errores y solicitudes](#reportar-errores-y-solicitudes)
- [Licencia](#licencia)
- [Autores / Contacto](#autores--contacto)

Características
---------------
- Tablero(es) configurables (tamaño configurable).
- Colocación de barcos manual o aleatoria.
- Gestión de turnos y detección de impacto/hundimiento.
- Interfaz de consola (y/o interfaz gráfica ligera si está implementada).
- Lógica modular pensada para pruebas unitarias.

Requisitos
----------
- Java Development Kit (JDK) 11 o superior (ajusta según la versión objetivo del proyecto).
- Maven o Gradle si el proyecto usa un sistema de construcción (verifica el repositorio).
- Un IDE para Java (IntelliJ IDEA, Eclipse, VS Code, etc.) opcional.

Instalación
-----------
Clona el repositorio:
```bash
git clone https://github.com/HOWARDDUVANSANCHEZGARCIA/Batalla-naval.git
cd Batalla-naval
```

Construir y ejecutar
--------------------
Si el proyecto utiliza Maven:
```bash
# Construir
mvn clean package

# Ejecutar (ajusta el nombre del archivo JAR si es necesario)
java -jar target/batalla-naval-1.0.0.jar
```

Si el proyecto utiliza Gradle:
```bash
# Con wrapper (recomendado)
./gradlew build

# Ejecutar (ajusta según la tarea o el JAR generado)
java -jar build/libs/batalla-naval-1.0.0.jar
```

Si es un proyecto Java simple sin build tool:
```bash
# Compilar
javac -d out $(find src -name "*.java")

# Ejecutar (ajusta la clase principal)
java -cp out com.tuempresa.batallanaval.Main
```

Ejecutar desde IDE
------------------
1. Abre el proyecto en tu IDE favorito.
2. Importa como proyecto Maven/Gradle si corresponde.
3. Configura la clase principal (Main) y ejecuta la aplicación.
4. Para depuración, crea una configuración de ejecución y usa breakpoints.

Estructura del proyecto (ejemplo)
---------------------------------
- src/main/java/     -> Código fuente Java
- src/main/resources/-> Recursos (config, assets)
- src/test/java/     -> Pruebas unitarias
- pom.xml / build.gradle -> Configuración de construcción (si aplica)
Ajusta esta sección según la estructura real del repositorio.

Cómo jugar
----------
Reglas básicas (adaptar según la implementación):
1. Cada jugador coloca una flota de barcos en su tablero sin que el rival los vea.
2. Por turnos, cada jugador dispara a una coordenada (fila, columna).
3. Impacto: si hay un barco, se marca como tocado; si no, se indica agua.
4. Un barco se hunde cuando todas sus casillas han sido tocadas.
5. Gana el jugador que hunda toda la flota contraria.

Pruebas
-------
Si hay pruebas unitarias con Maven:
```bash
mvn test
```
Con Gradle:
```bash
./gradlew test
```
Incluye pruebas para la colocación de barcos, detección de impactos y lógica de fin de juego. Si no hay pruebas, se recomienda agregar JUnit y pruebas para las clases centrales.

Reportar errores y solicitudes
------------------------------
Usa la sección de "Issues" en GitHub para:
- Reportar bugs reproducibles (incluye pasos para reproducir).
- Solicitar nuevas funcionalidades.
- Preguntas sobre el uso.

Licencia
--------
Este proyecto no tiene una licencia establecida en el repositorio (o reemplaza según corresponda). Se sugiere usar una licencia estándar, por ejemplo MIT:

Autores / Contacto
------------------
- Martin Stivensson Alvarez Quintero (2372233)
- Samuel Vargas Valderruten (2537761)
- Howard Duvan Sanchez (2438723)

- GitHub: https://github.com/HOWARDDUVANSANCHEZGARCIA

Notas finales
-------------
- Personaliza las instrucciones de compilación/ejecución según el sistema de construcción (pom.xml o build.gradle) que tenga el repositorio.
- Si quieres, puedo:
  - Generar un README más corto o más técnico.
  - Añadir badges automáticos (build, coverage).
  - Crear ejemplos de ejecución concretos leyendo el proyecto para detectar la clase principal y el build tool.
