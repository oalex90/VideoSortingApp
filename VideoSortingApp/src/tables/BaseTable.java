package tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BaseTable {
	
	public static final String ID = "id";
	
	Connection connection;
	Statement statement;
	String tableName;
	String[] columns;
	String[] columnTypes;
	String[] foreignKeys;
	String[] foreignKeyReferences;
	
	public BaseTable(Connection connection) {
		this.connection = connection;
		try {
			statement = this.connection.createStatement();
			statement.setQueryTimeout(30);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//TODO add overwrite input in case so table can be created if table doesn't exist and if it already exists.
	public String genCreateSql() {
		String createSql = null;
		String keys = "";
		for(int i=0; i<columns.length; i++) {
			keys += ", " + columns[i] + " " + columnTypes[i]; 
		}
		String fKeys = "";
		if(foreignKeys != null) {
			for (int i=0; i<foreignKeys.length; i++) {
				fKeys += ", FOREIGN KEY ("+ foreignKeys[i] + ") REFERENCES "+ foreignKeyReferences[i];
			}
		}
		createSql = tableName + " (" + ID + " INTEGER PRIMARY KEY" + keys  + fKeys + ")";
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
	
	public ResultSet getResults(String[] columns, String[] values) {
		ResultSet resultSet = null;
		try {
			String select = "SELECT * FROM "+  tableName +" WHERE "+ columns[0] +" = '"+ values[0] +"'";
			for(int i=1; i<columns.length; i++) {
				select += " AND "+ columns[i] +" = '"+ values[i] +"'";
			}
			resultSet = statement.executeQuery(select);
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		
		return resultSet;
		
	}
	
	public ResultSet getRowById(int value) {
		ResultSet resultSet = null;
		try {
			resultSet = statement.executeQuery("SELECT * FROM "+  tableName +" WHERE "+ ID +" = '"+ value +"'");
			resultSet.next();
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		return resultSet;
	}
	
	public int add(String[] values) {
		int addedId = -1;
		String addPartial = "";
		for(int i =0; i<values.length; i++) {
			if(columnTypes[i].contains("STRING")) {
				addPartial += ", '" + values[i] + "'";
			} else if(columnTypes[i].contains("INTEGER")) {
				addPartial += ", " + values[i];
			}
		}
		try {
			String sql = "INSERT INTO "+ tableName +" values(null"+ addPartial +")";
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.execute();
			
			ResultSet rs = ps.getGeneratedKeys();
			
			if (rs.next()) {
				addedId = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return addedId;
	}
	
	public int[] add(String[][] valueSets) {
		int[] addedIds = null;
		List<Integer> idList = new ArrayList<Integer>();
		try {
			for(int i =0; i<valueSets.length; i++) {
				String[] currentSet = valueSets[i];
				String addPartial = "";
				for(int j =0; j<currentSet.length; j++) {
					if(columnTypes[j].contains("STRING")) {
						addPartial += ", '" + currentSet[j] + "'";
					} else if(columnTypes[j].contains("INTEGER")) {
						addPartial += ", " + currentSet[j];
					}
				}
				String sql = "INSERT INTO "+ tableName +" values(null"+ addPartial +")";
				PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.execute();
				
				ResultSet rs = ps.getGeneratedKeys();
				
				if (rs.next()) {
					idList.add(rs.getInt(1));
				}
			}
			addedIds = new int[idList.size()];
			for (int i = 0; i<idList.size(); i++) {
				addedIds[i] = idList.get(i);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return addedIds;
	}
	
	public void delete(int id) {
		try {
			statement.executeUpdate("DELETE FROM "+ tableName +" WHERE "+ ID +"='" + id + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(String[] columns, String[] values) {
		try {
			String delete = "DELETE FROM "+ tableName +" WHERE "+ columns[0] +"='"+ values[0] +"'";
			for(int i=1; i<columns.length; i++) {
				delete += " AND "+ columns[i] +" = '"+ values[i] +"'";
			}
			statement.executeUpdate(delete);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(String column, String value) {
		try {
			String delete = "DELETE FROM "+ tableName +" WHERE "+ column +"='"+ value +"'";
			statement.executeUpdate(delete);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void display() {
		try {
			ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);
			System.out.println(tableName + " table...");
			while(resultSet.next()) {
				String printPartial = "";
				for(int i=0; i<columns.length; i++) {
					if(columnTypes[i].contains("STRING")) {
						printPartial += ", " + columns[i] + "= " + resultSet.getString(columns[i]);
					}else if(columnTypes[i].contains("INTEGER")) {
						printPartial += ", " + columns[i] + "= " + resultSet.getInt(columns[i]);
					}
				}
				System.out.println("id = " + resultSet.getInt(ID) + printPartial);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int[] getIds(String[] columns, String[] values) {
		int[] ids = null;
		String whereClause = "WHERE ";
		if(columns != null) {
			whereClause += columns[0] + "= '" + values[0] + "'";
			
			for(int i=1; i<columns.length; i++) {
				whereClause += " AND " + columns[i] + " = '" + values[i] + "'";
			}
		}
		
		try {
			ResultSet results = statement.executeQuery("SELECT * FROM " + tableName + " " + whereClause);
			List<Integer> idList = new ArrayList<Integer>();
			while(results.next()) {
				idList.add(results.getInt(ID));
			}
			ids = new int[idList.size()];
			for(int i=0; i<idList.size(); i++) {
				ids[i] = idList.get(i);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ids;
	}
}
