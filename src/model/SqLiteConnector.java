package model;
import java.sql.*;

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
