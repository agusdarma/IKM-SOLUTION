package com.ikm.myagenda.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ikm.myagenda.ParallaxActivity;
import com.ikm.myagenda.ParallaxKenBurnsActivity;
import com.ikm.myagenda.ParallaxSocialActivity;
import com.ikm.myagenda.ParallaxTravelActivity;
import com.ikm.myagenda.R;
import com.ikm.myagenda.adapter.SubcategoryAdapter;

public class ParallaxEffectsFragment extends Fragment implements
		OnItemClickListener {

	private ListView mListView;
	private List<String> mParallaxEffects;

	public static ParallaxEffectsFragment newInstance() {
		return new ParallaxEffectsFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mParallaxEffects = new ArrayList<String>();
		mParallaxEffects.add(ParallaxKenBurnsActivity.TAG);
		mParallaxEffects.add(ParallaxActivity.TAG);
		mParallaxEffects.add(ParallaxTravelActivity.TAG);
		mParallaxEffects.add(ParallaxSocialActivity.TAG);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_paralax, container,
				false);

		mListView = (ListView) rootView.findViewById(R.id.list_view);
		mListView.setAdapter(new SubcategoryAdapter(getActivity(),
				mParallaxEffects));
		mListView.setOnItemClickListener(this);

		return rootView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		String pe = mParallaxEffects.get(position);
		Intent intent = null;
		if (pe.equals(ParallaxKenBurnsActivity.TAG)) {
			intent = new Intent(getActivity(), ParallaxKenBurnsActivity.class);
		} else if (pe.equals(ParallaxActivity.TAG)) {
			intent = new Intent(getActivity(), ParallaxActivity.class);
		} else if (pe.equals(ParallaxTravelActivity.TAG)) {
			intent = new Intent(getActivity(), ParallaxTravelActivity.class);
		} else if (pe.equals(ParallaxSocialActivity.TAG)) {
			intent = new Intent(getActivity(), ParallaxSocialActivity.class);
		}
		if (intent != null)
			startActivity(intent);
	}
}
