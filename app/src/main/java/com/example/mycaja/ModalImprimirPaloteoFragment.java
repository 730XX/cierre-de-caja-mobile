package com.example.mycaja;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycaja.adapter.CustomSpinnerAdapter;
import com.example.mycaja.adapter.ImpresorasAdapter;
import com.example.mycaja.model.Impresora;
import com.example.mycaja.ui.FooterModal;
import com.example.mycaja.ui.HeaderModal;

import java.util.ArrayList;
import java.util.List;

public class ModalImprimirPaloteoFragment extends DialogFragment {

    private RecyclerView rvImpresoras;
    private ImpresorasAdapter adapterImpresoras;
    private Spinner spinnerCanalVenta;
    private Spinner spinnerOrdernarPor;

    // 1. CORREGIDO: El tipo de retorno debe ser la misma clase del Fragment
    public static ModalImprimirPaloteoFragment newInstance() {
        return new ModalImprimirPaloteoFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Usar estilo sin frame para poder poner nuestro propio fondo
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.ModalDialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflamos tu layout
        View view = inflater.inflate(R.layout.fragment_modal_imprimir_paloteo, container, false);

        // Configurar Header
        HeaderModal header = view.findViewById(R.id.header_modal);
        header.setOnCloseClickListener(v -> dismiss());

        // Cerrar al tocar el fondo oscuro
        View modalBackground = view.findViewById(R.id.modal_background);
        View modalContent = view.findViewById(R.id.modal_content);

        modalBackground.setOnClickListener(v -> dismiss());

        // Evitar que el clic en el contenido cierre el modal
        modalContent.setOnClickListener(v -> {
        });

        // Configurar Footer
        FooterModal footer = view.findViewById(R.id.footer_modal);
        footer.setOnCancelarClickListener(v -> dismiss());
        footer.setOnPrimarioClickListener(v -> {
            // TODO: Implementar lógica de impresión
            dismiss();
        });

        // 3. CORREGIDO: Pasamos la vista 'view' al método
        initSpinners(view);
        initRecyclerViewImpresoras(view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Configuramos la ventana para que sea transparente y ocupe toda la pantalla
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            Window window = dialog.getWindow();
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    // 4. CORREGIDO: Recibimos (View view) como parámetro
    private void initSpinners(View view) {

        // 5. CORREGIDO: Usamos los IDs reales de tu XML nuevo
        spinnerCanalVenta = view.findViewById(R.id.spinnerCanalVenta);
        spinnerOrdernarPor = view.findViewById(R.id.spinnerFiltros);

        // Datos de ejemplo
        String[] canalesVenta = {"Venta en salones", "Venta rápida", "Venta Delivery"};
        String[] filtros = {"Nombre", "Importe vendido", "Cantidad Vendida"};

        // Adaptadores con requireContext()
        CustomSpinnerAdapter adapterCanal = new CustomSpinnerAdapter(requireContext(), canalesVenta);
        spinnerCanalVenta.setAdapter(adapterCanal);

        CustomSpinnerAdapter adapterPeriodos = new CustomSpinnerAdapter(requireContext(), filtros);
        spinnerOrdernarPor.setAdapter(adapterPeriodos);

        // Establecer valores por defecto (opcional)
        // spinnerCanalVenta.setSelection(0);
    }

    private void initRecyclerViewImpresoras(View view) {
        rvImpresoras = view.findViewById(R.id.rv_impresoras);

        // 1. Configurar LayoutManager (Vertical)
        rvImpresoras.setLayoutManager(new LinearLayoutManager(requireContext()));

        // 2. Crear Datos Dummy
        List<Impresora> lista = new ArrayList<>();
        lista.add(new Impresora("EPSON", "TMU220", "BOLETA", "Vinculado", true));
        lista.add(new Impresora("STAR", "TSP100", "FACTURA", "Conectado", false));
        lista.add(new Impresora("BIXOLON", "SRP-350", "TICKET", "Vinculado", false));

        // 3. Crear y asignar Adapter
        adapterImpresoras = new ImpresorasAdapter(requireContext(), lista);
        rvImpresoras.setAdapter(adapterImpresoras);
    }
}