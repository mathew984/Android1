package com.gmail.mathew984.pogoda;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import android.media.Image;

public class WeatherHttpClient {

	private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?";
	private static String IMG_URL = "http://openweathermap.org/img/w/";
	private static String BASE_FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?mode=json&";

	
	public String getWeatherData(String location) {
		HttpURLConnection con = null ;
		InputStream is = null;

		try {
			con = (HttpURLConnection) ( new URL(BASE_URL + location)).openConnection();
			con.setRequestMethod("GET");
			con.setDoInput(true);
			con.setDoOutput(true);
			con.connect();
			
			// Let's read the response
			StringBuffer buffer = new StringBuffer();
			is = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while (  (line = br.readLine()) != null )
				buffer.append(line + "\r\n");
			
			is.close();
			con.disconnect();
			return buffer.toString();
	    }
		catch(Throwable t) {
			t.printStackTrace();
		}
		finally {
			try { is.close(); } catch(Throwable t) {}
			try { con.disconnect(); } catch(Throwable t) {}
		}

		return null;
				
	}
	
	public String getForecastWeatherData(String location, String sForecastDayNum) {
		HttpURLConnection con = null ;
		InputStream is = null;
		int forecastDayNum = Integer.parseInt(sForecastDayNum);
				
		try {
				
			// Forecast
			String url = BASE_FORECAST_URL + location;		
			url = url + "&cnt=" + forecastDayNum;
			
			con = (HttpURLConnection) ( new URL(url)).openConnection();
			con.setRequestMethod("GET");
			con.setDoInput(true);
			con.setDoOutput(true);
			con.connect();
			
			// Let's read the response
			StringBuffer buffer1 = new StringBuffer();
			is = con.getInputStream();
			BufferedReader br1 = new BufferedReader(new InputStreamReader(is));
			String line1 = null;
			while (  (line1 = br1.readLine()) != null )
				buffer1.append(line1 + "\r\n");
			
			is.close();
			con.disconnect();
			
			//System.out.println("Buffer ["+buffer1.toString()+"]");
			return buffer1.toString();
	    }
		catch(Throwable t) {
			t.printStackTrace();
		}
		finally {
			try { is.close(); } catch(Throwable t) {}
			try { con.disconnect(); } catch(Throwable t) {}
		}

		return null;
				
	}
	
	
	
	public byte[] getImage(String code) {
		HttpURLConnection con = null ;
		InputStream is = null;
		try {
			/*
			 con = (HttpURLConnection) ( new URL(IMG_URL + code + ".png?appid=fe7f360d2493d78200ca1cd20e0d2a77")).openConnection();
			 
			//con = (HttpURLConnection) ( new URL("http://openweathermap.org/img/w/01d.png?&appid=44db6a862fba0b067b1930da0d769e98")).openConnection();
			
			con.setRequestMethod("GET");
			//con.setRequestProperty( "User-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.4 (KHTML, like Gecko) Chrome/22.0.1229.94 Safari/537.4" );
			//con.setInstanceFollowRedirects(false);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.connect();
			
			// Let's read the response
			is = con.getInputStream();
			byte[] buffer = new byte[1024];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			while ( is.read(buffer) != -1)
				baos.write(buffer);
			
			return baos.toByteArray();
		*/
			System.out.println(IMG_URL + code + ".png");
			 URL url = new URL(IMG_URL + code + ".png");
			 InputStream in = new BufferedInputStream(url.openStream());
			 ByteArrayOutputStream out = new ByteArrayOutputStream();
			 byte[] buf = new byte[1024];
			 int n = 0;
			 while (-1!=(n=in.read(buf)))
			 {
			    out.write(buf, 0, n);
			 }
			 out.close();
			 in.close();
			 byte[] response = out.toByteArray();
			 return response;
	    }
		catch(Throwable t) {
			t.printStackTrace();
		}
		finally {
			try { is.close(); } catch(Throwable t) {}
			try { con.disconnect(); } catch(Throwable t) {}
		}
		
		return null;		
	}
}
