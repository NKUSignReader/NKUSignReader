package com.example.signreader;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyAlarm extends Activity{
	
	 public static final int NOTIFICATION_ID=1;   
     
	    @Override  
	    protected void onCreate(Bundle savedInstanceState) {  
	         super.onCreate(savedInstanceState);  
	         setContentView(R.layout.my_alarm);  
	          
	        // create the instance of NotificationManager  
	        final NotificationManager nm=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);  
	        // create the instance of Notification  
	        Notification n=new Notification();  
	        /* set the sound of the alarm. There are two way of setting the sound */  
	          // n.sound=Uri.parse("file:///sdcard/alarm.mp3");  
	        //n.sound=Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "20");  
	        // Post a notification to be shown in the status bar  
	        nm.notify(NOTIFICATION_ID, n);  
	          
	        /* display some information */  
	          
	        /* the button by which you can cancel the alarm */  
	        Button btnCancel=(Button)findViewById(R.id.btnCancel);  
	        btnCancel.setOnClickListener(new View.OnClickListener() {  
	              
	            @Override  
	            public void onClick(View arg0) {  
	                nm.cancel(NOTIFICATION_ID);  
	                finish();  
	            }  
	        });  
	    }  

}
