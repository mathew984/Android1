package com.gmail.mathew984.pogoda;

// application works quite well, but it is not fully completed
// supported resolution 1024x600
// tested on android 4.4
// TODO BETTER INTERFACE, FIX/REWRITE MainAcivity, REFACTOR
// feel free to use

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONException;

import com.gmail.mathew984.pogoda.model.*;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.format.Time;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	//------- mało cieakwe zmienne głobalne---------
	private static TextView cityTextView;
	private static TextView condDescrView;
	private static TextView tempView;
	private static TextView pressView;
	private static TextView windSpeedView;
	private static TextView windDegView;	
	private static TextView humView;
	private static ImageView imgView;
	private static String condDescr;
	private static String temp;
	private static String press;
	private static String windSpeed;
	private static String windDeg;	
	private static String hum;
	private static String cityText;
	private static Bitmap img;
		
	private static TextView tempViewDay1;
	private static TextView humViewDay1;
	private static TextView condDescrViewDay1;
	private static TextView pressViewDay1;
	private static TextView dateViewDay1;	
	private static ImageView condIconViewDay1;	
	private static String tempDay1;
	private static String humDay1;
	private static String condDescrDay1;
	private static String pressDay1;
	private static String dateDay1;
	private static TextView tempViewDay2;
	private static TextView humViewDay2;
	private static TextView condDescrViewDay2;
	private static TextView pressViewDay2;
	private static TextView dateViewDay2;	
	private static ImageView condIconViewDay2;	
	private static String tempDay2;
	private static String humDay2;
	private static String condDescrDay2;
	private static String pressDay2;
	private static String dateDay2;
	private static TextView tempViewDay3;
	private static TextView humViewDay3;
	private static TextView condDescrViewDay3;
	private static TextView pressViewDay3;
	private static TextView dateViewDay3;	
	private static ImageView condIconViewDay3;	
	private static String tempDay3;
	private static String humDay3;
	private static String condDescrDay3;
	private static String pressDay3;
	private static String dateDay3;
	private static TextView tempViewDay4;
	private static TextView humViewDay4;
	private static TextView condDescrViewDay4;
	private static TextView pressViewDay4;
	private static TextView dateViewDay4;	
	private static ImageView condIconViewDay4;	
	private static String tempDay4;
	private static String humDay4;
	private static String condDescrDay4;
	private static String pressDay4;
	private static String dateDay4;
	private static TextView tempViewDay5;
	private static TextView humViewDay5;
	private static TextView condDescrViewDay5;
	private static TextView pressViewDay5;
	private static TextView dateViewDay5;	
	private static ImageView condIconViewDay5;	
	private static String tempDay5;
	private static String humDay5;
	private static String condDescrDay5;
	private static String pressDay5;
	private static String dateDay5;
	private static int backgroundResourceID = R.drawable.slonecznydzien2;


	//-------- Ciekawsze zmienne----------------
	private static String forecastDaysNum = "6";
	private String city = null;
	private String appID = "&units=metric&lang=pl&appid=fe7f360d2493d78200ca1cd20e0d2a77";
	private SectionsPagerAdapter mSectionsPagerAdapter;
	private ViewPager mViewPager;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
				
		SharedPreferences preferences = getSharedPreferences("com.gmail.mathew984.pogoda", Activity.MODE_PRIVATE);
		String coords = preferences.getString("coords", null);
		//preferences.edit().clear().commit();
		if(coords != null){
			String temp[] = coords.split(",");
			city = "lat=" + temp[0] + "3&lon=" + temp[1] + appID;
		}else{
			city = "q=London,uk" + appID;
		}	
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		
		if(isNetworkConnected()){
			
			try{
				
				JSONWeatherTask task = new JSONWeatherTask();
				task.execute(new String[]{city});
				
				JSONForecastWeatherTask task1 = new JSONForecastWeatherTask();
				task1.execute(new String[]{city, forecastDaysNum});	
				
			}catch(Throwable t){
				preferences.edit().clear().commit();
				Toast.makeText(this, "Nie można pobrać pogody, spróbuj wybrać inne miasto" , Toast.LENGTH_LONG).show();
			}
				
		}
	}
	
	
	
	//--------------------------------------------------------- MENU -------------------------------------------------------------
	
		

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			if(isNetworkConnected()){
				Intent intent = new Intent(this, MapActivity.class);
			 	startActivity(intent);
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
		
	
	//----------------------------------------------------- SWIPE VIEW -------------------------------------------------------

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return PlaceholderFragment.newInstance(position + 1);
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			}
			return null;
		}
		
		@Override
		public int getItemPosition(Object object) {
		    // POSITION_NONE makes it possible to reload the PagerAdapter
		    return POSITION_NONE;
		}
	}


	public static class PlaceholderFragment extends Fragment {
		private static final String ARG_SECTION_NUMBER = "section_number";
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}		
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = null;
			Bundle arg = getArguments();
			if(arg.getInt(ARG_SECTION_NUMBER) == 1) {
				
				rootView = inflater.inflate(R.layout.fragment_main, container,false);
				rootView.setTag(1);
				
				imgView = (ImageView) rootView.findViewById(R.id.condIcon);
				cityTextView  = (TextView) rootView.findViewById(R.id.cityText);
				condDescrView  = (TextView) rootView.findViewById(R.id.condDescr);
				tempView  = (TextView) rootView.findViewById(R.id.temp);
				humView  = (TextView) rootView.findViewById(R.id.hum);
				pressView  = (TextView) rootView.findViewById(R.id.press);
				windSpeedView  = (TextView) rootView.findViewById(R.id.windSpeed);
				windDegView  = (TextView) rootView.findViewById(R.id.windDeg);
				rootView.setBackgroundResource(backgroundResourceID);
			
			}
			if(arg.getInt(ARG_SECTION_NUMBER) == 2) {
				rootView = inflater.inflate(R.layout.fragment_list, container,false);
				rootView.setTag(2);
				tempViewDay1 = (TextView) rootView.findViewById(R.id.tempDay1);
				humViewDay1 = (TextView) rootView.findViewById(R.id.humDay1);
				condDescrViewDay1 = (TextView) rootView.findViewById(R.id.condDescrDay1);
				pressViewDay1 = (TextView) rootView.findViewById(R.id.pressDay1);
				condIconViewDay1 = (ImageView) rootView.findViewById(R.id.condIconDay1);
				dateViewDay1 = (TextView) rootView.findViewById(R.id.DateDay1);
				
				tempViewDay2 = (TextView) rootView.findViewById(R.id.tempDay2);
				humViewDay2 = (TextView) rootView.findViewById(R.id.humDay2);
				condDescrViewDay2 = (TextView) rootView.findViewById(R.id.condDescrDay2);
				pressViewDay2 = (TextView) rootView.findViewById(R.id.pressDay2);
				condIconViewDay2 = (ImageView) rootView.findViewById(R.id.condIconDay2);
				dateViewDay2 = (TextView) rootView.findViewById(R.id.DateDay2);
				
				tempViewDay3 = (TextView) rootView.findViewById(R.id.tempDay3);
				humViewDay3 = (TextView) rootView.findViewById(R.id.humDay3);
				condDescrViewDay3 = (TextView) rootView.findViewById(R.id.condDescrDay3);
				pressViewDay3 = (TextView) rootView.findViewById(R.id.pressDay3);
				condIconViewDay3 = (ImageView) rootView.findViewById(R.id.condIconDay3);
				dateViewDay3 = (TextView) rootView.findViewById(R.id.DateDay3);
				
				tempViewDay4 = (TextView) rootView.findViewById(R.id.tempDay4);
				humViewDay4 = (TextView) rootView.findViewById(R.id.humDay4);
				condDescrViewDay4 = (TextView) rootView.findViewById(R.id.condDescrDay4);
				pressViewDay4 = (TextView) rootView.findViewById(R.id.pressDay4);
				condIconViewDay4 = (ImageView) rootView.findViewById(R.id.condIconDay4);
				dateViewDay4 = (TextView) rootView.findViewById(R.id.DateDay4);

				tempViewDay5 = (TextView) rootView.findViewById(R.id.tempDay5);
				humViewDay5 = (TextView) rootView.findViewById(R.id.humDay5);
				condDescrViewDay5 = (TextView) rootView.findViewById(R.id.condDescrDay5);
				pressViewDay5 = (TextView) rootView.findViewById(R.id.pressDay5);
				condIconViewDay5 = (ImageView) rootView.findViewById(R.id.condIconDay5);
				dateViewDay5 = (TextView) rootView.findViewById(R.id.DateDay5);


				}
			return rootView;
		}
	}
	
	
	//----------------------------------------------------------- TASKI ----------------------------------------------------------------------------
	
	
	
