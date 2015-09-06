package com.parking.utils;

import android.view.View;

import com.iangclifton.android.floatlabel.FloatLabel;

public class CustomLabelAnimator implements FloatLabel.LabelAnimator {
	/*package*/ static final float SCALE_X_SHOWN = 1f;
    /*package*/ static final float SCALE_X_HIDDEN = 2f;
    /*package*/ static final float SCALE_Y_SHOWN = 1f;
    /*package*/ static final float SCALE_Y_HIDDEN = 0f;

    @Override
    public void onDisplayLabel(View label) {
        final float shift = label.getWidth() / 2;
        label.setScaleX(SCALE_X_HIDDEN);
        label.setScaleY(SCALE_Y_HIDDEN);
        label.setX(shift);
        label.animate().alpha(1).scaleX(SCALE_X_SHOWN).scaleY(SCALE_Y_SHOWN).x(0f);
    }

    @Override
    public void onHideLabel(View label) {
        final float shift = label.getWidth() / 2;
        label.setScaleX(SCALE_X_SHOWN);
        label.setScaleY(SCALE_Y_SHOWN);
        label.setX(0f);
        label.animate().alpha(0).scaleX(SCALE_X_HIDDEN).scaleY(SCALE_Y_HIDDEN).x(shift);
    }
}
