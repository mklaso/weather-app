# weather-app #

The weather app was developed with Java 11 and uses JavaFX 8 for the GUI. The application utilizes the OpenWeatherAPI to retrieve weather data for the requested locations and an SQLite database is used to store relevant user/location information.

## Features ##
- users can search for a location of their choice; search format is: **city, country code** (e.g: Paris, FR)
- upon a successful search, two results are shown:
  1. current weather data for the requested location (more in detail: current, max, min, feels like temperature, humidity, wind speed, weather status, sunrise, sunset, etc.)
  2. the 5 day forecast for the requested location (less details: day, temperature, weather status and a weather icon)
- can switch between metric and imperial measurements 
- users can choose to save locations for easier lookup (max 10)
- users can delete previously saved locations from their list
- users will have their previous settings stored; the list of saved locations will remain after closing the application
- users can set a favourite location, so that when the application is launched, their favourite location loads up immediately

## Installation and Configuration ##

### Initial Setup  ###
- clone the repo
- install JavaFX 8
- install Java SDK 11

**Note:** GSON and SQLite .jars are already included within the repo, but newer versions may need to be installed as time goes on.

### Configuring the Application ###

Once all of the above are installed, using Eclipse or IntelliJ, right click on the cloned project repo and select the following in order:
Build Path --> Configure Build Path --> Libraries --> "Add External JARs..", at this point you'll need to navigate to wherever your JavaFX 8 folder is and add all the jars. The same applies with Google's GSON .jar and the SqLite .jar.

Once the build path is configured, we still need to setup the run configuration for the project. 
Go to the "WeatherApplication" class underneath the application package. From here right click --> Run As --> Run Configurations.
1. Name the configuration whatever you prefer.
2. Under the "Main" tab, for the "Project" write: weather
3. Under the "Main" tab, for the "Main class" write: application.WeatherApplication
4. Click on the "Arguments" tab and in the "VM arguments" section add the following:
   
   --module-path **C:\PATH-TO-JAVAFX-LOCATION** --add-modules javafx.controls,javafx.fxml

The path will depend on where you installed the JavaFX folder on your computer; for reference mine looks like this:

--module-path **C:\openjfx-11.0.2_windows-x64_bin-sdk\javafx-sdk-11.0.2\lib**--add-modules javafx.controls,javafx.fxml
