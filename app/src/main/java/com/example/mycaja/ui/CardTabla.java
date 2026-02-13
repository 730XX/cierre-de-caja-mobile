package com.example.mycaja.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mycaja.R;
import com.example.mycaja.adapter.TablaAdapter;
import com.example.mycaja.model.ItemTablaFila;
import java.util.ArrayList;
import java.util.List;

public class CardTabla extends LinearLayout {

    // Views
    private ImageView ivIcono;
    private TextView tvTitulo;
    private LinearLayout tablaHeaderContainer;
    private TextView tvHeader1, tvHeader2, tvHeader3, tvHeader4, tvHeader5;
    private LinearLayout emptyStateContainer;
    private ImageView ivEmptyIcon;
    private TextView tvEmptyMessage;
    private RecyclerView recyclerTabla;
    private LinearLayout footerContainer;
    private LinearLayout footerRow1, footerRow2;
    private TextView tvFooterTitulo1, tvFooterValor1;
    private TextView tvFooterTitulo2, tvFooterValor2;
    private LinearLayout mostrarMasContainer;
    private TextView tvMostrarMas;
    private ImageView ivMostrarMasIcon;

    // Atributos
    private String titulo;
    private Drawable icono;
    private int colorTitulo;

    // Adapter
    private TablaAdapter adapter;

    // Estado expandido
    private boolean isExpanded = false;
    private List<ItemTablaFila> allItems;
    private int initialItemCount = 5; // Número de items a mostrar inicialmente

    public CardTabla(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public CardTabla(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CardTabla(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.component_card_tabla, this, true);

        // Obtener referencias
        ivIcono = findViewById(R.id.ivIcono);
        tvTitulo = findViewById(R.id.tvTitulo);
        tablaHeaderContainer = findViewById(R.id.tablaHeaderContainer);
        tvHeader1 = findViewById(R.id.tvHeader1);
        tvHeader2 = findViewById(R.id.tvHeader2);
        tvHeader3 = findViewById(R.id.tvHeader3);
        tvHeader4 = findViewById(R.id.tvHeader4);
        tvHeader5 = findViewById(R.id.tvHeader5);
        emptyStateContainer = findViewById(R.id.emptyStateContainer);
        ivEmptyIcon = findViewById(R.id.ivEmptyIcon);
        tvEmptyMessage = findViewById(R.id.tvEmptyMessage);
        recyclerTabla = findViewById(R.id.recyclerTabla);
        footerContainer = findViewById(R.id.footerContainer);
        footerRow1 = findViewById(R.id.footerRow1);
        footerRow2 = findViewById(R.id.footerRow2);
        tvFooterTitulo1 = findViewById(R.id.tvFooterTitulo1);
        tvFooterValor1 = findViewById(R.id.tvFooterValor1);
        tvFooterTitulo2 = findViewById(R.id.tvFooterTitulo2);
        tvFooterValor2 = findViewById(R.id.tvFooterValor2);
        mostrarMasContainer = findViewById(R.id.mostrarMasContainer);
        tvMostrarMas = findViewById(R.id.tvMostrarMas);
        ivMostrarMasIcon = findViewById(R.id.ivMostrarMasIcon);

        // Leer atributos personalizados
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CardTabla);
            try {
                titulo = a.getString(R.styleable.CardTabla_tablaTitulo);
                icono = a.getDrawable(R.styleable.CardTabla_tablaIcono);
                colorTitulo = a.getColor(R.styleable.CardTabla_tablaColorTitulo,
                        ContextCompat.getColor(context, R.color.info));
            } finally {
                a.recycle();
            }
        }

        aplicarAtributos();

        // Configurar RecyclerView
        recyclerTabla.setLayoutManager(new LinearLayoutManager(context));

