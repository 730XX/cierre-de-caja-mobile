package com.example.mycaja.utils;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase de utilidad para manipular texto con estilos (negrita, cursiva, etc.)
 * Especialmente útil para aplicar negritas a palabras clave dentro de TextViews
 */
public class TextUtils {

    /**
     * Aplica negrita a múltiples palabras clave dentro de un texto
     *
     * @param textoCompleto El texto completo
     * @param palabrasClave Palabras o frases que deben ponerse en negrita
     * @return SpannableString con las palabras clave en negrita
     */
    public static SpannableString aplicarNegrita(String textoCompleto, String... palabrasClave) {
        if (textoCompleto == null || textoCompleto.isEmpty()) {
            return new SpannableString("");
        }

        SpannableString spannableString = new SpannableString(textoCompleto);

        if (palabrasClave == null || palabrasClave.length == 0) {
            return spannableString;
        }

        for (String palabra : palabrasClave) {
            if (palabra == null || palabra.isEmpty()) {
                continue;
            }

            // Buscar todas las ocurrencias de la palabra (case insensitive)
            int inicio = 0;
            String textoLower = textoCompleto.toLowerCase();
            String palabraLower = palabra.toLowerCase();

            while ((inicio = textoLower.indexOf(palabraLower, inicio)) != -1) {
                int fin = inicio + palabra.length();
                spannableString.setSpan(
                    new StyleSpan(Typeface.BOLD),
                    inicio,
                    fin,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );
                inicio = fin;
            }
        }

        return spannableString;
    }

    /**
     * Aplica negrita a palabras clave usando patrones regex
     * Útil para casos como números, códigos, etc.
     *
     * @param textoCompleto El texto completo
     * @param patronesRegex Patrones regex que deben ponerse en negrita
     * @return SpannableString con los patrones encontrados en negrita
     */
    public static SpannableString aplicarNegritaConRegex(String textoCompleto, String... patronesRegex) {
        if (textoCompleto == null || textoCompleto.isEmpty()) {
            return new SpannableString("");
        }

        SpannableString spannableString = new SpannableString(textoCompleto);

        if (patronesRegex == null || patronesRegex.length == 0) {
            return spannableString;
        }

        for (String patron : patronesRegex) {
            if (patron == null || patron.isEmpty()) {
                continue;
            }

            try {
                Pattern pattern = Pattern.compile(patron);
                Matcher matcher = pattern.matcher(textoCompleto);

                while (matcher.find()) {
                    spannableString.setSpan(
                        new StyleSpan(Typeface.BOLD),
                        matcher.start(),
                        matcher.end(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    );
                }
            } catch (Exception e) {
                // Ignorar patrones regex inválidos
                e.printStackTrace();
            }
        }

        return spannableString;
    }

    /**
     * Aplica negrita a texto entre delimitadores específicos
     * Ejemplo: "Texto normal **texto en negrita** más texto" con delimitador "**"
     *
     * @param textoCompleto El texto completo
     * @param delimitador El delimitador que encierra el texto a poner en negrita
     * @return SpannableString con el texto entre delimitadores en negrita
     */
    public static SpannableString aplicarNegritaConDelimitadores(String textoCompleto, String delimitador) {
        if (textoCompleto == null || textoCompleto.isEmpty() || delimitador == null || delimitador.isEmpty()) {
            return new SpannableString(textoCompleto != null ? textoCompleto : "");
        }

        SpannableStringBuilder builder = new SpannableStringBuilder();
        String[] partes = textoCompleto.split(Pattern.quote(delimitador));

        for (int i = 0; i < partes.length; i++) {
            int inicioSpan = builder.length();
            builder.append(partes[i]);
            int finSpan = builder.length();
            // Los elementos impares están entre delimitadores (deben ir en negrita)
            if (i % 2 == 1) {
                builder.setSpan(
                    new StyleSpan(Typeface.BOLD),
                    inicioSpan,
                    finSpan,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );
            }
        }

        return new SpannableString(builder);
    }

    /**
     * Método conveniente para aplicar negrita a palabras que están entre paréntesis
     * Ejemplo: "TOTAL INGRESOS (Efectivo y Tarjeta)" -> pone "Efectivo y Tarjeta" en negrita
     *
     * @param textoCompleto El texto completo
     * @return SpannableString con el texto entre paréntesis en negrita
     */
    public static SpannableString aplicarNegritaEntreParentesis(String textoCompleto) {
        return aplicarNegritaConRegex(textoCompleto, "\\([^)]+\\)");
    }

    /**
     * Método conveniente para aplicar negrita a números y códigos
     * Ejemplo: "Serie F002 del 00002266 al 00002267" -> pone los números en negrita
     *
     * @param textoCompleto El texto completo
     * @return SpannableString con números y códigos en negrita
     */
    public static SpannableString aplicarNegritaANumeros(String textoCompleto) {
        // Patrón que captura números y códigos alfanuméricos (ej: F002, 00002266)
        return aplicarNegritaConRegex(textoCompleto, "[A-Z]\\d+|\\d{4,}");
    }

    /**
     * Método conveniente para aplicar negrita a hashtags seguidos de números
     * Ejemplo: "delivery #31765" -> pone "#31765" en negrita
     *
     * @param textoCompleto El texto completo
     * @return SpannableString con hashtags en negrita
     */
    public static SpannableString aplicarNegritaAHashtags(String textoCompleto) {
        return aplicarNegritaConRegex(textoCompleto, "#\\d+");
    }

    /**
     * Combina múltiples SpannableStrings en uno solo
     * Útil cuando necesitas aplicar múltiples estilos
     *
     * @param spannables Array de SpannableStrings a combinar
     * @return SpannableString combinado
     */
    public static SpannableString combinarSpannables(SpannableString... spannables) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        for (SpannableString spannable : spannables) {
            if (spannable != null) {
                builder.append(spannable);
            }
        }
        return new SpannableString(builder);
    }

