package com.ikm.myagenda.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ikm.myagenda.R;
import com.ikm.myagenda.data.LoginData;
import com.ikm.myagenda.util.SharedPreferencesUtils;
import com.ikm.myagenda.view.FloatLabeledEditText;

public class ChangePasswordFragment extends Fragment {
	private static final String TAG = ChangePasswordFragment.class.getSimpleName();
	private Context ctx;
	View rootView;
	private FloatLabeledEditText txt_old_password;
	private FloatLabeledEditText txt_new_password;
	private FloatLabeledEditText txt_confirm_new_password;
	private TextView btnChangePassword;

	public static ChangePasswordFragment newInstance() {
		return new ChangePasswordFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_change_password,
				container, false);
		ctx = this.getActivity().getBaseContext();
		LoginData loginData = SharedPreferencesUtils.getLoginData(ctx);
		txt_old_password = (FloatLabeledEditText) rootView.findViewById(R.id.txt_old_password);
		txt_new_password = (FloatLabeledEditText) rootView.findViewById(R.id.txt_new_password);
		txt_confirm_new_password = (FloatLabeledEditText) rootView.findViewById(R.id.txt_confirm_new_password);
		btnChangePassword = (TextView) rootView.findViewById(R.id.change_password);
		
		btnChangePassword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		
		return rootView;
	}
	
}
