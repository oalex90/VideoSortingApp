package tables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import data_objects.Genre;

public class GenreTable extends BaseTable {
	
	public static final String TABLE_NAME = "genre";
	public static final String COLUMN_NAME = "name";
	
	public GenreTable (Connection connection) {
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
	
	public Genre getGenre(int id) {
		Genre genre =  null;
		String[] columns = {ID};
		String[] values = {Integer.toString(id)};
		ResultSet results = super.getResults(columns, values);
		try {
			if(results.next()) {
				String name = results.getString(COLUMN_NAME);
				String[] altNames = getAltNames(id);
				genre = new Genre(id, name, altNames);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return genre;
	}
	
	public int add(String name) {
		String[] values = {name};
		return super.add(values);
	}
	
	public int add(Genre genre) {
		int genreId = add(genre.getName());
		
		if(genre.getAltNames() != null) {
			GenreAltNameTable genreAltNameTable = new GenreAltNameTable(connection);
			String[] genreAltNames = genre.getAltNames();
			for(String altName : genreAltNames) {
				genreAltNameTable.add(altName, genreId);
			}
		}
		return genreId;
	}
	
	public int[] add(Genre[] genres) {
		int[] genreIds = new int[genres.length];
		
		for(int i=0; i<genres.length; i++) {
			genreIds[i] = add(genres[i]);
		}
		
		return genreIds;
	}
	
	public void delete(int id) {
		super.delete(id);
		
		GenreAltNameTable genreAltNameTable = new GenreAltNameTable(connection);
		genreAltNameTable.delete(GenreAltNameTable.COLUMN_GENRE_ID, Integer.toString(id));
		
	}
	
	public String[] getAltNames(int id) {
		String[] altNames = null;
		try {
			List<String> genreAltNameList = new ArrayList<String>();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM "+ GenreAltNameTable.TABLE_NAME +" WHERE "+ GenreAltNameTable.COLUMN_GENRE_ID +" = '" + id + "'");
			while(resultSet.next()) {
				genreAltNameList.add(resultSet.getString(GenreAltNameTable.COLUMN_NAME));
			}
			altNames = new String[genreAltNameList.size()];
			altNames = genreAltNameList.toArray(altNames);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return altNames;
	}
	
	public Genre[] getAllGenres() {
		Genre[] genres;
		List<Genre> genreList = new ArrayList<Genre>();
		try {
			ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
			while(resultSet.next()) {
				int id = resultSet.getInt(ID);
				String name = resultSet.getString(COLUMN_NAME);

				Genre genre = new Genre(id, name, null);
				genreList.add(genre);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		genres = new Genre[genreList.size()];
		for(int i=0; i<genreList.size(); i++) {
			Genre curGenre = genreList.get(i);
			int curId = curGenre.getId();
			curGenre.setAltNames(getAltNames(curId));
			genres[i] = curGenre;
		}
		
		return genres;
	}
}