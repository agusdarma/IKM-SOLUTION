package com.ikm.myagenda;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.ImageView;

import com.ikm.myagenda.adapter.ParallaxTravelAdapter;
import com.ikm.myagenda.util.DummyContent;
import com.ikm.myagenda.view.pzv.PullToZoomListViewEx;

public class ParallaxTravelActivity extends ActionBarActivity {

	public static final String TAG = "Parallax travel";

	ImageView iv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parallax_travel);

		iv = (ImageView) findViewById(R.id.header_parallax_travel_image);

		PullToZoomListViewEx listView = (PullToZoomListViewEx) findViewById(R.id.paralax_travel_list_view);
		listView.setShowDividers(0);
		listView.setAdapter(new ParallaxTravelAdapter(this, DummyContent
				.getDummyModelListTravel(), false));

		getSupportActionBar().hide();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
