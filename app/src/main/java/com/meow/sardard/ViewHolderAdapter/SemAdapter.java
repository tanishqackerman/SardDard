package com.meow.sardard.ViewHolderAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.meow.sardard.R;
import com.meow.sardard.SelectListener;

import java.util.List;

public class SemAdapter extends RecyclerView.Adapter<SemViewHolder> {

    Context context;
    List<String> data;
    SelectListener listener;

    public SemAdapter(Context context, List<String> data, SelectListener listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SemViewHolder(LayoutInflater.from(context).inflate(R.layout.semmateriallayout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SemViewHolder holder, int position) {
        holder.name.setText(data.get(position));
        holder.semcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnSemClicked(data.get(position), position + 2);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

class SemViewHolder extends RecyclerView.ViewHolder {

    CardView semcard;
    TextView name;

    public SemViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name);
        semcard = itemView.findViewById(R.id.semcard);
    }
}
