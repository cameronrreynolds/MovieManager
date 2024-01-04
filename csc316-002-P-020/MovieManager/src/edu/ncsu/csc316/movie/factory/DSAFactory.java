package edu.ncsu.csc316.movie.factory;

import edu.ncsu.csc316.dsa.list.ArrayBasedList;
import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.map.Map;
//import edu.ncsu.csc316.dsa.map.SearchTableMap;
import edu.ncsu.csc316.dsa.map.SkipListMap;
//import edu.ncsu.csc316.dsa.map.UnorderedLinkedMap;
//import edu.ncsu.csc316.dsa.sorter.MergeSorter;
import edu.ncsu.csc316.dsa.sorter.QuickSorter;
import edu.ncsu.csc316.dsa.sorter.Sorter;

/**
 * Factory for creating new data structure and algorithm instances
 * 
 * @author Dr. King
 *
 */
public class DSAFactory {

	/**
	 * Returns a data structure that implements a map
	 * 
	 * @param <K>
	 *            - the key type
	 * @param <V>
	 *            - the value type
	 * @return a data structure that implements a map
	 */
	public static <K extends Comparable<K>, V> Map<K, V> getMap() {
		return getSkipListMap();
	}

	/**
	 * Returns a data structure that implements an index-based list
	 * 
	 * @param <E>
	 *            - the element type
	 * @return an index-based list
	 */
	public static <E> List<E> getIndexedList() {
		return getArrayBasedList();
	}

	/**
	 * Returns a comparison based sorter
	 * 
	 * @param <E>
	 *            - the element type
	 * @return a comparison based sorter
	 */
	public static <E extends Comparable<E>> Sorter<E> getComparisonSorter() {
		return getQuickSorter();
	}

	/**
	 * Returns an unordered linked map
	 * 
	 * @return an unordered linked map
	 */
	/*private static <K, V> UnorderedLinkedMap<K, V> getUnorderedLinkedMap() {
		return new UnorderedLinkedMap<K, V>();
	}*/

	/**
	 * Returns a search table
	 * 
	 * @return a search table
	 */
	/*private static <K extends Comparable<K>, V> SearchTableMap<K, V> getSearchTableMap() {
		return new SearchTableMap<K, V>();
	}*/

	/**
	 * Returns a skip list map
	 * 
	 * @return a skip list map
	 */
	private static <K extends Comparable<K>, V> SkipListMap<K, V> getSkipListMap() {
		return new SkipListMap<K, V>();
	}

	/**
	 * Returns an array-based list
	 * 
	 * @return an array-based list
	 */
	private static <E> ArrayBasedList<E> getArrayBasedList() {
		return new ArrayBasedList<E>();
	}

	/**
	 * Returns a mergesorter
	 * 
	 * @return a mergesorter
	 */
	/*private static <E extends Comparable<E>> Sorter<E> getMergeSorter() {
		return new MergeSorter<E>();
	}*/

	/**
	 * Returns a quicksorter
	 * 
	 * @return a quicksorter
	 */
	private static <E extends Comparable<E>> Sorter<E> getQuickSorter() {
		return new QuickSorter<E>();
	}
}