private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {
		
		@Override
		protected Weather doInBackground(String... params) {
			Weather weather = new Weather();
			String data = ( (new WeatherHttpClient()).getWeatherData(params[0]));

			try {
				weather = JSONWeatherParser.getWeather(data);
				
				// Let's retrieve the icon
				weather.iconData = ( (new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));
				
			} catch (JSONException e) {				
				e.printStackTrace();
			}
			return weather;		
	}		
		
	@Override
		protected void onPostExecute(Weather weather) {			
			super.onPostExecute(weather);
			
			
		//	if (weather.iconData != null && weather.iconData.length > 0) {
		//		img = BitmapFactory.decodeByteArray(weather.iconData, 0, weather.iconData.length); 
		//		imgView.setImageBitmap(img);
		//	}
			
		//	System.out.println("test: " + weather.currentCondition.getIcon());
			
			if(weather.currentCondition.getIcon().equals("01d") || weather.currentCondition.getIcon().equals("01n")){
				backgroundResourceID = R.drawable.slonce;
			}else if(weather.currentCondition.getIcon().equals("09d") || weather.currentCondition.getIcon().equals("09n") || weather.currentCondition.getIcon().equals("10d") || weather.currentCondition.getIcon().equals("10n")){
				backgroundResourceID = R.drawable.deszczjpg;
			}else if(weather.currentCondition.getIcon().equals("02d") || weather.currentCondition.getIcon().equals("02n") || weather.currentCondition.getIcon().equals("03d") || weather.currentCondition.getIcon().equals("03n") || weather.currentCondition.getIcon().equals("04d") || weather.currentCondition.getIcon().equals("04n")){
				backgroundResourceID = R.drawable.chmury;
			}else if(weather.currentCondition.getIcon().equals("11d") || weather.currentCondition.getIcon().equals("11n")){
				backgroundResourceID = R.drawable.burza;
			}else{
				backgroundResourceID = R.drawable.snieg;
			}
			
			updateView();
			
			condDescr = weather.currentCondition.getDescr();
			temp = "" + Math.round((weather.temperature.getTemp())) + "C" ;
			press = " " + weather.currentCondition.getPressure() + " hPa";
			windSpeed = "" + weather.wind.getSpeed() + " m/s";
			windDeg = "" + weather.wind.getDeg();	
			hum = "" + weather.currentCondition.getHumidity() + "%" ;
			cityText = weather.location.getCity();
			
			double wiatr = Double.parseDouble(windDeg);
			if(wiatr <= 90){
				windDeg = "NW";
			}else if(wiatr <= 180){
				windDeg = "NE";
			}else if(wiatr <= 270){
				windDeg = "SE";
			}else if(wiatr <= 360){
				windDeg = "SN";
			}
			
			cityTextView.setText(cityText);
			condDescrView.setText(condDescr);
			tempView.setText(temp);
			humView.setText(hum);
			pressView.setText(press);
			windSpeedView.setText(windSpeed);
			windDegView.setText(windDeg);
				
		}	
  }


