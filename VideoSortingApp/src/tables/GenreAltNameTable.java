package tables;

import java.sql.Connection;
import java.sql.SQLException;

public class GenreAltNameTable extends BaseTable {
	
	public static final String TABLE_NAME = "genre_alt_name";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_GENRE_ID = "genreId";
	
	public GenreAltNameTable(Connection connection) {
		super(connection);
		
		tableName = TABLE_NAME;
		String[] tableColumns = {COLUMN_NAME, COLUMN_GENRE_ID};
		String[] tableColumnTypes = {"STRING", "INTEGER"};
		String[] tableForeignKeys = {tableColumns[1]};
		String[] tableForeignKeyReferences = {GenreTable.TABLE_NAME + "(" + GenreTable.ID + ")"};
		
		
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
	
	public int add(String name, int genreId) {
		String[] values = {name, Integer.toString(genreId)};
		return super.add(values);
	}
	
	public void delete(String name) {
		String[] cols = {columns[0]};
		String[] values = {name};
		super.delete(cols, values);
	}
	
}
