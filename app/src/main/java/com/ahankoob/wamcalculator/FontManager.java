package com.ahankoob.wamcalculator;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by a_ahankoob on 22/04/2020.
 */

public class FontManager {

	public static Typeface getVazirFont(AssetManager assets) {
		return Typeface.createFromAsset(assets,  "fonts/vazirfont.ttf");
	}

	public static Typeface getfontawesomeFont(AssetManager assets) {

		return Typeface.createFromAsset(assets,  "fonts/fontawesome-webfont.ttf");
	}
	public static void markAsIconContainer(View v, Typeface typeface) {
		if (v instanceof EditText ) {
			((EditText) v).setTypeface(typeface);
		}
		else if (v instanceof TextInputEditText) {
			((TextInputEditText) v).setTypeface(typeface);
		}
		else if(v instanceof AppCompatTextView){
			((AppCompatTextView) v).setTypeface(typeface);

		}
		else if (v instanceof ViewGroup) {
			ViewGroup vg = (ViewGroup) v;
			for (int i = 0; i < vg.getChildCount(); i++) {
				View child = vg.getChildAt(i);
				markAsIconContainer(child,typeface);
			}
		} else if (v instanceof TextView) {
			((TextView) v).setTypeface(typeface);
		}

	}


}
