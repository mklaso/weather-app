package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.reflect.*;
import com.google.gson.Gson;

public class WeatherSystem {
	
	private static final String API_KEY = "3c5aee26050f4b4e3b83118e62088949";
	private String location;
	private String data = "";
	
	public WeatherSystem(String location) {
		setLocation(location);
	}
	
	public void setLocation(String s) {
		this.location = s;
	}
	
	public void resetWeatherData() {
		this.data = "";
	}
	
	public void printWeatherData() {
		System.out.println(this.data);
	}
	
	public static Map<String, Object> jsonToMap(String str) {
		Map<String, Object> map = new Gson().fromJson(
				str, new TypeToken<HashMap<String, Object>>() {}.getType());
		
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public void obtainWeatherData() {
		String line;
		setLocation(location);
		String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" 
		+ this.location + "&APPID=" + API_KEY + "&units=metric";

		try {
			String result = "";
			URL url = new URL(urlString);
			URLConnection con = url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			while ((line = reader.readLine()) != null ) {
				result += line;
			}
			
			reader.close();
			
			//relevant data
			Map<String, Object> respMap = jsonToMap(result);
			
			Map<String, Object> mainMap = (Map<String, Object>)respMap.get("main");
			ArrayList<Map<String, Object>> weather = (ArrayList<Map<String, Object>>)respMap.get("weather");
			Map<String, Object> weatherStatus = (Map<String, Object>)weather.get(0);
			
			this.data += "Weather stats for " + location + " are:" + "\n";
			this.data += "Current Temp: " + mainMap.get("temp") + "\n";
			this.data += "Current Weather: " + weatherStatus.get("main")
					+ " - " + weatherStatus.get("description") + "\n";
			
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
//	public String getMinTemp(String s) {
//		
//	}
//	
//	public String getMaxTemp(String s) {
//		
//	}
//	
//	public String getHumidity(String s) {
//		
//	}
//	
//	public String getCurrentTemp(String s) {
//		
//	}
//	
//	public String getFeelsLikeTemp(String s) {
//		
//	}
//	
//	public String getWindConditions(String s) {
//		
//	}
//	
//	public String getWeatherStatus(String s) {
//		
//	}
//	
	public static String formatTime(Date dateObject) {
	    SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
	    return timeFormat.format(dateObject);
	 }
	
	// get rid of main method once mvc architecture is setup properly
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		String location = "Oakville, CA";
		String line;
		
		String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" 
		+ location + "&APPID=" + API_KEY + "&units=metric";

		try {
			String result = "";
			URL url = new URL(urlString);
			URLConnection con = url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			while ((line = reader.readLine()) != null ) {
				result += line;
			}
			
			reader.close();

			Map<String, Object> respMap = jsonToMap(result);
			
			Map<String, Object> mainMap = (Map<String, Object>)respMap.get("main");
			ArrayList<Map<String, Object>> weather = (ArrayList<Map<String, Object>>)respMap.get("weather");
			Map<String, Object> weatherStatus = (Map<String, Object>)weather.get(0);
			
			System.out.println(result);
			System.out.println("Weather stats for " + location + " are:");
			System.out.println("Current Temp: " + mainMap.get("temp") + "°C");
			System.out.println("Current Weather: " + weatherStatus.get("main")
					+ " - " + weatherStatus.get("description"));
			
			Map<String, Object> sun = (Map<String, Object>)respMap.get("sys");
			//System.out.println("Sunrise at: " + formatTime(new Date((long) sun.get("sunrise"))));
			
			Date date = new Date(1577633066);
			int time = 1577633066;
			//int time = 1577638196;
			//-18000
			date.setTime((long)time*1000);
			System.out.println(formatTime(date));
			
			// ^ this displays the time in UTC. (5hrs ahead of EST)
			// need to add some sort of timezone conversion based on which timezone the country is in
			
			
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
		
	}
}
