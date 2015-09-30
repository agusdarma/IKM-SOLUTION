package com.ikm.myagenda;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.ikm.myagenda.adapter.DrawerMenuAdapter;
import com.ikm.myagenda.data.Constants;
import com.ikm.myagenda.fragment.HomeFragment;
import com.ikm.myagenda.fragment.ListViewsFragment;
import com.ikm.myagenda.fragment.ParallaxEffectsFragment;
import com.ikm.myagenda.model.DummyModel;
import com.ikm.myagenda.util.ImageUtil;
import com.ikm.myagenda.util.MessageUtils;
import com.ikm.myagenda.util.RedirectUtils;

public class LeftMenusSocialActivity extends ActionBarActivity {

	private ListView mDrawerList;
	private DrawerLayout mDrawerLayout;
	private DrawerMenuAdapter mDrawerAdapter;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private Handler mHandler;
	private boolean mShouldFinish = false;
	private Context ctx;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ctx = LeftMenusSocialActivity.this;
		String tipeLogin = Constants.PARENTS;
		Bundle extras = getIntent().getExtras();
		if (extras != null && extras.containsKey(Constants.KEY_LOGIN)) {
			tipeLogin = extras.getString(Constants.KEY_LOGIN, Constants.PARENTS);
		}
		mHandler = new Handler();
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		mTitle = mDrawerTitle = "Home";
		setSupportActionBar(toolbar);
		getSupportActionBar().setTitle(mDrawerTitle);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//		mTitle = mDrawerTitle = getTitle();
		
		mDrawerList = (ListView) findViewById(R.id.list_view);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		View headerView = getLayoutInflater().inflate(
				R.layout.header_navigation_drawer_social, mDrawerList, false);

		ImageView iv = (ImageView) headerView.findViewById(R.id.image);
//		ImageUtil.displayRoundImage(iv,
//				"http://pengaja.com/uiapptemplate/newphotos/profileimages/0.jpg", null);
//		iv.setImageResource(R.drawable.cancel_red);
		ImageUtil.displayRoundImage(iv,"drawable://" + R.drawable.profile, null);
		

		mDrawerList.addHeaderView(headerView);// Add header before adapter (for
												// pre-KitKat)
		mDrawerAdapter = new DrawerMenuAdapter(ctx,tipeLogin);
		
		mDrawerList.setAdapter(mDrawerAdapter);
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		int color = getResources().getColor(R.color.material_grey_100);
		color = Color.argb(0xCD, Color.red(color), Color.green(color),
				Color.blue(color));
		mDrawerList.setBackgroundColor(color);
		mDrawerList.getLayoutParams().width = (int) getResources()
				.getDimension(R.dimen.drawer_width_social);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
				R.string.drawer_open, R.string.drawer_close) {
			public void onDrawerClosed(View view) {				
//				getSupportActionBar().setTitle(mTitle);				
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {				
//				getSupportActionBar().setTitle(mDrawerTitle);				
				invalidateOptionsMenu();
			}			
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			int position = 1;
			selectItem(position,((DummyModel) mDrawerAdapter.getItem(position)).getId());
			mDrawerLayout.openDrawer(mDrawerList);
		}
		
		
		
	}
	
	@Override
	public void onBackPressed() {
		if (!mShouldFinish && !mDrawerLayout.isDrawerOpen(mDrawerList)) {
			MessageUtils messageUtils = new MessageUtils(ctx);
         	messageUtils.snackBarMessage(LeftMenusSocialActivity.this,ctx.getResources().getString(R.string.confirm_exit));		
			mShouldFinish = true;
			mDrawerLayout.openDrawer(mDrawerList);
		} else if (!mShouldFinish && mDrawerLayout.isDrawerOpen(mDrawerList)) {
			mDrawerLayout.closeDrawer(mDrawerList);
			RedirectUtils redirectUtils = new RedirectUtils(ctx, LeftMenusSocialActivity.this);
			redirectUtils.redirectToLogin();
		} else {
			RedirectUtils redirectUtils = new RedirectUtils(ctx, LeftMenusSocialActivity.this);
			redirectUtils.redirectToLogin();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if(position !=0){
				selectItem(position,((DummyModel) mDrawerAdapter.getItem(position)).getId());
			}else{
				mDrawerLayout.closeDrawer(mDrawerList);
			}
			
			
		}
	}
	
	private void selectItem(int position, int drawerTag) {
		Fragment fragment = getFragmentByDrawerTag(drawerTag);
		commitFragment(fragment);

		mDrawerList.setItemChecked(position, true);
		setTitle(((DummyModel) mDrawerAdapter.getItem(position)).getText());
		mDrawerLayout.closeDrawer(mDrawerList);
	}
	
	private Fragment getFragmentByDrawerTag(int drawerTag) {
		Fragment fragment = null;
		if (drawerTag == Constants.DRAWER_ITEM_ID_HOME) {
			fragment = HomeFragment.newInstance();
		} else if (drawerTag == Constants.DRAWER_ITEM_ID_CHANGE_PASSWORD) {
			fragment = ParallaxEffectsFragment.newInstance();
		} else if (drawerTag == Constants.DRAWER_ITEM_ID_BROWSE) {
			fragment = ListViewsFragment.newInstance();
		} else if (drawerTag == Constants.DRAWER_ITEM_ID_UPLOAD) {
			fragment = ParallaxEffectsFragment.newInstance();
		} else {
			fragment = new Fragment();
		}
		mShouldFinish = false;
		return fragment;
	}
	
	public void commitFragment(Fragment fragment) {
		// Using Handler class to avoid lagging while
		// committing fragment in same time as closing
		// navigation drawer
		mHandler.post(new CommitFragmentRunnable(fragment));
	}
	
	private class CommitFragmentRunnable implements Runnable {

		private Fragment fragment;

		public CommitFragmentRunnable(Fragment fragment) {
			this.fragment = fragment;
		}

		@Override
		public void run() {
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();
		}
	}

	@Override
	public void setTitle(int titleId) {
		setTitle(getString(titleId));
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
}
