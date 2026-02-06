package com.example.mycaja.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycaja.R;
import com.example.mycaja.adapter.MovimientosAdapter;
import com.example.mycaja.model.ItemColumna;
import com.example.mycaja.model.ItemMovimiento;

import java.util.List;

public class CardResumen extends LinearLayout {

    // Views
    private LinearLayout headerContainer;
    private ImageView ivIcono;
    private TextView tvTitulo;
    private FrameLayout contentContainer;
    private RecyclerView recyclerContent;
    private LinearLayout columnasContainer;
    private LinearLayout footerContainer;
    private TextView tvTituloFooter;
    private TextView tvCantidadFooter;
    private TextView tvValorFooter;

    // Atributos
    private String titulo;
    private Drawable icono;
    private int colorTitulo;
    private boolean mostrarFooter;
    private String tituloFooter;
    private String valorFooter;
    private int colorFooter;
    private int tipoContenido; // 0 = recycler, 1 = columnas
    private int numColumnas;

    // Adapter para RecyclerView
    private MovimientosAdapter adapter;

    public CardResumen(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public CardResumen(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CardResumen(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        // Inflar el layout
        LayoutInflater.from(context).inflate(R.layout.component_card_resumen, this, true);

        // Obtener referencias a las vistas
        headerContainer = findViewById(R.id.headerContainer);
        ivIcono = findViewById(R.id.ivIcono);
        tvTitulo = findViewById(R.id.tvTitulo);
        contentContainer = findViewById(R.id.contentContainer);
        recyclerContent = findViewById(R.id.recyclerContent);
        columnasContainer = findViewById(R.id.columnasContainer);
        footerContainer = findViewById(R.id.footerContainer);
        tvTituloFooter = findViewById(R.id.tvTituloFooter);
        tvCantidadFooter = findViewById(R.id.tvCantidadFooter);
        tvValorFooter = findViewById(R.id.tvValorFooter);

        // Leer atributos personalizados
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CardResumen);
            try {
                titulo = a.getString(R.styleable.CardResumen_cardTitulo);
                icono = a.getDrawable(R.styleable.CardResumen_cardIcono);
                colorTitulo = a.getColor(R.styleable.CardResumen_cardColorTitulo,
                        ContextCompat.getColor(context, R.color.info));

                mostrarFooter = a.getBoolean(R.styleable.CardResumen_cardMostrarFooter, false);
                tituloFooter = a.getString(R.styleable.CardResumen_cardTituloFooter);
                valorFooter = a.getString(R.styleable.CardResumen_cardValorFooter);
                colorFooter = a.getColor(R.styleable.CardResumen_cardColorFooter,
                        ContextCompat.getColor(context, R.color.primary_5));

                tipoContenido = a.getInt(R.styleable.CardResumen_cardTipoContenido, 0);
                numColumnas = a.getInt(R.styleable.CardResumen_cardNumColumnas, 2);
            } finally {
                a.recycle();
            }
        }

        // Aplicar atributos
        aplicarAtributos();
    }

    private void aplicarAtributos() {
        // Header
        if (titulo != null) {
            tvTitulo.setText(titulo);
        }
        tvTitulo.setTextColor(colorTitulo);

        if (icono != null) {
            ivIcono.setImageDrawable(icono);
            ivIcono.setColorFilter(colorTitulo);
        }

        // Footer
        if (mostrarFooter) {
            footerContainer.setVisibility(View.VISIBLE);
            footerContainer.setBackgroundColor(colorFooter);
            if (tituloFooter != null) {
                tvTituloFooter.setText(tituloFooter);
            }
            if (valorFooter != null) {
                tvValorFooter.setText(valorFooter);
            }
        } else {
            footerContainer.setVisibility(View.GONE);
        }

        // Tipo de contenido
        if (tipoContenido == 0) {
            // RecyclerView
            recyclerContent.setVisibility(View.VISIBLE);
            columnasContainer.setVisibility(View.GONE);
            recyclerContent.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            // Columnas
            recyclerContent.setVisibility(View.GONE);
            columnasContainer.setVisibility(View.VISIBLE);
        }
    }

    // ========== MÉTODOS PÚBLICOS ==========

    /**
     * Establece los datos del RecyclerView (para tipo recycler)
     */
    public void setRecyclerData(List<ItemMovimiento> items) {
        if (tipoContenido == 0) {
            adapter = new MovimientosAdapter(items);
            recyclerContent.setAdapter(adapter);
        }
    }

