package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import com.google.gson.reflect.*;
import com.google.gson.Gson;

public class WeatherSystem {
	
	private static final String API_KEY = "3c5aee26050f4b4e3b83118e62088949";
	private String location;
	private String urlString;
	private String result = "";
	private String tempUnit = "°C"; //default
	private String measurementType = "&units=metric"; //default
	private Map<String, Object> responseMap;
	private Map<String, Object> mainMap;
	private Map<String, Object> sysMap;

	//add documentation later
	@SuppressWarnings("unchecked")
	public WeatherSystem(String location) {
		this.location = location;
		this.setURLString();
		this.obtainWeatherData();
		this.responseMap = jsonToMap(this.result);
		this.mainMap = (Map<String, Object>)responseMap.get("main");
		this.sysMap = (Map<String, Object>)responseMap.get("sys");
	}
	
	public void setURLString() {
		this.urlString = "http://api.openweathermap.org/data/2.5/weather?q=" 
				+ this.location + "&APPID=" + API_KEY + measurementType;
	}
	
	public void changeMeasurementSystem(String unitType) {
		if (unitType.toLowerCase().equals("metric")) {
			this.measurementType = "&units=metric";
			this.tempUnit = "°C";
		} else if (unitType.toLowerCase().equals("imperial")) {
			this.measurementType = "&units=imperial";
			this.tempUnit = "°F";
		}
		this.resetWeatherData();
	}
	
	public static Map<String, Object> jsonToMap(String str) {
		Map<String, Object> map = new Gson().fromJson(
				str, new TypeToken<HashMap<String, Object>>() {}.getType());
		
		return map;
	}
	
	public void obtainWeatherData() {
		String line;

		try {
			URL url = new URL(this.urlString);
			URLConnection con = url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			while ((line = reader.readLine()) != null ) {
				this.addToResult(line);
			}
			
			reader.close();
			
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void addToResult(String s) {
		this.result += s;
	}
	
	@SuppressWarnings("unchecked")
	public void resetWeatherData() {
		this.result = "";
		this.setURLString();
		this.obtainWeatherData();
		this.responseMap = jsonToMap(this.result);
		this.mainMap = (Map<String, Object>)responseMap.get("main");
		this.sysMap = (Map<String, Object>)responseMap.get("sys");
	}
	
	public void setLocation(String s) {
		this.location = s;
		this.measurementType = "&units=metric";
		this.tempUnit = "°C";
		this.resetWeatherData();
	}
	
	public String getCityName() {
		return (String) this.responseMap.get("name");
	}
	
	public String getCurrentTemp() {
		return String.valueOf(this.mainMap.get("temp")) + this.tempUnit;
	}
	
	public String getFeelsLikeTemp() {
		return String.valueOf(this.mainMap.get("feels_like")) + this.tempUnit;
	}
	
	public String getMinTemp() {
		return String.valueOf(this.mainMap.get("temp_min")) + this.tempUnit;
	}
	
	public String getMaxTemp() {
		return String.valueOf(this.mainMap.get("temp_max")) + this.tempUnit;
	}
	
	public String getHumidity() {
		return String.valueOf(this.mainMap.get("humidity")) + "%";
	}
	
	public String getWindConditions() {
		@SuppressWarnings("unchecked")
		Map<String, Object> wind = (Map<String, Object>)responseMap.get("wind");
		return String.valueOf(wind.get("speed")) + "m/s";
	}
	
	public String getWeatherStatus() {
		@SuppressWarnings("unchecked")
		ArrayList<Map<String, Object>> weather = (ArrayList<Map<String, Object>>)responseMap.get("weather");
		Map<String, Object> weatherStatus = (Map<String, Object>)weather.get(0);
		return (String) weatherStatus.get("description");	
	}
	
	public double getTimezoneOffset() {
		double timeOffset = (double)(this.responseMap.get("timezone"));
		return timeOffset;
	}
	
	public String convertTime(String s) {
		int offset = (int)(this.getTimezoneOffset());
		
		if (s.equals("sunrise") || s.equals("sunset")) {
			long l = (long)((double)sysMap.get(s));
			long time = l + offset;
			return formatTime(new Date(time * 1000));
		} else {
			long current = Instant.now().getEpochSecond();
			long time = current + offset;
			return formatTime(new Date(time * 1000));
		}
	}
	
	public String getSunriseTime() {
		return this.convertTime("sunrise");
	}
	
	public String getSunsetTime() {
		return this.convertTime("sunset");
	}
	
	public String getLocalTime() {
		return this.convertTime("local");
	}
	
	public static String formatTime(Date dateObject) {
	    SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
	    timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	    return timeFormat.format(dateObject);
	 }
	
	public void printWeatherData() {
		System.out.println("=========================================");
		System.out.println("Local time in " + this.getCityName() + " is: " + this.getLocalTime());
		System.out.println("Here are the stats for " + this.location + ".\n");
		System.out.println("Current temperature: " + this.getCurrentTemp());
		System.out.println("It feels like: " + this.getFeelsLikeTemp());
		System.out.println("Min temperature: " + this.getMinTemp());
		System.out.println("Max temperature: " + this.getMaxTemp() + "\n");
		System.out.println("Humidity of: " + this.getHumidity());
		System.out.println("Wind speeds of: " + this.getWindConditions());
		System.out.println("Current weather conditions: " + this.getWeatherStatus() + "\n");
		System.out.println("Sunrise at: " + this.getSunriseTime());
		System.out.println("Sunset at: " + this.getSunsetTime());
		System.out.println("=========================================");
	}
	
	// get rid of main method once mvc architecture is setup properly
	public static void main(String[] args) {
		
		WeatherSystem ws1 = new WeatherSystem("Oakville, CA");
		WeatherSystem ws2 = new WeatherSystem("San Francisco, US");
		
		ws1.printWeatherData();
		System.out.println();
		ws2.printWeatherData();
		System.out.println();
		ws2.setLocation("Skopje, MK");
		ws2.printWeatherData();
		ws2.changeMeasurementSystem("imperial");
		ws2.printWeatherData();
		ws2.changeMeasurementSystem("METRIC");
		ws2.printWeatherData();
		
	}
}
