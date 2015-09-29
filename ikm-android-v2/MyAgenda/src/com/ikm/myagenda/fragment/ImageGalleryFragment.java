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

import com.ikm.myagenda.ImageGalleryCategoryActivity;
import com.ikm.myagenda.ImageGallerySubcategoryActivity;
import com.ikm.myagenda.R;
import com.ikm.myagenda.adapter.SubcategoryAdapter;
import com.ikm.myagenda.util.DummyContent;

public class ImageGalleryFragment extends Fragment implements OnItemClickListener {
	
	private ListView mListView;
	private List<String> mImageGalleries;
	
	public static ImageGalleryFragment newInstance() {
		return new ImageGalleryFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mImageGalleries = new ArrayList<String>();
		mImageGalleries.add(ImageGalleryCategoryActivity.ANIMALS_CATEGORY);
		mImageGalleries.add(ImageGalleryCategoryActivity.DOGS_SUBCATEGORY);
		mImageGalleries.add(ImageGalleryCategoryActivity.MUSIC_CATEGORY);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_image_gallery, container, false);
		
		mListView = (ListView) rootView.findViewById(R.id.list_view);
		mListView.setAdapter(new SubcategoryAdapter(getActivity(), mImageGalleries));
		mListView.setOnItemClickListener(this);
		
		return rootView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (position == 1) {//Jump directly to sub-category
			Intent intent = new Intent(getActivity(), ImageGallerySubcategoryActivity.class);
			intent.putExtra(ImageGallerySubcategoryActivity.IMAGE_GALLERY_SUBCATEGORY, DummyContent.getImageGalleryDogsSubcategory());
			startActivity(intent);
			return;
		}
		String imageGallery = mImageGalleries.get(position);
		Intent intent = new Intent(getActivity(), ImageGalleryCategoryActivity.class);
		intent.putExtra(ImageGalleryCategoryActivity.IMAGE_GALLERY_CATEGORY, imageGallery);
		startActivity(intent);
	}
}
