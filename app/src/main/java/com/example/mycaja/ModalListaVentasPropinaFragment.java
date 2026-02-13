package com.example.mycaja;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycaja.adapter.VentasPropinaAdapter;
import com.example.mycaja.model.VentaPropina;
import com.example.mycaja.ui.FooterModal;
import com.example.mycaja.ui.HeaderModal;

import java.util.ArrayList;
import java.util.List;

public class ModalListaVentasPropinaFragment extends DialogFragment {

    private RecyclerView rvVentasPropina;
    private VentasPropinaAdapter adapter;

    public static ModalListaVentasPropinaFragment newInstance() {
        return new ModalListaVentasPropinaFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.ModalDialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modal_lista_ventas_propina, container, false);

        // Configurar Header
        HeaderModal header = view.findViewById(R.id.header_modal);
        header.setOnCloseClickListener(v -> dismiss());

        // Cerrar al tocar el fondo oscuro
        View modalBackground = view.findViewById(R.id.modal_background);
        View modalContent = view.findViewById(R.id.modal_content);

        modalBackground.setOnClickListener(v -> dismiss());

        // Evitar que el clic en el contenido cierre el modal
        modalContent.setOnClickListener(v -> {});

        // Configurar Footer
        FooterModal footer = view.findViewById(R.id.footer_modal);
        footer.setOnCancelarClickListener(v -> dismiss());
        footer.setOnPrimarioClickListener(v -> {
            // TODO: Implementar lógica de registro de egreso
            registrarEgreso();
            dismiss();
        });

        // Inicializar RecyclerView
        initRecyclerView(view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            Window window = dialog.getWindow();
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    private void initRecyclerView(View view) {
        rvVentasPropina = view.findViewById(R.id.rv_ventas_propina);
        rvVentasPropina.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Datos de ejemplo - puedes reemplazar esto con datos reales
        List<VentaPropina> listaVentas = obtenerDatosEjemplo();

        adapter = new VentasPropinaAdapter(requireContext(), listaVentas);
        rvVentasPropina.setAdapter(adapter);
    }

    /**
     * Método para obtener datos de ejemplo
     * Reemplaza esto con tu lógica real para obtener las ventas con propina
     */
    private List<VentaPropina> obtenerDatosEjemplo() {
        List<VentaPropina> lista = new ArrayList<>();
        lista.add(new VentaPropina("boleta", "B004-0000038", "S/ 333.33", "S/ 0.00", "S/ 333.33"));
        lista.add(new VentaPropina("boleta", "B004-0000038", "S/ 333.33", "S/ 0.00", "S/ 333.33"));
        return lista;
    }

    /**
     * Método para registrar el egreso de propinas
     * Implementa aquí tu lógica de negocio
     */
    private void registrarEgreso() {
        // TODO: Implementar lógica para registrar el egreso
        // Por ejemplo: calcular total, enviar a servidor, actualizar base de datos, etc.
    }

    /**
     * Método público para actualizar los datos desde fuera del fragment
     */
    public void actualizarDatos(List<VentaPropina> nuevasVentas) {
        if (adapter != null) {
            // Aquí puedes implementar un método updateData en el adapter
            adapter = new VentasPropinaAdapter(requireContext(), nuevasVentas);
            rvVentasPropina.setAdapter(adapter);
        }
    }
}
