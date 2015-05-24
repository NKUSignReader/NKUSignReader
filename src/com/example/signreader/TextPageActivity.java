package com.example.signreader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import com.example.signreader.para.Constant;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class TextPageActivity extends Activity {

	ReadView readView;
	BufferedReader reader;
	CharBuffer buffer = CharBuffer.allocate(8000);
	int position;
	private String _mFilePath = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_txtpage);
		Intent intent = getIntent();
		if (intent == null) {
			finish();
			return;
		}
		Bundle bundle = intent.getExtras();
		if (bundle == null) {
			finish();
			return;
		}
		_mFilePath = bundle.getString(Constant.FILE_PATH_KEY);
		if (_mFilePath == null || _mFilePath.equals("")) {
			finish();
			return;
		}
		readView = (ReadView) findViewById(R.id.read_view);
		
		loadBook();
		loadPage(0);
	}

	/**
	 * 将电子书都一部分到缓冲区
	 */
	private void loadBook() {
		AssetManager assets = getAssets();
		try {
			File f = new File(_mFilePath);
			String path=f.getAbsolutePath();
			FileInputStream fileIS = new FileInputStream(path);
			InputStream in = assets.open("documents/The Golden Compass.txt");
			Charset charset = CharsetDetector.detect(fileIS);
			reader = new BufferedReader(new InputStreamReader(fileIS, charset));
			reader.read(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 从指定位置开始载入一�?
	 */
	private void loadPage(int position) {
		buffer.position(position);
		readView.setText(buffer);
	}
	
	/**
	 * 上一页按�?
	 */
	public void previewPageBtn(View view) {
		
	}
	
	/**
	 * 下一页按�?
	 */
	public void nextPageBtn(View view) {
		position += readView.getCharNum();
		loadPage(position);
		readView.resize();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
