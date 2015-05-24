package com.example.signreader;

import java.util.Calendar;

//import kankan.wheel.R;
import com.example.signreader.wheelview.NumericWheelAdapter;
import com.example.signreader.wheelview.OnWheelChangedListener;
import com.example.signreader.wheelview.OnWheelScrollListener;
import com.example.signreader.wheelview.WheelView;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TimePicker;

public class TimePickActivity extends Activity {
	// Time changed flag
	private boolean timeChanged = false;
	
	//
	private boolean timeScrolled = false;
	
	private int displaywidth, displayheight;
	public void setupDisplay() {
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		displaywidth = metric.widthPixels;
		displayheight = metric.heightPixels;
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupDisplay();
		setContentView(R.layout.time_pick_layout);
	/*
		final WheelView hours = (WheelView) findViewById(R.id.hour);
		hours.setAdapter(new NumericWheelAdapter(0, 23));
		hours.setLabel("hours");
		*/
		
		LinearLayout.LayoutParams para = new LayoutParams((int)(displaywidth/1.5), (int)(displayheight/2));
		final WheelView days = (WheelView) findViewById(R.id.days);
		//days.setLayoutParams(para);
		days.setAdapter(new NumericWheelAdapter(0, 200));
		days.setLabel("days");
		days.setCyclic(true);
	/*
		final WheelView mins = (WheelView) findViewById(R.id.mins);
		mins.setAdapter(new NumericWheelAdapter(0, 59, "%02d"));
		mins.setLabel("mins");
		mins.setCyclic(true);
*/
		days.setCurrentItem(10);

	
		// add listeners
		addChangingListener(days, "day");
		//addChangingListener(hours, "hour");
	
		OnWheelChangedListener wheelListener = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (!timeScrolled) {
					timeChanged = true;
					timeChanged = false;
				}
			}
		};

		days.addChangingListener(wheelListener);
		//mins.addChangingListener(wheelListener);

		OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
			public void onScrollingStarted(WheelView wheel) {
				timeScrolled = true;
			}
			public void onScrollingFinished(WheelView wheel) {
				timeScrolled = false;
				timeChanged = true; 
				timeChanged = false;
			}
		};
		
		days.addScrollingListener(scrollListener);
		
	}

	/**
	 * Adds changing listener for wheel that updates the wheel label
	 * @param wheel the wheel
	 * @param label the wheel label
	 */
	private void addChangingListener(final WheelView wheel, final String label) {
		wheel.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				wheel.setLabel(newValue != 1 ? label + "s" : label);
			}
		});
	}
}
