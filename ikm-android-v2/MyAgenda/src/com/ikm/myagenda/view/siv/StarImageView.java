package com.ikm.myagenda.view.siv;

import android.content.Context;
import android.util.AttributeSet;

import com.ikm.myagenda.R;
import com.ikm.myagenda.view.siv.shader.ShaderHelper;
import com.ikm.myagenda.view.siv.shader.SvgShader;

public class StarImageView extends ShaderImageView {

    public StarImageView(Context context) {
        super(context);
    }

    public StarImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StarImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public ShaderHelper createImageViewHelper() {
        return new SvgShader(R.raw.imgview_star);
    }
}
