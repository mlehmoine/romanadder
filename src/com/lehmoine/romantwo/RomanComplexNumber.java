package com.lehmoine.romantwo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* General notes on Roman Numbers
 * 
 * General info on roman numbers 
 *  - http://en.wikipedia.org/wiki/Roman_numerals
 * 
 * values:
 * I: 1
 * V: 5
 * X: 10
 * L: 50
 * C: 100
 * D: 500
 * M: 1000
 */

public class RomanComplexNumber {

	/*
	 * Class Properties
	 */
	
	// I keep the roman numeral stored as two separate lists of
	// add and subtract characters.
	private List<Character> addList = new ArrayList<Character>();
	private List<Character> subtractList = new ArrayList<Character>();
	private String combinedValue;
		
	/*
	 * Given a roman number as an ascii string, create an
	 * equivalent RomanComplexNumber
	 */
	
	public RomanComplexNumber(String romanNumber) {
		if( romanNumber.isEmpty() ) {
			throw new RuntimeException("invalid roman number: empty string");
		}		

		if( ! RomanUtil.validate(romanNumber) ) {
			throw new RuntimeException("invalid roman number: bad format: " +  romanNumber);
		}
		
		char[] characters = romanNumber.toCharArray();
		int count = characters.length;
		int index = 0;

		// Iterate through all of the characters in the string.
		// Except the last one.  The last one is always an addition
		// character.
		//
		// Figure out which ones are 'adds' and which ones are
		// subtracts
		
		for( index = 0; index < (count-1); index++ ) {
			Character thisChar = characters[index];
			Character nextChar = characters[index+1];
			
			if( RomanUtil.getRomanValue(thisChar) < RomanUtil.getRomanValue(nextChar) ) {
				// The number on the left is smaller than the one on the right.
				// This indicates subtraction.
				subtractList.add(thisChar);
			}
			else {
				// The number on the left is larger or equal to the digit on
				// right.  This one indicates addition.
				addList.add(thisChar);
			}
		}
		
		// The last character is always an addition character
		Character lastChar = characters[index];
		addList.add(lastChar);
		
		combinedValue = romanNumber;
	}
	
	/*
	 * This is just a copy constructor.  It makes this number
	 * equal to the other number.
	 */
	public RomanComplexNumber( RomanComplexNumber other ) {
		
		this.setEqual(other);
	}
	
	/*
	 * Add this number to the other number and return the result
	 * to the caller.
	 */
	public RomanComplexNumber add( RomanComplexNumber other ) {
		// Adding is easy.  I just concatenate the add subtract lists
		// and sort them by order for easy handling.
		
		// Sets 'sum' equal to 'this'
		RomanComplexNumber sum = new RomanComplexNumber(this);

		// Then... Adds the value of 'other' to 'sum'
		sum.addList.addAll( other.addList );
		sortCharacters(sum.addList);
		
		sum.subtractList.addAll( other.subtractList );		
		sortCharacters(sum.subtractList);

		sum.simplify();
		
		return sum;
	}
	
	/*
	 * Take the complex roman numeral form of just adds & subtracts
	 * and convert it into the the most compact readable human string
	 * expression possible.
	 */
	@Override
	public String toString() {

	    return combinedValue;
	}

    private /*String*/ void updateCombinedValue() {
        // Build the final string.
		// Start with the add string
		List<Character> temp = new ArrayList<Character>();
		temp.addAll(addList);
		
		// Iterate over each subtraction character.  Start from the end
		// and work our way forward.
		int subOffset = subtractList.size();
		while( --subOffset >= 0 ) {
			Character subChar = subtractList.get(subOffset);
			
			boolean inserted = false;
			
			// Iterate over each addition character.  Start from the back and
			// work towards the front.
			int tempOffset = temp.size();
			while( --tempOffset >= 0 ) {
				Character addChar = temp.get(tempOffset);
				
				// Look at the relative values of the two characters.
				// If the subtraction character if less than the the
				// addition character, 
				if( RomanUtil.getRomanValue(subChar) < RomanUtil.getRomanValue(addChar) ) {
					// Insert the subchar in the position onces held by the
					// add char.  The add char gets moved to the right.
					temp.add( tempOffset, subChar);
					
					inserted = true;
					break;
				}
			}
			
			if( inserted == false ) {
				throw new RuntimeException( "unabled to insert subtraction character" );
			}
		}

		
		//sortCharacters(temp);
		//return characterListToString(temp);
		combinedValue = characterListToString(temp);
    }

