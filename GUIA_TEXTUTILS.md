# GUÍA DE IMPLEMENTACIÓN - TextUtils para Negritas

## 📋 Resumen

`TextUtils` es una clase de utilidad profesional para aplicar estilos de texto (principalmente negritas) en TextViews de forma programática y reutilizable.

---

## 🎯 Casos de Uso Resueltos

### 1. **Movimiento de Ingresos**
**Texto:** "Ingreso por confirmación de Delivery #31765 con forma de pago En línea"  
**Negrita:** `Delivery #31765`, `En línea`

```java
String texto = "Ingreso por confirmación de Delivery #31765 con forma de pago En línea";
SpannableString spannable = TextUtils.aplicarNegritaInteligente(texto, "En línea");
textView.setText(spannable);
```

### 2. **Correlativos**
**Texto:** "Serie F002 del 00002266 al 00002267"  
**Negrita:** `F002`, `00002266`, `00002267`

```java
String texto = "Serie F002 del 00002266 al 00002267";
SpannableString spannable = TextUtils.aplicarNegritaInteligente(texto);
textView.setText(spannable);
```

### 3. **Footer con Paréntesis**
**Texto:** "TOTAL INGRESOS (Efectivo y Tarjeta)"  
**Negrita:** `(Efectivo y Tarjeta)`

```java
String texto = "TOTAL INGRESOS (Efectivo y Tarjeta)";
SpannableString spannable = TextUtils.aplicarNegritaInteligente(texto);
textView.setText(spannable);
```

---

## 🔧 Métodos Disponibles

### 1. `aplicarNegrita(String texto, String... palabras)`
Aplica negrita a palabras clave específicas.

```java
SpannableString s = TextUtils.aplicarNegrita(
    "Usuario: Juan Pérez en Mesa 5",
    "Juan Pérez", "Mesa 5"
);
```

