package com.flypple.spandremotewidget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiqinglin
 * 2021/5/20
 * flypple20088@163.com
 */
public class SPDataAdapter extends RecyclerView.Adapter<SPDataAdapter.SPDataHolder> {
    private Context context;
    private List<Pair> data;

    public SPDataAdapter(Context context, List<Pair> data) {
        this.context = context;
        this.data = data != null ? data : new ArrayList<>();
    }

    @NonNull
    @Override
    public SPDataAdapter.SPDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sp_data, parent, false);
        SPDataHolder holder = new SPDataHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SPDataAdapter.SPDataHolder holder, int position) {
        Pair pair = data.get(position);
        holder.tvDataKey.setText(pair.key);
        holder.tvDataValue.setText(pair.value);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class SPDataHolder extends RecyclerView.ViewHolder {

        private final TextView tvDataKey;
        private final TextView tvDataValue;

        public SPDataHolder(@NonNull View itemView) {
            super(itemView);
            tvDataKey = itemView.findViewById(R.id.tv_data_key);
            tvDataValue = itemView.findViewById(R.id.tv_data_value);
        }
    }
}
