package com.ikm.myagenda.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.afollestad.materialdialogs.MaterialDialog.ButtonCallback;
import com.gc.materialdesign.widgets.SnackBar;
import com.ikm.myagenda.R;

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
	
	public void showDialogInfoCallback(String title,String content,ButtonCallback callback) {
        new MaterialDialog.Builder(ctx)
                .title(title)
                .content(content)
                .positiveColorRes(R.color.main_color_500)
                .positiveText(R.string.ok)
                .callback(callback)
                .show();
    }
	
	public void showDialogInfo(String title,String content) {
        new MaterialDialog.Builder(ctx)
                .title(title)
                .content(content)
                .positiveColorRes(R.color.main_color_500)
                .positiveText(R.string.ok)                
                .show();
    }
	
	public void showDialogInfoCustomTheme(String title,String content) {
        new MaterialDialog.Builder(ctx)
                .title(title)
                .content(content)
                .positiveColorRes(R.color.main_color_500)
                .positiveText(R.string.ok)     
                .titleGravity(GravityEnum.START)
                .titleColorRes(R.color.main_color_500)
                .contentColorRes(R.color.white)
                .dividerColorRes(R.color.white)
                .backgroundColorRes(R.color.black_transparant)
                .show();
    }
	
	
	public void showDialogConfirmationCallback(String title,String content,ButtonCallback callback) {
        new MaterialDialog.Builder(ctx)
                .title(title)
                .content(content)
                .positiveColorRes(R.color.main_color_500)
                .positiveText(R.string.ok)
                .negativeColorRes(R.color.material_red_300)
                .negativeText(R.string.cancel)
                .callback(callback)
                .show();
    }
		
	
}