### 2. `aplicarNegritaInteligente(String texto, String... palabras)`
⭐ **RECOMENDADO** - Detecta automáticamente:
- Hashtags (#31765)
- Códigos (F002, NC001)
- Números significativos (00002266)
- Texto entre paréntesis (Efectivo)
- Palabras clave adicionales que le pases

```java
SpannableString s = TextUtils.aplicarNegritaInteligente(texto);
```

### 3. `aplicarNegritaConRegex(String texto, String... patrones)`
Usa expresiones regulares para casos avanzados.

```java
// Ejemplo: Poner en negrita todos los emails
SpannableString s = TextUtils.aplicarNegritaConRegex(
    texto,
    "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"
);
```

### 4. `aplicarNegritaEntreParentesis(String texto)`
Método específico para texto entre paréntesis.

```java
SpannableString s = TextUtils.aplicarNegritaEntreParentesis(
    "TOTAL (Efectivo y Tarjeta)"
);
```

### 5. `aplicarNegritaANumeros(String texto)`
Método específico para números y códigos.

```java
SpannableString s = TextUtils.aplicarNegritaANumeros(
    "Boleta B001-00123456"
);
```

### 6. `aplicarNegritaAHashtags(String texto)`
Método específico para hashtags.

```java
SpannableString s = TextUtils.aplicarNegritaAHashtags(
    "Delivery #31765 confirmado"
);
```

### 7. `aplicarNegritaConDelimitadores(String texto, String delimitador)`
Para textos marcados con delimitadores personalizados.

```java
// Si tu backend envía: "Venta **aprobada** por **Sistema**"
SpannableString s = TextUtils.aplicarNegritaConDelimitadores(texto, "**");
// Resultado: "Venta aprobada por Sistema" (sin ** y con negritas)
```

---

## 💡 Implementación en tu Código Actual

### En MainActivity - initCardCorrelativos()

**ANTES:**
```java
datosCorrelativos.add(new ItemMovimiento(
    "Facturas", 
    "Serie F002 del 00002266 al 00002267"
));
```

**DESPUÉS (en tu Adapter o donde infles el TextView):**
```java
// En tu adapter o cuando setees el texto
String valor = itemMovimiento.getValor(); // "Serie F002 del 00002266 al 00002267"
SpannableString spannable = TextUtils.aplicarNegritaInteligente(valor);
tvValor.setText(spannable);
```

### En CardTabla - Para Movimientos de Ingresos

Si estás usando el adapter `TablaAdapter`, modifica el método `onBindViewHolder`:

```java
@Override
public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    ItemTablaFila item = items.get(position);
    
    // Para la columna de concepto
    if (holder.tvColumna3 != null) {
        String concepto = item.getColumna3();
        SpannableString spannable = TextUtils.aplicarNegritaInteligente(concepto);
        holder.tvColumna3.setText(spannable);
    }
    
    // ... resto del código
}
```

### En Footers - Para títulos con paréntesis

```java
// Si tu footer dice: "TOTAL INGRESOS (Efectivo y Tarjeta)"
SpannableString spannable = TextUtils.aplicarNegritaInteligente(tituloFooter);
tvTituloFooter.setText(spannable);
```

---

## 🎨 Casos de Uso Avanzados

### Caso 1: RecyclerView con datos dinámicos
```java
// En tu Adapter
@Override
public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Movimiento mov = movimientos.get(position);
    
    // Aplicar negrita inteligente
    SpannableString spannable = TextUtils.aplicarNegritaInteligente(
        mov.getConcepto(),
        "palabrasAdicionales" // Opcional
    );
    holder.tvConcepto.setText(spannable);
}
```

### Caso 2: Combinando con HTML del backend
Si tu backend envía HTML (ej: `<b>texto</b>`), puedes:

```java
// Primero convierte HTML a texto plano
String textoPlano = Html.fromHtml(htmlDelBackend).toString();

// Luego aplica tus negritas
SpannableString spannable = TextUtils.aplicarNegritaInteligente(textoPlano);
textView.setText(spannable);
```

### Caso 3: Múltiples estilos en mismo TextView
```java
// Si necesitas negritas Y otros estilos:
SpannableString spannable = TextUtils.aplicarNegritaInteligente(texto);

// Puedes agregar más spans manualmente
spannable.setSpan(
    new ForegroundColorSpan(Color.RED),
    inicio,
    fin,
    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
);

textView.setText(spannable);
```

---

## 📊 Tabla de Decisión: ¿Qué Método Usar?

| Caso | Método Recomendado | Razón |
|------|-------------------|-------|
| Texto general con hashtags/números | `aplicarNegritaInteligente()` | Detecta automáticamente patrones comunes |
| Palabras específicas conocidas | `aplicarNegrita()` | Control preciso |
| Solo números/códigos | `aplicarNegritaANumeros()` | Optimizado para este caso |
| Solo paréntesis | `aplicarNegritaEntreParentesis()` | Método específico |
| Patrón complejo personalizado | `aplicarNegritaConRegex()` | Máxima flexibilidad |
| Backend con delimitadores | `aplicarNegritaConDelimitadores()` | Procesa marcadores del backend |

---

## ⚠️ Consideraciones Importantes

1. **Performance:** Los métodos son eficientes, pero si tienes miles de items en un RecyclerView, considera cachear los SpannableStrings.

2. **Case Insensitive:** `aplicarNegrita()` busca palabras sin importar mayúsculas/minúsculas.

3. **Expresiones Regulares:** Si usas `aplicarNegritaConRegex()`, asegúrate de que tus patrones sean válidos.

4. **Texto Null:** Todos los métodos manejan texto null y retornan SpannableString vacío.

5. **Overlapping:** Si dos patrones se solapan, ambos se aplicarán correctamente.

---

## 🚀 Quick Start

### Paso 1: Ya tienes la clase `TextUtils.java` creada ✅

### Paso 2: Importar en tu Activity/Adapter
```java
import com.example.mycaja.utils.TextUtils;
```

### Paso 3: Usar en cualquier TextView
```java
SpannableString spannable = TextUtils.aplicarNegritaInteligente(texto);
textView.setText(spannable);
```

---

## 📝 Notas Finales

- Esta solución sigue el principio **DRY** (Don't Repeat Yourself)
- Es **escalable** y fácil de mantener
- **No requiere cambios en tus layouts XML**
- Compatible con **TextViews dentro de RecyclerViews**
- Funciona perfectamente con **databinding** si lo usas

---

## 🎓 Mejores Prácticas

1. **Usa `aplicarNegritaInteligente()` como primera opción** - Cubre el 80% de casos
2. **Para textos del backend:** Aplica negritas en el Adapter, no en el modelo
3. **Reutiliza:** Crea métodos helper en tu adapter si repites mucho código
4. **Testing:** Los textos con negritas funcionan igual en emulador y dispositivos reales

---

## 💻 Ejemplo Completo de Implementación

```java
public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        TextView tvConcepto = findViewById(R.id.tvConcepto);
        TextView tvCorrelativo = findViewById(R.id.tvCorrelativo);
        TextView tvFooter = findViewById(R.id.tvFooter);
        
        // Ejemplo 1
        String concepto = "Ingreso por confirmación de Delivery #31765 con forma de pago En línea";
        tvConcepto.setText(TextUtils.aplicarNegritaInteligente(concepto, "En línea"));
        
        // Ejemplo 2
        String correlativo = "Serie F002 del 00002266 al 00002267";
        tvCorrelativo.setText(TextUtils.aplicarNegritaInteligente(correlativo));
        
        // Ejemplo 3
        String footer = "TOTAL INGRESOS (Efectivo y Tarjeta)";
        tvFooter.setText(TextUtils.aplicarNegritaInteligente(footer));
    }
}
```

---

¿Necesitas más ejemplos o casos de uso específicos? ¡La clase está lista para usar! 🎉
