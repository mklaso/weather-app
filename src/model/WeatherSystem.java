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
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import com.google.gson.reflect.*;
import com.google.gson.Gson;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeatherSystem extends Observable {
	
	private static final String API_KEY = "3c5aee26050f4b4e3b83118e62088949";
	private String location;
	private String urlString;
	private String result = "";
	private String tempUnit = "°C"; //default
	private String measurementType = "&units=metric"; //default
	private Map<String, Object> responseMap;
	private Map<String, Object> mainMap;
	private Map<String, Object> sysMap;
//	public HashMap<String, ArrayList<String>> forecastData
//			= new HashMap<String, ArrayList<String>>();
	private ArrayList<ArrayList<String>> forecastData = new ArrayList<ArrayList<String>>();
	public static final String WEATHER_MODE = "weather";
	public static final String FORECAST_MODE = "forecast";
	public static final String METRIC_MODE = "metric";
	public static final String IMPERIAL_MODE = "imperial";
	public boolean valid = false;
	
	//add documentation later
	@SuppressWarnings("unchecked")
	public WeatherSystem(String location) {
		this.location = location;
		this.setURLString(WEATHER_MODE);
		this.obtainWeatherData();
	}
	
	public void setURLString(String type) {
		if (type.equals(WEATHER_MODE)) {
			this.urlString = "http://api.openweathermap.org/data/2.5/weather?q=" 
				+ this.location + "&APPID=" + API_KEY + measurementType;
		} else if (type.equals(FORECAST_MODE)) {
			this.urlString = "http://api.openweathermap.org/data/2.5/forecast?q=" 
				+ this.location + "&APPID=" + API_KEY + measurementType;
		}
	}
	
	public void changeMeasurementSystem(String unitType) {
		if (unitType.toLowerCase().equals(METRIC_MODE)) {
			this.measurementType = "&units=metric";
			this.tempUnit = "°C";
		} else if (unitType.toLowerCase().equals(IMPERIAL_MODE)) {
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
	
	@SuppressWarnings("unchecked")
	public void get5DayForecast() {
		
		this.result = "";
		setURLString(FORECAST_MODE);
		
		this.obtainWeatherData();
		this.valid = true;
		
		Pattern midDay = Pattern.compile("^20(19|20|21|22)-(0[1-9]|1[0-2])-"
				+ "(0[1-9]|1[0-9]|2[0-9]|3[0-1]) 12:00:00$");
		Matcher m;
		
		//holds all forecast data over 5 day/3hr periods
		if (responseMap != null) {
		ArrayList<Map<String, Object>> forecast = (ArrayList<Map<String, Object>>)responseMap.get("list");
		//to get timezone
		Map<String, Object> city = (Map<String, Object>)responseMap.get("city");
		
		System.out.println("Here is the 5 day forecast for " + this.location + ".\n");
		for(int i = 0; i < forecast.size(); i++) {
			Map<String, Object> currentObject = forecast.get(i);
			String checkThis = (String) currentObject.get("dt_txt");
			m = midDay.matcher(checkThis);
			
			if (m.matches()) {
				Map<String, Object> main = (Map<String, Object>)currentObject.get("main");
				String temp = getCurrentTemp(main);
				String weatherCondition = getWeatherStatus(currentObject);
				
				//getting and setting up the days according to the parsed unix timestamp
				int offset = (int)getTimezoneOffset(city);
				long l = (long)((double)currentObject.get("dt"));
				long time = l + offset;
				
				//day of the week for the specific timestamp
				String day = getDayOfTheWeek(time);
				
				//setting up weather data in an arraylist for each day
				ArrayList<String> conditions = new ArrayList<String>();
				conditions.add(day);
				conditions.add(temp);
				conditions.add(weatherCondition);
				
				this.forecastData.add(conditions);
			}
		}
		//back to default settings
		this.resetWeatherData();
		} else {
			System.out.println(this.location + " is an invalid location. 5 Day Forecast is unavailable.");
		}
	}
	
	//testing purposes
	public void print5DayForecast() {
		for(int i = 0; i < this.forecastData.size(); i++) {
			String day = this.forecastData.get(i).get(0);
			String temp = this.forecastData.get(i).get(1);
			String weatherCondition = this.forecastData.get(i).get(2);
			
			System.out.println("Day of the Week: " + day);
			System.out.println("Temperature for the day: " + temp);
			System.out.println("Weather conditions for the day: " + weatherCondition);
			System.out.println("=============================================");
		}
	}
	
	public String getDayOfTheWeek(long unixTimestamp) {
		double timeValue = (Math.floor(unixTimestamp / 86400) + 4) % 7;
		
		if (timeValue == 1.0) {
			return "Monday";
		} else if (timeValue == 2.0) {
			return "Tuesday";
		} else if (timeValue == 3.0) {
			return "Wednesday";
		} else if (timeValue == 4.0) {
			return "Thursday";
		} else if (timeValue == 5.0) {
			return "Friday";
		} else if (timeValue == 6.0) {
			return "Saturday";
		} else if (timeValue == 0.0) {
			return "Sunday";
		}
		return "Unknown";
	}
	
	@SuppressWarnings("unchecked")
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
			
			this.responseMap = jsonToMap(this.result);
			
			if (this.isValidSearch()) {
				this.mainMap = (Map<String, Object>)responseMap.get("main");
				this.sysMap = (Map<String, Object>)responseMap.get("sys");
				this.valid = true;
				//update view
				this.notifyObservers();
			} else {
				this.valid = false;
			}
			
		} catch(IOException e) {
			this.valid = false;
			//System.out.println(e.getMessage());
		}
	}
	
	public void addToResult(String s) {
		this.result += s;
	}
	
	@SuppressWarnings("unchecked")
	public void resetWeatherData() {
		this.result = "";
		this.setURLString(WEATHER_MODE);
		this.obtainWeatherData();
	}
	
	public void setLocation(String s) {
		this.location = s;
		this.measurementType = "&units=metric";
		this.tempUnit = "°C";
		this.resetWeatherData();
	}
	
	public String getTempType(Map<String, Object> map, String tempType) {
		if (this.valid) {
			return String.valueOf(map.get(tempType)) + this.tempUnit;
		}
		return "N/A";
	}
	
	public String getCityName() {
		if (this.valid) {
			return (String) this.responseMap.get("name");
		}
		return "N/A";
	}
	
	public String getCurrentTemp(Map<String, Object> map) {
		return this.getTempType(map, "temp");
	}
	
	public String getFeelsLikeTemp(Map<String, Object> map) {
		return this.getTempType(map, "feels_like");
	}
	
	public String getMinTemp(Map<String, Object> map) {
		return this.getTempType(map, "temp_min");
	}
	
	public String getMaxTemp(Map<String, Object> map) {
		return this.getTempType(map, "temp_max");
	}
	
	public String getHumidity() {
		if (this.valid) {
			return String.valueOf(this.mainMap.get("humidity")) + "%";
		}
		return "N/A";
	}
	
	@SuppressWarnings("unchecked")
	public String getWindConditions() {
		
		if (this.valid) {
			Map<String, Object> wind = (Map<String, Object>)responseMap.get("wind");
			return String.valueOf(wind.get("speed")) + "m/s";
		}
		return "N/A";
	}
	
	@SuppressWarnings("unchecked")
	public String getWeatherStatus(Map<String, Object> map) {
		
		if (this.valid) {
			ArrayList<Map<String, Object>> weather = (ArrayList<Map<String, Object>>)map.get("weather");
			Map<String, Object> weatherStatus = (Map<String, Object>)weather.get(0);
			return (String) weatherStatus.get("description");
		}
		return "N/A";
	}
	
	public String getCode(Map<String, Object> map) {
		if (map != null) {
			String code = String.valueOf(map.get("cod"));
			return code;
		}
		return "404";
	}
	
	public boolean isValidSearch() {
		if (this.getCode(this.responseMap).equals("200.0")) {
			return true;
		} else {
			return false;
		}
	}
	
	public double getTimezoneOffset(Map<String, Object> map) {
		if (this.valid) { 
			double timeOffset = (double)(map.get("timezone"));
			return timeOffset;
		}
		return 0;
	}
	
	public String convertTime(String s) {
		if (this.valid) { 
			int offset = (int)(this.getTimezoneOffset(this.responseMap));
			
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
		return "N/A";
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
	
	//testing purposes
	public void printWeatherData() {
		System.out.println("=========================================");
		System.out.println("Local time in " + this.getCityName() + " is: " + this.getLocalTime());
		System.out.println("Here are the stats for " + this.location + ".\n");
		System.out.println("Current temperature: " + this.getCurrentTemp(this.mainMap));
		System.out.println("It feels like: " + this.getFeelsLikeTemp(this.mainMap));
		System.out.println("Min temperature: " + this.getMinTemp(this.mainMap));
		System.out.println("Max temperature: " + this.getMaxTemp(this.mainMap) + "\n");
		System.out.println("Humidity of: " + this.getHumidity());
		System.out.println("Wind speeds of: " + this.getWindConditions());
		System.out.println("Current weather conditions: " + this.getWeatherStatus(this.responseMap) + "\n");
		System.out.println("Sunrise at: " + this.getSunriseTime());
		System.out.println("Sunset at: " + this.getSunsetTime());
		System.out.println("Code for this API request: " + this.getCode(this.responseMap) + "\n");
		System.out.println("Is it a valid search: " + this.isValidSearch());
		System.out.println("=========================================");
	}
	
	public Map<String, Object> getTempMap() {
		return this.mainMap;
	}
	
	public Map<String, Object> getAllDataMap() {
		return this.responseMap;
	}
	
	public Map<String, Object> getSysMap() {
		return this.sysMap;
	}
	
	public ArrayList<ArrayList<String>> getForecastData() {
		return this.forecastData;
	}
	
	// get rid of main method once view/controller architecture is setup properly
	public static void main(String[] args) {

		WeatherSystem ws1 = new WeatherSystem("Oakville, CA");
		WeatherSystem ws2 = new WeatherSystem("San Francisco, US");
		DayForecastSystem dfs = new DayForecastSystem(ws1);
		
		dfs.setForecastDay(1);
		dfs.printForecastDay();
		
		dfs.setForecastDay(2);
		dfs.printForecastDay();
		
		dfs.setForecastDay(3);
		dfs.printForecastDay();
		
		dfs.setForecastDay(4);
		dfs.printForecastDay();
		
		dfs.setForecastDay(5);
		dfs.printForecastDay();
		
		dfs.setWeatherSystem(ws2);
		
		dfs.setForecastDay(1);
		dfs.printForecastDay();
//		ws1.print5DayForecast();
//		
//		ws2.get5DayForecast();
//		ws2.print5DayForecast();
//		
//		ws1.setLocation("Oakville, CA");
//		ws1.get5DayForecast();
//		ws1.print5DayForecast();

		//System.out.println(ws1.getCurrentTemp(ws1.mainMap));
		//ws1.printWeatherData();
//		ws2.printWeatherData();
//
//		ws2.setLocation("Skopje, MK");
//		ws2.printWeatherData();
//		ws2.changeMeasurementSystem(IMPERIAL_MODE);
//		ws2.printWeatherData();
//		ws2.changeMeasurementSystem(METRIC_MODE);
//		ws2.printWeatherData();
//		
//		ws2.get5DayForecast();

	}
}
