package com.example.signreader;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver{

	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setClass(context, MyAlarm.class);
		context.startActivity(intent);
		}
	
	

}
