package com.ikm.myagenda;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

public class LogInPageActivity extends Activity implements OnClickListener {

	public static final String LOGIN_PAGE_AND_LOADERS_CATEGORY = "com.csform.android.uiapptemplate.LogInPageAndLoadersActivity";
	public static final String DARK = "Dark";
	public static final String LIGHT = "Light";
	public static final String TRAVEL = "Travel";
	public static final String SOCIAL = "Social";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE); // Removing
																// ActionBar
		setContentView(R.layout.activity_login_page_travel);
		TextView login, register, forgot_password;
		login = (TextView) findViewById(R.id.login);
		register = (TextView) findViewById(R.id.register);
		forgot_password = (TextView) findViewById(R.id.forgot_password);

		login.setOnClickListener(this);
		register.setOnClickListener(this);
		forgot_password.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Context ctx = LogInPageActivity.this;
		if (v instanceof TextView) {
			TextView tv = (TextView) v;
//			Toast.makeText(this, tv.getText(), Toast.LENGTH_SHORT).show();
			if(ctx.getResources().getString(R.string.register).equalsIgnoreCase(tv.getText().toString())){
				Intent i = new Intent(ctx, RegisterPageActivity.class);
                startActivity(i);
			}else if(ctx.getResources().getString(R.string.forgot_password).equalsIgnoreCase(tv.getText().toString())){
				Intent i = new Intent(ctx, ForgotPasswordPageActivity.class);
                startActivity(i);
			}else if(ctx.getResources().getString(R.string.login).equalsIgnoreCase(tv.getText().toString())){
				Intent i = new Intent(ctx, LeftMenusSocialActivity.class);
                startActivity(i);
			}
		}
	}
}
