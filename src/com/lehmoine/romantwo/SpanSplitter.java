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
	
	static public class SpanInfo<T> {
	    private int offset;
	    private List<T> elements;

	    public SpanInfo(int offset, List<T> elements) {
	        this.offset = offset;
	        this.elements = elements;
	    }
	    
	    public int getOffset() {
            return offset;
        }
        public List<T> getElements() {
            return elements;
        }

        @Override
        public String toString() {
            // TODO Auto-generated method stub
            return super.toString();
        }
	    
	    
	}
	
	/*
	 * Split the list into sublists of identical elements
	 * 
	 */
	
	public List<SpanInfo<T>> split( List<T> list) {
		
		List<SpanInfo<T>> result = new ArrayList<SpanInfo<T>>();
		
		if( list.isEmpty() ) {
			return result;
		}
			
		T lastElement = list.get(0);
		
		List<T> runFragment = new ArrayList<T>();
		
		int index = 0;
		int spanStart = 0;
		for( T thisElement : list ) {
			if( thisElement.equals(lastElement) ) {
				// Nothing to do here
			}
			else {
				result.add(new SpanInfo<T>(spanStart, runFragment));
				runFragment = new ArrayList<T>();
				
				spanStart = index;
				
			}

			runFragment.add(thisElement);
			lastElement = thisElement;
			
			index++;
		}
		
		if( ! runFragment.isEmpty() ) { 		    
		    
		    result.add(new SpanInfo<T>(spanStart, runFragment));
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
