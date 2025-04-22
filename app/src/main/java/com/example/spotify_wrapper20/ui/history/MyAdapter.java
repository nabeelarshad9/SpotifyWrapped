package com.example.spotify_wrapper20.ui.history;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify_wrapper20.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private List<DataClass> dataList;
    public void setSearchList(List<DataClass> dataSearchList){
        this.dataList = dataSearchList;
        notifyDataSetChanged();
    }
    public MyAdapter(Context context, List<DataClass> dataList){
        this.context = context;
        this.dataList = dataList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.recDate.setText(dataList.get(position).getDataDate());
        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("date", dataList.get(holder.getAdapterPosition()).getDataDate());
                intent.putExtra("topArtists", dataList.get(holder.getAdapterPosition()).getDataArtist());
                intent.putExtra("topTracks", dataList.get(holder.getAdapterPosition()).getDataSongs());
                intent.putExtra("topGenres", dataList.get(holder.getAdapterPosition()).getDataGenres());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
class MyViewHolder extends RecyclerView.ViewHolder{
    TextView recDate;
    CardView recCard;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        recDate = itemView.findViewById(R.id.recDate);
        recCard = itemView.findViewById(R.id.recCard);
    }
}