    /**
     * Método versátil que aplica negrita usando múltiples estrategias
     * Detecta automáticamente hashtags, números, texto entre paréntesis y palabras clave
     *
     * @param textoCompleto El texto completo
     * @param palabrasClave Palabras adicionales específicas a poner en negrita
     * @return SpannableString con múltiples elementos en negrita
     */
    public static SpannableString aplicarNegritaInteligente(String textoCompleto, String... palabrasClave) {
        if (textoCompleto == null || textoCompleto.isEmpty()) {
            return new SpannableString("");
        }

        SpannableString spannableString = new SpannableString(textoCompleto);
        List<int[]> rangosNegrita = new ArrayList<>();

        // 1. Buscar hashtags (#31765)
        Pattern patternHashtag = Pattern.compile("#\\d+");
        Matcher matcherHashtag = patternHashtag.matcher(textoCompleto);
        while (matcherHashtag.find()) {
            rangosNegrita.add(new int[]{matcherHashtag.start(), matcherHashtag.end()});
        }

        // 2. Buscar texto entre paréntesis
        Pattern patternParentesis = Pattern.compile("\\([^)]+\\)");
        Matcher matcherParentesis = patternParentesis.matcher(textoCompleto);
        while (matcherParentesis.find()) {
            rangosNegrita.add(new int[]{matcherParentesis.start(), matcherParentesis.end()});
        }

        // 3. Buscar códigos y números significativos (F002, 00002266)
        Pattern patternCodigos = Pattern.compile("[A-Z]\\d+|\\b\\d{4,}\\b");
        Matcher matcherCodigos = patternCodigos.matcher(textoCompleto);
        while (matcherCodigos.find()) {
            rangosNegrita.add(new int[]{matcherCodigos.start(), matcherCodigos.end()});
        }

        // 4. Buscar palabras clave específicas
        if (palabrasClave != null) {
            for (String palabra : palabrasClave) {
                if (palabra != null && !palabra.isEmpty()) {
                    int inicio = 0;
                    String textoLower = textoCompleto.toLowerCase();
                    String palabraLower = palabra.toLowerCase();

                    while ((inicio = textoLower.indexOf(palabraLower, inicio)) != -1) {
                        int fin = inicio + palabra.length();
                        rangosNegrita.add(new int[]{inicio, fin});
                        inicio = fin;
                    }
                }
            }
        }

        // Aplicar negrita a todos los rangos encontrados
        for (int[] rango : rangosNegrita) {
            spannableString.setSpan(
                new StyleSpan(Typeface.BOLD),
                rango[0],
                rango[1],
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }

        return spannableString;
    }

    /**
     * Aplica negrita combinando palabras clave específicas Y patrones regex
     * Este método NO pierde los spans al combinar ambas funcionalidades
     *
     * @param textoCompleto El texto completo
     * @param palabrasClave Array de palabras clave a poner en negrita
     * @param patronesRegex Array de patrones regex a poner en negrita
     * @return SpannableString con todas las coincidencias en negrita
     */
    public static SpannableString aplicarNegritaCombinado(String textoCompleto, String[] palabrasClave, String[] patronesRegex) {
        if (textoCompleto == null || textoCompleto.isEmpty()) {
            return new SpannableString("");
        }

        SpannableString spannableString = new SpannableString(textoCompleto);
        List<int[]> rangosNegrita = new ArrayList<>();

        // 1. Buscar palabras clave específicas
        if (palabrasClave != null) {
            for (String palabra : palabrasClave) {
                if (palabra != null && !palabra.isEmpty()) {
                    int inicio = 0;
                    String textoLower = textoCompleto.toLowerCase();
                    String palabraLower = palabra.toLowerCase();

                    while ((inicio = textoLower.indexOf(palabraLower, inicio)) != -1) {
                        int fin = inicio + palabra.length();
                        rangosNegrita.add(new int[]{inicio, fin});
                        inicio = fin;
                    }
                }
            }
        }

        // 2. Buscar patrones regex
        if (patronesRegex != null) {
            for (String patron : patronesRegex) {
                if (patron != null && !patron.isEmpty()) {
                    try {
                        Pattern pattern = Pattern.compile(patron);
                        Matcher matcher = pattern.matcher(textoCompleto);
                        while (matcher.find()) {
                            rangosNegrita.add(new int[]{matcher.start(), matcher.end()});
                        }
                    } catch (Exception e) {
                        // Ignorar patrones regex inválidos
                    }
                }
            }
        }

        // Aplicar negrita a todos los rangos encontrados
        for (int[] rango : rangosNegrita) {
            spannableString.setSpan(
                new StyleSpan(Typeface.BOLD),
                rango[0],
                rango[1],
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }
        return spannableString;
    }
}
