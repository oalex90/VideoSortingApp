import java.util.Arrays;

import data_objects.*;

public class TestSuite {
	
	DatabaseHelper dbHelper;

	public TestSuite () {
		dbHelper = new DatabaseHelper("test");
	}
	
	public void testCreateDb() {
		System.out.println("Creating test db\n");	
		dbHelper.createDb();
		dbHelper.display();
	}
	
	public void testVideoAdd() {
		System.out.println("\nAdding six Video items");
		
		System.out.println(dbHelper.video.add("Video One", "one.avi"));
		System.out.println(dbHelper.video.add("Video Two", "two.avi"));
		
		String[] values = {"Video Three", "three.mp4"};
		System.out.println(dbHelper.video.add(values));
		
		String[] valuesOne = {"Video Four", "four.avi"};
		String[] valuesTwo = {"Video Five", "five.mp3"};
		String[][] valueSets  = {valuesOne, valuesTwo};
		System.out.println(Arrays.toString(dbHelper.video.add(valueSets)));
		
		Video video = new Video("Video Six", "six.avi");
		dbHelper.video.add(video);
		
		dbHelper.video.display();
		
		System.out.println("Getting id value for Video Three");
		System.out.println(dbHelper.video.getId("Video Three", "three.mp4"));
	}
	
	public void testVideoDelete() {
		System.out.println("Deleting Video Four");
		
		dbHelper.video.delete(4);
		
		dbHelper.video.display();
		dbHelper.videoStar.display();
		dbHelper.videoGenre.display();
	}
	
	public void testStarAdd() {
		System.out.println("\nAdding six Star items");
		
		dbHelper.star.add("Star One");
		dbHelper.star.add("Star Two");
		
		String[] values = {"Star Three"};
		dbHelper.star.add(values);
		
		String[] valuesOne = {"Star Four"};
		String[] valuesTwo = {"Star Five"};
		String[][] valueSets  = {valuesOne, valuesTwo};
		dbHelper.star.add(valueSets);
		
		Star star = new Star("Star Six");
		dbHelper.star.add(star);
		
		dbHelper.star.display();
		
		System.out.println("Getting id value for Star Three");
		System.out.println(dbHelper.star.getId("Star Three"));
	}
	
	public void testGenreAdd() {
		System.out.println("\nAdding six Genre items");
		
		dbHelper.genre.add("Genre One");
		dbHelper.genre.add("Genre Two");
		
		String[] values = {"Genre Three"};
		dbHelper.genre.add(values);
		
		String[] valuesOne = {"Genre Four"};
		String[] valuesTwo = {"Genre Five"};
		String[][] valueSets  = {valuesOne, valuesTwo};
		dbHelper.genre.add(valueSets);
		
		Genre genre = new Genre("Genre Six");
		dbHelper.genre.add(genre);
		
		dbHelper.star.display();
		
		dbHelper.genre.display();
		
		System.out.println("Getting id value for Genre Three");
		System.out.println(dbHelper.genre.getId("Genre Three"));
	}
	
	public void testStarAltNameAdd() {
		System.out.println("\nAdding five StarAltName items");
		
		dbHelper.starAltName.add("StarAltName One", 2);
		dbHelper.starAltName.add("StarAltName Two", 4);
		
		String[] values = {"StarAltName Three", Integer.toString(2)};
		dbHelper.starAltName.add(values);
		
		String[] valuesOne = {"StarAltName Four", Integer.toString(5)};
		String[] valuesTwo = {"StarAltName Five", Integer.toString(5)};
		String[][] valueSets  = {valuesOne, valuesTwo};
		dbHelper.starAltName.add(valueSets);
		
		dbHelper.starAltName.display();
	}
	
