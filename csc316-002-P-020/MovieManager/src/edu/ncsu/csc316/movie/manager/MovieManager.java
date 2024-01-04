package edu.ncsu.csc316.movie.manager;

import java.io.FileNotFoundException;
import java.text.ParseException;
import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.dsa.sorter.Sorter;
import edu.ncsu.csc316.movie.data.Movie;
import edu.ncsu.csc316.movie.data.WatchRecord;
import edu.ncsu.csc316.movie.factory.DSAFactory;
import edu.ncsu.csc316.movie.io.InputFileReader;

public class MovieManager {
	
	private List<Movie> movieList;
	
	private List<WatchRecord> watchList;
	
	private Map<String, Movie> movieMap;
	
	private Map<String, List<WatchRecord>> watchDates;
	
	/**
	 * Creates a MovieManager instance for handling utility functions
	 * 
	 * @param pathToMovies  the path to the file of movie records
	 * @param pathToHistory the path to the file of watch records
	 * @throws FileNotFoundException if the file cannot be found
	 * @throws ParseException        if the watch history file has incorrectly
	 *                               formatted date information
	 */
	public MovieManager(String pathToMovies, String pathToHistory) {
		try {
			movieList = InputFileReader.readMovieFile(pathToMovies);
			watchList = InputFileReader.readHistoryFile(pathToHistory);
		} catch (Exception e) {
			if(e instanceof ParseException) {
				System.exit(1);
			} else {
				throw new IllegalArgumentException();
			}
		}
		movieMap = buildMovieMap(movieList);
		watchDates = loadWatchDates(movieMap, watchList);
	}

	/**
	 * Returns a list of watch records associated with the requested movie title
	 * 
	 * @param title the title of the movie for which to retrieve watch record
	 *              information
	 * @return a list of watch records associated with the requested movie title
	 */
	public List<WatchRecord> getWatchHistory(String title) {
		List<WatchRecord> watchRecords = DSAFactory.getIndexedList();
		for(Movie m : movieList) {
			if(m.getTitle().equalsIgnoreCase(title)) {
				watchRecords = watchDates.get(m.getId());
			}
		}
		if(watchRecords == null) {
			return DSAFactory.getIndexedList();
		}
		// Not empty so sort
		if(!watchRecords.isEmpty()) {
			WatchInfo[] sorted = new WatchInfo[watchRecords.size()];
			for(int i = 0; i < watchRecords.size(); i++) {
				sorted[i] = new WatchInfo(watchRecords.get(i));
			}
			Sorter<WatchInfo> sorter = DSAFactory.getComparisonSorter();
			sorter.sort(sorted);
			for(int i = 0; i < sorted.length; i++) {
				watchRecords.set(i, sorted[i].watchRecord);
			}
		} 
		return watchRecords;
	}

	/**
	 * Return a list of movie records that contains the top n most frequently
	 * watched movies
	 * 
	 * @param numberOfMovies the n most frequently watched movies to include in the
	 *                       list
	 * @return a list of movie records that contains the top n most frequently
	 *         watched movies
	 */
	public List<Movie> getMostFrequentlyWatchedMovies(int numberOfMovies) {
		if(watchList.isEmpty()) {
			return DSAFactory.getIndexedList();
		}
		Map<Movie, Integer> frequencies = DSAFactory.getMap();
		for(int i = 0; i < watchList.size(); i++) {
			Movie m = movieMap.get(watchList.get(i).getMovieId());
			Integer count = frequencies.get(m);
			if(count == null) {
				count = 0;
			}
			count++;
			frequencies.put(m, count);
		}
		FrequencyInfo[] sorted = new FrequencyInfo[frequencies.size()];
		int i = 0;
		for(Map.Entry<Movie, Integer> e : frequencies.entrySet()) {
			sorted[i++] = new FrequencyInfo(e.getKey(), e.getValue());
		}
		Sorter<FrequencyInfo> sorter = DSAFactory.getComparisonSorter();
		sorter.sort(sorted);
		List<Movie> list = DSAFactory.getIndexedList();
		for(int j = 0; j < numberOfMovies && j != frequencies.size(); j++) {
			list.addLast(sorted[j].movie);
		}
		return list;
	}

