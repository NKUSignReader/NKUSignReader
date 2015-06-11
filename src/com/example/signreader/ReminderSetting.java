package com.example.signreader;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ReminderSetting extends Activity{
	
	Button btnEnable;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.remindersett);
		btnEnable = (Button)(findViewById(R.id.btn1));
		btnEnable.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setReminder();
			}
		});
	}
	
	private void setReminder() {  
        
        // get the AlarmManager instance   
        AlarmManager am= (AlarmManager) getSystemService(ALARM_SERVICE);  
		// create a PendingIntent that will perform a broadcast
		PendingIntent pi = PendingIntent.getBroadcast(ReminderSetting.this, 0,
				new Intent(this, MyReceiver.class), 0);

		// just use current time as the Alarm time.
		Calendar c = Calendar.getInstance();
		// schedule an alarm
		am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);

    }  

}
