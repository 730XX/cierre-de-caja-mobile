# Modal Lista de Ventas con Propina

## 📋 Descripción
Modal reutilizable para mostrar una lista de ventas que tienen propina y permitir registrar el egreso correspondiente.

## 🎯 Componentes Creados

### 1. Layout XML
- **`fragment_modal_lista_ventas_propina.xml`**: Layout principal de la modal
- **`item_venta_propina.xml`**: Layout para cada item de la lista

### 2. Clases Java
- **`ModalListaVentasPropinaFragment.java`**: DialogFragment principal
- **`VentasPropinaAdapter.java`**: Adapter del RecyclerView
- **`VentaPropina.java`**: Modelo de datos

## 🚀 Cómo Usar

### Abrir la modal desde cualquier Activity o Fragment:

```java
if (getSupportFragmentManager().findFragmentByTag("ModalListaVentasPropina") == null) {
    ModalListaVentasPropinaFragment modal = ModalListaVentasPropinaFragment.newInstance();
    modal.show(getSupportFragmentManager(), "ModalListaVentasPropina");
}
```

### Actualizar datos desde fuera:

```java
ModalListaVentasPropinaFragment modal = ModalListaVentasPropinaFragment.newInstance();

// Crear lista de ventas
List<VentaPropina> ventas = new ArrayList<>();
ventas.add(new VentaPropina("Venta #001", "S/ 15.50"));
ventas.add(new VentaPropina("Venta #002", "S/ 20.00"));

// Actualizar datos
modal.actualizarDatos(ventas);
modal.show(getSupportFragmentManager(), "ModalListaVentasPropina");
```

## 🎨 Características

- ✅ Header personalizado con título "Lista de ventas con propina"
- ✅ RecyclerView para mostrar lista de ventas
- ✅ Footer con dos botones:
  - **Cancelar**: Cierra la modal
  - **Registrar egreso**: Ejecuta lógica de registro (configurable)
- ✅ Fondo oscuro semi-transparente
- ✅ Click fuera de la modal la cierra
- ✅ Modal responsive (85% de altura de pantalla)

## 📝 Personalización

### Modificar el layout de items
Edita `item_venta_propina.xml` para cambiar cómo se muestra cada venta.

### Cambiar datos de ejemplo
En `ModalListaVentasPropinaFragment.java`, modifica el método `obtenerDatosEjemplo()`:

```java
private List<VentaPropina> obtenerDatosEjemplo() {
    List<VentaPropina> lista = new ArrayList<>();
    // Agrega tus datos aquí
    lista.add(new VentaPropina("Venta #001", "S/ 15.50"));
    return lista;
}
```

### Implementar lógica de registro
En `ModalListaVentasPropinaFragment.java`, modifica el método `registrarEgreso()`:

```java
private void registrarEgreso() {
    // TODO: Implementar tu lógica aquí
    // Ejemplo: calcular total, enviar a servidor, etc.
}
```

## 🔧 Integración con MainActivity

El botón "Egreso propina" en MainActivity ya está configurado para abrir esta modal automáticamente.

## 📦 Estructura del Proyecto

```
app/src/main/
├── java/com/example/mycaja/
│   ├── ModalListaVentasPropinaFragment.java
│   ├── adapter/
│   │   └── VentasPropinaAdapter.java
│   └── model/
│       └── VentaPropina.java
└── res/layout/
    ├── fragment_modal_lista_ventas_propina.xml
    └── item_venta_propina.xml
```

## 💡 Tips

1. El RecyclerView soporta scroll automático cuando hay muchos items
2. La modal está optimizada para tablets y teléfonos
3. Usa el modelo `VentaPropina` para extender funcionalidad (agregar fecha, mesero, etc.)
4. El adapter soporta actualizaciones dinámicas de datos

---

**Nota**: Esta modal está lista para recibir contenido principal personalizado. Simplemente modifica los layouts y adapters según tus necesidades.
