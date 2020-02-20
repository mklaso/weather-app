# weather-app #

This weather app was developed with Java 11 and the GUI development was done with JavaFX 8.

## Features ##
- users can search for a location of their choice (format is [city, country code] e.g: Paris, FR)
- upon a successful search, two results are shown:
  1. current weather data for the requested location (more in detail: current, max, min, feels like temperature, humidity, wind speed, weather status, sunrise, sunset, etc.)
  2. the 5 day forecast for the requested location (less details: day, temperature, weather status and a weather icon)
 - users can switch between metric and imperial for temperatures/wind speed
- users can choose to save locations for easier/quicker lookup at a later time (up to 10), these locations can also be deleted at any time
- users will have their previous settings saved, i.e their "saved locations" will remain even if they close the app, users can also set a favourite location, so that when the application is run, it is the base location displayed.

## Configuration / Installation ##
There are two ways to use the application.

### Approach 1 ###
- download the executable weather application. (weatherapp.exe)
- link here: //to be added when done the entire project

### Approach 2 ###
- clone the repo
- install Google's GSON .jar file (in repo)
- install SqLite .jar (in repo)
- install JavaFX 8 (.jars included)
- install Java SDK 11

Note: the GSON and SqLite .jars are included in the repo, you just need to configure the build path and add them.

Once all of the above are installed, using Eclipse or IntelliJ, right click on the cloned project repo and select the following in order:
Build Path --> Configure Build Path --> Libraries --> "Add External JARs..", at this point you'll need to navigate to wherever your JavaFX 8 folder is
and add all the jars. The same applies with Google's GSON .jar and the SqLite .jar.

Once the build path is configured, we still need to setup the run configuration for the project. 
Go to the "WeatherApplication" class underneath the application package. From here right click --> Run As --> Run Configurations.
1. Name the configuration whatever you prefer.
2. Under the "Main" tab, for the "Project" write: weather
3. Under the "Main" tab, for the "Main class" write: application.WeatherApplication
4. Click on the "Arguments" tab and in the "VM arguments" section add the following:
[--module-path C:\PATH-TO-JAVAFX-LOCATION --add-modules javafx.controls,javafx.fxml]
the path will depend on where you installed the JavaFX folder on your computer,
for instance mine looks like this:
--module-path  C:\openjfx-11.0.2_windows-x64_bin-sdk\javafx-sdk-11.0.2\lib --add-modules javafx.controls,javafx.fxml
