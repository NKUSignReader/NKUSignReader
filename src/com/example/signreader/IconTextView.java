package com.example.signreader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * This class for Icon Text View when browser file
 * @author Wang XinFeng
 * @version 1.0
 *
 */
public class IconTextView extends LinearLayout {
	/**for the text view*/
	private TextView mText;
	/**file icon view*/
	private ImageView mIcon;
	
	public IconTextView(Context context, IconText iconText) {
		super(context);
		this.setOrientation(HORIZONTAL);

		mIcon = new ImageView(context);
		mIcon.setImageDrawable(iconText.getIcon());
		mIcon.setPadding(0, 2, 5, 0); 
		
		addView(mIcon,  new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		
		mText = new TextView(context);
		mText.setText(iconText.getText());
		addView(mText, new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	}

	public void setText(String words) {
		mText.setText(words);
	}
	
	public void setIcon(Drawable bullet) {
		mIcon.setImageDrawable(bullet);
	}
}