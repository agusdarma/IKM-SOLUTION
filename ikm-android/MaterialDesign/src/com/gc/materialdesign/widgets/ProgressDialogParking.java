package com.gc.materialdesign.widgets;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gc.materialdesign.R;
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;

public class ProgressDialogParking extends android.app.Dialog{
	
	Context context;
	View view;
	View backView;
	String title;
	String judul;
	TextView titleTextView;
	TextView judulTextView;
	
	int progressColor = -1;
	
	public ProgressDialogParking(Context context,String title,String judul) {
		super(context, android.R.style.Theme_Translucent);
		this.title = title;
		this.judul = judul;
		this.context = context;
	}
	
	public ProgressDialogParking(Context context,String title,String judul, int progressColor) {
		super(context, android.R.style.Theme_Translucent);
		this.title = title;
		this.judul = judul;
		this.progressColor = progressColor;
		this.context = context;
	}
	
	@Override
	  protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.progress_dialog);
	    
		view = (RelativeLayout)findViewById(R.id.contentDialog);
		backView = (RelativeLayout)findViewById(R.id.dialog_rootView);
		backView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getX() < view.getLeft() 
						|| event.getX() >view.getRight()
						|| event.getY() > view.getBottom() 
						|| event.getY() < view.getTop()) {
					dismiss();
				}
				return false;
			}
		});
		
	    this.titleTextView = (TextView) findViewById(R.id.title);
	    this.judulTextView = (TextView) findViewById(R.id.judul);
	    setTitle(title);
	    setJudul(judul);
	    if(progressColor != -1){
	    	ProgressBarCircularIndeterminate progressBarCircularIndeterminate = (ProgressBarCircularIndeterminate) findViewById(R.id.progressBarCircularIndetermininate);
	    	progressBarCircularIndeterminate.setBackgroundColor(progressColor);
	    }
	    
	    
	}
	
	@Override
	public void show() {
		// TODO 自动生�?的方法存根
		super.show();
		// set dialog enter animations
		view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.dialog_main_show_amination));
		backView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.dialog_root_show_amin));
	}
	
	// GETERS & SETTERS

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		if(title == null)
			titleTextView.setVisibility(View.GONE);
		else{
			titleTextView.setVisibility(View.VISIBLE);
			titleTextView.setText(title);
		}
	}
	
	public String getJudul() {
		return judul;
	}

	public void setJudul(String judul) {
		this.judul = judul;
		if(judul == null)
			judulTextView.setVisibility(View.GONE);
		else{
			judulTextView.setVisibility(View.VISIBLE);
			judulTextView.setText(judul);
		}
	}
	
	

	public TextView getTitleTextView() {
		return titleTextView;
	}

	public void setTitleTextView(TextView titleTextView) {
		this.titleTextView = titleTextView;
	}

	@Override
	public void dismiss() {
		Animation anim = AnimationUtils.loadAnimation(context, R.anim.dialog_main_hide_amination);
		anim.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				view.post(new Runnable() {
					@Override
					public void run() {
			        	ProgressDialogParking.super.dismiss();
			        }
			    });
				
			}
		});
		Animation backAnim = AnimationUtils.loadAnimation(context, R.anim.dialog_root_hide_amin);
		
		view.startAnimation(anim);
		backView.startAnimation(backAnim);
	}

	public TextView getJudulTextView() {
		return judulTextView;
	}

	public void setJudulTextView(TextView judulTextView) {
		this.judulTextView = judulTextView;
	}

	
	
	

}
