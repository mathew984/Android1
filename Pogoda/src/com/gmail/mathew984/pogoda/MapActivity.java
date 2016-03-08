package com.gmail.mathew984.pogoda;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

@SuppressLint("SetJavaScriptEnabled")
public class MapActivity extends Activity {
	private static WebView webView;
	private static TextView textView1;
	
	
	@Override	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		textView1 = (TextView) findViewById(R.id.textView1);
		webView = (WebView) findViewById(R.id.webView1);
		webView.setWebViewClient(new WebViewClient());
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl("https://www.google.pl/maps/@50.0474411,21.4160496,13z");
		
		//---------------------------------------------------------------------------
		webView.setOnTouchListener(new View.OnTouchListener() {
           	@Override
			public boolean onTouch(View v, MotionEvent event) {
				textView1.setText(splitCoords(webView.getUrl()));
				return false;
			}
        });
		//----------------------------------------------------------------------------
		
		
	}
	
	private static String splitCoords(String url){
		String[] temp = url.split("@")[1].split(",");
		url = (((float)Math.round(Float.parseFloat(temp[0]) * 100))/100) + "," + (((float)Math.round(Float.parseFloat(temp[1]) * 100))/100);
		System.out.println("Coords: " + url);
		return url;
	}
	

	public void SaveCoords(View v){		
		SharedPreferences preferences = getSharedPreferences("com.gmail.mathew984.pogoda", Activity.MODE_PRIVATE);
		preferences.edit().putString("coords", splitCoords(webView.getUrl())).commit();
		//this.onBackPressed();
		Intent i = new Intent(this, MainActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}
	
}