	/*
	 * There did not seem to be an easy way to convert a List of Characters back
	 * into a string.  This method is dirty and easy.  This way might not be the best.
	 * Improve it if possible.
	 */
	private String characterListToString(List<Character> temp) {
		StringBuffer sb = new StringBuffer();
		
		for( Character x : temp ) {
			sb.append(x);
		}
		return sb.toString();
	}

	/*
	 * Simplify the internal representation of this roman numeral.
	 *  - Use digits in the subtraction list to cancel out digits in the addition list.
	 *  - Combine digits in the add list into bigger digits
	 *  - Combine digits in the add list into subtraction sequences.  move the subtraction
	 *    digits to the subtract list.
	 */
	private void simplify() {
		// Run this loop until the content of the add and subtract lists
		// stops changing on calls to cancelCharacters or compactCharacters.
		// When the functions stop altering the lists, we've reached the final
		// form of the roman numeral.
		

		RomanComplexNumber before = null;
		
		do {
		    before = new RomanComplexNumber(this);

		    expandAdds2();		    
		    
			// Allow matching adds and subtracts to cancel each other out.
			cancelCharacters();
			
			// Look for contigiuous groups of digits that I can convert to
			// larger digits.
			compactCharacters();

			// Stop the loop when both cancelCharacters and compactCharacters
			// fail to make any changes to the content of addList and subtractList

		} while( ! this.equals(before) );
		
		updateCombinedValue();
	}
	
    private boolean expandAdds2() {

        boolean changed = false;
        
        // Do we have an intersection between the add & subtract lists?
        // Yes:  
        if( subtractList.size() > 0
                && (addList.size() < subtractList.size()
                        || (addList.size() <= subtractList.size() && !containsAny(addList,subtractList) )  ) ) {
            
//            if( containsAny(addList, subtractList) ) {
//                return false;
//            }
            
                /* ! containsAny( addList, subtractList) ||  addList.size() < subtractList.size() */ 
            // There is no intersection between the two lists...
            
            // We need to find one the characters in the add list
            // and replace it with smaller value problems.
            
            // Source character?
            // Destination character?
            
            Character biggestSubractCharacter = subtractList.get(0);
            
            SpanSplitter<Character> splitter = new SpanSplitter<Character>();
            
            List<List<Character>> lists = splitter.split(addList);
            Collections.reverse(lists);
            
            // For each sublist...
            for( List<Character> thisList : lists ) {
                Character thisChar = thisList.get(0);
                
                if( RomanUtil.getRomanValue(thisChar) > RomanUtil.getRomanValue(biggestSubractCharacter)) {
                    int newCharCount = RomanUtil.getRomanValue(thisChar) / RomanUtil.getRomanValue(biggestSubractCharacter);
                    thisList.remove(0);
                    thisList.addAll(Collections.nCopies(newCharCount, biggestSubractCharacter));
                    
                    changed = true;
                    break;
                }
            }

            List<Character> newAddList = new ArrayList<Character>();
            
            for( List<Character> thisList : lists ) {
                newAddList.addAll(thisList);
            }
            
            // Sort the new add list
            sortCharacters(newAddList);
            
            addList = newAddList;            
        }

        return changed;
    }

    /*
     * Return true if any elements from listTwo
     * exist in listOne
     */
    private boolean containsAny(List<Character> listOne,
            List<Character> listTwo) {
        for( Character i : listTwo) {
            if( listOne.contains(i)) {
                return true;
            }
        }
        
        return false;
    }

    /*
	 * Look for matching characters the two lists and remove the matching ones
	 * from both lists.
	 * 
	 * The lists are sorted.
	 */
	protected boolean cancelCharacters() {

		int offsetOne = 0;
		int offsetTwo = 0;
		
		boolean changed = false;
		
		// Keep looking till I reach the of at least one of the lists.
		while( offsetOne < addList.size() && offsetTwo < subtractList.size() ) {
			Character charOne = addList.get(offsetOne);
			Character charTwo = subtractList.get(offsetTwo);
			
			int valueOne = RomanUtil.getRomanValue(charOne);
			int valueTwo = RomanUtil.getRomanValue(charTwo);
			
			if( valueOne == valueTwo ) {
				// These are equal.
				// They cancel each other out.  Remove them.
				addList.remove(offsetOne);
				subtractList.remove(offsetTwo);
				
				changed = true;
			}
			else if( valueOne > valueTwo ) {
				// Not each.  move to the position in one of the lists.
				offsetOne++;
			}
			else { // valueTwo > ValueOne
				// Not each.  move to the position in one of the lists.
				offsetTwo++;
			}
		}

		sortCharacters(addList);
		sortCharacters(subtractList);
		
		return changed;
	}

