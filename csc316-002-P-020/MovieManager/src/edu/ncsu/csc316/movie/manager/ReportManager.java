package edu.ncsu.csc316.movie.manager;

import java.io.FileNotFoundException;
import java.text.ParseException;
import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.movie.data.Movie;
import edu.ncsu.csc316.movie.data.WatchRecord;

public class ReportManager {

	private MovieManager manager;
	private static final String INDENT = "   ";

	/**
	 * Creates a new ReportManager for generating reports for the MovieManager software
	 * 
	 * @param pathToMovieFile the path to the file that contains movie records
	 * @param pathToWatchFile the path to the file that contains watch records
	 * @throws FileNotFoundException if either input file cannot be found/read/opened
	 * @throws ParseException if the watch record file contains incorrectly formatted date information
	 */
	public ReportManager(String pathToMovieFile, String pathToWatchFile) {
		manager = new MovieManager(pathToMovieFile, pathToWatchFile);
	}
	
	/**
	 * Returns a report of the most frequently watched movies that contains the top
	 * n movies most watched
	 * 
	 * @param numberOfMovies the number of movies to include in the report
	 * @return a report of the most frequently watched movies
	 */
	public String getTopMoviesReport(int numberOfMovies) {
		List<Movie> movies = manager.getMostFrequentlyWatchedMovies(numberOfMovies);
		if(movies.isEmpty()) {
			return "No movies have been streamed.";
		}
		StringBuilder string = new StringBuilder("The " + numberOfMovies + " most frequently watched movies [\n");
		for(Movie m : movies) {
			string.append(INDENT + m.getTitle() + " (" + m.getYear() + ")\n");
		}
		string.append("]");
		return string.toString();
	}

	/**
	 * Returns a report of movies below a specific watch percentage threshold.
	 * 
	 * @param threshold the percentage threshold (as a whole number)
	 * @return a report of movies below a specific watch percentage threshold
	 */
	public String getMovieCompletionReport(int threshold) {
		List<Movie> movies = manager.getMoviesByWatchDuration(threshold);
		if(movies.isEmpty()) {
			return "No movies are less than " + threshold + "% completed.";
		}
		StringBuilder string = new StringBuilder("The movies that have been watched less than " + threshold + "% [\n");
		for(Movie m : movies) {
			string.append(INDENT + m.getTitle() + " (" + m.getYear() + ")\n");
		}
		string.append("]");
		return string.toString();
	}

	/**
	 * Return a report of dates on which a specific movie was watched
	 * 
	 * @param title the title of the movie for which to retrieve watch dates
	 * @return a report of dates on which a specific movie was watched
	 */
	public String getWatchDates(String title) {
		List<WatchRecord> watches = manager.getWatchHistory(title);
		if(watches.isEmpty()) {
			return "No watch history for \"" + title + "\".";
		}
		StringBuilder string = new StringBuilder("The movie \"" + title + "\" was streamed on [\n");
		for(WatchRecord w : watches) {
			int month = w.getDate().getMonthValue();
			int day = w.getDate().getDayOfMonth();
			string.append(INDENT + (month < 10 ? "0" + month : month) + "/" + (day < 10 ? "0" + day : day) + "/" + w.getDate().getYear() + "\n");
		}
		string.append("]");
		return string.toString();
	}
}
