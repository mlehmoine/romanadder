package com.lehmoine.romantwo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

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

/*
 * Here are some things that I've learned about roman numerals through experimentation.
 * 
 * 1 - This was harder than I expected it to be.
 * 
 */

public class RomanNumber {

    /*
     * Class Properties
     */

    private LinkedList<RomanDigit> digits = new LinkedList<RomanDigit>();
    
    
    /*
     * Given a roman number as an ascii string, create an
     * equivalent RomanComplexNumber
     */
    public RomanNumber(String romanNumber) {        
        parseDigits(romanNumber);
    }
    
    /*
     * This is just a copy constructor.  It makes this number
     * equal to the other number.
     */
    public RomanNumber( RomanNumber other ) {

        this.setEqual(other);
    }

    /*
     * Add this number to the other number and return the result
     * to the caller.
     */
    public RomanNumber add( RomanNumber other ) {
        // Adding is easy.  I just concatenate the add subtract lists
        // and sort them by order for easy handling.

        // Sets 'sum' equal to 'this'
        RomanNumber sum = new RomanNumber(this);
        sum.digits.addAll(other.digits);
        sum.reorderSubtracts();
        sum.normalize();

        return sum;
    }

    /*
     * Take the complex roman numeral form of just adds & subtracts
     * and convert it into the the most compact readable human string
     * expression possible.
     */
    @Override
    public String toString() {

        return digits.toString();
    }

    /*
     * Make this number equal to that other number
     */
    public void setEqual( RomanNumber other ) {

        this.digits.clear();
        for( RomanDigit digit : other.digits ) {
            this.digits.add( new RomanDigit(digit));
        }
    }

    /*
     * basic equality test.
     */
    public boolean equals(RomanNumber other) {

        return digits.equals(other.digits);
   }

    /*
     *  
     */
    public boolean validate() {
        int i;
        
        for( i = 0; i < digits.size()-1; i++ ) {
            RomanDigit thisDigit = digits.get(i);
            RomanDigit nextDigit = digits.get(i+1);
            
            if( thisDigit.isAdd() && thisDigit.getValue().compareTo(nextDigit.getValue() ) < 0 ) {
                return false;
            }
            
        }
        
        RomanDigit lastDigit = digits.get(i);
        if( lastDigit.isSubtract() ) {
            return false;            
        }
        
        return true;
    }

    /*
     * Format the roman number as a string
     * and return it to the caller
     */
    public String format() {
        StringBuffer sb = new StringBuffer();
        for( RomanDigit digit : digits ) {
            sb.append(digit.getCharacter());
        }
        return sb.toString();
    }

    /*
     * Try to figure out if the roman numeral is valid
     */
    static public boolean validate(String romanNumberString) {
        
        try {
            RomanNumber number = new RomanNumber(romanNumberString);
            return number.validate();
        }
        catch(Exception e) {
            return false;
        }
    }    

    /*
     * Given a roman number in string format, set this instance of RomanNumber
     * equal to the value of the roman number.
     */
    private void parseDigits(String romanNumber) {

        if( romanNumber.isEmpty() ) {
            throw new RuntimeException("invalid roman number: empty string");
        }       

        digits.clear();
        
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
            RomanDigitType thisCharType = RomanDigitFactory.getDigitType(characters[index]);
            RomanDigitType nextCharType = RomanDigitFactory.getDigitType(characters[index+1]);

            if( thisCharType.getValue() < nextCharType.getValue() ) {
                // The number on the left is smaller than the one on the right.
                // This indicates subtraction.
                RomanDigit digit = thisCharType.newSubtractDigit();
                digits.add(digit);
            }
            else {
                // The number on the left is larger or equal to the digit on
                // right.  This one indicates addition.
                RomanDigit digit = thisCharType.newAddDigit();
                digits.add(digit);
            }
        }

