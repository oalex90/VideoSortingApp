import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import data_objects.*;
import tables.*;

public class DatabaseHelper {
	
	public static final String DB_NAME = "sample";
	
	Connection connection;
	VideoTable video;
	StarTable star;
	GenreTable genre;
	StarAltNameTable starAltName;
	GenreAltNameTable genreAltName;
	VideoStarTable videoStar;
	VideoGenreTable videoGenre;
	SettingsTable settings;
	
	VideoSortingAppDb appDb;
	
	public DatabaseHelper(String dbName) {
		try {
			if (dbName == null) {
				connection = DriverManager.getConnection("jdbc:sqlite:"+ DB_NAME + ".db");
			} else {
				connection = DriverManager.getConnection("jdbc:sqlite:"+ dbName +".db");
			}
			
			video = new VideoTable(connection);
			star = new StarTable(connection);
			genre = new GenreTable(connection);
			starAltName = new StarAltNameTable(connection);
			genreAltName = new GenreAltNameTable(connection);
			videoStar = new VideoStarTable(connection);
			videoGenre = new VideoGenreTable(connection);
			settings = new SettingsTable(connection);	
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void loadData() {
		if(appDb == null) {
		
		Genre[] genres = genre.getAllGenres();
		Star[] stars = star.getAllStars();
		Video[] videos = video.getAllVideos();
		Settings settingsObj = settings.getSettings();
		
		appDb = new VideoSortingAppDb(videos, stars, genres);
		}
	}
	
	public void close() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.err.println(e);
		}
	}
	
	public void createDb() {
		video.createTable();
		star.createTable();
		genre.createTable();
		starAltName.createTable();
		genreAltName.createTable();
		videoStar.createTable();
		videoGenre.createTable();
		settings.createTable();
	}
	
	public void display() {
		System.out.println("Displaying Table:");
		video.display();
		star.display();
		genre.display();
		starAltName.display();
		genreAltName.display();
		videoStar.display();
		videoGenre.display();
	}
}
