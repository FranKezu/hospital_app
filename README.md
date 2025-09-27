# 🏥 Proyecto SIA - Programación avanzada (ICI2241-1)

Manejo de sistema de salud: Gestión de hospital, asignación de camas a pacientes y reubicación de los mismos según gravedad. Implica manejo de demanda (automatización de estatus "alta") para liberar camas (se debe manejar indicadores de gravedad).

## 👥 Integrantes

- **Ariel Leiva**
- **Hugo Palomino**
- **Franco Bernal**

## ⚡ Funcionalidades

- **🏢 Gestión de Departamentos**: Crear, eliminar y mostrar departamentos
- **👤 Gestión de Pacientes**: Ingresar, buscar y mostrar pacientes
- **🛏️ Gestión de Camas**: Asignar pacientes a camas disponibles por departamento
- **📊 Visualización**: Ver estado de camas y departamentos en tiempo real
- **🔍 Búsqueda Avanzada**: Buscar pacientes por RUT o nombre
- **💾 Persistencia de Datos**: Base de datos SQLite para almacenamiento permanente
- **✅ Validación de Datos**: Validación de RUT chileno y entrada de datos
- **⚠️ Gestión de Gravedad**: Sistema de clasificación por niveles de severidad

## 🏗️ Estructura del Sistema

- **🏥 Hospital**: Contiene departamentos y gestiona la base de datos
- **🏢 Department**: Contiene camas y maneja ocupación
- **👤 Patient**: Información completa del paciente (RUT, edad, género, dirección, fechas)
- **🛏️ Bed**: Estado de ocupación y ID único
- **⚠️ Severity**: Niveles de gravedad (MINIMO, MEDIO, MODERADO, CRITICO)
- **💾 DatabaseManager**: Gestión de persistencia con SQLite
- **🔧 InvalidRutException**: Manejo de errores de validación de RUT

## 🚀 Instrucciones de Ejecución

### 📋 Requisitos

- **☕ JDK 11** (configurado en el proyecto)
- **🔧 Maven 3.6+**
- **💻 IDE**: IntelliJ IDEA 2025.2.2 (recomendado) o NetBeans IDE (versión 21 o inferior)
- **🗄️ SQLite**: Base de datos incluida automáticamente

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
   mvn exec:java -Dexec.mainClass="hospital.Main"
   ```

   O alternativamente:
   ```bash
   mvn clean package
   java -cp target/classes hospital.Main
   ```

## 🖥️ Compilar con IntelliJ IDEA (Recomendado)

1. Abre IntelliJ IDEA.
2. Ve a File → New → Project from Version Control.
3. En URL coloca:
   ```
   https://github.com/FranKezu/hospital_app.git
   ```
4. Una vez abierto el proyecto, asegúrate de que el SDK esté configurado para Java 11.
5. Ejecuta la aplicación desde la clase `Main.java` en el paquete `hospital`.

## 🖥️ Compilar con NetBeans (Alternativo)

1. Abre NetBeans.
2. Ve a Team → Git → Clone...
3. En Repository URL coloca:
   ```
   https://github.com/FranKezu/hospital_app.git
   ```
4. Una vez abierto el proyecto en NetBeans, haz clic derecho sobre el proyecto.
5. Selecciona **Run** o presiona F6 para ejecutar la aplicación.

## 🗄️ Base de Datos

El sistema utiliza **SQLite** para persistencia de datos:
- **📁 Archivo**: `hospital.db` (se crea automáticamente)
- **📊 Tablas**: 
  - `departments` - Información de departamentos
  - `beds` - Estado y asignación de camas
  - `patients` - Datos completos de pacientes
- **🔄 Inicialización**: Datos de prueba se cargan automáticamente al primer uso

## 🎯 Funcionalidades Técnicas

### ✅ Validación de RUT Chileno
- Verificación de dígito verificador
- Manejo de excepciones personalizadas (`InvalidRutException`)
- Formato automático de entrada

### 🔍 Sistema de Búsqueda
- Búsqueda por RUT (número único)
- Búsqueda por nombre del paciente
- Localización automática de departamento y cama

### 📊 Gestión de Ocupación
- Control automático de camas disponibles/ocupadas
- Estadísticas en tiempo real por departamento
- Reasignación de pacientes entre departamentos
