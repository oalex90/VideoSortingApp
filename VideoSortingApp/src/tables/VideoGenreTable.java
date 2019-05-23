package tables;

import java.sql.Connection;
import java.sql.SQLException;

public class VideoGenreTable extends BaseTable {
	
	public static final String TABLE_NAME = "video_genre";
	public static final String COLUMN_VIDEO_ID = "videoId";
	public static final String COLUMN_GENRE_ID = "genreId";
	
	public VideoGenreTable(Connection connection) {
		super(connection);
		
		tableName = TABLE_NAME;
		String[] tableColumns = {COLUMN_VIDEO_ID, COLUMN_GENRE_ID};
		String[] tableColumnTypes = {"INTEGER", "INTEGER"};
		String[] tableForeignKeys = {tableColumns[0], tableColumns[1]};
		String[] tableForeignKeyReferences = {VideoTable.TABLE_NAME + "(" + VideoTable.ID + ")", GenreTable.TABLE_NAME + "(" + GenreTable.ID + ")"};
		
		
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
	
	public int add(int videoId, int genreId) {
		String[] values = {Integer.toString(videoId), Integer.toString(genreId)};
		return super.add(values);
	}
	
	public void delete(int videoId, int genreId) {
		String[] values = {Integer.toString(videoId), Integer.toString(genreId)};
		super.delete(columns, values);
	}
	
	
}

