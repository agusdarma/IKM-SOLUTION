package com.ikm.swipelistview.sample.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ikm.R;

public class AgendaAdapter extends RecyclerView.Adapter<AgendaViewHolder> {
    Context context;
    ArrayList<AgendaVO> itemsList;
    int noUrut;
 
    public AgendaAdapter(Context context, ArrayList<AgendaVO> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }
 
    @Override
    public int getItemCount() {
        if (itemsList == null) {
            return 0;
        } else {
            return itemsList.size();
        }
    }
 
    @Override
    public AgendaViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.agenda_row, null);
        AgendaViewHolder viewHolder = new AgendaViewHolder(view);
        return viewHolder;
    }
 
    @Override
    public void onBindViewHolder(AgendaViewHolder rowViewHolder, int position) {
        AgendaVO items = itemsList.get(position);
        noUrut++;
        rowViewHolder.no.setText(Integer.toString(noUrut));
        rowViewHolder.tgl.setText(String.valueOf(items.getTglAgenda()));
    }
}