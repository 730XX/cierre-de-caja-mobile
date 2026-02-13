package com.example.mycaja.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.mycaja.R;

public class FooterModal extends LinearLayout {

    private LinearLayout btnCancelar;
    private LinearLayout btnPrimario;
    private ImageView iconCancelar;
    private ImageView iconPrimario;
    private TextView textCancelar;
    private TextView textPrimario;
    // Listeners
    private OnClickListener onCancelarClickListener;
    private OnClickListener onPrimarioClickListener;

    public FooterModal(Context context) {
        super(context);
        init(context, null);
    }

    public FooterModal(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FooterModal(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        // Configurar el LinearLayout principal
        setOrientation(HORIZONTAL);

        // Crear botón Cancelar
        btnCancelar = createButton(context, true);

        // Crear botón Primario
        btnPrimario = createButton(context, false);

        // Agregar los botones al layout
        addView(btnCancelar);
        addView(btnPrimario);

        // Leer atributos personalizados si existen
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FooterModal);

            // Textos
            String textCancelarStr = a.getString(R.styleable.FooterModal_textoCancelar);
            String textPrimarioStr = a.getString(R.styleable.FooterModal_textoPrimario);

            // Iconos
            Drawable iconCancelarDrawable = a.getDrawable(R.styleable.FooterModal_iconoCancelar);
            Drawable iconPrimarioDrawable = a.getDrawable(R.styleable.FooterModal_iconoPrimario);

            // Color del botón primario
            int colorPrimario = a.getColor(R.styleable.FooterModal_colorPrimario,
                    ContextCompat.getColor(context, R.color.info));

            // Aplicar valores
            if (textCancelarStr != null) {
                textCancelar.setText(textCancelarStr);
            }
            if (textPrimarioStr != null) {
                textPrimario.setText(textPrimarioStr);
            }
            if (iconCancelarDrawable != null) {
                iconCancelar.setImageDrawable(iconCancelarDrawable);
            }
            if (iconPrimarioDrawable != null) {
                iconPrimario.setImageDrawable(iconPrimarioDrawable);
            }

            // Aplicar color al botón primario
            btnPrimario.setBackgroundColor(colorPrimario);

            a.recycle();
        }
    }

    private LinearLayout createButton(Context context, boolean isCancelar) {
        LinearLayout button = new LinearLayout(context);
        LayoutParams buttonParams = new LayoutParams(0, dpToPx(70));
        buttonParams.weight = 1;
        button.setLayoutParams(buttonParams);
        button.setOrientation(VERTICAL);
        button.setGravity(Gravity.CENTER);
        button.setClickable(true);
        button.setFocusable(true);
        aplicarEfectoPulsacion(button);

        // Fondo del botón
        if (isCancelar) {
            // Usamos el XML que tiene el fondo blanco + su propio borde superior
            button.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_boton_cancelar));
        } else {
            // Botón primario: fondo con color
            button.setBackgroundColor(ContextCompat.getColor(context, R.color.info));
        }

        // Crear ImageView para el icono
        ImageView icon = new ImageView(context);
        LayoutParams iconParams = new LayoutParams(dpToPx(18), dpToPx(20));
        iconParams.bottomMargin = dpToPx(4);
        icon.setLayoutParams(iconParams);

        if (isCancelar) {
            icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_svg_xmark));
            icon.setColorFilter(ContextCompat.getColor(context, R.color.text_65));
        } else {
            icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_svg_check_circle));
            icon.setColorFilter(Color.WHITE);
        }

        // Crear TextView para el texto
        TextView text = new TextView(context);
        LayoutParams textParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );
        text.setLayoutParams(textParams);
        text.setTextSize(12);
        text.setGravity(Gravity.CENTER);

        if (isCancelar) {
            text.setText("Cancelar");
            text.setTextColor(ContextCompat.getColor(context, R.color.text_65));
            text.setTypeface(null, android.graphics.Typeface.NORMAL);
        } else {
            text.setText("Guardar arqueo");
            text.setTextColor(Color.WHITE);
            text.setTypeface(null, android.graphics.Typeface.BOLD);
        }

        // Agregar icono y texto al botón
        button.addView(icon);
        button.addView(text);

        // Guardar referencias
        if (isCancelar) {
            iconCancelar = icon;
            textCancelar = text;
            button.setOnClickListener(v -> {
                if (onCancelarClickListener != null) {
                    onCancelarClickListener.onClick(v);
                }
            });
        } else {
            iconPrimario = icon;
            textPrimario = text;
            button.setOnClickListener(v -> {
                if (onPrimarioClickListener != null) {
                    onPrimarioClickListener.onClick(v);
                }
            });
        }

        return button;
    }

    /**
     * Al crear el boton programaticamente y aplicarle un color solido,este pierde el efecto
     * de pulsacion clasico de cualquier boton en android. esta funcion lo retorna
     * @param view componente a aplicar el efecto de pulsacion
     */
    private void aplicarEfectoPulsacion(View view) {
        android.util.TypedValue outValue = new android.util.TypedValue();
        // Buscamos el atributo de "fondo seleccionable" del tema actual
        getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);

        // Lo aplicamos como Foreground para que la onda se vea sobre el color de fondo
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            view.setForeground(ContextCompat.getDrawable(getContext(), outValue.resourceId));
        }
    }

    // Métodos públicos para configurar
    public void setTextoCancelar(String texto) {
        textCancelar.setText(texto);
    }

    public void setTextoPrimario(String texto) {
        textPrimario.setText(texto);
    }

    public void setIconoCancelar(int drawableRes) {
        iconCancelar.setImageResource(drawableRes);
    }

    public void setIconoPrimario(int drawableRes) {
        iconPrimario.setImageResource(drawableRes);
    }

    public void setColorPrimario(int color) {
        btnPrimario.setBackgroundColor(color);
    }

    public void setOnCancelarClickListener(OnClickListener listener) {
        this.onCancelarClickListener = listener;
    }

    public void setOnPrimarioClickListener(OnClickListener listener) {
        this.onPrimarioClickListener = listener;
    }

    private int dpToPx(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
