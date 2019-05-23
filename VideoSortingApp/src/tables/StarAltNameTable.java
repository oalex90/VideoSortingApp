package tables;

import java.sql.Connection;
import java.sql.SQLException;

public class StarAltNameTable extends BaseTable {
	
	public static final String TABLE_NAME = "star_alt_name";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_STAR_ID = "starId";
	
	public StarAltNameTable(Connection connection) {
		super(connection);
		
		tableName = TABLE_NAME;
		String[] tableColumns = {COLUMN_NAME, COLUMN_STAR_ID};
		String[] tableColumnTypes = {"STRING", "INTEGER"};
		String[] tableForeignKeys = {tableColumns[1]};
		String[] tableForeignKeyReferences = {StarTable.TABLE_NAME + "(" + StarTable.ID + ")"};
		
		
		columns = tableColumns;
		columnTypes = tableColumnTypes;
		foreignKeys = tableForeignKeys;
		foreignKeyReferences = tableForeignKeyReferences;
		
		String createSql = genCreateSql();
		try {
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + createSql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int add(String name, int starId) {
		String[] values = {name, Integer.toString(starId)};
		return super.add(values);
	}
	
	public void delete(String name) {
		String[] cols = {columns[0]};
		String[] values = {name};
		super.delete(cols, values);
	}
	
	
}
