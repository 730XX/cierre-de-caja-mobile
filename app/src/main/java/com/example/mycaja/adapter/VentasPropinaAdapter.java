package com.example.mycaja.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycaja.R;
import com.example.mycaja.model.VentaPropina;

import java.util.List;

public class VentasPropinaAdapter extends RecyclerView.Adapter<VentasPropinaAdapter.ViewHolder> {

    private List<VentaPropina> listaVentas;
    private Context context;

    public VentasPropinaAdapter(Context context, List<VentaPropina> listaVentas) {
        this.context = context;
        this.listaVentas = listaVentas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_venta_propina, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VentaPropina venta = listaVentas.get(position);
        holder.tvTipoDoc.setText(venta.getTipoDoc());
        holder.tvSerieNumero.setText(venta.getSerieNumero());
        holder.tvPropina.setText(venta.getPropina());
        holder.tvComision.setText(venta.getComision());
        holder.tvTotalPropina.setText(venta.getTotalPropina());
    }

    @Override
    public int getItemCount() {
        return listaVentas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTipoDoc;
        TextView tvSerieNumero;
        TextView tvPropina;
        TextView tvComision;
        TextView tvTotalPropina;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTipoDoc = itemView.findViewById(R.id.tv_tipo_doc);
            tvSerieNumero = itemView.findViewById(R.id.tv_serie_numero);
            tvPropina = itemView.findViewById(R.id.tv_propina);
            tvComision = itemView.findViewById(R.id.tv_comision);
            tvTotalPropina = itemView.findViewById(R.id.tv_total_propina);
        }
    }
}
