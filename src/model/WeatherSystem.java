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
	String urlString;
	private String tempUnit = "";
	private String data = "";
	private String result = "";
	private Map<String, Object> responseMap;
	private Map<String, Object> mainMap;
	private Map<String, Object> sysMap;

	@SuppressWarnings("unchecked")
	public WeatherSystem(String location) {
		setLocation(location);
		setUnitType("Metric"); //default
		this.obtainWeatherData();
		this.responseMap = jsonToMap(this.result);
		this.mainMap = (Map<String, Object>)responseMap.get("main");
		this.sysMap = (Map<String, Object>)responseMap.get("sys");
	}
	
	public void setUnitType(String unitType) {
		if (unitType.toLowerCase().equals("metric")) {
			this.urlString = "http://api.openweathermap.org/data/2.5/weather?q=" 
					+ this.location + "&APPID=" + API_KEY + "&units=metric";
			this.tempUnit = "°C";
		} else if (unitType.equals("imperial")) {
			this.urlString = "http://api.openweathermap.org/data/2.5/weather?q=" 
					+ this.location + "&APPID=" + API_KEY + "&units=imperial";
			this.tempUnit = "°F";
		}
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
	public void setLocation(String s) {
		this.location = s;
	}
	
	public void resetWeatherData() {
		this.data = "";
	}
	
	public void printWeatherData() {
		System.out.println(this.data);
	}
	
	public String getCityName() {
		return (String) this.responseMap.get("name");
	}
	
	public String getCurrentTemp() {
		return (String) this.mainMap.get("temp") + this.tempUnit;
	}
	
	public String getFeelsLikeTemp() {
		return (String) this.mainMap.get("feels_like") + this.tempUnit;
	}
	
	public String getMinTemp() {
		return (String) this.mainMap.get("temp_min") + this.tempUnit;
	}
	
	public String getMaxTemp() {
		return (String) this.mainMap.get("temp_max") + this.tempUnit;
	}
	
	public String getHumidity() {
		return (String) this.mainMap.get("humidity") + "%";
	}
	
	public String getWindConditions() {
		@SuppressWarnings("unchecked")
		Map<String, Object> wind = (Map<String, Object>)responseMap.get("wind");
		return (String) wind.get("speed") + "m/s";
	}
	
	public String getWeatherStatus() {
		@SuppressWarnings("unchecked")
		ArrayList<Map<String, Object>> weather = (ArrayList<Map<String, Object>>)responseMap.get("weather");
		Map<String, Object> weatherStatus = (Map<String, Object>)weather.get(0);
		return (String) weatherStatus.get("description");	
	}
	
	public int getTimezoneOffset() {
		int timeOffset = Integer.parseInt((String) this.responseMap.get("timezone"));
		return timeOffset;
	}
	
	public String convertTime(String s) {
		if (s.equals("sunrise") || s.equals("sunset")) {
			double d = (double) sysMap.get(s);
			long l = (long) d;
			long time = l + this.getTimezoneOffset();
			return formatTime(new Date(time * 1000));
		} else {
			long current = Instant.now().getEpochSecond();
			long time = current + this.getTimezoneOffset();
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
	
	// get rid of main method once mvc architecture is setup properly
	public static void main(String[] args) {
		
		WeatherSystem ws = new WeatherSystem("Oakville,CA");
		
		System.out.println("Local time in " + ws.getCityName() + "is: " + ws.getLocalTime());
		System.out.println("Here are the stats for " + ws.getCityName() + "\n");
		System.out.println("Current temperature: " + ws.getCurrentTemp());
		System.out.println("It feels like: " + ws.getFeelsLikeTemp());
		System.out.println("Min temperature: " + ws.getMinTemp());
		System.out.println("Max temperature: " + ws.getMaxTemp());
		System.out.println("The humidity is at: " + ws.getHumidity());
		System.out.println("Wind speeds of: " + ws.getWindConditions());
		System.out.println("Weather conditions currently: " + ws.getWeatherStatus());
		System.out.println("Sunrise at: " + ws.getSunriseTime());
		System.out.println("Sunset at: " + ws.getSunsetTime());
		
	}
}