        // The last character is always an addition character
        RomanDigitType lastCharType = RomanDigitFactory.getDigitType(characters[index]);
        RomanDigit lastDigit = lastCharType.newAddDigit();
        digits.add(lastDigit);
    }

    /*
     * Simplify the internal representation of this roman numeral.
     *  - Look for add & substract characters that cancel each other out.
     *  - Make bigger digits into smaller digits if they can cancel out subtraction characters
     */
    private void normalize() {
        // Run this loop until the operations are no longer able to change the
        // number and we've stopped at a reasonable roman number

        int count = 0;
        boolean changed;
                
        do {
            changed = false;
            
            // Allow matching adds and subtracts to cancel each other out.
            changed |= cancelCharacters();

            // Look for contiguous groups of digits that I can convert to
            // larger digits.
            changed |= compactAddCharacters();
            changed |= balanceAddSubtract();            
            if( changed == false ) {
                if( ! validate() ) {
                    changed = reorderSubtracts();
                
                    if( !validate() ) {
                        throw new RuntimeException( "number is not valid: " + digits.toString() );
                    }
                }
            }
            
            // This is a safety check to break a potential infinite loop.
            if( count > 500 ) {
                throw new RuntimeException("killing infinite loop");
            }
            count++;
            
        } while( changed == true );        
    }

    /*
     * Look for characters that cancel each other out.
     * Look for an add and a subtract of the same value.
     * 
     */
    private boolean cancelCharacters() {

        boolean changed = false;
        
        List<List<RomanDigit>> lists = groupDigitsByValue();
        
        // List are grouped by value;
        for( List<RomanDigit> list : lists ) {

            Stack<RomanDigit> cancelStack = new Stack<RomanDigit>();
            // bias > 0 : surplus of adds
            // bias < 0 : surplus of subs
            int bias = 0;
            
            for( RomanDigit digit : list ) {
                if( digit.getOperation().equals(RomanDigit.OPERATION.ADD) && bias < 0 ) {
                    bias++;
                    RomanDigit r = cancelStack.pop();
                    digits.remove(r);
                    digits.remove(digit);
                }
                else if( digit.getOperation().equals(RomanDigit.OPERATION.SUBTRACT) && bias > 0 ) {
                    bias--;
                    RomanDigit r = cancelStack.pop();
                    digits.remove(r);
                    digits.remove(digit);
                }
                else {
                    if( digit.getOperation().equals(RomanDigit.OPERATION.ADD)) {
                        cancelStack.push(digit);
                        bias++;
                    }
                    else if( digit.getOperation().equals(RomanDigit.OPERATION.SUBTRACT) ) {
                        cancelStack.push(digit);
                        bias--;
                    }
                    else {
                        throw new RuntimeException( "unknown RomanDigit operation");
                    }
                }               
            }            
        }
        return changed;
    }

    /*
     * Look for subtracts that can be partially offset by an add.
     * That allows us to get rid of an add character
     */
    private boolean balanceAddSubtract() {
        
        int i;
        int j;
        
        for( i = 0; i < digits.size(); i++ ) {
            RomanDigit a = digits.get(i);
            
            // We disregard anything that's an add
            if( a.isAdd() ) {
                continue;
            }
            
            for( j = i + 1; j < digits.size(); j++ ) {
                RomanDigit b = digits.get(j);
                
                if( b.isSubtract() ) {
                    continue;
                }
                
                if( a.getValue().equals( 2*b.getValue() )) {
                    int replaceOffset = digits.indexOf(a);
                    
                    // Replace the first digit with a smaller digit
                    digits.remove(replaceOffset);
                    digits.add(replaceOffset, b.getType().newSubtractDigit() );
                    
                    // Remove the second digit completely
                    digits.remove(b);
                    return true;
                }
            }
        }
        
        return false;
    }
    
    /*
     * Look for contiguous groups of add digits that can be replaced with more compact
     * expressions of other characters
     */
    private boolean compactAddCharacters() {
        boolean changed = false;        

        // Filter out the adds
        ListFilter<RomanDigit> addFilter = new ListFilter<RomanDigit>();
        List<RomanDigit> addDigits = addFilter.filter(digits, new IFilterCondition<RomanDigit>(){

            @Override
            public boolean include(RomanDigit element) {
                return element.getOperation().equals(RomanDigit.OPERATION.ADD);
            }});

        SpanSplitter<RomanDigit> splitter = new SpanSplitter<RomanDigit>();
        List<SpanSplitter.SpanInfo<RomanDigit>> lists = splitter.split(addDigits);
        
        changed = compactLists(lists);
        
        if( canEncodeSubtracts() == false ) {
            // decompose one of the add characters
            decomposeAddDigits();
            changed = true;
        }
                        
        return changed;
        
    }
    
    /*
     * Look for spans of continguous digits that can replaced by more compact sequences
     * of digits and replace them.
     */
    private boolean compactLists(List<SpanSplitter.SpanInfo<RomanDigit>> lists) {
        
        boolean changed = false;
        
        for( SpanSplitter.SpanInfo<RomanDigit> list : lists ) {
            List<RomanDigit> elements = list.getElements();
            RomanDigitType type = list.getElements().get(0).getType();
            RomanDigitType nextBiggerChar = type.getNextBiggerDigit();
            
            RomanDigit firstDigit = elements.get(0);
            
            switch( type.getRunLength() ) {
            case SHORT:
                if( elements.size() >= 2 ) {
                    RomanDigit replacement = nextBiggerChar.newDigit(RomanDigit.OPERATION.ADD);
                    List<RomanDigit> replacementList = new ArrayList<RomanDigit>();
                    replacementList.add(replacement);
                    replaceDigits( firstDigit, 2, replacementList );
                    changed = true;
                }                
                break;
              
            case LONG:
                if( elements.size() >= 5 ) {
                    RomanDigit replacement = nextBiggerChar.newDigit(RomanDigit.OPERATION.ADD);
                    List<RomanDigit> replacementList = new ArrayList<RomanDigit>();
                    replacementList.add(replacement);
                    replaceDigits( firstDigit, 5, replacementList );
                    changed = true;
                }
                else if( elements.size() == 4 ) {
                    List<RomanDigit> replacementList = new ArrayList<RomanDigit>();
                    replacementList.add(type.newSubtractDigit());
                    replacementList.add(nextBiggerChar.newAddDigit());
                    replaceDigits( firstDigit, 4, replacementList );
                    changed = true;
                }
                
                break;
                
            case VERYLONG:
                // These is nothing that can be done for this type of character
                // to make repeated sequences any shorter.
                break;
                
            default:
                throw new RuntimeException("illegal run length");
             
            }
            
            if( changed == true ) {
                break;
            }
        }
        return changed;
    }

    /*
     * Sort the add digits by value.
     * Then, place the subtraction digits into the number in a place that does
     * not violate the roman number encoding rules.
     */
    private boolean reorderSubtracts() {
        
        // Filter out the adds
        List<RomanDigit> addDigits = getAddDigits();
        sortDigits(addDigits);

        // Filter out the subtracts
        List<RomanDigit> subtractDigits = getSubtractDigits(); 
        sortDigits(subtractDigits);
        
        LinkedList<RomanDigit> reorderedDigits = new LinkedList<RomanDigit>();
        
        ListIterator<RomanDigit> addIterator = addDigits.listIterator(addDigits.size());
        ListIterator<RomanDigit> subIterator = subtractDigits.listIterator(subtractDigits.size());

        RomanDigit nextSubtractDigit = null;
        if( subIterator.hasPrevious() ) {
            nextSubtractDigit = subIterator.previous();
        }
        while( addIterator.hasPrevious() ) {
            RomanDigit thisAddDigit = addIterator.previous();
            reorderedDigits.push(thisAddDigit);
            
            if( nextSubtractDigit != null ) {
                if( nextSubtractDigit.getValue().compareTo(thisAddDigit.getValue()) < 0 ) {
                    reorderedDigits.push(nextSubtractDigit);
                    
                    if( subIterator.hasPrevious() ) {
                        nextSubtractDigit = subIterator.previous();                      
                    }
                    else {
                        nextSubtractDigit = null;
                    }
                }
            }
        }
                
        LinkedList<RomanDigit> oldDigits = digits;
        digits = reorderedDigits;
        
        return ! digits.equals(oldDigits);
    }

    /*
     * Return a sublist containing just the add characters.
     * The returned list is shallow.  Digits are not duplicated.
     */
    private List<RomanDigit> getAddDigits() {
        ListFilter<RomanDigit> addFilter = new ListFilter<RomanDigit>();
        List<RomanDigit> addDigits = addFilter.filter(digits, new IFilterCondition<RomanDigit>(){

            @Override
            public boolean include(RomanDigit element) {
                return element.getOperation().equals(RomanDigit.OPERATION.ADD);
            }});
        return addDigits;
    }

    /*
     * Return a sublist containing only the subtract digits.
     * The returned list is shallow.  Digits are not duplicated.
     */
    private List<RomanDigit> getSubtractDigits() {
        ListFilter<RomanDigit> subtractFilter = new ListFilter<RomanDigit>();
        List<RomanDigit> subtractDigits = subtractFilter.filter(digits, new IFilterCondition<RomanDigit>(){

            @Override
            public boolean include(RomanDigit element) {
                return element.getOperation().equals(RomanDigit.OPERATION.SUBTRACT);
            }});
        return subtractDigits;
    }    

  /*
     * Sort these digits buy absolute value.  Adds are equivalent to subtracts
     */
    private void sortDigits(List<RomanDigit> theseDigits) {

        Collections.sort( theseDigits, new Comparator<RomanDigit>(){

            @Override
            public int compare(RomanDigit o1, RomanDigit o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
    }

    /*
     * Return true if all of the subtraction characters can be legally encoded
     * in the roman numeral without breaking any of the encoding rules. 
     */
    private boolean canEncodeSubtracts() {
        
        int subtractCount = 0;
        int addCount = 0;
        
        //
        // Create a subset containing just the subtract characters
        //
        
        List<RomanDigit> subtractDigits = getSubtractDigits();

        //
        // Count up the number of adds & subtracts.
        // Figure out if we have too many subtracts.
        // 
        
        subtractCount = subtractDigits.size();
        addCount = digits.size() - subtractCount;
            
        if( subtractCount > addCount ) {
            // We have too many subtracts
            return false;
        }
        
        //
        // Group the subtract characters up by value.
        // Figure out if we have too many subtracts for any 
        // one value
        //
        
        ListGrouper<RomanDigit> g = new ListGrouper<RomanDigit>();
        List<List<RomanDigit>> groups = g.groupBy(subtractDigits, new Comparator<RomanDigit>(){

            @Override
            public int compare(RomanDigit arg0, RomanDigit arg1) {
                return arg0.getValue().compareTo(arg1.getValue());
            }});
        
        for( List<RomanDigit> group : groups ) {
            if( group.size() > 1 ) {
                return false;
            }
        }

        return true;
    }

    /*
     * Group the digits into sublists grouped by value.
     * Put the Is in a list.
     * Put the Vs in a list.
     * Etc...
     */

    private List<List<RomanDigit>> groupDigitsByValue() {
        ListGrouper<RomanDigit> g = new ListGrouper<RomanDigit>();
        List<List<RomanDigit>> lists = g.groupBy(digits, new Comparator<RomanDigit>(){

            @Override
            public int compare(RomanDigit o1, RomanDigit o2) {
                return o1.getValue().compareTo(o2.getValue());
            }});
        return lists;
    }

    private void decomposeAddDigits() {
        
        RomanDigitType smallestAddDigitType = null;
        RomanDigitType smallestSubtractDigitType = null;
        
        ListIterator<RomanDigit> i = digits.listIterator(digits.size());
        
        while( i.hasPrevious() && ( smallestAddDigitType == null || smallestSubtractDigitType == null ) ) {
            RomanDigit thisDigit = i.previous();
            
            if( thisDigit.isAdd() && smallestAddDigitType == null ) {
                smallestAddDigitType = thisDigit.getType();
            }
            else if( thisDigit.isSubtract() && smallestSubtractDigitType == null) {
                smallestSubtractDigitType = thisDigit.getType();
            }
        }
        
        if( smallestAddDigitType == null || smallestSubtractDigitType == null ) {
            throw new RuntimeException( "something bad has happened");
        }
        
        LinkedList<RomanDigitType> decomposeList = new LinkedList<RomanDigitType>();
        
        RomanDigitType thisType = smallestSubtractDigitType;
        
        while( true ) {
            thisType = thisType.getNextBiggerDigit();            

            decomposeList.push(thisType);
            
            if( thisType.equals(smallestAddDigitType) ) {
                break;
            }
        }
        
        while( decomposeList.size() > 0  ) {
            thisType = decomposeList.pop();
            RomanDigit digitToExplode = thisType.newAddDigit();
            RomanDigitType smallerDigitType = thisType.getNextSmallerDigit();
            
            int numSmallerDigits = digitToExplode.getValue() / smallerDigitType.getValue();
            RomanDigit smallerDigit = smallerDigitType.newAddDigit();
            
            digits.remove(digitToExplode);
            
            for( int j = 0; j < numSmallerDigits; j++ ) {
                digits.add(smallerDigit);
            }            
        }        
    }

    private void replaceDigits(RomanDigit element, int count,
            List<RomanDigit> replacementList) {
        
        int i = 0;
        for( i = 0; i < count; i++ ) {
            digits.remove(element);
        }
        digits.addAll(replacementList);
    }
        

}

