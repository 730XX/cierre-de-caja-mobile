package com.example.mycaja;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;
import android.view.MotionEvent;

import com.example.mycaja.model.ItemCategoria;
import com.example.mycaja.model.ItemMovimiento;
import com.example.mycaja.model.ItemProductoEstrella;
import com.example.mycaja.model.ItemTablaFila;
import com.example.mycaja.model.DatosCaja;
import com.example.mycaja.model.ResumenPagos;
import com.example.mycaja.model.DatosCreditos;
import com.example.mycaja.model.DatosAtenciones;
import com.example.mycaja.model.DatosImpuestos;
import com.example.mycaja.ui.CardResumen;
import com.example.mycaja.utils.TextUtils;
import com.example.mycaja.adapter.CustomSpinnerAdapter;
import com.example.mycaja.ui.CardTabla;
import com.example.mycaja.ui.RoundedBarChartRenderer;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CardResumen cardIngresos, cardEgresos;
    private CardResumen cardCorrelativos, cardTransaccionesHechas, cardTransaccionesAnuladas;
    private CardTabla cardMovimientosIngresos, cardMovimientosEgresos;
    private CardTabla cardIngresosEfectivo, cardIngresosDelivery;
    private CardTabla cardIngresosPropinas, cardIngresosCreditos;
    // TextViews para datos de caja y resumen
    private TextView tvUsuarioNombre, tvAperturaMonto, tvTurnoNombre, tvFechaApertura;
    private TextView tvTotalCajaPrincipal, tvFechaResumen, tvEnEfectivo, tvEnTarjetas, tvTotalCajaResumen;
    private TextView tvCreditosGenerados, tvTotalCreditos, tvValesPago, tvMontoValesPago;
    private TextView tvMesasAtendidas, tvPersonasAtendidas;
    private TextView tvImpuestosIgv, tvImpuestosIcbper;
    // Tabs
    private LinearLayout tabTopVentas, tabProductosEstrellas;
    private LinearLayout contentTopVentas, contentProductosEstrellas;
    private ImageView iconTopVentas, iconProductosEstrellas;
    private TextView tvTabTopVentas, tvTabProductosEstrellas;
    // Gráficos
    private BarChart barChartCategorias;
    private PieChart pieChartProductos;
    private LinearLayout listaCategorias, listaProductos;
    private NestedScrollView nestedScrollProductos;
    private LinearLayout listaOtrosProductosEstrella;
    // Spinners para filtros
    private Spinner spinnerProductos, spinnerPeriodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar datos de caja y resumen
        initDatosCaja();
        initResumenPagos();
        initDatosCreditos();
        initDatosAtenciones();
        initDatosImpuestos();

        // Inicializar Cards
        initCardIngresos();
        initCardEgresos();
        initCardCorrelativos();
        initCardTransaccionesHechas();
        initCardTransaccionesAnuladas();
        initCardMovimientosIngresos();
        initCardMovimientosEgresos();
        initCardIngresosEfectivo();
        initCardIngresosDelivery();
        initCardIngresosPropinas();
        initCardIngresosCreditos();

        // Inicializar Tabs
        initTabs();
        // Inicializar Gráficos
        initGraficos();
        initSpinners();
        // Inicializar lista de productos estrella
        initProductosEstrella();
    }
    private void initSpinners() {
        spinnerProductos = findViewById(R.id.spinnerProductos);
        spinnerPeriodo = findViewById(R.id.spinnerPeriodo);

        // Datos para spinner de productos
        String[] productos = {
                "Productos", "Insumos", "Recetas y subrecetas"
        };

        // Datos para spinner de período
        String[] periodos = {
                "Últimos 7 días", "Últimos 15 días", "Último mes"
        };

        // Configurar adapters personalizados
        CustomSpinnerAdapter adapterProductos = new CustomSpinnerAdapter(this, productos);
        spinnerProductos.setAdapter(adapterProductos);

        CustomSpinnerAdapter adapterPeriodos = new CustomSpinnerAdapter(this, periodos);
        spinnerPeriodo.setAdapter(adapterPeriodos);

        // Establecer valores por defecto
        spinnerProductos.setSelection(0);
        spinnerPeriodo.setSelection(0);
    }

    private void initCardIngresos() {
        cardIngresos = findViewById(R.id.cardIngresos);

        // Datos estáticos de ejemplo (luego estos vendrán del endpoint)
        List<ItemMovimiento> datosIngresos = new ArrayList<>();
        datosIngresos.add(new ItemMovimiento("Apertura", "S/1,062.70"));
        datosIngresos.add(new ItemMovimiento("Ventas en S/", "S/0.00"));
        datosIngresos.add(new ItemMovimiento("Ingresos Extra", "S/0.00"));
        datosIngresos.add(new ItemMovimiento("Ventas Tarjeta", "S/0.00"));
        datosIngresos.add(new ItemMovimiento("Ventas por transferencia", "S/6,875.09"));
        datosIngresos.add(new ItemMovimiento("Ventas por vales", "S/0.00"));
        datosIngresos.add(new ItemMovimiento("Créditos cobrados en soles", "S/0.00"));
        datosIngresos.add(new ItemMovimiento("Créditos cobrados en tarjeta", "S/0.00"));
        datosIngresos.add(new ItemMovimiento("Créditos cobrados con otros medios de pagos", "S/0.00"));
        datosIngresos.add(new ItemMovimiento("Propinas en efectivo", "S/0.00"));
        datosIngresos.add(new ItemMovimiento("Propinas con tarjeta", "S/0.00"));
        datosIngresos.add(new ItemMovimiento("Propinas con otros medios", "S/0.00"));
        datosIngresos.add(new ItemMovimiento("Total de costo de delivery (incluido en total de ventas)", "S/0.00"));

        // Configurar datos en el CardResumen
        cardIngresos.setRecyclerData(datosIngresos);
    }

    private void initCardEgresos() {
        cardEgresos = findViewById(R.id.cardEgresos);

        // Datos estáticos de ejemplo (luego estos vendrán del endpoint)
        List<ItemMovimiento> datosEgresos = new ArrayList<>();
        datosEgresos.add(new ItemMovimiento("Egresos Extra", "S/0.00"));
        datosEgresos.add(new ItemMovimiento("Compras", "S/0.00"));
        datosEgresos.add(new ItemMovimiento("Pago .  de compras al crédito", "S/0.00"));
        datosEgresos.add(new ItemMovimiento("Descuentos en soles", "S/0.00"));
        datosEgresos.add(new ItemMovimiento("Descuentos en dolares", "S/0.00"));
        datosEgresos.add(new ItemMovimiento("Descuentos en euros", "S/0.00"));

        // Configurar datos en el CardResumen
        cardEgresos.setRecyclerData(datosEgresos);
    }

    private void initCardCorrelativos() {
        cardCorrelativos = findViewById(R.id.cardCorrelativos);

        // Datos estáticos de ejemplo
        List<ItemMovimiento> datosCorrelativos = new ArrayList<>();
        datosCorrelativos.add(new ItemMovimiento("Facturas", "Serie F002 del 00002266 al 00002267"));
        datosCorrelativos.add(new ItemMovimiento("Boletas", "Serie B002 del 00130834 al 00131035"));
        datosCorrelativos.add(new ItemMovimiento("Nota de Ventas", "-"));
        datosCorrelativos.add(new ItemMovimiento("Nota de credito Boletas", "-"));
        datosCorrelativos.add(new ItemMovimiento("Nota de debito Boletas", "-"));
        datosCorrelativos.add(new ItemMovimiento("Nota de credito Facturas", "-"));
        datosCorrelativos.add(new ItemMovimiento("Nota de debito Facturas", "-"));

        cardCorrelativos.setRecyclerData(datosCorrelativos);
    }

    private void initCardTransaccionesHechas() {
        cardTransaccionesHechas = findViewById(R.id.cardTransaccionesHechas);

        // Datos estáticos de ejemplo con 3 columnas (concepto, cantidad, monto)
        List<ItemMovimiento> datosTransacciones = new ArrayList<>();
        datosTransacciones.add(new ItemMovimiento("N° Facturas", "0", "S/1,062.70"));
        datosTransacciones.add(new ItemMovimiento("N° Boletas", "0", "S/0.00"));
        datosTransacciones.add(new ItemMovimiento("N° Nota de Ventas", "0", "S/0.00"));
        datosTransacciones.add(new ItemMovimiento("N° Nota de credito Boletas", "0", "S/6,875.09"));
        datosTransacciones.add(new ItemMovimiento("N° Nota de debito Boletas", "0", "S/0.00"));
        datosTransacciones.add(new ItemMovimiento("N° Nota de credito Facturas", "0", "S/0.00"));
        datosTransacciones.add(new ItemMovimiento("N° Nota de debito Facturas", "0", "S/0.00"));

        cardTransaccionesHechas.setRecyclerData(datosTransacciones);
        cardTransaccionesHechas.setCantidadFooter("0");
    }

    private void initCardTransaccionesAnuladas() {
        cardTransaccionesAnuladas = findViewById(R.id.cardTransaccionesAnuladas);

        // Datos estáticos de ejemplo
        List<ItemMovimiento> datosAnuladas = new ArrayList<>();
        datosAnuladas.add(new ItemMovimiento("N° Facturas", "0"));
        datosAnuladas.add(new ItemMovimiento("N° Boletas", "0"));
        datosAnuladas.add(new ItemMovimiento("N° Nota de Ventas", "0"));
        datosAnuladas.add(new ItemMovimiento("N° Nota de credito Boletas", "0"));
        datosAnuladas.add(new ItemMovimiento("N° Nota de debito Boletas", "0"));
        datosAnuladas.add(new ItemMovimiento("N° Nota de credito Facturas", "0"));
        datosAnuladas.add(new ItemMovimiento("N° Nota de debito Facturas", "0"));

        cardTransaccionesAnuladas.setRecyclerData(datosAnuladas);
    }

    private void initCardMovimientosIngresos() {
        cardMovimientosIngresos = findViewById(R.id.cardMovimientosIngresos);

        // Configurar headers de la tabla
        cardMovimientosIngresos.setHeaders("Fecha de ingreso", "Usuario", "Concepto", "Monto");

        // Datos estáticos de ejemplo - Puedes cambiar a lista vacía para probar el
        // estado vacío
        List<ItemTablaFila> datosMovimientos = new ArrayList<>();
        datosMovimientos.add(new ItemTablaFila(
                "00/00/0000", "04:05 P.M.",
                "Jean Pierre Santillán García",
                "Ingreso por confirmación de <b>Delivery #31765</b> con forma de pago <b>En línea</b>",
                "S/20.00"));
        datosMovimientos.add(new ItemTablaFila(
                "00/00/0000", "04:05 P.M.",
                "Jean Pierre Santillán García",
                "Ingreso por confirmación de <b>Delivery #31765</b> con forma de pago <b>En línea</b>",
                "S/20.00"));

        cardMovimientosIngresos.setTablaData(datosMovimientos);

        // Los footers solo se muestran si hay datos
        if (!datosMovimientos.isEmpty()) {
            cardMovimientosIngresos.setFooter1("TOTAL INGRESOS (Efectivo y Tarjeta)", "S/ 0.00");
            cardMovimientosIngresos.setFooter2("TOTAL INGRESOS POR DELIVERY (En línea, transferencia, Yape, Plin)",
                    "S/ 40.00");
        }
    }

    private void initCardMovimientosEgresos() {
        cardMovimientosEgresos = findViewById(R.id.cardMovimientosEgresos);

        // Configurar headers de la tabla (5 columnas)
        cardMovimientosEgresos.setHeaders("Fecha de egreso", "Usuario", "Recibido de", "Concepto", "Monto");

        // Datos estáticos de ejemplo con 5 columnas
        List<ItemTablaFila> datosMovimientos = new ArrayList<>();

        cardMovimientosEgresos.setTablaData(datosMovimientos);

        // Los footers se muestran si hay datos
        if (!datosMovimientos.isEmpty()) {
            cardMovimientosEgresos.setFooter1("TOTAL EGRESOS (Efectivo)", "S/ 530.00");
            cardMovimientosEgresos.setFooter2("TOTAL EGRESOS POR TRANSFERENCIA", "S/ 0.00");
        }
    }

    private void initCardIngresosEfectivo() {
        cardIngresosEfectivo = findViewById(R.id.cardIngresosEfectivo);

        // Configurar headers de la tabla (3 columnas)
        cardIngresosEfectivo.setHeaders("Moneda o tarjeta", "Retención", "Monto");

        // Datos estáticos de ejemplo (más de 8 para activar "Mostrar más")
        // noBorder = true para que no tengan bordes divisores
        List<ItemTablaFila> datosIngresos = new ArrayList<>();
        datosIngresos.add(new ItemTablaFila("Soles", "0", "S/546.00", true));
        datosIngresos.add(new ItemTablaFila("POS", "0", "S/4,027.60", true));
        datosIngresos.add(new ItemTablaFila("Pago en linea", "0", "S/9.80", true));
        datosIngresos.add(new ItemTablaFila("Pago por transferencia", "0", "S/1,331.30", true));
        datosIngresos.add(new ItemTablaFila("Pago por Yape", "0", "S/2,405.38", true));
        datosIngresos.add(new ItemTablaFila("Pago por Plin", "0", "S/195.91", true));
        datosIngresos.add(new ItemTablaFila("Pago por Niubiz", "0", "S/228.22", true));
        datosIngresos.add(new ItemTablaFila("Pago por Vale", "0", "S/88.46", true));
        datosIngresos.add(new ItemTablaFila("Pago por Mercado pago", "0", "S/112.72", true));
        datosIngresos.add(new ItemTablaFila("Pago por Fpay", "-", "S/0.00", true));
        datosIngresos.add(new ItemTablaFila("Pago por POS Niubiz", "-", "S/0.00", true));
        datosIngresos.add(new ItemTablaFila("Pago por Izipay", "-", "S/0.00", true));
        datosIngresos.add(new ItemTablaFila("Pago por Qr Izipay", "-", "S/0.00", true));
        datosIngresos.add(new ItemTablaFila("Pago por Databank", "-", "S/0.00", true));
        datosIngresos.add(new ItemTablaFila("Pago por Qr Databank", "-", "S/0.00", true));
        datosIngresos.add(new ItemTablaFila("Cupón Yape", "-", "S/0.00", true));
        datosIngresos.add(new ItemTablaFila("Pago Otros", "-", "S/0.00", true));

        // Configurar footer que se mostrará al expandir
        cardIngresosEfectivo.setFooter1("TOTAL INGRESOS EFECTIVO/TARJETAS POR VENTAS", "S/9,027.60");
        cardIngresosEfectivo.removeFooterMarginTop();

        // Usar setTablaDataWithShowMore para mostrar solo 8 items inicialmente
        cardIngresosEfectivo.setTablaDataWithShowMore(datosIngresos, 8);
    }

    private void initCardIngresosDelivery() {
        cardIngresosDelivery = findViewById(R.id.cardIngresosDelivery);

        // Configurar headers de la tabla (4 columnas) con weights equilibrados
        cardIngresosDelivery.setHeaders("Canal de delivery", "# Operaciones", "Importe total", "Prom. x operación",
                1.2f, 1f, 1f, 1f);

        // Datos estáticos de ejemplo
        // noBorder = true para que no tengan bordes divisores
        List<ItemTablaFila> datosDelivery = new ArrayList<>();
        datosDelivery.add(new ItemTablaFila("Ecommerce Cfi", "0", "S/546.00", "S/546.00", true));
        datosDelivery.add(new ItemTablaFila("Ecommerce Android", "0", "S/4,027.60", "S/4,027.60", true));
        datosDelivery.add(new ItemTablaFila("Rappi", "0", "S/1,331.30", "S/1,331.30", true));
        datosDelivery.add(new ItemTablaFila("Pedidos Ya", "0", "S/2,405.38", "S/2,405.38", true));
        datosDelivery.add(new ItemTablaFila("Justo", "0", "S/228.22", "S/228.22", true));
        datosDelivery.add(new ItemTablaFila("Didi Food", "0", "S/88.46", "S/88.46", true));
        datosDelivery.add(new ItemTablaFila("Ecommerce", "0", "S/1,331.30", "S/1,331.30", true));
        datosDelivery.add(new ItemTablaFila("Ecommerce IOS", "0", "S/195.91", "S/195.91", true));

        cardIngresosDelivery.setTablaData(datosDelivery);

        // Configurar footer con total
        cardIngresosDelivery.setFooter1("TOTAL INGRESOS POR CANALES", "S/9,027.60");
        cardIngresosDelivery.removeFooterMarginTop();
    }

    private void initCardIngresosPropinas() {
        cardIngresosPropinas = findViewById(R.id.cardIngresosPropinas);

        // Configurar headers de la tabla (2 columnas)
        cardIngresosPropinas.setHeaders("Moneda o tarjeta", "Monto");


        List<ItemTablaFila> datosPropinas = new ArrayList<>();
         datosPropinas.add(new ItemTablaFila("POS", "S/30.00", true));

        cardIngresosPropinas.setTablaData(datosPropinas, "No hay ingresos por propinas");

        // El footer solo se muestra si hay datos
        if (!datosPropinas.isEmpty()) {
            cardIngresosPropinas.setFooter1("TOTAL INGRESOS PROPINAS", "S/30.00");
            cardIngresosPropinas.removeFooterMarginTop();
        }
    }

    private void initCardIngresosCreditos() {
        cardIngresosCreditos = findViewById(R.id.cardIngresosCreditos);

        // Configurar headers de la tabla (2 columnas)
        cardIngresosCreditos.setHeaders("Moneda o tarjeta", "Monto");

        // Lista vacía para mostrar el estado vacío
        List<ItemTablaFila> datosCreditos = new ArrayList<>();
        // No hay datos - se mostrará el mensaje de vacío

        cardIngresosCreditos.setTablaData(datosCreditos, "No hay ingresos por créditos cobrados");

        // El footer solo se muestra si hay datos
        if (!datosCreditos.isEmpty()) {
            cardIngresosCreditos.setFooter1("TOTAL INGRESOS CRÉDITOS", "S/0.00");
            cardIngresosCreditos.removeFooterMarginTop();
        }
    }

    private void initTabs() {
        // Referencias a las tabs
        tabTopVentas = findViewById(R.id.tabTopVentas);
        tabProductosEstrellas = findViewById(R.id.tabProductosEstrellas);

        // Referencias a los contenidos
        contentTopVentas = findViewById(R.id.contentTopVentas);
        contentProductosEstrellas = findViewById(R.id.contentProductosEstrellas);

        // Referencias a los iconos y textos
        iconTopVentas = findViewById(R.id.iconTopVentas);
        iconProductosEstrellas = findViewById(R.id.iconProductosEstrellas);
        tvTabTopVentas = findViewById(R.id.tvTabTopVentas);
        tvTabProductosEstrellas = findViewById(R.id.tvTabProductosEstrellas);

        // Listener para Tab Top Ventas
        tabTopVentas.setOnClickListener(v -> selectTab(true));

        // Listener para Tab Productos Estrellas
        tabProductosEstrellas.setOnClickListener(v -> selectTab(false));

        // Establecer estado inicial - Top Ventas activo
        selectTab(true);
    }

    private void selectTab(boolean isTopVentas) {
        int colorActive = ContextCompat.getColor(this, R.color.info);
        int colorInactive = ContextCompat.getColor(this, R.color.text_25);

        if (isTopVentas) {
            // Activar Tab Top Ventas
            iconTopVentas.setColorFilter(colorActive);
            tvTabTopVentas.setTextColor(colorActive);
            tabTopVentas.setBackgroundResource(R.drawable.border_bottom_active);
            contentTopVentas.setVisibility(View.VISIBLE);

            // Desactivar Tab Productos Estrellas
            iconProductosEstrellas.setColorFilter(colorInactive);
            tvTabProductosEstrellas.setTextColor(colorInactive);
            tabProductosEstrellas.setBackgroundResource(R.drawable.border_bottom_tab);
            contentProductosEstrellas.setVisibility(View.GONE);
        } else {
            // Desactivar Tab Top Ventas
            iconTopVentas.setColorFilter(colorInactive);
            tvTabTopVentas.setTextColor(colorInactive);
            tabTopVentas.setBackgroundResource(R.drawable.border_bottom_tab);
            contentTopVentas.setVisibility(View.GONE);

            // Activar Tab Productos Estrellas
            iconProductosEstrellas.setColorFilter(colorActive);
            tvTabProductosEstrellas.setTextColor(colorActive);
            tabProductosEstrellas.setBackgroundResource(R.drawable.border_bottom_active);
            contentProductosEstrellas.setVisibility(View.VISIBLE);
        }
    }

    private void initGraficos() {
        // Referencias
        barChartCategorias = findViewById(R.id.barChartCategorias);
        pieChartProductos = findViewById(R.id.pieChartProductos);
        listaCategorias = findViewById(R.id.listaCategorias);
        listaProductos = findViewById(R.id.listaProductos);

        // Colores para los gráficos (en orden según la imagen)
        int colorInfo = ContextCompat.getColor(this, R.color.info);
        int colorPrimary = ContextCompat.getColor(this, R.color.primary);
        int colorYellow = ContextCompat.getColor(this, R.color.yellow);
        int colorSecondary1 = ContextCompat.getColor(this, R.color.secondary_1);
        int colorSecondary3 = ContextCompat.getColor(this, R.color.secondary_3);

        // Datos de categorías
        List<ItemCategoria> categorias = new ArrayList<>();
        categorias.add(new ItemCategoria("Bebidas con alcohol", "S/989.09", 95.8f, colorInfo));
        categorias.add(new ItemCategoria("Bebidas sin alcohol", "S/497.25", 76.12f, colorPrimary));
        categorias.add(new ItemCategoria("Menú", "S/352.32", 59.20f, colorYellow));
        categorias.add(new ItemCategoria("Platos a la carta", "S/170.87", 25.20f, colorSecondary1));
        categorias.add(new ItemCategoria("Postres", "S/90.25", 10.19f, colorSecondary3));

        // Configurar gráfico de barras
        setupBarChart(categorias);

        // Configurar lista de categorías
        setupListaCategorias(categorias);

        // Datos de productos para el pie chart - Orden correcto según leyenda
        List<ItemCategoria> productos = new ArrayList<>();

        productos.add(new ItemCategoria("Torta helada", "S/10.39", 15f, colorPrimary)); // Verde - 55%
        productos.add(new ItemCategoria("Ensalada Cesar's", "S/40.39", 30f, colorYellow)); // Amarillo - 15%
        productos.add(new ItemCategoria("Pilsen", "S/90.39", 55f, colorInfo)); // Azul - 30%
        // Configurar gráfico de pie
        setupPieChart(productos);

        // Configurar lista de productos (leyenda)
        setupListaProductos(productos);
    }

    private void setupBarChart(List<ItemCategoria> categorias) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();

        // Las barras se posicionan en índices 0, 1, 2, 3, 4...
        for (int i = 0; i < categorias.size(); i++) {
            ItemCategoria cat = categorias.get(i);
            entries.add(new BarEntry(i, cat.getPorcentaje()));
            colors.add(cat.getColor());
        }

        BarDataSet dataSet = new BarDataSet(entries, "");
        dataSet.setColors(colors);
        dataSet.setDrawValues(true);
        dataSet.setValueTextSize(12f);
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format("%.1f%%", value).replace(",", ".");
            }
        });

        BarData barData = new BarData(dataSet);
        // CLAVE: barWidth más pequeño = más espacio entre barras
        // El espacio entre barras = 1.0 - barWidth (porque las posiciones son 0,1,2,3,4)
        barData.setBarWidth(0.25f); // Barras ocupan 40% del espacio, 60% es separación

        // Configuración del gráfico
        barChartCategorias.setFitBars(true); // Activar para que ajuste las barras al viewport
        barChartCategorias.getDescription().setEnabled(false);
        barChartCategorias.getLegend().setEnabled(false);
        barChartCategorias.setDrawGridBackground(false);
        barChartCategorias.setDrawBorders(false);
        barChartCategorias.setTouchEnabled(false);
        barChartCategorias.setDragEnabled(false);
        barChartCategorias.setScaleEnabled(false);
        barChartCategorias.setPinchZoom(false);
        barChartCategorias.setDoubleTapToZoomEnabled(false);
        barChartCategorias.setExtraBottomOffset(20f);
        barChartCategorias.setExtraLeftOffset(10f);
        barChartCategorias.setExtraRightOffset(10f);

        // Eje X
        XAxis xAxis = barChartCategorias.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawLabels(false);
        // Centrar las barras en el viewport
        xAxis.setAxisMinimum(-0.5f);
        xAxis.setAxisMaximum(categorias.size() - 0.5f);

        // Eje Y izquierdo
        barChartCategorias.getAxisLeft().setEnabled(false);
        barChartCategorias.getAxisLeft().setAxisMinimum(0f);
        barChartCategorias.getAxisLeft().setAxisMaximum(100f);

        // Eje Y derecho
        barChartCategorias.getAxisRight().setEnabled(false);

        // Establecer datos
        barChartCategorias.setData(barData);

        // Configurar renderer personalizado DESPUÉS de setData
        int backgroundColor = ContextCompat.getColor(this, R.color.text_5);
        RoundedBarChartRenderer renderer = new RoundedBarChartRenderer(
                barChartCategorias,
                barChartCategorias.getAnimator(),
                barChartCategorias.getViewPortHandler(),
                backgroundColor);
        renderer.setRadius(50f);
        renderer.initBuffers();
        barChartCategorias.setRenderer(renderer);

        barChartCategorias.invalidate();
    }

    private void setupListaCategorias(List<ItemCategoria> categorias) {
        listaCategorias.removeAllViews();

        for (ItemCategoria cat : categorias) {
            View itemView = LayoutInflater.from(this).inflate(R.layout.item_categoria, listaCategorias, false);

            View colorDot = itemView.findViewById(R.id.viewColorDot);
            TextView tvNombre = itemView.findViewById(R.id.tvCategoriaNombre);
            TextView tvMonto = itemView.findViewById(R.id.tvCategoriaMonto);

            // Cambiar color del punto
            GradientDrawable dotDrawable = new GradientDrawable();
            dotDrawable.setShape(GradientDrawable.OVAL);
            dotDrawable.setColor(cat.getColor());
            dotDrawable.setSize(24, 24);
            colorDot.setBackground(dotDrawable);

            // Agregar puntos suspensivos entre el nombre y el monto
            tvNombre.setText(
                    cat.getNombre() + " ...........................................................................");
            tvMonto.setText(cat.getMonto());

            listaCategorias.addView(itemView);
        }
    }

    private void setupPieChart(List<ItemCategoria> productos) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();

        for (ItemCategoria prod : productos) {
            entries.add(new PieEntry(prod.getPorcentaje(), ""));
            colors.add(prod.getColor());
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors);
        dataSet.setSliceSpace(0f);
        dataSet.setSelectionShift(0f);

        // Mostrar porcentajes dentro del pie
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(12f);
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return (int) value + "%";
            }
        });

        PieData pieData = new PieData(dataSet);
        pieChartProductos.setData(pieData);

        // Configuración del gráfico
        pieChartProductos.setUsePercentValues(false);
        pieChartProductos.getDescription().setEnabled(false);
        pieChartProductos.getLegend().setEnabled(false);
        pieChartProductos.setDrawHoleEnabled(false);
        pieChartProductos.setDrawEntryLabels(false);
        pieChartProductos.setRotationEnabled(false);
        pieChartProductos.setHighlightPerTapEnabled(false);
        pieChartProductos.setTouchEnabled(false);

        pieChartProductos.invalidate();
    }

    private void setupListaProductos(List<ItemCategoria> productos) {
        listaProductos.removeAllViews();

        for (ItemCategoria prod : productos) {
            View itemView = LayoutInflater.from(this).inflate(R.layout.item_producto_leyenda, listaProductos, false);

            View colorDot = itemView.findViewById(R.id.viewColorDot);
            TextView tvNombre = itemView.findViewById(R.id.tvProductoNombre);
            TextView tvMonto = itemView.findViewById(R.id.tvProductoMonto);

            // Cambiar color del punto
            GradientDrawable dotDrawable = new GradientDrawable();
            dotDrawable.setShape(GradientDrawable.OVAL);
            dotDrawable.setColor(prod.getColor());
            dotDrawable.setSize(24, 24);
            colorDot.setBackground(dotDrawable);

            tvNombre.setText(prod.getNombre());
            tvMonto.setText(prod.getMonto());

            listaProductos.addView(itemView);
        }
    }

    private void initProductosEstrella() {
        listaOtrosProductosEstrella = findViewById(R.id.listaOtrosProductosEstrella);
        nestedScrollProductos = findViewById(R.id.nestedScrollProductos);

        // Fix para el scroll anidado: evita comportamiento anidado y que el padre
        // intercepte
        nestedScrollProductos.setNestedScrollingEnabled(false);
        nestedScrollProductos.setOnTouchListener((v, event) -> {
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
            }
            return false;
        });

        // Datos mock de otros productos estrella
        List<ItemProductoEstrella> productos = new ArrayList<>();
        productos.add(new ItemProductoEstrella(2, "P", "Cerveza Pilsen", "650ml", "150 VENTAS", "52,45%"));
        productos.add(new ItemProductoEstrella(3, "P", "Lomo saltado", "Plato", "120 VENTAS", "48,30%"));
        productos.add(new ItemProductoEstrella(4, "R", "Ronda criolla familiar", "Fuente", "98 VENTAS", "42,15%"));
        productos.add(
                new ItemProductoEstrella(5, "P", "Tallarines verdes con chuleta", "Personal", "85 VENTAS", "38,50%"));
        productos.add(new ItemProductoEstrella(6, "I", "Pollo", "Entero", "72 VENTAS", "35,20%"));
        productos.add(new ItemProductoEstrella(7, "I", "Mayonesa", "Personal", "65 VENTAS", "30,10%"));
        productos.add(new ItemProductoEstrella(8, "R", "Arroz chaufa especial", "Personal", "58 VENTAS", "28,45%"));
        productos.add(new ItemProductoEstrella(9, "P", "Chicharrón", "Porción", "45 VENTAS", "22,30%"));
        productos.add(new ItemProductoEstrella(10, "P", "Ceviche mixto", "Personal", "40 VENTAS", "18,75%"));

        // Limpiar lista y agregar items
        listaOtrosProductosEstrella.removeAllViews();

        for (ItemProductoEstrella producto : productos) {
            View itemView = LayoutInflater.from(this).inflate(R.layout.item_producto_estrella,
                    listaOtrosProductosEstrella, false);

            TextView tvRanking = itemView.findViewById(R.id.tvRanking);
            TextView tvLetraInicial = itemView.findViewById(R.id.tvLetraInicial);
            TextView tvNombreProducto = itemView.findViewById(R.id.tvNombreProducto);
            TextView tvCategoriaProducto = itemView.findViewById(R.id.tvCategoriaProducto);
            TextView tvVentasProducto = itemView.findViewById(R.id.tvVentasProducto);
            TextView tvPorcentajeProducto = itemView.findViewById(R.id.tvPorcentajeProducto);

            tvRanking.setText("#" + producto.getRanking());
            tvLetraInicial.setText(producto.getLetraInicial());
            tvNombreProducto.setText(producto.getNombreProducto());
            tvCategoriaProducto.setText(producto.getCategoria());
            tvVentasProducto.setText(producto.getVentas());
            tvPorcentajeProducto.setText(producto.getPorcentaje());

            // Aplicar colores según el tipo
            String tipo = producto.getLetraInicial();
            if ("P".equals(tipo)) {
                // Producto - azul (info)
                tvLetraInicial.setBackgroundResource(R.drawable.rectangular_badge_info);
                tvLetraInicial.setTextColor(ContextCompat.getColor(this, R.color.info));
            } else if ("I".equals(tipo)) {
                // Insumo - verde (primary)
                tvLetraInicial.setBackgroundResource(R.drawable.rectangular_badge_primary);
                tvLetraInicial.setTextColor(ContextCompat.getColor(this, R.color.primary));
            } else if ("R".equals(tipo)) {
                // Receta - morado (secondary)
                tvLetraInicial.setBackgroundResource(R.drawable.rectangular_badge_secondary);
                tvLetraInicial.setTextColor(ContextCompat.getColor(this, R.color.secondary));
            }

            listaOtrosProductosEstrella.addView(itemView);
        }
    }

    /**
     * Inicializa los datos de la caja (Usuario, Apertura, Turno, Fecha Apertura, Total en Caja)
     */
    private void initDatosCaja() {
        // Vincular TextViews
        tvUsuarioNombre = findViewById(R.id.tvUsuarioNombre);
        tvAperturaMonto = findViewById(R.id.tvAperturaMonto);
        tvTurnoNombre = findViewById(R.id.tvTurnoNombre);
        tvFechaApertura = findViewById(R.id.tvFechaApertura);
        tvTotalCajaPrincipal = findViewById(R.id.tvTotalCajaPrincipal);

        // Crear objeto con datos mock (en producción vendría del endpoint)
        DatosCaja datosCaja = new DatosCaja(
                "Mónica González",
                "S/ 00.00",
                "Mañana",
                "30/06 - 04:00 PM",
                "S/ 7,937.79"
        );

        // Setear datos en los TextViews
        tvUsuarioNombre.setText(datosCaja.getUsuario());
        tvAperturaMonto.setText(datosCaja.getApertura());
        tvTurnoNombre.setText(datosCaja.getTurno());
        tvFechaApertura.setText(datosCaja.getFechaApertura());
        tvTotalCajaPrincipal.setText(datosCaja.getTotalCaja());
    }

    /**
     * Inicializa los datos del resumen de pagos (Efectivo, Tarjetas, Total)
     */
    private void initResumenPagos() {
        // Vincular TextViews
        tvFechaResumen = findViewById(R.id.tvFechaResumen);
        tvEnEfectivo = findViewById(R.id.tvEnEfectivo);
        tvEnTarjetas = findViewById(R.id.tvEnTarjetas);
        tvTotalCajaResumen = findViewById(R.id.tvTotalCajaResumen);

        // Crear objeto con datos mock
        ResumenPagos resumen = new ResumenPagos(
                "Resumen hasta 27/12/2025, 9:48 AM",
                "S/ 1,062.70",
                "S/ 6,875.09",
                "S/ 6,875.09"
        );

        // Setear datos en los TextViews
        tvFechaResumen.setText(resumen.getFechaResumen());
        tvEnEfectivo.setText(resumen.getEnEfectivo());
        tvEnTarjetas.setText(resumen.getEnTarjetas());
        tvTotalCajaResumen.setText(resumen.getTotalCaja());
    }

    /**
     * Inicializa los datos de créditos
     */
    private void initDatosCreditos() {
        // Vincular TextViews
        tvCreditosGenerados = findViewById(R.id.tvCreditosGenerados);
        tvTotalCreditos = findViewById(R.id.tvTotalCreditos);
        tvValesPago = findViewById(R.id.tvValesPago);
        tvMontoValesPago = findViewById(R.id.tvMontoValesPago);

        // Crear objeto con datos mock
        DatosCreditos creditos = new DatosCreditos(
                "0",
                "S/00.00",
                "S/00.00",
                "S/00.00"
        );

        // Setear datos en los TextViews
        tvCreditosGenerados.setText(creditos.getCreditosGenerados());
        tvTotalCreditos.setText(creditos.getTotalCreditos());
        tvValesPago.setText(creditos.getValesPago());
        tvMontoValesPago.setText(creditos.getMontoValesPago());
    }

    /**
     * Inicializa los datos de atenciones
     */
    private void initDatosAtenciones() {
        // Vincular TextViews
        tvMesasAtendidas = findViewById(R.id.tvMesasAtendidas);
        tvPersonasAtendidas = findViewById(R.id.tvPersonasAtendidas);

        // Crear objeto con datos mock
        DatosAtenciones atenciones = new DatosAtenciones(
                "0",
                "204"
        );

        // Setear datos en los TextViews
        tvMesasAtendidas.setText(atenciones.getMesasAtendidas());
        tvPersonasAtendidas.setText(atenciones.getPersonasAtendidas());
    }

    /**
     * Inicializa los datos de impuestos
     */
    private void initDatosImpuestos() {
        // Vincular TextViews
        tvImpuestosIgv = findViewById(R.id.tvImpuestosIgv);
        tvImpuestosIcbper = findViewById(R.id.tvImpuestosIcbper);

        // Crear objeto con datos mock
        DatosImpuestos impuestos = new DatosImpuestos(
                "S/1,672.55",
                "S/00.00"
        );

        // Setear datos en los TextViews
        tvImpuestosIgv.setText(impuestos.getIgv());
        tvImpuestosIcbper.setText(impuestos.getIcbper());
    }
}


