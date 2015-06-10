package com.example.signreader;

import java.io.IOException;
import java.util.List;

import com.example.signreader.domain.Book;
import com.example.signreader.domain.BookOperations;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class turntest extends Activity {
	/** Called when the activity is first created. */
	private PageWidget mPageWidget;
	Bitmap mCurPageBitmap, mNextPageBitmap;
	Canvas mCurPageCanvas, mNextPageCanvas;
	BookPageFactory pagefactory;
	private int displaywidth, displayheight;
	SharedPreferences sharedpreference;
	SharedPreferences sharedpreferencebuf;
	
	int bufBegin;
	int bufEnd;
	int bufLength;
	private static final int CHANGEFONT = Menu.FIRST;
	private static final int CHANGEBG = Menu.FIRST + 3;
	private static final int SAVEBOOKMARK = Menu.FIRST + 4;
	// 旋屏菜单
	private static final int CIRC_SCREEN = Menu.FIRST + 9;
	private static final int BACK = Menu.FIRST + 6;
	private static final int EXIT = Menu.FIRST + 7;
	private static final int ABOUT = Menu.FIRST + 8;
	// 查看书签
	private static final int VIEWBOOKMARK = Menu.FIRST + 20;
	int storedBufEnd;
	/** 用于设置读书背景的请求代码 */
	private final int REQUEST_CODE_SET_BAGKGROUD = 10;

	/** 用于设置字体相关的请求代码 */
	private final int REQUEST_CODE_SET_FONT = 11;

	/** dialog id */
	//private final int SAVEBOOKMARKSUCCESS = 11;
	//private final int SAVEBOOKMARKFAIL = 12;
	BookOperations bookoperations;
	List<Book> BookList;
	Book book = new Book();
	boolean hasBook = false;
	int finished;
	public void setupDisplay() {
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		displaywidth = metric.widthPixels;
		displayheight = metric.heightPixels;
	}
	
	Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg){
			if(msg.what==0x126)
			{
				Toast.makeText(getApplicationContext(), "已成功读取您的信息",Toast.LENGTH_LONG).show();
			}
			if(msg.what==0x127)
			{
				Toast.makeText(getApplicationContext(), "读取信息失败，请您检查账号密码以及网络状况",Toast.LENGTH_LONG).show();
			}
		}
	};
	

	@SuppressLint("WrongCall") @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupDisplay();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		mPageWidget = new PageWidget(this);
		setContentView(mPageWidget);

		mCurPageBitmap = Bitmap.createBitmap(displaywidth, displayheight, Bitmap.Config.ARGB_8888);
		mNextPageBitmap = Bitmap
				.createBitmap(displaywidth, displayheight, Bitmap.Config.ARGB_8888);

		mCurPageCanvas = new Canvas(mCurPageBitmap);
		mNextPageCanvas = new Canvas(mNextPageBitmap);
		sharedpreference = getSharedPreferences("color", 0);
		sharedpreferencebuf = getSharedPreferences("bufend", 0);
		storedBufEnd = sharedpreferencebuf.getInt("bufend", 1);
		int color=sharedpreference.getInt("color", Color.BLACK);
		Log.e("color",String.valueOf(color));
		pagefactory = new BookPageFactory(displaywidth, displayheight, KeyParam.textSize, color, this );

		pagefactory.setBgBitmap(BitmapFactory.decodeResource(
				this.getResources(), R.drawable.bg));
		
		bookoperations = new BookOperations(this);
		bookoperations.open();
		try
		{
			BookList = bookoperations.searchClass("godfather");
			Log.e("sqlite", "yes");
			Log.e("booklist",BookList.toString());
			if(BookList.isEmpty())
			{
				Log.e("booklist","no");
				try {
					pagefactory.openbookFirstTime("/sdcard/test.txt");
					pagefactory.Fix_onDraw(mCurPageCanvas, displayheight, displaywidth);
					BookList = bookoperations.searchClass("godfather");
					book = BookList.get(0);
					Log.e("bookpath",book.getBookPath());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					Toast.makeText(this, "电子书不存在",
							Toast.LENGTH_SHORT).show();
				}
			}
			else
			{
				Log.e("booklist","has");
				book = BookList.get(0);
				readBook();
				Log.e("bookpath",book.getBookPath());
			}
		}
		catch(Exception e)
		{
			Toast.makeText(this, "数据库找不到",
					Toast.LENGTH_SHORT).show();
		}
		finished = book.getCurrent();

		//Log.e("bookpath",book.getBookPath());


		mPageWidget.setBitmaps(mCurPageBitmap, mCurPageBitmap);

		mPageWidget.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent e) {
				// TODO Auto-generated method stub
				
				boolean ret=false;
				if (v == mPageWidget) {
					if (e.getAction() == MotionEvent.ACTION_DOWN) {
						mPageWidget.abortAnimation();
						mPageWidget.calcCornerXY(e.getX(), e.getY());

						pagefactory.Fix_onDraw(mCurPageCanvas, displayheight, displaywidth);
						if (mPageWidget.DragToRight()) {
							try {
								pagefactory.prePage();
								
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}						
							if(pagefactory.isfirstPage())return false;
							pagefactory.Fix_onDraw(mNextPageCanvas, displayheight, displaywidth);
						} else {
							try {
								pagefactory.nextPage();
								dotheMath(pagefactory.returnBufEnd(),pagefactory.returnBufLength());
								
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							if(pagefactory.islastPage())return false;
							pagefactory.Fix_onDraw(mNextPageCanvas, displayheight, displaywidth);
						}
						mPageWidget.setBitmaps(mCurPageBitmap, mNextPageBitmap);
					}
                 
					 ret = mPageWidget.doTouchEvent(e);
					return ret;
				}
				return false;
			}

		});
	}
	
	private void readBook()
	{
		try {
			Log.e("current",String.valueOf(book.getCurrent()));
			pagefactory.openbook(book.getBookPath(),book.getCurrent());
			pagefactory.Fix_onDraw(mCurPageCanvas, displayheight, displaywidth);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			Toast.makeText(this, "电子书不存在",
					Toast.LENGTH_SHORT).show();
		}
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(1, CHANGEFONT, 0, R.string.changefont).setShortcut('3', 'a')
				.setIcon(R.drawable.setfont);
		menu.add(1, CHANGEBG, 1, R.string.changebg).setShortcut('3', 'c')
				.setIcon(R.drawable.setbackgroud);
		menu.add(2, SAVEBOOKMARK, 2, R.string.savebookmark).setShortcut('3',
				'd').setIcon(R.drawable.addbookmark);
		menu.add(2, VIEWBOOKMARK, 3, R.string.viewbookmark).setShortcut('3',
				'q').setIcon(R.drawable.viewbookmark);
		// 旋转屏幕菜单
		menu.add(2, CIRC_SCREEN, 3, R.string.circumgyrate)
				.setShortcut('3', 'c').setIcon(R.drawable.circscreen);

		menu.add(3, BACK, 5, R.string.back).setShortcut('3', 'x').setIcon(
				R.drawable.uponelevel);
		menu.add(3, EXIT, 6, R.string.exit).setShortcut('3', 'e').setIcon(
				R.drawable.close);
		menu.add(3, ABOUT, 7, R.string.about).setShortcut('3', 'o').setIcon(
				android.R.drawable.star_big_on);
		return true;
	}
	
	private void dotheMath(int bufpos, int buflen)
	{
		int fPercent =  bufpos - finished;
		int days = book.getMark();
		
		int mark = (int)((buflen-finished)/days);
		Log.e("days",String.valueOf(mark));
		if(fPercent >mark)
		{
			Log.e("yes",String.valueOf(fPercent));
			handler.sendEmptyMessage(0x126);
			bookoperations.updateSchedue(book.getId(), bufpos);
			BookList = bookoperations.searchClass("godfather");
			dialog();
			//Book book3 = BookList.get(0);
			Log.e("booklist1",BookList.toString());
		}
		else
		{
			Log.e("No",String.valueOf(fPercent));
		}
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case CHANGEFONT:// change text size
			// 设置字体相关
			Intent ifs = new Intent(getApplicationContext(),
					FontSetActivity.class);
			startActivity(ifs);
			//startActivityForResult(ifs, REQUEST_CODE_SET_FONT);
			this.finish();
			return true;
		case CHANGEBG:// change background image
		//	Intent ix = new Intent(getApplicationContext(), ImageBrowser.class);
		//	startActivityForResult(ix, REQUEST_CODE_SET_BAGKGROUD);
			return true;

		case CIRC_SCREEN:// 触发了旋屏菜单

		//	circumgyrateScreen();
			return true;

		case SAVEBOOKMARK:// save book mark
			//saveBookMarkDialog();
			return true;
		case VIEWBOOKMARK:// 浏览书签
			//bookMarkView();
			return true;

		case EXIT:// exit system
			this.finish();
			return true;
		case BACK:// back to browser file
			setProgressBarIndeterminateVisibility(false);
	/*		Intent i = new Intent();
			i.setClass(getApplicationContext(), FileBrowser.class);
			startActivity(i);*/
			setProgressBarIndeterminateVisibility(true);
			this.finish();

			return true;
		case ABOUT:// about this software
			showDialog(ABOUT);
			return true;
		default:
			return true;
		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_SET_FONT && resultCode == RESULT_OK) {// 设置字体相关
		
			String tag = "onActivityResult";
			Log.d(tag, "go into the activity result...");

		}
	}

	protected void dialog() {
		AlertDialog.Builder builder = new Builder(turntest.this);
		builder.setMessage("今日计划已完成，确认退出吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {

			public void onClick(DialogInterface dialog, int arg1) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				turntest.this.finish();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			public void onClick(DialogInterface dialog, int arg1) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
}