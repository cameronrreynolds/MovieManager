package edu.ncsu.csc316.movie.manager;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.movie.data.Movie;
import edu.ncsu.csc316.movie.data.WatchRecord;

public class MovieManagerTest {

	private MovieManager manager;
	
	@Before
	public void setUp() {
		manager = new MovieManager("./input/movie-record.csv", "./input/watch-record.csv");
	}
	
	@Test
	public void testInvalidFile() {
		try {
			manager = new MovieManager("./invalid", "invalid");
		} catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
		}
	}

	@Test
	public void testGetMoviesByWatchDuration() {
		List<Movie> list = manager.getMoviesByWatchDuration(11);
		assertEquals(2, list.size());
		assertEquals("Guardians of the Galaxy", list.get(0).getTitle());
		assertEquals("Pete's Dragon", list.get(1).getTitle());
	}
	
	@Test
	public void testGetWatchFrequency() {
		List<WatchRecord> list = manager.getWatchHistory("Pete's Dragon");
		assertEquals(2, list.size());
	}
}
