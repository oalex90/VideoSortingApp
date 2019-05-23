package tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import data_objects.Settings;

public class SettingsTable {
	
	public static final String TABLE_NAME = "settings";
	public static final String COLUMN_DIRECTORY = "directory";
	
	Connection connection;
	Statement statement;
	String tableName = TABLE_NAME;
	String[] columns = {COLUMN_DIRECTORY};
	String[] columnTypes = {"STRING"};
	
	public SettingsTable (Connection connection) {
		this.connection = connection;
		try {
			statement = this.connection.createStatement();
			statement.setQueryTimeout(30);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String genCreateSql() {
		String createSql = null;
		String keys = columns[0] + " " + columnTypes[0];
		for(int i=1; i<columns.length; i++) {
			keys += ", " + columns[i] + " " + columnTypes[i];
		}
		
		createSql = tableName + " (" + keys + ")";
		return createSql;
	}
	
	public void createTable() {
		String createSql = genCreateSql();
		try {
			statement.executeUpdate("DROP TABLE IF EXISTS " + tableName);
			statement.executeUpdate("CREATE TABLE " + createSql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void add(String[] values) {
		String addPartial = "'" + values[0] + "'";
		for(int i =1; i<values.length; i++) {
			if(columnTypes[i].contains("STRING")) {
				addPartial += ", '" + values[i] + "'";
			} else if(columnTypes[i].contains("INTEGER")) {
				addPartial += ", " + values[i];
			}
		}
		try {
			String sql = "INSERT INTO "+ tableName +" values("+ addPartial +")";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet getResult() {
		ResultSet resultSet = null;
		try {
			String select = "SELECT * FROM " + tableName;
			resultSet = statement.executeQuery(select);
			resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}
	
	public Settings getSettings() {
		Settings settings = null;
		ResultSet result = getResult();
		try {
			String directory = result.getString(COLUMN_DIRECTORY);
			settings = new Settings(directory);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return settings;
	}
	
	public void update(Settings settings) {
		try {
			statement.executeUpdate("DELETE FROM " + tableName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String[] values = {settings.getDirectory()};
		add(values);
	}
	
	public void display() {
		try {
			ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);
			System.out.println(tableName + " table...");
			while(resultSet.next()) {
				String printPartial = columns[0] + "= " + resultSet.getString(columns[0]);
				for(int i=1; i<columns.length; i++) {
					if(columnTypes[i].contains("STRING")) {
						printPartial += ", " + columns[i] + "= " + resultSet.getString(columns[i]);
					}else if(columnTypes[i].contains("INTEGER")) {
						printPartial += ", " + columns[i] + "= " + resultSet.getInt(columns[i]);
					}
				}
				System.out.println(printPartial);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
