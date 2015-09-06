package com.parking.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class ImageUtil {

	public static Drawable getImage(Context context, String name) {
		return context.getResources().getDrawable(
				context.getResources().getIdentifier(name, "drawable",
						context.getPackageName()));
	}
}
