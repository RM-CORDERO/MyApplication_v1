package pupr.capstone.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MantenimientoAdapter extends RecyclerView.Adapter<MantenimientoAdapter.MantenimientoViewHolder> {

    private List<Mantenimiento> listaMantenimientos;
    private OnItemClickListener listener;

    public MantenimientoAdapter(List<Mantenimiento> listaMantenimientos) {
        this.listaMantenimientos = listaMantenimientos;
    }

    @NonNull
    @Override
    public MantenimientoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mantenimiento, parent, false);
        return new MantenimientoViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MantenimientoViewHolder holder, int position) {
        Mantenimiento mantenimiento = listaMantenimientos.get(position);
        holder.textMantenimiento.setText(mantenimiento.getTipo());

        if (mantenimiento.getImagenBitmap() != null) {
            holder.imageMantenimiento.setImageBitmap(mantenimiento.getImagenBitmap());
        } else {
            holder.imageMantenimiento.setImageResource(R.drawable.audi_r8);
        }

        holder.itemView.setTag(mantenimiento);
    }

    @Override
    public int getItemCount() {
        return listaMantenimientos.size();
    }

    // Interfaz para clics
    public interface OnItemClickListener {
        void onItemClick(Mantenimiento mantenimiento);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class MantenimientoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageMantenimiento;
        TextView textMantenimiento;

        public MantenimientoViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            imageMantenimiento = itemView.findViewById(R.id.imageMantenimiento);
            textMantenimiento = itemView.findViewById(R.id.textMantenimiento);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick((Mantenimiento) v.getTag());
                }
            });
        }
    }
}
