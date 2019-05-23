package tables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import data_objects.Video;

public class VideoTable extends BaseTable {
	
	public static final String TABLE_NAME = "video";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_PATH = "filepath";
	
	public VideoTable(Connection connection) {
		super(connection);
		
		tableName = TABLE_NAME;
		String[] tableColumns = {COLUMN_NAME, COLUMN_PATH};
		String[] tableColumnTypes = {"STRING", "STRING"};
		
		columns = tableColumns;
		columnTypes = tableColumnTypes;
		
		String createSql = genCreateSql();
		try {
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + createSql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getId(String name, String path) {
		String[] selectColumns = {COLUMN_NAME, COLUMN_PATH};
		String[] values = {name, path};
		return getIds(selectColumns, values)[0];
	}
	
	public Video getVideo(int id) {
		Video video =  null;
		String[] columns = {ID};
		String[] values = {Integer.toString(id)};
		ResultSet results = super.getResults(columns, values);
		try {
			if(results.next()) {
				String name = results.getString(COLUMN_NAME);
				String path = results.getString(COLUMN_PATH);
				int[] starIds = getStars(id);
				int[] genreIds = getGenres(id);
				video = new Video(id, name, path, starIds, genreIds);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return video;
	}
	
	public int add(String name, String filepath) {
		String[] values = {name, filepath};
		return super.add(values);
	}
	
	public int add(Video video) {
		int vidId = add(video.getName(), video.getFilepath());
		
		if (video.getStarIds() != null) {
			VideoStarTable videoStarTable = new VideoStarTable(connection);
			int[] vidStarIds = video.getStarIds();
			for(int starId : vidStarIds) {
				videoStarTable.add(vidId, starId);
			}
		}
		
		if (video.getGenreIds() != null) {
			VideoGenreTable videoGenreTable = new VideoGenreTable(connection);
			int[] vidGenreIds = video.getGenreIds();
			for(int genreId : vidGenreIds) {
				videoGenreTable.add(vidId, genreId);
			}
		}
		
		return vidId;
	}
	
	public int[] add(Video[] videos) {
		int[] vidIds = new int[videos.length];
		
		for(int i=0; i<videos.length; i++) {
			vidIds[i] = add(videos[i]);
		}
		
		return vidIds;
	}
	
	public void delete(int id) {
		super.delete(id);
		
		VideoStarTable videoStarTable = new VideoStarTable(connection);
		videoStarTable.delete(VideoStarTable.COLUMN_VIDEO_ID, Integer.toString(id));
		
		VideoGenreTable videoGenreTable = new VideoGenreTable(connection);
		videoGenreTable.delete(VideoGenreTable.COLUMN_VIDEO_ID, Integer.toString(id));
	}
	
	public int[] getGenres(int id) {
		int[] genres = null;
		try {
			List<Integer> genreIdList = new ArrayList<Integer>();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM "+ VideoGenreTable.TABLE_NAME +" WHERE "+ VideoGenreTable.COLUMN_VIDEO_ID +" = '" + id + "'");
			while(resultSet.next()) {
				genreIdList.add(resultSet.getInt(VideoGenreTable.COLUMN_GENRE_ID));
			}
			List<Integer> genreList = new ArrayList<Integer>();
			for(int genreId : genreIdList) {
				ResultSet resultSetTwo = statement.executeQuery("SELECT * FROM " + GenreTable.TABLE_NAME + " WHERE " + BaseTable.ID + " = '" + genreId + "'");
				resultSetTwo.next();
				genreList.add(resultSetTwo.getInt(GenreTable.ID));
			}
			genres = new int[genreList.size()];
			for(int i=0; i<genreList.size(); i++) {
				genres[i] = genreList.get(i);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return genres;
	}
	
	public int[] getStars(int id) {
		int[] stars = null;
		try {
			List<Integer> starIdList = new ArrayList<Integer>();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM "+ VideoStarTable.TABLE_NAME +" WHERE "+ VideoStarTable.COLUMN_VIDEO_ID +" = '" + id + "'");
			while(resultSet.next()) {
				starIdList.add(resultSet.getInt(VideoStarTable.COLUMN_STAR_ID));
			}
			List<Integer> starList = new ArrayList<Integer>();
			for(int starId : starIdList) {
				ResultSet resultSetTwo = statement.executeQuery("SELECT * FROM " + StarTable.TABLE_NAME + " WHERE " + StarTable.ID + " = '" + starId + "'");
				resultSetTwo.next();
				starList.add(resultSetTwo.getInt(StarTable.ID));
			}
			stars = new int[starList.size()];
			for(int i=0; i<starList.size(); i++) {
				stars[i] = starList.get(i);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stars;
	}
	
	public Video[] getAllVideos() {
		Video[] videos;
		List<Video> videoList = new ArrayList<Video>();
		try {
			ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
			while(resultSet.next()) {
				int id = resultSet.getInt(ID);
				String name = resultSet.getString(COLUMN_NAME);
				String filepath = resultSet.getString(COLUMN_PATH);

				Video video = new Video(id, name, filepath, null, null);
				
				videoList.add(video);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		videos = new Video[videoList.size()];
		for(int i=0; i<videoList.size(); i++) {
			Video curVideo = videoList.get(i);
			int curId = curVideo.getId();
			curVideo.setStarIds(getStars(curId));
			curVideo.setGenreIds(getGenres(curId));
			videos[i] = curVideo;
		}
		
		return videos;
	}
	
}
