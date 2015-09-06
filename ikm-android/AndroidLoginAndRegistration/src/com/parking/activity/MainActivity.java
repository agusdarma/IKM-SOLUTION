package com.parking.activity;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parking.R;


public class MainActivity extends Activity {

	private TextView txtName;
	private TextView txtEmail;
	private Button btnLogout;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		txtName = (TextView) findViewById(R.id.name);
		txtEmail = (TextView) findViewById(R.id.email);
		btnLogout = (Button) findViewById(R.id.btnLogout);

		

		// Logout button click event
		btnLogout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
			}
		});
	}

	/**
	 * Logging out the user. Will set isLoggedIn flag to false in shared
	 * preferences Clears the user data from sqlite users table
	 * */
	
}
