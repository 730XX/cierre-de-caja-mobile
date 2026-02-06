package com.example.mycaja.adapter;

import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycaja.R;
import com.example.mycaja.model.ItemMovimiento;
import com.example.mycaja.utils.TextUtils;

import java.util.List;

public class MovimientosAdapter extends RecyclerView.Adapter<MovimientosAdapter.ViewHolder> {

    private List<ItemMovimiento> items;

    public MovimientosAdapter(List<ItemMovimiento> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingreso_egreso, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemMovimiento item = items.get(position);
        holder.tvConcepto.setText(item.getConcepto());
        SpannableString montoConNegrita = TextUtils.aplicarNegritaConRegex(
            item.getMonto(),
            "\\b[A-Z]\\d+\\b",     // Códigos como F002, B001 (con límites de palabra)
            "\\b\\d{5,}\\b"        // Números de 5+ dígitos como 00002266 (con límites de palabra)
        );
        holder.tvMonto.setText(montoConNegrita);

        // Mostrar columna del medio solo si tiene cantidad
        if (item.tieneCantidad()) {
            holder.tvCantidad.setVisibility(View.VISIBLE);
            holder.tvCantidad.setText(item.getCantidad());

            // Ajustar LayoutParams para 3 columnas
            LinearLayout.LayoutParams conceptoParams = new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.5f);
            holder.tvConcepto.setLayoutParams(conceptoParams);

            LinearLayout.LayoutParams cantidadParams = new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f);
            holder.tvCantidad.setLayoutParams(cantidadParams);

            LinearLayout.LayoutParams montoParams = new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f);
            holder.tvMonto.setLayoutParams(montoParams);
        } else {
            holder.tvCantidad.setVisibility(View.GONE);

            // Ajustar LayoutParams para 2 columnas
            LinearLayout.LayoutParams conceptoParams = new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            holder.tvConcepto.setLayoutParams(conceptoParams);

            LinearLayout.LayoutParams montoParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            holder.tvMonto.setLayoutParams(montoParams);
        }
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public void updateData(List<ItemMovimiento> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvConcepto;
        TextView tvCantidad;
        TextView tvMonto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvConcepto = itemView.findViewById(R.id.tvConcepto);
            tvCantidad = itemView.findViewById(R.id.tvCantidad);
            tvMonto = itemView.findViewById(R.id.tvMonto);
        }
    }
}
