package com.example.signreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class dialog_activity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_test);
		findViewById(R.id.dialog_custom_button).setOnClickListener(this);
		findViewById(R.id.dialog_activity_button).setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.dialog_activity_button:
			Intent intent = new Intent(dialog_activity.this, TimePickActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}
