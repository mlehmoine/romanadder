package com.lehmoine.romantwo;

/*
 * What does this class do?
 * 
 * This class can convert between base 10 integers and roman numeral notation.
 */

public class RomanCoder {
    
    class SubtractionDetails {
        private boolean isSubtraction;
        private Character subtractionCharacter;
        private int subtractionValue;

        public SubtractionDetails() {
            reset();
        }

        public boolean isSubtraction() {
            return isSubtraction;
        }
        public void setSubtraction(boolean isSubtraction) {
            this.isSubtraction = isSubtraction;
        }
        public Character getSubtractionCharacter() {
            return subtractionCharacter;
        }
        public void setSubtractionCharacter(Character subtractionCharacter) {
            this.subtractionCharacter = subtractionCharacter;
        }
        public int getSubtractionValue() {
            return subtractionValue;
        }
        public void setSubtractionValue(int subtractionValue) {
            this.subtractionValue = subtractionValue;
        }
        
        public void reset() {
            isSubtraction = false;
            subtractionCharacter = null;
            subtractionValue = 0;                   
        }
        
    }
    
    public int toInt( String romanNumberString ) {
        validate(romanNumberString);
        
        int value = 0;

        int charIndex = 0;
        int charLength = romanNumberString.length();
        
        // Iterate through every character but the last one.
        for( charIndex = 0; charIndex < charLength-1; charIndex++ ) {
            Character thisChar = romanNumberString.charAt(charIndex);
            Character nextChar = romanNumberString.charAt(charIndex+1);            
            
            if( RomanUtil.getRomanValue(thisChar) >= RomanUtil.getRomanValue(nextChar) ) {
                value += RomanUtil.getRomanValue(thisChar);
            }
            else {
                value -= RomanUtil.getRomanValue(thisChar);
            }            
        }
        
        // The last character is always an add
        value += RomanUtil.getRomanValue(romanNumberString.charAt(charIndex));
        
        return value;
    }

    
    public String fromInt( int number ) {
        String romanNumberString = new String();
        
        Character thisChar = 'M';
        int thisCharValue = RomanUtil.getRomanValue(thisChar);

        Character nextChar = RomanUtil.getNextSmallerCharacter(thisChar);
        int nextCharValue = RomanUtil.getRomanValue(nextChar);

        SubtractionDetails subDetails = new SubtractionDetails();
        
        while( number > 0 ) {            
            if( number >= thisCharValue ) {
                romanNumberString += thisChar;
                number -= thisCharValue;
            }
            else if( isSubtractionCase(number, thisChar, subDetails) ) {
                romanNumberString += subDetails.getSubtractionCharacter();
                romanNumberString += thisChar;
                number -= ( subDetails.getSubtractionValue() );
            }
            else if ( (number < thisCharValue) && (number > nextCharValue ) ) { 
                romanNumberString += nextChar;
                number -= nextCharValue;
            }
            else {
                
                thisChar = nextChar;
                thisCharValue = nextCharValue;
                
                nextChar = RomanUtil.getNextSmallerCharacter(thisChar);
                nextCharValue = RomanUtil.getRomanValue(nextChar);
            }
        }
        
        return romanNumberString;
    }
    
    protected boolean isSubtractionCase( int number, Character thisChar, SubtractionDetails subDetails ) {
        int thisCharValue = RomanUtil.getRomanValue(thisChar);
        
        Character nextChar = RomanUtil.getNextSmallerCharacter(thisChar);
        int nextCharValue = RomanUtil.getRomanValue(nextChar);

        while( nextCharValue > 0 ) {
            
            if( number == (thisCharValue - nextCharValue ) && nextCharValue != number ) {
                subDetails.setSubtractionValue(thisCharValue - nextCharValue);
                subDetails.setSubtractionCharacter(nextChar);
                subDetails.setSubtraction(true);
                return true;
            }
            else {
                nextChar = RomanUtil.getNextSmallerCharacter(nextChar);
                nextCharValue = RomanUtil.getRomanValue(nextChar);
            }
        }
        
        subDetails.setSubtraction(false);        
        return false;
    }

    private void validate(String romanNumberString) {
        if( RomanUtil.validate(romanNumberString) == false ) {
            throw new IllegalArgumentException( romanNumberString + " is not a valid roman number.");
        }
    }
}
