package com.example.mycaja;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.example.mycaja.ui.FooterModal;
import com.example.mycaja.ui.HeaderModal;

public class ModalCerrarCajaActivity extends DialogFragment {

    // Patrón Factory para crear la modal
    public static ModalCerrarCajaActivity newInstance() {
        return new ModalCerrarCajaActivity();
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
        // Inflamos tu layout actual
        View view = inflater.inflate(R.layout.modal_cerrar_caja, container, false);

        // Configurar Header
        HeaderModal header = view.findViewById(R.id.header_modal);

        // Aquí le damos la orden de cerrar la modal
        header.setOnCloseClickListener(v -> dismiss());

        // Cerrar al tocar el fondo oscuro
        View modalBackground = view.findViewById(R.id.modal_background);
        View modalContent = view.findViewById(R.id.modal_content);

        modalBackground.setOnClickListener(v -> dismiss());

        // Evitar que el clic en el contenido cierre el modal
        modalContent.setOnClickListener(v -> {
            // No hacer nada, esto previene que el clic se propague al fondo
        });

        // Configurar FooterModal
        FooterModal footerModal = view.findViewById(R.id.footer_modal);

        // Listener para botón Cancelar
        footerModal.setOnCancelarClickListener(v -> dismiss());

        // Listener para botón Guardar Arqueo
        footerModal.setOnPrimarioClickListener(v -> {
            // TODO: Implementar lógica para guardar el arqueo
            // Por ahora solo cierra el modal
            dismiss();
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Configuramos la ventana para que sea transparente y ocupe el tamaño correcto
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            Window window = dialog.getWindow();

            // Hacer el fondo transparente
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            // Hacer que ocupe toda la pantalla para que el fondo oscuro cubra todo
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }
}