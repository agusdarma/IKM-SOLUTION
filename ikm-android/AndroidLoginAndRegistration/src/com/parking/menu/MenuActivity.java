package com.parking.menu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;

import com.parking.R;
import com.parking.data.Constants;
import com.parking.data.LoginData;
import com.parking.utils.SharedPreferencesUtils;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

public class MenuActivity extends FragmentActivity implements View.OnClickListener{

    private ResideMenu resideMenu;
    private MenuActivity ctx;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemChangePassword;
    private ResideMenuItem itemLogout;
    private ResideMenuItem itemMall;
    private ResideMenuItem itemHistoryBooking;
    private ResideMenuItem itemCheckIn;
    private ResideMenuItem itemRefreshingMall;
    Shimmer shimmer;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ctx = this;
        setUpMenu();
        ShimmerTextView tvFancy = (ShimmerTextView) findViewById(R.id.tvFancy);
        if (shimmer != null && shimmer.isAnimating()) {
			shimmer.cancel();
        } else {
        	shimmer = new Shimmer();
        	shimmer.start(tvFancy);
        }
        if( savedInstanceState == null )
            changeFragment(new HomeFragment());
    }

    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setUse3D(true);
        resideMenu.setBackground(R.drawable.menu_bg);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip. 
        resideMenu.setScaleValue(0.7f);

        // create menu items;
        itemHome     = new ResideMenuItem(this, R.drawable.icon_home,     "Home");
        itemChangePassword  = new ResideMenuItem(this, R.drawable.icon_profile,  "Password");
        itemLogout  = new ResideMenuItem(this, R.drawable.icon_profile,  "Logout");
        itemMall = new ResideMenuItem(this, R.drawable.icon_calendar, "Malls");
        itemHistoryBooking = new ResideMenuItem(this, R.drawable.icon_calendar, "History");
        itemCheckIn = new ResideMenuItem(this, R.drawable.icon_settings, "CheckIn");
        itemRefreshingMall = new ResideMenuItem(this, R.drawable.icon_settings, "Refreshing List Mall");

        itemHome.setOnClickListener(this);
        itemChangePassword.setOnClickListener(this);
        itemLogout.setOnClickListener(this);        
        itemMall.setOnClickListener(this);
        itemHistoryBooking.setOnClickListener(this);
        itemCheckIn.setOnClickListener(this);
        itemRefreshingMall.setOnClickListener(this);

        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemChangePassword, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemLogout, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemMall, ResideMenu.DIRECTION_RIGHT);
        
        LoginData loginData = SharedPreferencesUtils.getLoginData(ctx);    
    	// ambil dari session untuk email, session key
        if(loginData.getGroupUser().equalsIgnoreCase(Constants.USER)){
        	
        }else if(loginData.getGroupUser().equalsIgnoreCase(Constants.STAFF)){
        	 resideMenu.addMenuItem(itemCheckIn, ResideMenu.DIRECTION_RIGHT);
        }else if(loginData.getGroupUser().equalsIgnoreCase(Constants.ADMIN)){
        	 resideMenu.addMenuItem(itemCheckIn, ResideMenu.DIRECTION_RIGHT);
        	 resideMenu.addMenuItem(itemRefreshingMall, ResideMenu.DIRECTION_RIGHT);
        }
        resideMenu.addMenuItem(itemHistoryBooking, ResideMenu.DIRECTION_RIGHT);
       

        // You can disable a direction by setting ->
        // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
        findViewById(R.id.title_bar_right_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {

        if (view == itemHome){
            changeFragment(new HomeFragment());
        }else if (view == itemChangePassword){
            changeFragment(new ChangePasswordFragment());
        }else if (view == itemMall){
            changeFragment(new MallFragment());
        }else if (view == itemCheckIn){
            changeFragment(new CheckInFragment());
        }else if (view == itemLogout){
            changeFragment(new LogoutFragment());
        }else if (view == itemRefreshingMall){
            changeFragment(new RefreshingMallFragment());
        }else if (view == itemHistoryBooking){
            changeFragment(new HistoryBookingFragment());
        }

        resideMenu.closeMenu();
    }

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
//            Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void closeMenu() {
//            Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT).show();
        }
    };

    private void changeFragment(Fragment targetFragment){
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    // What good method is to access resideMenu？
    public ResideMenu getResideMenu(){
        return resideMenu;
    }
    
    @SuppressLint("NewApi")
	@Override
    public void onBackPressed() { 
    	 if (getFragmentManager().getBackStackEntryCount() == 0) {
//           this.finish();
       } else {
           getFragmentManager().popBackStack();
       }
    }
}