    /**
     * Actualiza los datos del RecyclerView
     */
    public void updateRecyclerData(List<ItemMovimiento> items) {
        if (adapter != null) {
            adapter.updateData(items);
        } else {
            setRecyclerData(items);
        }
    }

    /**
     * Establece las columnas estáticas
     */
    public void setColumnas(List<ItemColumna> columnas) {
        if (tipoContenido == 1) {
            columnasContainer.removeAllViews();

            for (int i = 0; i < columnas.size(); i++) {
                ItemColumna columna = columnas.get(i);

                LinearLayout columnaView = crearColumnaView(columna, i > 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        0, LayoutParams.WRAP_CONTENT, 1f);
                columnaView.setLayoutParams(params);

                columnasContainer.addView(columnaView);
            }
        }
    }

    private LinearLayout crearColumnaView(ItemColumna columna, boolean conBordeIzquierdo) {
        Context context = getContext();

        LinearLayout container = new LinearLayout(context);
        container.setOrientation(LinearLayout.VERTICAL);

        if (conBordeIzquierdo) {
            container.setPadding(dpToPx(16), 0, 0, 0);
        }

        // Etiqueta
        TextView tvEtiqueta = new TextView(context);
        tvEtiqueta.setText(columna.getEtiqueta());
        tvEtiqueta.setTextSize(12);
        tvEtiqueta.setTextColor(ContextCompat.getColor(context, R.color.text_25));
        try {
            tvEtiqueta.setTypeface(getResources().getFont(R.font.roboto_medium));
        } catch (Exception e) {
            // Fallback si la fuente no existe
        }

        LinearLayout.LayoutParams etiquetaParams = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        etiquetaParams.bottomMargin = dpToPx(4);
        tvEtiqueta.setLayoutParams(etiquetaParams);

        // Valor
        TextView tvValor = new TextView(context);
        tvValor.setText(columna.getValor());
        tvValor.setTextSize(16);
        tvValor.setTextColor(ContextCompat.getColor(context, R.color.text_85));
        try {
            tvValor.setTypeface(getResources().getFont(R.font.montserrat_bold));
        } catch (Exception e) {
            // Fallback si la fuente no existe
        }
        container.addView(tvEtiqueta);
        container.addView(tvValor);
        return container;
    }

    private int dpToPx(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    // ========== SETTERS DINÁMICOS ==========

    public void setTitulo(String titulo) {
        this.titulo = titulo;
        tvTitulo.setText(titulo);
    }

    public void setIcono(int iconoResId) {
        ivIcono.setImageResource(iconoResId);
        ivIcono.setColorFilter(colorTitulo);
    }

    public void setColorTitulo(int color) {
        this.colorTitulo = color;
        tvTitulo.setTextColor(color);
        ivIcono.setColorFilter(color);
    }

    public void setTituloFooter(String titulo) {
        this.tituloFooter = titulo;
        tvTituloFooter.setText(titulo);
    }

    public void setValorFooter(String valor) {
        this.valorFooter = valor;
        tvValorFooter.setText(valor);
    }

    public void setCantidadFooter(String cantidad) {
        if (cantidad != null && !cantidad.isEmpty()) {
            tvCantidadFooter.setVisibility(View.VISIBLE);
            tvCantidadFooter.setText(cantidad);

            // Ajustar LayoutParams para 3 columnas (igual que el adapter)
            LinearLayout.LayoutParams tituloParams = new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.5f);
            tvTituloFooter.setLayoutParams(tituloParams);

            LinearLayout.LayoutParams cantidadParams = new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f);
            tvCantidadFooter.setLayoutParams(cantidadParams);

            LinearLayout.LayoutParams valorParams = new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f);
            tvValorFooter.setLayoutParams(valorParams);
        } else {
            tvCantidadFooter.setVisibility(View.GONE);
        }
    }

    public void setColorFooter(int color) {
        this.colorFooter = color;
        footerContainer.setBackgroundColor(color);
    }

    public void setMostrarFooter(boolean mostrar) {
        this.mostrarFooter = mostrar;
        footerContainer.setVisibility(mostrar ? View.VISIBLE : View.GONE);
    }

    public RecyclerView getRecyclerView() {
        return recyclerContent;
    }
}