	public void testGenreAltNameAdd() {
		System.out.println("\nAdding five GenreAltName items");
		
		dbHelper.genreAltName.add("GenreAltName One", 2);
		dbHelper.genreAltName.add("GenreAltName Two", 4);
		
		String[] values = {"GenreAltName Three", Integer.toString(2)};
		dbHelper.genreAltName.add(values);
		
		String[] valuesOne = {"GenreAltName Four", Integer.toString(5)};
		String[] valuesTwo = {"GenreAltName Five", Integer.toString(5)};
		String[][] valueSets  = {valuesOne, valuesTwo};
		dbHelper.genreAltName.add(valueSets);
		
		dbHelper.genreAltName.display();
	}
	
	public void testVideoStarAdd() {
		System.out.println("\nAdding five VideoStar items");
		
		dbHelper.videoStar.add(2, 2);
		dbHelper.videoStar.add(4, 4);
		
		String[] values = {Integer.toString(1), Integer.toString(2)};
		dbHelper.videoStar.add(values);
		
		String[] valuesOne = {Integer.toString(4), Integer.toString(5)};
		String[] valuesTwo = {Integer.toString(5), Integer.toString(5)};
		String[][] valueSets  = {valuesOne, valuesTwo};
		dbHelper.videoStar.add(valueSets);
		
		dbHelper.videoStar.display();
	}
	
	public void testVideoGenreAdd() {
		System.out.println("\nAdding five VideoGenre items");
		
		dbHelper.videoGenre.add(2, 2);
		dbHelper.videoGenre.add(4, 4);
		
		String[] values = {Integer.toString(1), Integer.toString(2)};
		dbHelper.videoGenre.add(values);
		
		String[] valuesOne = {Integer.toString(4), Integer.toString(5)};
		String[] valuesTwo = {Integer.toString(5), Integer.toString(5)};
		String[][] valueSets  = {valuesOne, valuesTwo};
		dbHelper.videoGenre.add(valueSets);
		
		dbHelper.videoGenre.display();
	}
	
	public void testSettingsAdd() {
		System.out.println("\nAdding Settings");
		
		String[] values = {"home/myFolder/database.db"};
		dbHelper.settings.add(values);
		
		dbHelper.settings.display();
	}
	
	public void populateDb() {
		testCreateDb();
		testVideoAdd();
		testStarAdd();
		testGenreAdd();
		testStarAltNameAdd();
		testGenreAltNameAdd();
		testVideoStarAdd();
		testVideoGenreAdd();
		testSettingsAdd();
	}
	
	public void testGetVideoGenres() {
		System.out.println("\nTesting getGenres from VideoTable");
		dbHelper.video.display();
		dbHelper.videoGenre.display();
		int[] genres = dbHelper.video.getGenres(4);
		String genresList = "";
		for(int genre : genres) {
			genresList += genre + ", ";
		}
		System.out.println("\nVideo id= 4; Genres = " + genresList);
		
		genres = dbHelper.video.getGenres(3);
	    genresList = "";
		for(int genre : genres) {
			genresList += genre + ", ";
		}
		System.out.println("\nVideo id= 3; Genres = " + genresList);
	}
	
	public void testGetVideoStars() {
		System.out.println("\nTesting getStars from VideoTable");
		dbHelper.video.display();
		dbHelper.videoStar.display();
		int[] stars = dbHelper.video.getStars(4);
		String starsList = "";
		for(int star : stars) {
			starsList += star + ", ";
		}
		System.out.println("\nVideo id= 4; Stars = " + starsList);
		
		stars = dbHelper.video.getStars(3);
	    starsList = "";
		for(int star : stars) {
			starsList += star + ", ";
		}
		System.out.println("\nVideo id= 3; Stars = " + starsList);
	}
	
	public void testGetStarAltNames() {
		System.out.println("\nTesting getAltNames from StarTable");
		dbHelper.star.display();
		dbHelper.starAltName.display();
		String[] starAltNames = dbHelper.star.getAltNames(5);
		
		String  altNameList = "";
		for(String altName: starAltNames) {
			altNameList += altName + ", ";
		}
		System.out.println("\nStar id= 5; AltNames = " + altNameList);
		
		starAltNames = dbHelper.star.getAltNames(1);
		altNameList = "";
		for(String altName: starAltNames) {
			altNameList += altName + ", ";
		}
		System.out.println("\nStar id= 1; AltNames = " + altNameList);
	}
	
