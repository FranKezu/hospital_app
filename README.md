# 🏥 Proyecto SIA - Programación avanzada (ICI2241-1)

Manejo de sistema de salud: Gestión de hospital, asignación de camas a pacientes y reubicación de los mismos según gravedad. Implica manejo de demanda (automatización de estatus "alta") para liberar camas (se debe manejar indicadores de gravedad).

## 👥 Integrantes

- **Ariel Leiva**
- **Hugo Palomino**
- **Franco Bernal**

## ⚡ Funcionalidades

- **🏢 Gestión de Departamentos**: Crear, eliminar y mostrar departamentos
- **👤 Gestión de Pacientes**: Ingresar, buscar y mostrar pacientes
- **🛏️ Gestión de Camas**: Asignar pacientes a camas disponibles
- **📊 Visualización**: Ver estado de camas y departamentos

## 🏗️ Estructura

- **🏥 Hospital**: Contiene departamentos
- **🏢 Department**: Contiene camas
- **👤 Patient**: Información del paciente
- **🛏️ Bed**: Estado de ocupación
- **⚠️ Severity**: Niveles de gravedad (Mínimo, Medio, Moderado, Crítico)

## 🚀 Instrucciones de Ejecución

### 📋 Prerrequisitos

- **☕ Java 11** o superior
- **🔧 Maven 3.6** o superior

### ⚙️ Instalación y Ejecución

1. **📥 Clonar el repositorio**
   ```bash
   git clone https://github.com/FranKezu/hospital_app.git
   cd hospital_app
   ```

2. **🔨 Compilar el proyecto**
   ```bash
   mvn clean compile
   ```

3. **🏃‍♂️ Ejecutar la aplicación**
   ```bash
   mvn exec:java
   ```

   O alternativamente:
   ```bash
   mvn clean package
   java -jar target/hospital_app-1.0-SNAPSHOT.jar
   ```
