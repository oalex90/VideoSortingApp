package tables;

import java.sql.Connection;
import java.sql.SQLException;

public class VideoStarTable extends BaseTable {
	
	public static final String TABLE_NAME = "video_star";
	public static final String COLUMN_VIDEO_ID = "videoId";
	public static final String COLUMN_STAR_ID = "starId";
	
	public VideoStarTable(Connection connection) {
		super(connection);
		
		tableName = TABLE_NAME;
		String[] tableColumns = {COLUMN_VIDEO_ID, COLUMN_STAR_ID};
		String[] tableColumnTypes = {"INTEGER", "INTEGER"};
		String[] tableForeignKeys = {tableColumns[0], tableColumns[1]};
		String[] tableForeignKeyReferences = {VideoTable.TABLE_NAME + "(" + VideoTable.ID + ")", StarTable.TABLE_NAME + "(" + StarTable.ID + ")"};
		
		
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
	
	public int add(int videoId, int starId) {
		String[] values = {Integer.toString(videoId), Integer.toString(starId)};
		return super.add(values);
	}
	
	public void delete(int videoId, int starId) {
		String[] values = {Integer.toString(videoId), Integer.toString(starId)};
		super.delete(columns, values);
	}
}
