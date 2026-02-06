package com.example.mycaja.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.ColorUtils;
import com.google.android.material.button.MaterialButton;
import com.example.mycaja.R;

public class BotonReutilizable extends MaterialButton {

    private int tipoBoton;
    private int colorBase;

    public BotonReutilizable(@NonNull Context context) {
        super(context);
        init(null);
    }

    public BotonReutilizable(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BotonReutilizable(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        setCornerRadius(8);
        setIconGravity(ICON_GRAVITY_TEXT_START);
        setIconPadding(8); //<--- separacion entre el icono y el texto
        setAllCaps(false);
        setTextSize(14);
        setLetterSpacing(0);
        setPadding(30, 20, 30, 20);
        setPadding(16, 20, 16, 20);

        if (attrs != null) {
            try (TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.BotonReutilizable)) {
                tipoBoton = a.getInt(R.styleable.BotonReutilizable_tipoBoton, 0);
                int colorDefault = getContext().getColor(com.google.android.material.R.color.design_default_color_primary);
                colorBase = a.getColor(R.styleable.BotonReutilizable_colorBase, colorDefault);
            }
        }

        aplicarEstilo();
    }

    private void aplicarEstilo() {
        if (tipoBoton == 0) {
            // --- ESTILO BORDEADO ---
            int colorFondoTenue = ColorUtils.setAlphaComponent(colorBase, 25);
            setBackgroundTintList(ColorStateList.valueOf(colorFondoTenue));

            setStrokeColor(ColorStateList.valueOf(colorBase));
            setStrokeWidth(2);

            setTextColor(colorBase);
            setIconTint(ColorStateList.valueOf(colorBase));

        } else {
            // --- ESTILO RELLENO ---
            setBackgroundTintList(ColorStateList.valueOf(colorBase));
            setStrokeWidth(0);

            setTextColor(Color.WHITE);
            setIconTint(ColorStateList.valueOf(Color.WHITE));
        }
    }
}