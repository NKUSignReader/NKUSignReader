/**
 *  Author :  nsbdsxh
 *  Description :	set size and color of fonts
 */
package com.example.signreader;

import com.example.signreader.para.Constant;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * 
 *
 *@author nsbdsxh
 *
 */
public class FontSetActivity extends Activity {
	
	SharedPreferences settings;
	SharedPreferences buf1;
	
	int color = 0;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fontset);
		settings = getSharedPreferences("color", 0);
		buf1 = getSharedPreferences("bufend", 0);
		SharedPreferences.Editor editor2 = buf1.edit();
		editor2.putInt("bufend", (int) 5);
		editor2.commit();

		final TextView viewfont = (TextView) findViewById(R.id.viewfont);
		viewfont.setTextSize(KeyParam.textSize);
		viewfont.setTextColor(KeyParam.textColor);
		
		
		//字体大小设置开始
		final Spinner fontsizespinner = (Spinner) findViewById(R.id.fontsizespinner);
		ArrayAdapter<CharSequence> fontsize = ArrayAdapter.createFromResource(
				getApplicationContext(), R.array.fontsize,
				android.R.layout.simple_spinner_item);
		
		fontsizespinner.setAdapter(fontsize);
		fontsizespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long loation) {
				KeyParam.textSize =Integer.parseInt(fontsizespinner.getSelectedItem().toString());
				viewfont.setTextSize(KeyParam.textSize);
			}

			
			public void onNothingSelected(AdapterView<?> arg0) {
			}
			
		});
		
		
		//文字颜色设置开始
		final Spinner fontcolorspinner = (Spinner) findViewById(R.id.fontcolorspinner);
		
		ArrayAdapter<CharSequence> fontcolor = ArrayAdapter.createFromResource(
				getApplicationContext(), R.array.colors,
				android.R.layout.simple_spinner_item);
		
		fontcolorspinner.setAdapter(fontcolor);
		
		fontcolorspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				SharedPreferences.Editor editor = settings.edit();
				String textcolor = fontcolorspinner.getSelectedItem().toString();
				if (Constant.RED.equals(textcolor)) {
					viewfont.setTextColor(Color.RED);
					KeyParam.textColor = Color.RED;
					color=Color.RED;
				}
				if (Constant.GRAY.equals(textcolor)) {
					viewfont.setTextColor(Color.GRAY);
					KeyParam.textColor = Color.GRAY;
					color=Color.GRAY;
				}
				if (Constant.YELLOW.equals(textcolor)) {
					viewfont.setTextColor(Color.YELLOW);
					KeyParam.textColor = Color.YELLOW;
					color=Color.YELLOW;
				}
				if (Constant.GREEN.equals(textcolor)) {
					viewfont.setTextColor(Color.GREEN);
					KeyParam.textColor = Color.GREEN;
					color=Color.GREEN;
				}
				if (Constant.BLUE.equals(textcolor)) {
					viewfont.setTextColor(Color.BLUE);
					KeyParam.textColor = Color.BLUE;
					color=Color.BLUE;
				}
				if (Constant.BLACK.equals(textcolor)) {
					viewfont.setTextColor(Color.BLACK);
					KeyParam.textColor = Color.BLACK;
					color=Color.BLACK;
				}
				if (Constant.WHITE.equals(textcolor)){
					viewfont.setTextColor(Color.WHITE);
					KeyParam.textColor = Color.WHITE;
					color=Color.WHITE;
				}
				editor.putInt("color", color);
				editor.commit();
			}

			
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
			
		});
		
		
		//按钮返回事件开始
		Button go = (Button) findViewById(R.id.gotoreader);
		
		go.setOnClickListener(new View.OnClickListener(){

			
			public void onClick(View v) {
				Intent mIntent = new Intent(getApplicationContext(),
						turntest.class);
			//	CR.textColor = fontcolorspinner.getSelectedItem().toString();
				KeyParam.textSize =Integer.parseInt(fontsizespinner.getSelectedItem().toString());
				//setResult(RESULT_OK, mIntent);
				startActivity(mIntent);
				finish();
			}
			
		});
		

	}

}