	/*
	 * Look for sequences of repeated characters in the add list that can be replaced
	 * with bigger digits or subtraction sequences
	 * 
	 * The way I'm doing this lists may be a bit inefficient.  It would be quicker it
	 * iterate over the original list without breaking it up.  I got hung up and iterating
	 * it while trying to change it without messing it up.  I settled on this approach because it
	 * worked, was simpler, and easier to read.
	 */
	protected boolean compactCharacters(  ) {
		
		boolean changed = false;

		// Split the add list into set of lists each containing a group of matching digits.
		//
		// [LCMXXVIII] -> [L][C][M][XX][V][III]
		//
		 
		SpanSplitter<Character> splitter = new SpanSplitter<Character>();
		List<List<Character>> lists = splitter.split(addList);
		
		// For each sublist...
		for( List<Character> thisList : lists ) {
			Character thisCharacter = thisList.get(0);
			Character nextCharacter = RomanUtil.getNextBiggerCharacter(thisCharacter);
			int maxSpan = getMaxRunForCharacter(thisCharacter);
			
			int spanLength = thisList.size();
			
			if( spanLength >= maxSpan ) {
				// We have enough characters to do a replacement.
				// Replace with next biggest character.
				
				List<Character> tempList = new ArrayList<Character>(thisList.subList(maxSpan, thisList.size()));
				thisList.clear();
				thisList.addAll(tempList);
				
				// Put a new character in its place.
				thisList.add( nextCharacter);
				
				changed = true;
			}
			else if ( spanLength == 4 )  { // 
				// We have just enough characters replace this span
				// with a subtraction sequence
				
				// delete the elements in the span
				List<Character> tempList = new ArrayList<Character>(thisList.subList(spanLength, thisList.size()));
				thisList.clear();
				thisList.addAll(tempList);
				
				// Add two mode
				thisList.add( nextCharacter );				
				subtractList.add( thisCharacter );
				
				changed = true;
			}
			else {
				// Span is too short.  Leave it along.
			}
		}

		// Rebuild the list from the split lists.
		List<Character> joinedList = splitter.join(lists);
		addList.clear();
		addList.addAll(joinedList);
		
		sortCharacters(addList);
		sortCharacters(subtractList);
		
		return changed;
	}

	/*
	 * For the given character, what is the maximum number of contiguous
	 * numbers that I can have before I must replace them with the next
	 * biggest roman number digit
	 */
	private static int getMaxRunForCharacter(Character runCharacter) {
		int runCharValue = RomanUtil.getRomanValue(runCharacter);
		Character nextChar = RomanUtil.getNextBiggerCharacter(runCharacter);

		if( nextChar != null ) {
			int nextCharValue = RomanUtil.getRomanValue(nextChar);
			
			return nextCharValue / runCharValue;			
		}
		else {
			return 5;
		}
	}

	/*
	 * Sort a list of roman numerals 
	 */
	static protected void sortCharacters( List<Character> list ) {
		Collections.sort( list, new Comparator<Character>() {

			@Override
			public int compare(Character o1, Character o2) {
				return RomanUtil.getRomanValue(o2) - RomanUtil.getRomanValue(o1);
			}
		});
	}

	public void setEqual( RomanComplexNumber other ) {
	    this.addList.clear();
	    this.subtractList.clear();
	    
	    this.addList.addAll(other.addList);
        this.subtractList.addAll(other.subtractList);
        this.combinedValue = other.combinedValue;
	}

    public boolean equals(RomanComplexNumber other) {

        if( ! this.addList.equals(other.addList) ) {
            return false;
        }
        if( ! this.subtractList.equals(other.subtractList) ) {
            return false;
        }
        
        return true;
    }
	
	
}

