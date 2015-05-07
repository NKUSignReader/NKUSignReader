package com.example.signreader;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


/**
 * a Overwrite list adapter
 * @author Wang XinFeng
 *
 */
public class IconTextListAdapter extends BaseAdapter {

	private Context mContext;
	private List<IconText> mItems = new ArrayList<IconText>();
	public IconTextListAdapter(Context context) {
		mContext = context;
	}

	public void addItem(IconText it) { mItems.add(it); }

	public void setListItems(List<IconText> lit) { mItems = lit; }

	/** @return The number of items in the */
	public int getCount() { return mItems.size(); }

	public Object getItem(int position) { return mItems.get(position); }

	public boolean areAllItemsSelectable() { return false; }

	public boolean isSelectable(int position) { 
		return mItems.get(position).isSelectable();
	}

	/** Use the array index as a unique id. */
	public long getItemId(int position) {
		return position;
	}

	/** @param convertView The old view to overwrite, if one is passed
	 * @returns a IconifiedTextView that holds wraps around an IconifiedText */
	public View getView(int position, View convertView, ViewGroup parent) {
		IconTextView btv;
		if (convertView == null) {
			btv = new IconTextView(mContext, mItems.get(position));
		} else { // Reuse/Overwrite the View passed
			// We are assuming(!) that it is castable! 
			btv = (IconTextView) convertView;
			btv.setText(mItems.get(position).getText());
			btv.setIcon(mItems.get(position).getIcon());
		}
		return btv;
	}
}