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

public class WeatherSystem {
	
	//darksky api = "1cd7dd4a37265e80ff955fc5021019d5";
	private static final String API_KEY = "3c5aee26050f4b4e3b83118e62088949";
	private String location;
	private String urlString;
	private String result = "";
	private String tempUnit = "°C"; //default
	private String measurementType = "&units=metric"; //default
	private Map<String, Object> responseMap;
	private Map<String, Object> mainMap;
	private Map<String, Object> sysMap;
	public HashMap<String, ArrayList<String>> forecastData
			= new HashMap<String, ArrayList<String>>();


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
	
	@SuppressWarnings("unchecked")
	public void get5DayForecast() {
		
		this.result = "";
		this.urlString = "http://api.openweathermap.org/data/2.5/forecast?q=Oakville,CA&APPID=3c5aee26050f4b4e3b83118e62088949&units=metric";
		
		this.obtainWeatherData();
			
		this.responseMap = jsonToMap(this.result);
		
		Pattern midDay = Pattern.compile("^20(19|20|21|22)-(0[1-9]|1[0-2])-"
				+ "(0[1-9]|1[0-9]|2[0-9]|3[0-1]) 12:00:00$");
		Matcher m;
		
		//holds all forecast data over 5 day/3hr periods
		ArrayList<Map<String, Object>> forecastData = (ArrayList<Map<String, Object>>)responseMap.get("list");
		//to get timezone
		Map<String, Object> city = (Map<String, Object>)responseMap.get("city");
		

		for(int i = 0; i < forecastData.size(); i++) {
			Map<String, Object> currentObject = forecastData.get(i);
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
				conditions.add(temp);
				conditions.add(weatherCondition);
				
				//key: temp of that day, value: weather data of the day
				this.forecastData.put(day, conditions);
				
				temp = this.forecastData.get(day).get(0);
				weatherCondition = this.forecastData.get(day).get(1);
				
				//System.out.println(this.forecastData.)
				System.out.println(checkThis);
				System.out.println("Day of the Week: " + day);
				System.out.println("Temperature for the day: " + temp);
				System.out.println("Weather conditions for the day: " + weatherCondition);
				System.out.println("=============================================");
			}
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
		return "invalid";
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
	
	public String getTempType(Map<String, Object> map, String tempType) {
		return String.valueOf(map.get(tempType)) + this.tempUnit;
	}
	
	public String getCityName() {
		return (String) this.responseMap.get("name");
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
		return String.valueOf(this.mainMap.get("humidity")) + "%";
	}
	
	public String getWindConditions() {
		@SuppressWarnings("unchecked")
		Map<String, Object> wind = (Map<String, Object>)responseMap.get("wind");
		return String.valueOf(wind.get("speed")) + "m/s";
	}
	
	public String getWeatherStatus(Map<String, Object> map) {
		@SuppressWarnings("unchecked")
		ArrayList<Map<String, Object>> weather = (ArrayList<Map<String, Object>>)map.get("weather");
		Map<String, Object> weatherStatus = (Map<String, Object>)weather.get(0);
		return (String) weatherStatus.get("description");	
	}
	
	public double getTimezoneOffset(Map<String, Object> map) {
		double timeOffset = (double)(map.get("timezone"));
		return timeOffset;
	}
	
	public String convertTime(String s) {
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
		System.out.println("Current temperature: " + this.getCurrentTemp(this.mainMap));
		System.out.println("It feels like: " + this.getFeelsLikeTemp(this.mainMap));
		System.out.println("Min temperature: " + this.getMinTemp(this.mainMap));
		System.out.println("Max temperature: " + this.getMaxTemp(this.mainMap) + "\n");
		System.out.println("Humidity of: " + this.getHumidity());
		System.out.println("Wind speeds of: " + this.getWindConditions());
		System.out.println("Current weather conditions: " + this.getWeatherStatus(this.responseMap) + "\n");
		System.out.println("Sunrise at: " + this.getSunriseTime());
		System.out.println("Sunset at: " + this.getSunsetTime());
		System.out.println("=========================================");
	}
	
	// get rid of main method once mvc architecture is setup properly
	public static void main(String[] args) {

		WeatherSystem ws1 = new WeatherSystem("Oakville, CA");
		WeatherSystem ws2 = new WeatherSystem("San Francisco, US");
		
		ws2.get5DayForecast();
		
//		ws1.printWeatherData();
//		System.out.println();
//		ws2.printWeatherData();
//		System.out.println();
//		ws2.setLocation("Skopje, MK");
//		ws2.printWeatherData();
//		ws2.changeMeasurementSystem("imperial");
//		ws2.printWeatherData();
//		ws2.changeMeasurementSystem("METRIC");
//		ws2.printWeatherData();
		
		
		
	}
}
