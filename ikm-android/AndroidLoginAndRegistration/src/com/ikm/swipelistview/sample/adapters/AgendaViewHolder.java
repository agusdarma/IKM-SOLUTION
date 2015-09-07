package com.ikm.swipelistview.sample.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ikm.R;

public class AgendaViewHolder extends RecyclerView.ViewHolder {
    
    
    TextView no;
    TextView tgl;
 
    public AgendaViewHolder(View view) {
        super(view);
        this.no = (TextView) view.findViewById(R.id.no);
        this.tgl = (TextView) view.findViewById(R.id.tgl);
    }
}
