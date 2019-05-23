package tables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import data_objects.Star;

public class StarTable extends BaseTable {
	
	public static final String TABLE_NAME = "star";
	public static final String COLUMN_NAME = "name";
	
	public StarTable(Connection connection) {
		super(connection);
		
		tableName = TABLE_NAME;
		String[] tableColumns = {COLUMN_NAME};
		String[] tableColumnTypes = {"STRING"};
		
		columns = tableColumns;
		columnTypes = tableColumnTypes;
		
		String createSql = genCreateSql();
		try {
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + createSql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getId(String name) {
		String[] selectColumns = {COLUMN_NAME};
		String[] values = {name};
		return getIds(selectColumns, values)[0];
	}
	
	public Star getStar(int id) {
		Star star =  null;
		String[] columns = {ID};
		String[] values = {Integer.toString(id)};
		ResultSet results = super.getResults(columns, values);
		try {
			if(results.next()) {
				String name = results.getString(COLUMN_NAME);
				String[] altNames = getAltNames(id);
				star = new Star(id, name, altNames);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return star;
	}
	
	public int add(String name) {
		String[] values = {name};
		return super.add(values);
	}
	
	public int add(Star star) {
		int starId = add(star.getName());
		
		if(star.getAltNames() != null) {
			StarAltNameTable starAltNameTable = new StarAltNameTable(connection);
			String[] starAltNames = star.getAltNames();
			for(String altName : starAltNames) {
				starAltNameTable.add(altName, starId);
			}
		}
		return starId;
	}
	
	public void delete(int id) {
		super.delete(id);
		
		StarAltNameTable starAltNameTable = new StarAltNameTable(connection);
		starAltNameTable.delete(StarAltNameTable.COLUMN_STAR_ID, Integer.toString(id));
		
	}
	
	public String[] getAltNames(int id) {
		String[] altNames = null;
		try {
			List<String> starAltNameList = new ArrayList<String>();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM "+ StarAltNameTable.TABLE_NAME +" WHERE "+ StarAltNameTable.COLUMN_STAR_ID +" = '" + id + "'");
			while(resultSet.next()) {
				starAltNameList.add(resultSet.getString(StarAltNameTable.COLUMN_NAME));
			}
			altNames = new String[starAltNameList.size()];
			altNames = starAltNameList.toArray(altNames);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return altNames;
	}
	
	public Star[] getAllStars() {
		Star[] stars;
		List<Star> starList = new ArrayList<Star>();
		try {
			ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
			while(resultSet.next()) {
				int id = resultSet.getInt(ID);
				String name = resultSet.getString(COLUMN_NAME);

				Star star = new Star(id, name, null);
				starList.add(star);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		stars = new Star[starList.size()];
		for(int i=0; i<starList.size(); i++) {
			Star curStar = starList.get(i);
			int curId = curStar.getId();
			curStar.setAltNames(getAltNames(curId));
			stars[i] = curStar;
		}
		
		return stars;
	}
}
