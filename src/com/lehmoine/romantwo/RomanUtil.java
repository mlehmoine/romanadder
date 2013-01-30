package com.lehmoine.romantwo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;

@Deprecated
public class RomanUtil {
    // value of each digit
    static final HashMap<Character, Integer> valueMap = new HashMap<Character, Integer>();
    // The next most valuable digit relative to another
    static final HashMap<Character, Character> nextBiggerCharMap = new HashMap<Character, Character>();
    static final HashMap<Character, Character> nextSmallerCharMap = new HashMap<Character, Character>();

    static {
        // I use this static data to make decision about roman digits.
        // It's constant for all instances of Roman Digits.  Just lookup information.
        
        
        //
        // Initialize the value map.
        //
        valueMap.put( new Character('I'), new Integer(1) );
        valueMap.put( new Character('V'), new Integer(5) );
        valueMap.put( new Character('X'), new Integer(10) );
        valueMap.put( new Character('L'), new Integer(50) );
        valueMap.put( new Character('C'), new Integer(100) );
        valueMap.put( new Character('D'), new Integer(500) );
        valueMap.put( new Character('M'), new Integer(1000) );

        // Look at the value of each digit.
        // Sort them.
        // Find the next most valuable digit for each digit.
        Set<Character> keys = valueMap.keySet();
        ArrayList<Character> keyList = new ArrayList<Character>(keys);
        Collections.sort(keyList, new Comparator<Character>(){

            @Override
            public int compare(Character o1, Character o2) {
                return getRomanValue(o1) - getRomanValue(o2);
            }});

        Character lastCharacter = null;
        for( Character key : keyList ) {
            if( lastCharacter != null ) {
                nextBiggerCharMap.put( lastCharacter, key);
            }

            lastCharacter = key;
        }
        
        Collections.reverse(keyList);
        lastCharacter = null;
        for( Character key : keyList ) {
            if( lastCharacter != null ) {
                nextSmallerCharMap.put( lastCharacter, key);
            }

            lastCharacter = key;
        }
    }

    /*
     * Given a roman numeral return its value.
     */
    static public int getRomanValue( Character character ) {
        if( character == null) {
            return 0;
        }
        
        Integer i = valueMap.get(character);
        if( i == null ) {
            throw new IllegalArgumentException( character +  " is not a legal roman digit");
        }
        return i;
    }
    
    /* 
     * For the given character, return the next largest roman
     * numeral
     */
    static public Character getNextBiggerCharacter(Character thisChar) {
        return nextBiggerCharMap.get(thisChar);
    }

    /* 
     * For the given character, return the next largest roman
     * numeral
     */
    static public Character getNextSmallerCharacter(Character thisChar) {
        return nextSmallerCharMap.get(thisChar);
    }
    
    /*
     * Try to figure out if the roman numeral is valid
     */
    static public boolean validate(String romanNumber) {
        
        // Check to see if there are any invalid characters in the string.
        if( !romanNumber.matches("[IVXLCDM]+") ) {
            return false;
        }

        // Look for sequences of digits that are too long.
        // D L V: cannot be repeated
        // I X C M: No more than 3 in a row
        
        if( romanNumber.matches(".*(DD|LL|VV|IIII|XXXX|CCCC|MMMM).*") ) {
            return false;
        }
        
        return true;
    }    
}
