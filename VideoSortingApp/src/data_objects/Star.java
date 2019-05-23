package data_objects;

import java.util.Arrays;

public class Star {
	private int id;
	private String name;
	private String[] altNames;

	public Star (int id, String name, String[] altNames) {
		this.id = id;
		this.name = name;
		this.altNames = altNames;
	}
	
	public Star(String name) {
		this.name = name;
	}
	
	public Star(String name, String[] altNames) {
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
		return "Star [id=" + id + ", name=" + name + ", altNames=" + Arrays.toString(altNames) + "]";
	}
	
	
}
