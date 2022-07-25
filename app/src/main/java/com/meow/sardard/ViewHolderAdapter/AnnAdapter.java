package com.meow.sardard.ViewHolderAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.meow.sardard.Models.AnnData;
import com.meow.sardard.R;

import java.util.List;

public class AnnAdapter extends RecyclerView.Adapter<AnnViewHolder>{

    private Context context;
    private List<AnnData> data;

    public AnnAdapter(Context context, List<AnnData> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public AnnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AnnViewHolder(LayoutInflater.from(context).inflate(R.layout.annlayout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AnnViewHolder holder, int position) {
        holder.title.setText(data.get(position).getTitle());
        holder.msg.setText(data.get(position).getMsg());
        holder.date.setText(data.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

class AnnViewHolder extends RecyclerView.ViewHolder {

    CardView anncard;
    TextView title, msg, date;

    public AnnViewHolder(@NonNull View itemView) {
        super(itemView);
        anncard = itemView.findViewById(R.id.anncard);
        title = itemView.findViewById(R.id.title);
        msg = itemView.findViewById(R.id.msg);
        date = itemView.findViewById(R.id.date);
    }
}