	/**
	 * Return a list of movie records that have been watched less than a specific
	 * threshold percentage
	 * 
	 * @param threshold the percentages threshold to use, as a whole number
	 * @return a list of movie records that have been watched less than the specified
	 *         threshold percentage
	 */
	public List<Movie> getMoviesByWatchDuration(int threshold) {
		List<Movie> list = DSAFactory.getIndexedList();
		Map<String, Integer> watchDurations = DSAFactory.getMap();
		for(int i = 0; i < watchList.size(); i++) {
			WatchRecord curr = watchList.get(i);
			String id = curr.getMovieId();
			double watched = curr.getWatchTime();
			int duration = movieMap.get(curr.getMovieId()).getRuntime();
			int percentage = (int) (watched * 100) / duration;
			// No entry in map
			if(watchDurations.get(id) == null) {
				// Put in list
				if(percentage < threshold) {
					watchDurations.put(id, percentage);
				}
			} else {										// Already in map
				if(percentage < threshold) {
					int other = watchDurations.get(id);
					if(percentage > other) {
						watchDurations.put(id, percentage);
					}
				} else {
					watchDurations.remove(id);
				}
			}
		}
		DurationInfo[] sorted = new DurationInfo[watchDurations.size()];
		int i = 0;
		for(Map.Entry<String, Integer> e : watchDurations.entrySet()) {
			sorted[i++] = new DurationInfo(movieMap.get(e.getKey()), e.getValue());
		}
		Sorter<DurationInfo> sorter = DSAFactory.getComparisonSorter();
		sorter.sort(sorted);
		for(int j = 0; j < sorted.length; j++) {
			list.addLast(sorted[j].movie);
		}
		return list;
	}
	
	private Map<String, Movie> buildMovieMap(List<Movie> movies) {
		Map<String, Movie> map = DSAFactory.getMap();
		for(Movie m : movies) {
			map.put(m.getId(), m);
		}
		return map;
	}
	
	private Map<String, List<WatchRecord>> loadWatchDates(Map<String, Movie> movies, List<WatchRecord> watchRecords) {
		Map<String, List<WatchRecord>> watches = DSAFactory.getMap();
		for(WatchRecord w : watchRecords) {
			List<WatchRecord> list = watches.get(w.getMovieId());
			if(list == null) {
				list = DSAFactory.getIndexedList();
				list.addLast(w);
				watches.put(w.getMovieId(), list);
			} else {
				list.addLast(w);
			}
		}
		return watches;
	}
	
	private class FrequencyInfo implements Comparable<FrequencyInfo> {
		private Movie movie;
		
		private Integer frequency;
		
		public FrequencyInfo(Movie movie, Integer frequency) {
			setMovie(movie);
			setFrequency(frequency);
		}

		public Movie getMovie() {
			return movie;
		}

		public void setMovie(Movie movie) {
			this.movie = movie;
		}

		public Integer getFrequency() {
			return frequency;
		}

		public void setFrequency(Integer frequency) {
			this.frequency = frequency;
		}

		@Override
		public int compareTo(FrequencyInfo o) {
			if(frequency.compareTo(o.getFrequency()) == 0) {
				return movie.getTitle().compareToIgnoreCase(o.getMovie().getTitle());
			}
			return -1 * frequency.compareTo(o.getFrequency());
		}
		
	}
	
	private class DurationInfo implements Comparable<DurationInfo> {

		private Movie movie;
		
		private int duration;
		
		public DurationInfo(Movie movie, int duration) {
			setMovie(movie);
			setDuration(duration);
		}
		
		public Movie getMovie() {
			return movie;
		}

		public void setMovie(Movie movie) {
			this.movie = movie;
		}

		public int getDuration() {
			return duration;
		}

		public void setDuration(int duration) {
			this.duration = duration;
		}

		@Override
		public int compareTo(DurationInfo o) {
			if(duration < o.getDuration()) {
				return 1;
			} else if(duration > o.duration) {
				return -1;
			} else {
				return movie.getTitle().compareToIgnoreCase(o.getMovie().getTitle());
			}
		}
	}
	
	private class WatchInfo implements Comparable<WatchInfo> {
		
		private WatchRecord watchRecord;
		
		public WatchInfo(WatchRecord watchRecord) {
			setWatchRecord(watchRecord);
		}

		public WatchRecord getWatchRecord() {
			return watchRecord;
		}

		public void setWatchRecord(WatchRecord watchRecord) {
			this.watchRecord = watchRecord;
		}

		@Override
		public int compareTo(WatchInfo o) {
			if(watchRecord.compareTo(o.watchRecord) == 0) {
				if(watchRecord.getWatchTime() > o.getWatchRecord().getWatchTime()) {
					return -1;
				} else {
					return 1;
				}
			} else {
				return -1 * watchRecord.compareTo(o.watchRecord);
			}
		}
		
		
	}
}