        // Configurar click listener para Mostrar más
        mostrarMasContainer.setOnClickListener(v -> toggleExpandCollapse());
    }

    private void toggleExpandCollapse() {
        if (allItems == null) return;

        isExpanded = !isExpanded;

        if (isExpanded) {
            // Mostrar todos los items
            adapter.updateData(allItems);
            tvMostrarMas.setText("Mostrar menos");

            // Animación de rotación del icono
            ivMostrarMasIcon.animate()
                    .rotation(180)
                    .setDuration(150)
                    .start();

            // Mostrar footer
            footerContainer.setVisibility(View.VISIBLE);
        } else {
            // Mostrar solo los primeros items
            List<ItemTablaFila> limitedItems = allItems.subList(0, Math.min(initialItemCount, allItems.size()));
            adapter.updateData(new ArrayList<>(limitedItems));
            tvMostrarMas.setText("Mostrar más");

            // Animación de rotación del icono
            ivMostrarMasIcon.animate()
                    .rotation(0)
                    .setDuration(150)
                    .start();

            // Ocultar footer
            footerContainer.setVisibility(View.GONE);
        }
    }

    private void aplicarAtributos() {
        if (titulo != null) {
            tvTitulo.setText(titulo);
        }
        tvTitulo.setTextColor(colorTitulo);

        if (icono != null) {
            ivIcono.setImageDrawable(icono);
            ivIcono.setColorFilter(colorTitulo);
        }
    }

    /**
     * Configura los headers de la tabla (2 columnas)
     */
    public void setHeaders(String header1, String header2) {
        tvHeader1.setText(header1);
        tvHeader2.setText(header2);
        tvHeader3.setVisibility(View.GONE);
        tvHeader4.setVisibility(View.GONE);
        tvHeader5.setVisibility(View.GONE);

        // Ajustar weights para 2 columnas
        setHeaderWeight(tvHeader1, 1f);
        setHeaderWeight(tvHeader2, 0f);
        tvHeader2.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;

        // Ajustar gravity para 2 columnas
        tvHeader1.setGravity(android.view.Gravity.START);
        tvHeader2.setGravity(android.view.Gravity.END);
    }

    /**
     * Configura los headers de la tabla (3 columnas)
     * Weights: 1, 1, 1
     */
    public void setHeaders(String header1, String header2, String header3) {
        tvHeader1.setText(header1);
        tvHeader2.setText(header2);
        tvHeader3.setText(header3);
        tvHeader4.setVisibility(View.GONE);
        tvHeader5.setVisibility(View.GONE);

        // Ajustar weights para 3 columnas
        setHeaderWeight(tvHeader1, 1f);
        setHeaderWeight(tvHeader2, 1f);
        setHeaderWeight(tvHeader3, 1f);

        // Ajustar gravity para 3 columnas
        tvHeader1.setGravity(android.view.Gravity.START);
        tvHeader2.setGravity(android.view.Gravity.CENTER_HORIZONTAL);
        tvHeader3.setGravity(android.view.Gravity.END);
    }

    /**
     * Configura los headers de la tabla (4 columnas) con weights por defecto
     * Weights: 1, 1.5, 3, 0.8 (para tablas con concepto largo)
     */
    public void setHeaders(String header1, String header2, String header3, String header4) {
        setHeaders(header1, header2, header3, header4, 1f, 1.5f, 3f, 0.8f);
    }

    /**
     * Configura los headers de la tabla (4 columnas) con weights personalizados
     */
    public void setHeaders(String header1, String header2, String header3, String header4,
                          float w1, float w2, float w3, float w4) {
        tvHeader1.setText(header1);
        tvHeader2.setText(header2);
        tvHeader3.setText(header3);
        tvHeader4.setText(header4);
        tvHeader4.setVisibility(View.VISIBLE);
        tvHeader5.setVisibility(View.GONE);

        // Ajustar weights personalizados
        setHeaderWeight(tvHeader1, w1);
        setHeaderWeight(tvHeader2, w2);
        setHeaderWeight(tvHeader3, w3);
        setHeaderWeight(tvHeader4, w4);

        // Ajustar gravity para 4 columnas
        tvHeader1.setGravity(android.view.Gravity.START);
        tvHeader2.setGravity(android.view.Gravity.CENTER_HORIZONTAL);
        tvHeader3.setGravity(android.view.Gravity.CENTER_HORIZONTAL);
        tvHeader4.setGravity(android.view.Gravity.END);
    }

    /**
     * Configura los headers de la tabla (5 columnas)
     * Weights distribuidos equitativamente
     */
    public void setHeaders(String header1, String header2, String header3, String header4, String header5) {
        tvHeader1.setText(header1);
        tvHeader2.setText(header2);
        tvHeader3.setText(header3);
        tvHeader4.setText(header4);
        tvHeader5.setText(header5);
        tvHeader5.setVisibility(View.VISIBLE);

        // Ajustar weights para 5 columnas
        setHeaderWeight(tvHeader1, 1f);
        setHeaderWeight(tvHeader2, 1f);
        setHeaderWeight(tvHeader3, 1f);
        setHeaderWeight(tvHeader4, 1.5f);
        setHeaderWeight(tvHeader5, 0.8f);

        // Ajustar gravity para 5 columnas
        tvHeader1.setGravity(android.view.Gravity.START);
        tvHeader2.setGravity(android.view.Gravity.CENTER_HORIZONTAL);
        tvHeader3.setGravity(android.view.Gravity.CENTER_HORIZONTAL);
        tvHeader4.setGravity(android.view.Gravity.CENTER_HORIZONTAL);
        tvHeader5.setGravity(android.view.Gravity.END);
    }

    private void setHeaderWeight(TextView tv, float weight) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tv.getLayoutParams();
        params.weight = weight;
        tv.setLayoutParams(params);
    }

    /**
     * Muestra el estado vacío con un mensaje personalizado
     */
    public void showEmptyState(String message) {
        tvEmptyMessage.setText(message);
        emptyStateContainer.setVisibility(View.VISIBLE);
        recyclerTabla.setVisibility(View.GONE);
        // Ocultar footers cuando no hay datos
        footerContainer.setVisibility(View.GONE);
    }

    /**
     * Oculta el estado vacío y muestra el RecyclerView
     */
    public void hideEmptyState() {
        emptyStateContainer.setVisibility(View.GONE);
        recyclerTabla.setVisibility(View.VISIBLE);
    }

    /**
     * Configura los datos de la tabla
     */
    public void setTablaData(List<ItemTablaFila> items) {
        if (items == null || items.isEmpty()) {
            showEmptyState("No hay egresos registros");
            mostrarMasContainer.setVisibility(View.GONE);
        } else {
            hideEmptyState();
            adapter = new TablaAdapter(items);
            recyclerTabla.setAdapter(adapter);
            mostrarMasContainer.setVisibility(View.GONE);
        }
    }

    /**
     * Configura los datos de la tabla con mensaje personalizado para estado vacío
     */
    public void setTablaData(List<ItemTablaFila> items, String emptyMessage) {
        if (items == null || items.isEmpty()) {
            showEmptyState(emptyMessage);
            mostrarMasContainer.setVisibility(View.GONE);
        } else {
            hideEmptyState();
            adapter = new TablaAdapter(items);
            recyclerTabla.setAdapter(adapter);
            mostrarMasContainer.setVisibility(View.GONE);
        }
    }

    /**
     * Configura los datos de la tabla con la opción de "Mostrar más"
     * @param items Lista completa de items
     * @param initialCount Número de items a mostrar inicialmente
     */
    public void setTablaDataWithShowMore(List<ItemTablaFila> items, int initialCount) {
        if (items == null || items.isEmpty()) {
            showEmptyState("No hay registros");
            mostrarMasContainer.setVisibility(View.GONE);
            return;
        }

        hideEmptyState();
        this.allItems = new ArrayList<>(items);
        this.initialItemCount = initialCount;
        this.isExpanded = false;

        // Si hay más items que el límite inicial, mostrar el botón
        if (items.size() > initialCount) {
            List<ItemTablaFila> limitedItems = items.subList(0, initialCount);
            adapter = new TablaAdapter(new ArrayList<>(limitedItems));
            mostrarMasContainer.setVisibility(View.VISIBLE);
            tvMostrarMas.setText("Mostrar más");
            ivMostrarMasIcon.setRotation(0);
            // Ocultar footer inicialmente, se mostrará al expandir
            footerContainer.setVisibility(View.GONE);
        } else {
            adapter = new TablaAdapter(items);
            mostrarMasContainer.setVisibility(View.GONE);
        }
        recyclerTabla.setAdapter(adapter);
    }

    /**
     * Actualiza los datos de la tabla
     */
    public void updateTablaData(List<ItemTablaFila> items) {
        if (adapter != null) {
            adapter.updateData(items);
        } else {
            setTablaData(items);
        }
    }

    /**
     * Configura el primer footer
     */
    public void setFooter1(String titulo, String valor) {
        footerContainer.setVisibility(View.VISIBLE);
        footerRow1.setVisibility(View.VISIBLE);

        // NO aplicar negritas automáticas - el texto del footer se muestra tal cual
        tvFooterTitulo1.setText(titulo);
        tvFooterValor1.setText(valor);
    }

    /**
     * Quita el margin top del footer (útil para cards con "Mostrar más")
     */
    public void removeFooterMarginTop() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) footerRow1.getLayoutParams();
        params.topMargin = 0;
        footerRow1.setLayoutParams(params);
    }

    /**
     * Configura el segundo footer
     */
    public void setFooter2(String titulo, String valor) {
        footerContainer.setVisibility(View.VISIBLE);
        footerRow2.setVisibility(View.VISIBLE);

        // NO aplicar negritas automáticas - el texto del footer se muestra tal cual
        tvFooterTitulo2.setText(titulo);
        tvFooterValor2.setText(valor);

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

    public RecyclerView getRecyclerView() {
        return recyclerTabla;
    }
}
