package data_objects;

import java.util.Arrays;

public class Video {
	private int id;
	private String name;
	private String filepath;
	private int[] starIds;
	private int[] genreIds;
	
	public Video(int id, String name, String filepath, int[] starIds, int[] genreIds) {
		this.id = id;
		this.name = name;
		this.filepath = filepath;
		this.starIds = starIds;
		this.genreIds = genreIds;
	}
	
	public Video (String name, String filepath) {
		this.name = name;
		this.filepath = filepath;
	}
	
	public Video(String name, String filepath, int[] starIds, int[] genreIds) {
		this.name = name;
		this.filepath = filepath;
		this.starIds = starIds;
		this.genreIds = genreIds;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public int[] getStarIds() {
		return starIds;
	}

	public void setStarIds(int[] starIds) {
		this.starIds = starIds;
	}

	public int[] getGenreIds() {
		return genreIds;
	}

	public void setGenreIds(int[] genreIds) {
		this.genreIds = genreIds;
	}

	@Override
	public String toString() {
		return "Video [id=" + id + ", name=" + name + ", filepath=" + filepath + ", starIds=" + Arrays.toString(starIds)
				+ ", genreIds=" + Arrays.toString(genreIds) + "]";
	}
}
