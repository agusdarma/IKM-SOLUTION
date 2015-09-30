package com.ikm.myagenda.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;

import com.ikm.myagenda.R;
import com.ikm.myagenda.gc.materialdesign.widgets.SnackBar;

public class MessageUtils {
	private static final String TAG = "MessageUtils";
	private Context ctx;
	private static SimpleDateFormat sdfDateTime = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
	/* STANDART MESSAGE */

	public MessageUtils(Context context) {
		super();
		this.ctx = context;
	}
	
	public static String displayDateTime(Date dateTime) {
		return sdfDateTime.format(dateTime);
	}
	
	public void snackBarMessage(Activity act,String msg){
		SnackBar snackBar = new SnackBar(act, msg);
		snackBar.setBackgroundSnackBar(act.getResources().getColor(R.color.black_transparant));
		snackBar.show();
	}
		
	
}