	public void testGetGenreAltNames() {
		System.out.println("\nTesting getAltNames from GenreTable");
		dbHelper.genre.display();
		dbHelper.genreAltName.display();
		String[] genreAltNames = dbHelper.genre.getAltNames(5);
		
		String  altNameList = "";
		for(String altName: genreAltNames) {
			altNameList += altName + ", ";
		}
		System.out.println("\nGenre id= 5; AltNames = " + altNameList);
		
		genreAltNames = dbHelper.star.getAltNames(1);
		altNameList = "";
		for(String altName: genreAltNames) {
			altNameList += altName + ", ";
		}
		System.out.println("\nGenre id= 1; AltNames = " + altNameList);
	}
	
	public void testDataObjects() {
		System.out.println("\nTesting Data Objects");
		int[] ids = {1,2,3,4};
		Video videoOne = new Video(1,"VideoOne", "One.avi", ids, ids);
		Video videoTwo = new Video(2,"VideoTwo", "Two.avi", ids, ids);
		Video videoThree = new Video(3,"VideoThree", "Three.avi", ids, ids);
		Video[] videos = {videoOne, videoTwo, videoThree};
		
		String[] altNames = {"AltNameOne", "AltNameTwo", "AltNameThree"};
		Star starOne = new Star(1, "StarOne", altNames);
		Star starTwo = new Star(2, "StarTwo", altNames);
		Star starThree = new Star(3, "StarThree", altNames);
		Star[] stars = {starOne, starTwo, starThree};
		
		Genre genreOne = new Genre(1, "GenreOne", altNames);
		Genre genreTwo = new Genre(2, "GenreTwo", altNames);
		Genre genreThree = new Genre(3, "GenreThree", altNames);
		Genre[] genres = {genreOne, genreTwo, genreThree};
		
		VideoSortingAppDb testDb = new VideoSortingAppDb(videos, stars, genres);
		System.out.println(testDb);
	}
	
	public void testDataObjectGenre() {
		System.out.println("\nTesting Genre Data Object.");
		String[] altNames = {"Genre One", "Genre Uno"};
		Genre genre = new Genre(1, "GenreOne", altNames);
		System.out.println(genre);
	}
	
	public void testGetAllVideos() {
		System.out.println("\nTesting GetAllVideos from VideoTable.");
		dbHelper.video.display();
		dbHelper.videoStar.display();
		dbHelper.videoGenre.display();
		
		Video[] videos = dbHelper.video.getAllVideos();
		for(Video video : videos) {
			System.out.println(video);
		}
	}
	
	public void testGetAllStars() {
		System.out.println("\nTesting GetAllStars from StarTable.");
		dbHelper.star.display();
		dbHelper.starAltName.display();
		
		Star[] stars = dbHelper.star.getAllStars();
		for(Star star : stars) {
			System.out.println(star);
		}
	}
	
	public void testGetAllGenres() {
		System.out.println("\nTesting GetAllGenres from GenreTable.");
		dbHelper.genre.display();
		dbHelper.genreAltName.display();
		
		Genre[] genres = dbHelper.genre.getAllGenres();
		for(Genre genre : genres) {
			System.out.println(genre);
		}
	}

	
	
	
	public void runDbTest() {
		populateDb();
		//dbHelper.display();
		//testGetVideoGenres();
		//testGetVideoStars();
		//testGetStarAltNames();
		//testGetGenreAltNames();
		//testDataObjects();
		//testGetAllVideos();
		//testGetAllStars();
		//testGetAllGenres();
		
		//testVideoDelete();
		
		dbHelper.close();
	}
}
