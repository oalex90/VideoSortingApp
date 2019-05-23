package data_objects;

public class VideoSortingAppDb {
	private Video[] videos;
	private Star[] stars;
	private Genre[] genres;
	
	public VideoSortingAppDb(Video[] videos, Star[] stars, Genre[] genres) {
		this.videos = videos;
		this.stars = stars;
		this.genres = genres;
	}

	public Video[] getVideos() {
		return videos;
	}

	public void setVideos(Video[] videos) {
		this.videos = videos;
	}

	public Star[] getStars() {
		return stars;
	}

	public void setStars(Star[] stars) {
		this.stars = stars;
	}

	public Genre[] getGenres() {
		return genres;
	}

	public void setGenres(Genre[] genres) {
		this.genres = genres;
	}

	@Override
	public String toString() {
		String ret = "VideoSortingAppDb...";
		ret += "\n\tVideos:";
		for (Video video: videos) {
			ret+= "\n\t\t" + video;
		}
		ret += "\n\tStars:";
		for (Star star: stars) {
			ret+= "\n\t\t" + star;
		}
		
		ret += "\n\tGenres";
		for (Genre genre: genres) {
			ret+= "\n\t\t" + genre;
		}
		return ret;
	}
	
	
	
	
	
	

}
