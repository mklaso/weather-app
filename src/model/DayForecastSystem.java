package model;

import java.util.ArrayList;

public class DayForecastSystem {
	
	//in the view, make a method thats called something like getForecastDay()
		//and have it return a vbox, in this vbox set everything up, i.e
		//call on DayForecastSystem.getDay(),getWeather(),getTemp(), set them to labels or something
		//and include an icon for the weather condition, add all of these to the vbox and have it return
		//the completed vbox that contains the forecast for that day.
		//just need to call on this method 5 times, with each diff day, i.e getForecastDay(1), (2), ...
		//then just add each vbox to the view/pane
	
	private WeatherSystem ws;
	private ArrayList<ArrayList<String>> forecastData = new ArrayList<ArrayList<String>>();
	private String day;
	private String temp;
	private String weather;
	
	public DayForecastSystem(WeatherSystem ws) {
		this.ws = ws;
		this.ws.get5DayForecast();
		this.forecastData = ws.getForecastData();
	}
	
	public void setWeatherSystem(WeatherSystem newWs) {
		this.ws = newWs;
		this.ws.get5DayForecast();
		this.forecastData = ws.getForecastData();
	}
	
	public void setForecastDay(int number) {
		int dayNumber = 0;
		
		if (number == 1) {
			dayNumber = 0;
		} else if (number == 2) {
			dayNumber = 1;
		} else if (number == 3) {
			dayNumber = 2;
		} else if (number == 4) {
			dayNumber = 3;
		} else if (number == 5) {
			dayNumber = 4;
		}
		
		if (this.forecastData != null) {
			setDay(this.forecastData.get(dayNumber).get(0));
			setTemp(this.forecastData.get(dayNumber).get(1));
			setWeather(this.forecastData.get(dayNumber).get(2));
		} else {
			System.out.print("Forecast data list is empty.");
		}
	}
	
	public void printForecastDay() {
		System.out.println("=============================================");
		System.out.println("Day of the Week: " + this.day);
		System.out.println("Temperature for the day: " + this.temp);
		System.out.println("Weather conditions for the day: " + this.weather);
		System.out.println("=============================================");
	}
	
	public void setDay(String day) {
		this.day = day;
	}
	
	public void setTemp(String temp) {
		this.temp = temp;
	}
	
	public void setWeather(String weather) {
		this.weather = weather;
	}
	
	public String getDay() {
		return this.day;
	}
	
	public String getTemp() {
		return this.temp;
	}
	
	public String getWeather() {
		return this.weather;
	}
}
