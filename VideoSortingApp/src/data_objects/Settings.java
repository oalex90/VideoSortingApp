package data_objects;

public class Settings {

	private String directory;
	
	public Settings() {};
	
	public Settings(String directory) {
		this.directory =  directory;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	@Override
	public String toString() {
		return "Settings [directory=" + directory + "]";
	}
}
