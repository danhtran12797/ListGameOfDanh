package com.danhtran12797.thd.app_game2019;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterGame extends RecyclerView.Adapter<AdapterGame.ViewHolder> {

    private ArrayList<Game> arrGame;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public AdapterGame(ArrayList<Game> arrGame, Context context) {
        this.arrGame = arrGame;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterGame.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_game, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterGame.ViewHolder viewHolder, int i) {
        Game game = arrGame.get(i);

        viewHolder.imgGame1.setImageResource(game.getImage());
        viewHolder.txtGame1.setText(game.getName());
    }

    @Override
    public int getItemCount() {
        return arrGame.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgGame1;
        TextView txtGame1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgGame1 = itemView.findViewById(R.id.imgHinh);
            txtGame1 = itemView.findViewById(R.id.txtName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int postion = getAdapterPosition();
                        if (postion != RecyclerView.NO_POSITION) {
                            listener.onItemClick(postion);
                        }
                    }
                }
            });

        }
    }
}
