/*
 * Copyright (C) 2013 47 Degrees, LLC
 *  http://47deg.com
 *  hello@47deg.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.parking.swipelistview.sample.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fortysevendeg.swipelistview.SwipeListView;
import com.gc.materialdesign.views.ButtonRectangle;
import com.parking.R;
import com.parking.activity.InputCreditCardActivity;
import com.parking.data.Constants;
import com.parking.data.LoginData;

public class HistoryBookingAdapter extends BaseAdapter {
	private static final String TAG = HistoryBookingAdapter.class.getSimpleName();
	private List<HistoryBookingVO> data;
	private Context ctx;
	private Activity act;
	private LoginData login;
	private String mallName;

	public HistoryBookingAdapter(Context context, Activity activity,
			List<HistoryBookingVO> data) {
		this.ctx = context;
		this.data = data;
		this.act = activity;
	}

	public HistoryBookingAdapter(Context context, Activity activity,
			List<HistoryBookingVO> data, LoginData loginData) {
		this.ctx = context;
		this.data = data;
		this.act = activity;
		this.login = loginData;
	}

	public HistoryBookingAdapter(Context context, List<HistoryBookingVO> data) {
		this.ctx = context;
		this.data = data;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public HistoryBookingVO getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final HistoryBookingVO item = getItem(position);
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater li = (LayoutInflater) ctx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = li.inflate(R.layout.history_booking_row, parent,
					false);
			holder = new ViewHolder();
			holder.titleMall = (TextView) convertView
					.findViewById(R.id.titleMall);
			holder.valTanggalBooking = (TextView) convertView
					.findViewById(R.id.valTanggalBooking);
			holder.valBookingId = (TextView) convertView
					.findViewById(R.id.valBookingId);
			holder.valBookingCode = (TextView) convertView
					.findViewById(R.id.valBookingCode);
			holder.valStatusBooking = (TextView) convertView
					.findViewById(R.id.valStatusBooking);
			holder.bAction1 = (ButtonRectangle) convertView
					.findViewById(R.id.button_pay);

			// holder.bAction3 = (ButtonRectangle)
			// convertView.findViewById(R.id.example_row_b_action_3);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		((SwipeListView) parent).recycle(convertView, position);

		holder.titleMall.setText(item.getMallName());
		holder.valTanggalBooking.setText(item.getBookingDateValue());
		holder.valBookingId.setText(item.getBookingId());
		holder.valBookingCode.setText(item.getBookingCode());

		holder.bAction1.setEnabled(false);
		String status = Constants.STATUS_VAL_AVAILABLE;		
		switch (item.getStatus()) {
		case Constants.STATUS_NEED_TO_PAY:
			status = Constants.STATUS_VAL_NEED_TO_PAY;
			holder.valStatusBooking.setTextColor(ctx.getResources().getColor(
					R.color.blue));			
			holder.bAction1.setEnabled(true);
			break;
		case Constants.STATUS_AUTO_RELEASE_AFTER_BOOKING:
			status = Constants.STATUS_VAL_EXPIRED_PAY;
			holder.valStatusBooking.setTextColor(ctx.getResources().getColor(
					R.color.red));
			break;
		case Constants.STATUS_ALREADY_PAY:
			status = Constants.STATUS_VAL_ALREADY_PAY;
			holder.valStatusBooking.setTextColor(ctx.getResources().getColor(
					R.color.grey));
			break;
		case Constants.STATUS_ALREADY_CHECK_IN:
			status = Constants.STATUS_VAL_ALREADY_CHECK_IN;
			holder.valStatusBooking.setTextColor(ctx.getResources().getColor(
					R.color.pink));
			break;
		case Constants.STATUS_AUTO_RELEASE_AFTER_PAY:
			status = Constants.STATUS_VAL_EXPIRED_CHECK_IN;
			holder.valStatusBooking.setTextColor(ctx.getResources().getColor(
					R.color.red));
			break;
		case Constants.STATUS_ALREADY_CHECK_OUT:
			status = Constants.STATUS_VAL_EXPIRED_COMPLETE;
			holder.valStatusBooking.setTextColor(ctx.getResources().getColor(
					R.color.green));
			break;
		default:
			status = Constants.STATUS_VAL_AVAILABLE;
			break;
		}

		holder.valStatusBooking.setText(status);

		holder.bAction1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goToPayScreen(item.getMallName(), Long.parseLong(item.getHargaParkir()), item.getSlotName(), item.getBookingId());
			}
		});

		return convertView;
	}
	
	private void goToPayScreen(String mallName, long hargaParkir,String slotName,String bookingId) {
    	Intent i = new Intent(ctx, InputCreditCardActivity.class);            	
    	i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
    	i.putExtra("mallName", mallName);
    	i.putExtra("hargaParkir", hargaParkir);
    	i.putExtra("slotName", slotName);
    	i.putExtra("bookingId", bookingId);
    	ctx.startActivity(i);
    }

	static class ViewHolder {
		TextView titleMall;
		TextView valTanggalBooking;
		TextView valBookingId;
		TextView valBookingCode;
		TextView valStatusBooking;
		ButtonRectangle bAction1;
	}

}
