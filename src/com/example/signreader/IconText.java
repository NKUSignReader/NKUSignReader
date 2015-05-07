package com.example.signreader;

import android.graphics.drawable.Drawable;
/**
 * This class for a item contain icon,text and selectable
 * @author Wang XinFeng
 * @version 1.0
 *
 */
public class IconText implements Comparable<IconText>{
    /**the text*/
	private String mText = "";
	
	/**icon*/
	private Drawable mIcon;
	/**can be select?*/
	private boolean mSelectable = true;

	public IconText(String text, Drawable bullet) {
		mIcon = bullet;
		mText = text;
	}
	
	public boolean isSelectable() {
		return mSelectable;
	}
	
	public void setSelectable(boolean selectable) {
		mSelectable = selectable;
	}
	
	public String getText() {
		return mText;
	}
	
	public void setText(String text) {
		mText = text;
	}
	
	public void setIcon(Drawable icon) {
		mIcon = icon;
	}
	
	public Drawable getIcon() {
		return mIcon;
	}

	public int compareTo(IconText other) {
		if(this.mText != null)
			return this.mText.compareTo(other.getText()); 
		else 
			throw new IllegalArgumentException();
	}
}
