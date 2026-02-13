package com.example.mycaja.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.mycaja.R;

public class HeaderModal extends LinearLayout {

    private ImageView ivIcono;
    private TextView tvTitulo;
    private ImageView btnClose;

    public HeaderModal(Context context) {
        super(context);
        init(context, null);
    }

    public HeaderModal(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public HeaderModal(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        // Inflamos el layout usando 'this' como raíz
        LayoutInflater.from(context).inflate(R.layout.component_header_modal, this, true);

        // Referencias a las vistas
        ivIcono = findViewById(R.id.iv_icono_header);
        tvTitulo = findViewById(R.id.tv_titulo_header);
        btnClose = findViewById(R.id.btn_close_header);

        // Si hay atributos en el XML, los aplicamos
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HeaderModal);

            String titulo = a.getString(R.styleable.HeaderModal_headerTitulo);
            Drawable icono = a.getDrawable(R.styleable.HeaderModal_headerIcono);
            int tintColor = a.getColor(R.styleable.HeaderModal_headerIconoTint, -1);

            if (titulo != null) {
                tvTitulo.setText(titulo);
            }

            if (icono != null) {
                ivIcono.setImageDrawable(icono);
            }

            // Si quisieras cambiar el color del icono dinámicamente
            if (tintColor != -1) {
                ivIcono.setColorFilter(tintColor);
            }

            a.recycle();
        }
    }

    // --- Métodos Públicos ---

    // Método vital para asignar la acción de cerrar desde el Fragment
    public void setOnCloseClickListener(OnClickListener listener) {
        btnClose.setOnClickListener(listener);
    }

    public void setTitle(String title) {
        tvTitulo.setText(title);
    }

    public void setIcon(int drawableRes) {
        ivIcono.setImageResource(drawableRes);
    }
}