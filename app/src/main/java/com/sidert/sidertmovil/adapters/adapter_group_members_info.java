package com.sidert.sidertmovil.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sidert.sidertmovil.R;

import java.util.List;

public class adapter_group_members_info extends RecyclerView.Adapter<adapter_group_members_info.ViewHolder> {

    private Context ctx;
    private List<Integer> data;
    private Event evento;

    public interface Event {
        void IneOnClick(String ine);
    }

    public adapter_group_members_info(Context ctx, Event evento) {
        this.ctx = ctx;
        this.evento = evento;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_client_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        holder.ivIne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evento.IneOnClick("1074469");
            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivIne;
        public ViewHolder(View itemView) {
            super(itemView);
            ivIne   = itemView.findViewById(R.id.ivIne);
        }
    }
}
