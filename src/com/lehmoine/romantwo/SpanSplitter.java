package com.lehmoine.romantwo;

import java.util.ArrayList;
import java.util.List;

/*
 * This is general method for breaking up a list of items into
 * separate lists of adjacent equal objects.
 * 
 * [AABCCD] -> [AA] [B] [CC] [D]
 * 
 * It can also perform the exact opposite operation
 */

public class SpanSplitter<T> {
	
	
	/*
	 * Split the list into sublists of identical elements
	 * 
	 */
	
	public List<List<T>> split( List<T> list) {
		
		List<List<T>> result = new ArrayList<List<T>>();
		
		if( list.isEmpty() ) {
			return result;
		}
			
		T lastElement = list.get(0);
		
		List<T> runFragment = new ArrayList<T>();
		
		for( T thisElement : list ) {
			if( thisElement.equals(lastElement) ) {
				// Nothing to do here
			}
			else {
				result.add(runFragment);
				runFragment = new ArrayList<T>();
				
			}

			runFragment.add(thisElement);
			lastElement = thisElement;
		}
		
		if( ! runFragment.isEmpty() ) {
			result.add(runFragment);
		}
		
		return result;
	}
	
	/*
	 * Join a set of sub lists into a larger list
	 */
	List<T> join( List<List<T>> lists ) {

		List<T> result = new ArrayList<T>();
		
		for( List<T> list : lists ) {			
				result.addAll(list);
		}
		
		return result;
	}
}
