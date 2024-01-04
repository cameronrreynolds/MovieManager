package edu.ncsu.csc316.movie.manager;

import static org.junit.Assert.*;

import org.junit.Test;

public class ReportManagerTest {
	
	
	private ReportManager manager = new ReportManager("./input/movieRecord_sample.csv", "./input/watchRecord_sample.csv");
	
	@Test
	public void testGetTopMoviesReport() {
		assertEquals("The 3 most frequently watched movies [\n" + 
				"   Pete's Dragon (2016)\n" + 
				"   Guardians of the Galaxy (2014)\n" + 
				"   Hidden Figures (2016)\n" + 
				"]", manager.getTopMoviesReport(3));
		assertEquals("No movies have been streamed.", manager.getTopMoviesReport(0));
	}
	
	@Test
	public void testGetMovieCompletionReport() {
		assertEquals("The movies that have been watched less than 90% [\n" + 
				"   The Martian (2015)\n" + 
				"   The Great Wall (2016)\n" + 
				"   Guardians of the Galaxy (2014)\n" + 
				"]", manager.getMovieCompletionReport(90));
		assertEquals("The movies that have been watched less than 50% [\n" + 
				"   Guardians of the Galaxy (2014)\n" + 
				"]", manager.getMovieCompletionReport(50));
		assertEquals("No movies are less than 7% completed.", manager.getMovieCompletionReport(7));
	}

	@Test
	public void testGetWatchDates() {
		assertEquals("The movie \"Pete's Dragon\" was streamed on [\n" + 
				"   03/04/2020\n" + 
				"   02/05/2020\n" + 
				"   02/04/2020\n" + 
				"   05/01/2019\n" + 
				"]", manager.getWatchDates("Pete's Dragon"));
		assertEquals("The movie \"The Martian\" was streamed on [\n" + 
				"   07/05/2019\n" + 
				"]", manager.getWatchDates("The Martian"));
		assertEquals("No watch history for \"The Wizard of Oz\".", manager.getWatchDates("The Wizard of Oz"));
	}
}
