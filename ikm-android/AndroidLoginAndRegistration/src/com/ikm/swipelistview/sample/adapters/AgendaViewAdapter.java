package com.ikm.swipelistview.sample.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fortysevendeg.swipelistview.SwipeListView;
import com.gc.materialdesign.views.ButtonRectangle;
import com.ikm.R;

public class AgendaViewAdapter extends BaseAdapter {
	private static final String TAG = AgendaViewAdapter.class.getSimpleName();
	private List<AgendaViewVO> data;
	private Context ctx;
	private Activity act;
    
 
    public AgendaViewAdapter(Context context, Activity activity,
			List<AgendaViewVO> data) {
		this.ctx = context;
		this.data = data;
		this.act = activity;
	}

	

	public AgendaViewAdapter(Context context, List<AgendaViewVO> data) {
		this.ctx = context;
		this.data = data;
	}
 
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public AgendaViewVO getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final AgendaViewVO item = getItem(position);
		
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater li = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = li.inflate(R.layout.agenda_row, parent,false);
			holder = new ViewHolder();
			holder.txtNo = (TextView) convertView.findViewById(R.id.txtNo);
			holder.txtTgl = (TextView) convertView.findViewById(R.id.txtTgl);
			holder.bAction1 = (ButtonRectangle) convertView.findViewById(R.id.button_pay);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		((SwipeListView) parent).recycle(convertView, position);

		holder.txtNo.setText(Integer.toString(position+1));
		holder.txtTgl.setText(item.getTglAgenda());
		holder.bAction1.setEnabled(false);		

		holder.bAction1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				goToPayScreen(item.getMallName(), Long.parseLong(item.getHargaParkir()), item.getSlotName(), item.getBookingId());
			}
		});

		return convertView;
	}
	
//	private void goToPayScreen(String mallName, long hargaParkir,String slotName,String bookingId) {
//    	Intent i = new Intent(ctx, InputCreditCardActivity.class);            	
//    	i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
//    	i.putExtra("mallName", mallName);
//    	i.putExtra("hargaParkir", hargaParkir);
//    	i.putExtra("slotName", slotName);
//    	i.putExtra("bookingId", bookingId);
//    	ctx.startActivity(i);
//    }

	static class ViewHolder {
		TextView txtNo;
		TextView txtTgl;		
		ButtonRectangle bAction1;
	}
}