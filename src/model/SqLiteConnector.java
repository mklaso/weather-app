package model;
import java.sql.*;
import java.util.ArrayList;

import model.AddressObtainer;

public class SqLiteConnector {
	public Connection con = null;
	
	//connects to the database
	public Connection connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:InfoDb.db");
			return con;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//checks if database connection is active
	public boolean isDbConnected() {
		try {
			return !con.isClosed();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	//testing purposes
	public void connectionStatus() {
		if (this.isDbConnected()) {
			System.out.println("Database is connected.");
		} else {
			System.out.println("Database is not connected.");
		}
	}
	
	//checks if user is registered
	public boolean isRegistered(String macAddress) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select * from table_user_info where MACAddress = ?";
		try {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, macAddress);
			
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			} else {
				return false;
			}
			
		} catch (Exception e) {
			System.out.println("error");
			return false;
			
		} finally {
			preparedStatement.close();
			resultSet.close();
		}
	}
	
	//if user is not recognized within database
	//will set favouritelocation on buttonclick from GUI later
	public void registerUser(String macAddress) throws SQLException {
		PreparedStatement preparedStatement = null;
		String insert = "INSERT INTO table_user_info(MACAddress) VALUES(?)";
		try {
			preparedStatement = con.prepareStatement(insert);
			
			preparedStatement.setString(1, macAddress);
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			preparedStatement.close();
		}
	}
	
	public void setDbLocationsData(ArrayList<String> locationsList) { 
		PreparedStatement ps = null;
		ResultSet resultSet;
		String query = "select * from table_user_info";
			
		try {
			
			ps = con.prepareStatement(query);
			resultSet = ps.executeQuery();
			
			if (resultSet.next()) {
				for (int i = 2; i <= 11; i++) {
					if (resultSet.getString(i) != null) {
						locationsList.add(resultSet.getString(i));
					}
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	
	}
	
	public void registerLocation(String location) throws SQLException {
		PreparedStatement preparedStatement = null;
		PreparedStatement test = null;
		ResultSet resultSet;
		String test1 = "select * from table_user_info";
		int locationCounter = 2;
			
		try {
			
			test = con.prepareStatement(test1);
			resultSet = test.executeQuery();
			
			//checking for empty spot in database to add a location
			int i = 0;
			
			if (resultSet.next()) {
				for (i = locationCounter; i <= 11; i++) {
					if (resultSet.getString(i) != null) {
						//System.out.println("location" + (i-1) + ": " + resultSet.getString(i) + "\n");
					} else {
						locationCounter = i-1;
						//System.out.println("location" + (i-1) + ": not set currently.\n");
						break;
					}
					
					if (resultSet.getString(i) != null && i == 11) {
					       locationCounter = 12; //12 indicates no empty location found
					}
				}
			}
			
			//update location value in database if space is available
			if (locationCounter != 12) {
				String sql = "UPDATE table_user_info SET Location" + locationCounter + " = ?";
				preparedStatement = con.prepareStatement(sql);
				preparedStatement.setString(1, location);
				preparedStatement.executeUpdate();
			} else {
				System.out.println("Max number of locations. Please delete one to add another.");
			}

			
			// create a list/maybe hashmap might be best here actually
			// loop through all the values in the resultSet, and if they're not null -> add them to the
			// hashmap/list
			// then later, with the delete functionality, make it so that when that item from the list is 
			// deleted (compare the getText() from the X box clicked on to the hashmap/list location), then
			// also delete that value from the database (just set the location to null)
			
			// then, based off the list/hashmap values, loop through them and on launch of the application,
			// add these (hboxes/locations/delete buttons) to the locations list/box. (so that they're loaded
			// from the database on launch).
			
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (locationCounter != 12) {
				preparedStatement.close();
			}
		}
	}
	
	public void removeLocation(ArrayList<String> locationsList, String locationToDelete) {
		PreparedStatement preparedStatement = null;
		boolean exists = false;
		
		int i;
		for (i = 0; i < locationsList.size(); i++) {
			if (locationsList.get(i).toLowerCase().equals(locationToDelete.toLowerCase())) {
				exists = true;
				break;
			}
		}
		i++;
		System.out.println("location" + i + " in the database");
		
		if (exists) {
			String sql = "UPDATE table_user_info SET Location" + i + " = NULL";
			try {
				preparedStatement = con.prepareStatement(sql);
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("Error - Trying to remove a location that does not exist.");
		}
	}
	
	//logs in if the user exists
	public void login() {
		AddressObtainer aoObtainer = new AddressObtainer();
		try {
			if (isRegistered(aoObtainer.getMACAddress())) {
				System.out.println(aoObtainer.getMACAddress() + " is a registered user.");
			} else {
				System.out.println(aoObtainer.getMACAddress() + " is not a registered user.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