private class JSONForecastWeatherTask extends AsyncTask<String, Void, WeatherForecast> {
	
	@Override
	protected WeatherForecast doInBackground(String... params) {
		
		String data = ( (new WeatherHttpClient()).getForecastWeatherData(params[0], params[1]));
		WeatherForecast forecast = new WeatherForecast();
		try {
			forecast = JSONWeatherParser.getForecastWeather(data);
			//System.out.println("Weather ["+forecast+"]");
			///Let's retrieve the icon
			//weather.iconData = ( (new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));
			
		} catch (JSONException e) {				
			e.printStackTrace();
		}
		return forecast;
	
}
	
	
@Override
	protected void onPostExecute(WeatherForecast forecastWeather) {			
		super.onPostExecute(forecastWeather);
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		
		condIconViewDay1.setImageResource(getResources().getIdentifier("a"+ forecastWeather.getForecast(1).weather.currentCondition.getIcon(), "drawable", getPackageName()));	
		tempDay1 = "" + Math.round(forecastWeather.getForecast(1).forecastTemp.day) + "C";
		tempViewDay1.setText(tempDay1);
		humDay1 = "" + Math.round(forecastWeather.getForecast(1).weather.currentCondition.getHumidity()) + "%";
		humViewDay1.setText(humDay1);
		condDescrDay1 = "" + forecastWeather.getForecast(1).weather.currentCondition.getDescr();
		condDescrViewDay1.setText(condDescrDay1);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		date = calendar.getTime();
		dateDay1 = "" + date.getDate() + "/" + (date.getMonth()+1) + "/" + (date.getYear()+1900);
		dateViewDay1.setText(dateDay1);
		pressDay1 = "" + Math.round(forecastWeather.getForecast(1).weather.currentCondition.getPressure()) + "hPa";
		pressViewDay1.setText(pressDay1);
		
		condIconViewDay2.setImageResource(getResources().getIdentifier("a"+ forecastWeather.getForecast(2).weather.currentCondition.getIcon(), "drawable", getPackageName()));
		tempDay2 = "" + Math.round(forecastWeather.getForecast(2).forecastTemp.day) + "C";
		tempViewDay2.setText(tempDay2);
		humDay2 = "" + Math.round(forecastWeather.getForecast(2).weather.currentCondition.getHumidity()) + "%";
		humViewDay2.setText(humDay2);
		condDescrDay2 = "" + forecastWeather.getForecast(2).weather.currentCondition.getDescr();
		condDescrViewDay2.setText(condDescrDay2);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		date = calendar.getTime();
		dateDay2 = "" + date.getDate() + "/" + (date.getMonth()+1) + "/" + (date.getYear()+1900);
		dateViewDay2.setText(dateDay2);
		pressDay2 = "" + Math.round(forecastWeather.getForecast(2).weather.currentCondition.getPressure()) + "hPa";
		pressViewDay2.setText(pressDay2);
		
		condIconViewDay3.setImageResource(getResources().getIdentifier("a"+ forecastWeather.getForecast(3).weather.currentCondition.getIcon(), "drawable", getPackageName()));
		tempDay3 = "" + Math.round(forecastWeather.getForecast(3).forecastTemp.day) + "C";
		tempViewDay3.setText(tempDay3);
		humDay3 = "" + Math.round(forecastWeather.getForecast(3).weather.currentCondition.getHumidity()) + "%";
		humViewDay3.setText(humDay3);
		condDescrDay3 = "" + forecastWeather.getForecast(3).weather.currentCondition.getDescr();
		condDescrViewDay3.setText(condDescrDay3);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		date = calendar.getTime();
		dateDay3 = "" + date.getDate() + "/" + (date.getMonth()+1) + "/" + (date.getYear()+1900);
		dateViewDay3.setText(dateDay3);
		pressDay3 = "" + Math.round(forecastWeather.getForecast(3).weather.currentCondition.getPressure()) + "hPa";
		pressViewDay3.setText(pressDay3);
		
		condIconViewDay4.setImageResource(getResources().getIdentifier("a"+ forecastWeather.getForecast(4).weather.currentCondition.getIcon(), "drawable", getPackageName()));
		tempDay4 = "" + Math.round(forecastWeather.getForecast(4).forecastTemp.day) + "C";
		tempViewDay4.setText(tempDay4);
		humDay4 = "" + Math.round(forecastWeather.getForecast(4).weather.currentCondition.getHumidity()) + "%";
		humViewDay4.setText(humDay4);
		condDescrDay4 = "" + forecastWeather.getForecast(4).weather.currentCondition.getDescr();
		condDescrViewDay4.setText(condDescrDay4);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		date = calendar.getTime();
		dateDay4 = "" + date.getDate() + "/" + (date.getMonth()+1) + "/" + (date.getYear()+1900);
		dateViewDay4.setText(dateDay4);
		pressDay4 = "" + Math.round(forecastWeather.getForecast(4).weather.currentCondition.getPressure()) + "hPa";
		pressViewDay4.setText(pressDay4);
		
		condIconViewDay5.setImageResource(getResources().getIdentifier("a"+ forecastWeather.getForecast(5).weather.currentCondition.getIcon(), "drawable", getPackageName()));
		tempDay5 = "" + Math.round(forecastWeather.getForecast(5).forecastTemp.day) + "C";
		tempViewDay5.setText(tempDay5);
		humDay5 = "" + Math.round(forecastWeather.getForecast(5).weather.currentCondition.getHumidity()) + "%";
		humViewDay5.setText(humDay5);
		condDescrDay5 = "" + forecastWeather.getForecast(5).weather.currentCondition.getDescr();
		condDescrViewDay5.setText(condDescrDay5);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		date = calendar.getTime();
		dateDay5 = "" + date.getDate() + "/" + (date.getMonth()+1) + "/" + (date.getYear()+1900);
		dateViewDay5.setText(dateDay5);
		pressDay5 = "" + Math.round(forecastWeather.getForecast(5).weather.currentCondition.getPressure()) + "hPa";
		pressViewDay5.setText(pressDay5);
		
		
		GraphView graph = (GraphView) findViewById(R.id.graph);
		LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
		          new DataPoint(1, Math.round(forecastWeather.getForecast(1).forecastTemp.day)),
		          new DataPoint(2, Math.round(forecastWeather.getForecast(2).forecastTemp.day)),
		          new DataPoint(3, Math.round(forecastWeather.getForecast(3).forecastTemp.day)),
		          new DataPoint(4, Math.round(forecastWeather.getForecast(4).forecastTemp.day)),
		          new DataPoint(5, Math.round(forecastWeather.getForecast(5).forecastTemp.day))
		});
		graph.addSeries(series);

		
		System.out.println("KONIEC ONPOST");
	}
}

	//------------------------------------------------------------- INNE -----------------------------------------------------------
	

	private void updateView(){
		mViewPager.getAdapter().notifyDataSetChanged();	
	}
	
	public void przyciskTest(View v){
		System.out.println("button test");
	}
	
	private boolean isNetworkConnected() {
		  ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		  if(cm.getActiveNetworkInfo() == null){
			  Toast.makeText(this, "Brak połączenia z Internetem" , Toast.LENGTH_LONG).show();
			  return false;
		  }
		  return true;
		 }
}
