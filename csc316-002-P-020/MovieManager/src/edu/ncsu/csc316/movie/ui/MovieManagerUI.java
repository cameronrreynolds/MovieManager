package edu.ncsu.csc316.movie.ui;

import java.util.Scanner;

import edu.ncsu.csc316.movie.manager.ReportManager;

public class MovieManagerUI {
	
	private static ReportManager reportManager;
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Welcome to PackFlix!");
		boolean isFile = false;
		while(!isFile) {
			System.out.println("Please enter the file name for the movie record data:");
			String moviefp = scan.nextLine();
			System.out.println("Please enter the file name for the watch record data:");
			String watchfp = scan.nextLine();
			try {
				reportManager = new ReportManager(moviefp, watchfp);
				isFile = true;
			} catch (IllegalArgumentException e) {
				System.out.println("Invalid file");
			}
		}
		System.out.println("Functionality:\n   1: View most frequently streamed movie\n   2: View unfinished movies\n 3: View watch history for a movie\n   Q: quit");
		boolean repeat = true;
		while(repeat) {
			String cmd = scan.next();
			if(cmd.equalsIgnoreCase("Q")) {
				repeat = false;
			} else if(cmd.equalsIgnoreCase("1")) {
				System.out.println("Enter the number of movies to report:");
				int numberOfMovies = scan.nextInt();
				while(numberOfMovies <= 0) {
					System.out.println("Please enter a number > 0.");
					numberOfMovies = scan.nextInt();
				}
				String report = reportManager.getTopMoviesReport(numberOfMovies);
				System.out.println(report);
			} else if(cmd.equalsIgnoreCase("2")) {
				System.out.println("Please enter a percentage completion between 1 and 100.");
				int percent = scan.nextInt();
				while(percent < 1 || percent > 100) {
					System.out.println("Please enter a percentage completion between 1 and 100.");
					percent = scan.nextInt();
				}
				String report = reportManager.getMovieCompletionReport(percent);
				System.out.println(report);
			} else if(cmd.equalsIgnoreCase("3")) {
				System.out.println("Please enter a movie title");
				String title = scan.nextLine();
				while(title.isBlank()) {
					System.out.println("Please enter a valid movie title.");
					title = scan.nextLine();
				}
				String report = reportManager.getWatchDates(title);
				System.out.println(report);
			} else {
				System.out.println("Please enter a valid command");
				System.out.println("Functionality:\n   1: View most frequently streamed movie\n   2: View unfinished movies\n 3: View watch history for a movie\n   Q: quit");
			}
		}
		System.out.println("Exiting program");
		scan.close();
		System.exit(0);
	}
}
