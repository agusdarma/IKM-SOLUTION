package com.ikm.utils;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;

import com.ikm.activity.PreLoginActivity;

public class RedirectUtils extends Fragment{
	private static final String TAG = "RedirectUtils";
	private Context ctx;
	private Activity act;
	
	/* STANDART MESSAGE */

	public RedirectUtils(Context ctx,Activity activity) {
		super();
		this.ctx = ctx;
		this.act = activity;
	}
	
	public void redirectToLogin() {
		act.getFragmentManager().beginTransaction().remove(this).commit();
		act.finish();
        Intent intent = new Intent(ctx, PreLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ctx.startActivity(intent);
	}
	
}
