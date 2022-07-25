package com.meow.sardard.ViewHolderAdapter;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.meow.sardard.Models.PDFData;
import com.meow.sardard.R;
import com.meow.sardard.SelectListener;

import java.util.List;

public class PDFAdapter extends RecyclerView.Adapter<PDFViewHolder> {

    private Context context;
    private List<PDFData> data;
    private SelectListener listener;

    public PDFAdapter(Context context, List<PDFData> data, SelectListener listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PDFViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PDFViewHolder(LayoutInflater.from(context).inflate(R.layout.pdflayout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PDFViewHolder holder, int position) {
        holder.name.setText(data.get(position).getName());
        holder.open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnPDFClicked(data.get(position));
            }
        });
        holder.dnld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(data.get(position).getUrl());
                DownloadManager.Request request = new DownloadManager.Request(uri);

                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, data.get(position).getName() + ".pdf");
                downloadManager.enqueue(request);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}

class PDFViewHolder extends RecyclerView.ViewHolder {

        ImageView dnld, open;
        TextView name;
        String url;

        public PDFViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.pdfname);
            dnld = itemView.findViewById(R.id.dnld);
            open = itemView.findViewById(R.id.open);
        }
    }
