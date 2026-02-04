package com.example.mycaja.ejemplos;

import android.text.SpannableString;
import android.widget.TextView;

import com.example.mycaja.utils.TextUtils;

/**
 * EJEMPLOS DE USO DE LA CLASE TextUtils
 *
 * Esta clase contiene ejemplos prácticos de cómo usar TextUtils
 * para aplicar negritas en diferentes escenarios
 */
public class EjemplosUsoTextUtils {

    /**
     * EJEMPLO 1: Movimiento de Ingresos
     * "Ingreso por confirmación de Delivery #31765 con forma de pago En línea"
     * Palabras en negrita: "Delivery #31765" y "En línea"
     */
    public static void ejemplo1_MovimientoIngresos(TextView textView) {
        String texto = "Ingreso por confirmación de Delivery #31765 con forma de pago En línea";

        // Opción A: Especificar palabras clave manualmente
        SpannableString spannable = TextUtils.aplicarNegrita(texto, "Delivery #31765", "En línea");
        textView.setText(spannable);

        // Opción B: Usar el método inteligente (detecta hashtags automáticamente)
        SpannableString spannableInteligente = TextUtils.aplicarNegritaInteligente(texto, "En línea");
        textView.setText(spannableInteligente);
    }

    /**
     * EJEMPLO 2: Correlativos
     * "Serie F002 del 00002266 al 00002267"
     * Números/códigos en negrita: "F002", "00002266", "00002267"
     */
    public static void ejemplo2_Correlativos(TextView textView) {
        String texto = "Serie F002 del 00002266 al 00002267";

        // Opción A: Aplicar negrita a números automáticamente
        SpannableString spannable = TextUtils.aplicarNegritaANumeros(texto);
        textView.setText(spannable);

        // Opción B: Usar el método inteligente
        SpannableString spannableInteligente = TextUtils.aplicarNegritaInteligente(texto);
        textView.setText(spannableInteligente);
    }

    /**
     * EJEMPLO 3: Footer con texto entre paréntesis
     * "TOTAL INGRESOS (Efectivo y Tarjeta)"
     * En negrita: "(Efectivo y Tarjeta)"
     */
    public static void ejemplo3_Footer(TextView textView) {
        String texto = "TOTAL INGRESOS (Efectivo y Tarjeta)";

        // Opción A: Aplicar negrita a texto entre paréntesis
        SpannableString spannable = TextUtils.aplicarNegritaEntreParentesis(texto);
        textView.setText(spannable);

        // Opción B: Especificar el contenido manualmente
        SpannableString spannable2 = TextUtils.aplicarNegrita(texto, "Efectivo y Tarjeta");
        textView.setText(spannable2);

        // Opción C: Usar el método inteligente
        SpannableString spannableInteligente = TextUtils.aplicarNegritaInteligente(texto);
        textView.setText(spannableInteligente);
    }

    /**
     * EJEMPLO 4: Uso en Adapter/RecyclerView
     * Caso práctico para lista de movimientos
     */
    public static void ejemplo4_EnAdapter(TextView tvConcepto, String concepto) {
        // Ejemplo con datos dinámicos del backend
        // concepto = "Ingreso por confirmación de Delivery #31765 con forma de pago En línea"

        SpannableString spannable = TextUtils.aplicarNegritaInteligente(concepto);
        tvConcepto.setText(spannable);
    }

    /**
     * EJEMPLO 5: Múltiples palabras clave específicas
     */
    public static void ejemplo5_MultiplesPalabras(TextView textView) {
        String texto = "Venta realizada por Juan Pérez en Mesa 5 con método POS";

        // Poner en negrita: "Juan Pérez", "Mesa 5", "POS"
        SpannableString spannable = TextUtils.aplicarNegrita(texto, "Juan Pérez", "Mesa 5", "POS");
        textView.setText(spannable);
    }

    /**
     * EJEMPLO 6: Usando delimitadores personalizados
     * Si decides marcar tus textos con delimitadores como **negrita**
     */
    public static void ejemplo6_ConDelimitadores(TextView textView) {
        String texto = "Ingreso por **Delivery #31765** con pago **En línea**";

        // Esto quitará los ** y pondrá el texto en negrita
        SpannableString spannable = TextUtils.aplicarNegritaConDelimitadores(texto, "**");
        textView.setText(spannable);
    }

    /**
     * EJEMPLO 7: Caso real en tu código - initCardMovimientosIngresos
     * Así es como lo usarías en tu método actual
     */
    public static void ejemplo7_CasoReal_MovimientosIngresos(TextView tvConcepto) {
        // Tu texto actual del ejemplo
        String concepto = "Ingreso por confirmación de Delivery #31765 con forma de pago En línea";

        // Aplicar negrita automáticamente
        SpannableString spannable = TextUtils.aplicarNegritaInteligente(concepto);
        tvConcepto.setText(spannable);

        // O si prefieres control manual:
        // SpannableString spannable = TextUtils.aplicarNegrita(concepto, "Delivery #31765", "En línea");
        // tvConcepto.setText(spannable);
    }

    /**
     * EJEMPLO 8: Caso real - Correlativos en tu código
     */
    public static void ejemplo8_CasoReal_Correlativos(TextView tvValor) {
        String valor = "Serie F002 del 00002266 al 00002267";

        // Aplicar negrita a los códigos y números
        SpannableString spannable = TextUtils.aplicarNegritaInteligente(valor);
        tvValor.setText(spannable);
    }

    /**
     * EJEMPLO 9: Caso real - Footer
     */
    public static void ejemplo9_CasoReal_Footer(TextView tvFooterTitulo) {
        String titulo = "TOTAL INGRESOS (Efectivo y Tarjeta)";

        // Aplicar negrita al contenido entre paréntesis
        SpannableString spannable = TextUtils.aplicarNegritaInteligente(titulo);
        tvFooterTitulo.setText(spannable);
    }

    /**
     * EJEMPLO 10: Caso complejo con múltiples elementos
     */
    public static void ejemplo10_CasoComplejo(TextView textView) {
        String texto = "Nota de crédito NC001-00001234 emitida a Cliente Premium por Devolución #5678 " +
                       "procesada por Sistema (Automático) con monto de S/150.50";

        // El método inteligente detectará:
        // - Códigos: NC001-00001234, #5678
        // - Texto entre paréntesis: (Automático)
        // Además podemos agregar palabras específicas:
        SpannableString spannable = TextUtils.aplicarNegritaInteligente(texto,
            "Cliente Premium", "Devolución", "Sistema");
        textView.setText(spannable);
    }
}
