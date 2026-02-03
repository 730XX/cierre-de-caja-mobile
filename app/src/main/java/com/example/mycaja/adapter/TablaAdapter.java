package com.example.mycaja.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycaja.R;
import com.example.mycaja.model.ItemTablaFila;

import java.util.List;

public class TablaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_2_COL_NOBORDER = 12;
    private static final int VIEW_TYPE_3_COL = 0;
    private static final int VIEW_TYPE_4_COL = 1;
    private static final int VIEW_TYPE_4_COL_SIMPLE = 3;
    private static final int VIEW_TYPE_5_COL = 2;
    private static final int VIEW_TYPE_3_COL_NOBORDER = 10;
    private static final int VIEW_TYPE_4_COL_NOBORDER = 11;

    private List<ItemTablaFila> items;

    public TablaAdapter(List<ItemTablaFila> items) {
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        ItemTablaFila item = items.get(position);
        int numCol = item.getNumColumnas();
        boolean noBorder = item.isNoBorder();

        if (numCol == 2) return VIEW_TYPE_2_COL_NOBORDER;
        if (numCol == 3) return noBorder ? VIEW_TYPE_3_COL_NOBORDER : VIEW_TYPE_3_COL;
        if (numCol == 44) return noBorder ? VIEW_TYPE_4_COL_NOBORDER : VIEW_TYPE_4_COL_SIMPLE;
        if (numCol == 5) return VIEW_TYPE_5_COL;
        return VIEW_TYPE_4_COL;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_2_COL_NOBORDER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_tabla_fila_2col_noborder, parent, false);
            return new ViewHolder2Col(view);
        } else if (viewType == VIEW_TYPE_3_COL) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_tabla_fila_3col, parent, false);
            return new ViewHolder3Col(view);
        } else if (viewType == VIEW_TYPE_3_COL_NOBORDER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_tabla_fila_3col_noborder, parent, false);
            return new ViewHolder3Col(view);
        } else if (viewType == VIEW_TYPE_4_COL_SIMPLE) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_tabla_fila_4col_simple, parent, false);
            return new ViewHolder4ColSimple(view);
        } else if (viewType == VIEW_TYPE_4_COL_NOBORDER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_tabla_fila_4col_noborder, parent, false);
            return new ViewHolder4ColSimple(view);
        } else if (viewType == VIEW_TYPE_5_COL) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_tabla_fila_5col, parent, false);
            return new ViewHolder5Col(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_tabla_fila, parent, false);
            return new ViewHolder4Col(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemTablaFila item = items.get(position);

        if (holder instanceof ViewHolder2Col) {
            ViewHolder2Col vh = (ViewHolder2Col) holder;
            vh.tvCol1.setText(item.getCol1Line1());
            vh.tvCol2.setText(item.getCol2());

        } else if (holder instanceof ViewHolder3Col) {
            ViewHolder3Col vh = (ViewHolder3Col) holder;
            vh.tvCol1.setText(item.getCol1Line1());
            vh.tvCol2.setText(item.getCol2());
            vh.tvCol3.setText(item.getCol3());

        } else if (holder instanceof ViewHolder4ColSimple) {
            ViewHolder4ColSimple vh = (ViewHolder4ColSimple) holder;
            vh.tvCol1.setText(item.getCol1Line1());
            vh.tvCol2.setText(item.getCol2());
            vh.tvCol3.setText(item.getCol3());
            vh.tvCol4.setText(item.getCol4());

        } else if (holder instanceof ViewHolder5Col) {
            ViewHolder5Col vh = (ViewHolder5Col) holder;
            vh.tvCol1Line1.setText(item.getCol1Line1());

            if (item.tieneSegundaLinea()) {
                vh.tvCol1Line2.setVisibility(View.VISIBLE);
                vh.tvCol1Line2.setText(item.getCol1Line2());
            } else {
                vh.tvCol1Line2.setVisibility(View.GONE);
            }

            vh.tvCol2.setText(item.getCol2());
            vh.tvCol3.setText(item.getCol3());
            setHtmlText(vh.tvCol4, item.getCol4());
            vh.tvCol5.setText(item.getCol5());

        } else if (holder instanceof ViewHolder4Col) {
            ViewHolder4Col vh = (ViewHolder4Col) holder;
            vh.tvCol1Line1.setText(item.getCol1Line1());

            if (item.tieneSegundaLinea()) {
                vh.tvCol1Line2.setVisibility(View.VISIBLE);
                vh.tvCol1Line2.setText(item.getCol1Line2());
            } else {
                vh.tvCol1Line2.setVisibility(View.GONE);
            }

            vh.tvCol2.setText(item.getCol2());
            setHtmlText(vh.tvCol3, item.getCol3());
            vh.tvCol4.setText(item.getCol4());
        }
    }

    private void setHtmlText(TextView tv, String text) {
        if (text != null && text.contains("<b>")) {
            tv.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT));
        } else {
            tv.setText(text);
        }
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public void updateData(List<ItemTablaFila> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    // ViewHolder para 3 columnas
    // ViewHolder para 2 columnas
    public static class ViewHolder2Col extends RecyclerView.ViewHolder {
        TextView tvCol1;
        TextView tvCol2;

        public ViewHolder2Col(@NonNull View itemView) {
            super(itemView);
            tvCol1 = itemView.findViewById(R.id.tvCol1);
            tvCol2 = itemView.findViewById(R.id.tvCol2);
        }
    }

    // ViewHolder para 3 columnas
    public static class ViewHolder3Col extends RecyclerView.ViewHolder {
        TextView tvCol1;
        TextView tvCol2;
        TextView tvCol3;

        public ViewHolder3Col(@NonNull View itemView) {
            super(itemView);
            tvCol1 = itemView.findViewById(R.id.tvCol1);
            tvCol2 = itemView.findViewById(R.id.tvCol2);
            tvCol3 = itemView.findViewById(R.id.tvCol3);
        }
    }

    // ViewHolder para 4 columnas simples (sin fecha)
    public static class ViewHolder4ColSimple extends RecyclerView.ViewHolder {
        TextView tvCol1;
        TextView tvCol2;
        TextView tvCol3;
        TextView tvCol4;

        public ViewHolder4ColSimple(@NonNull View itemView) {
            super(itemView);
            tvCol1 = itemView.findViewById(R.id.tvCol1);
            tvCol2 = itemView.findViewById(R.id.tvCol2);
            tvCol3 = itemView.findViewById(R.id.tvCol3);
            tvCol4 = itemView.findViewById(R.id.tvCol4);
        }
    }

    // ViewHolder para 4 columnas con fecha (2 líneas)
    public static class ViewHolder4Col extends RecyclerView.ViewHolder {
        LinearLayout col1Container;
        TextView tvCol1Line1;
        TextView tvCol1Line2;
        TextView tvCol2;
        TextView tvCol3;
        TextView tvCol4;

        public ViewHolder4Col(@NonNull View itemView) {
            super(itemView);
            col1Container = itemView.findViewById(R.id.col1Container);
            tvCol1Line1 = itemView.findViewById(R.id.tvCol1Line1);
            tvCol1Line2 = itemView.findViewById(R.id.tvCol1Line2);
            tvCol2 = itemView.findViewById(R.id.tvCol2);
            tvCol3 = itemView.findViewById(R.id.tvCol3);
            tvCol4 = itemView.findViewById(R.id.tvCol4);
        }
    }

    // ViewHolder para 5 columnas
    public static class ViewHolder5Col extends RecyclerView.ViewHolder {
        LinearLayout col1Container;
        TextView tvCol1Line1;
        TextView tvCol1Line2;
        TextView tvCol2;
        TextView tvCol3;
        TextView tvCol4;
        TextView tvCol5;

        public ViewHolder5Col(@NonNull View itemView) {
            super(itemView);
            col1Container = itemView.findViewById(R.id.col1Container);
            tvCol1Line1 = itemView.findViewById(R.id.tvCol1Line1);
            tvCol1Line2 = itemView.findViewById(R.id.tvCol1Line2);
            tvCol2 = itemView.findViewById(R.id.tvCol2);
            tvCol3 = itemView.findViewById(R.id.tvCol3);
            tvCol4 = itemView.findViewById(R.id.tvCol4);
            tvCol5 = itemView.findViewById(R.id.tvCol5);
        }
    }
}
