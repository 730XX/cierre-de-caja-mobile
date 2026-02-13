package com.example.mycaja.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycaja.R;
import com.example.mycaja.model.Impresora; // Asumiendo que creaste el modelo Impresora

import java.util.List;

public class ImpresorasAdapter extends RecyclerView.Adapter<ImpresorasAdapter.ImpresoraViewHolder> {

    private List<Impresora> listaImpresoras;
    private Context context;

    // Variable para rastrear cuál está seleccionada (-1 = ninguna)
    private int posicionSeleccionada = -1;

    // Payload para actualización parcial
    private static final String PAYLOAD_SELECTION = "SELECTION";

    public ImpresorasAdapter(Context context, List<Impresora> listaImpresoras) {
        this.context = context;
        this.listaImpresoras = listaImpresoras;
    }

    @NonNull
    @Override
    public ImpresoraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_impresora_imprimir_paloteo, parent, false);
        return new ImpresoraViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImpresoraViewHolder holder, int position) {
        onBindViewHolder(holder, position, new java.util.ArrayList<>());
    }

    @Override
    public void onBindViewHolder(@NonNull ImpresoraViewHolder holder, int position, @NonNull List<Object> payloads) {
        Impresora item = listaImpresoras.get(position);

        // Si hay payload, solo actualizamos la selección
        if (!payloads.isEmpty() && payloads.contains(PAYLOAD_SELECTION)) {
            boolean isSelected = (position == posicionSeleccionada);
            holder.itemView.setSelected(isSelected);
            return;
        }

        // Bind completo (primera vez o cuando no hay payload)
        holder.tvNombre.setText(item.nombre);
        holder.tvModelo.setText(item.modelo);
        holder.tvTipo.setText(item.tipoDoc);
        holder.tvEstado.setText(item.estado);

        // Lógica del Icono (Bluetooth vs Wifi)
        if (item.esBluetooth) {
            holder.ivIcono.setImageResource(R.drawable.icon_svg_bluetooh);
        } else {
            holder.ivIcono.setImageResource(R.drawable.icon_svg_wifi);
        }

        // Aplicar estado de selección
        boolean isSelected = (position == posicionSeleccionada);
        holder.itemView.setSelected(isSelected);

        // Click Listener
        holder.itemView.setOnClickListener(v -> {
            int posicionAnterior = posicionSeleccionada;
            posicionSeleccionada = holder.getAdapterPosition();

            // Usar payload para actualización parcial (sin animación de parpadeo)
            if (posicionAnterior != -1) {
                notifyItemChanged(posicionAnterior, PAYLOAD_SELECTION);
            }
            notifyItemChanged(posicionSeleccionada, PAYLOAD_SELECTION);
        });
    }

    @Override
    public int getItemCount() {
        return listaImpresoras.size();
    }

    // Método para obtener la impresora seleccionada desde el Fragmento
    public Impresora getImpresoraSeleccionada() {
        if (posicionSeleccionada != -1) {
            return listaImpresoras.get(posicionSeleccionada);
        }
        return null;
    }

    // ViewHolder: Mantiene las referencias a los controles
    public static class ImpresoraViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvModelo, tvTipo, tvEstado;
        ImageView ivIcono;

        public ImpresoraViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tv_nombre_impresora);
            tvModelo = itemView.findViewById(R.id.tv_modelo_impresora);
            tvTipo = itemView.findViewById(R.id.tv_tipo_doc);
            tvEstado = itemView.findViewById(R.id.tv_estado);
            ivIcono = itemView.findViewById(R.id.iv_tipo_conexion);
        }
    }
}