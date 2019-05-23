package data_objects;

import java.util.Arrays;

public class Genre {
	private int id;
	private String name;
	private String[] altNames;
	
	public Genre(int id, String name, String[] altNames) {
		this.id = id;
		this.name = name;
		this.altNames = altNames;
	}
	
	public Genre(String name) {
		this.name = name;
	}
	
	public Genre(String name, String[] altNames) {
		this.name = name;
		this.altNames = altNames;
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

	public String[] getAltNames() {
		return altNames;
	}

	public void setAltNames(String[] altNames) {
		this.altNames = altNames;
	}

	@Override
	public String toString() {
		return "Genre [id=" + id + ", name=" + name + ", altNames=" + Arrays.toString(altNames) + "]";
	}
}
