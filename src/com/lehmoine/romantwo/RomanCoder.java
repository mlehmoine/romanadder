package com.lehmoine.romantwo;

/*
 * What does this class do?
 * 
 * This class can convert between base 10 integers and roman numeral notation.
 */

public class RomanCoder {
    
    class SubtractionDetails {
        private boolean isSubtraction;
        private RomanDigitType subtractionCharacterType;
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
        public RomanDigitType getSubtractionCharacterType() {
            return subtractionCharacterType;
        }
        public void setSubtractionCharacterType(RomanDigitType subtractionCharacterType) {
            this.subtractionCharacterType = subtractionCharacterType;
        }
        public int getSubtractionValue() {
            return subtractionValue;
        }
        public void setSubtractionValue(int subtractionValue) {
            this.subtractionValue = subtractionValue;
        }
        
        public void reset() {
            isSubtraction = false;
            subtractionCharacterType = null;
            subtractionValue = 0;                   
        }
        
    }
    /*
     * This was relatively easy to write.
     */
    public int toInt( String romanNumberString ) {
        validate(romanNumberString);
        
        int value = 0;

        int charIndex = 0;
        int charLength = romanNumberString.length();
        
        // Iterate through every character but the last one.
        for( charIndex = 0; charIndex < charLength-1; charIndex++ ) {
            RomanDigitType thisDigitType = RomanDigitFactory.getDigitType(romanNumberString.charAt(charIndex));
            RomanDigitType nextDigitType = RomanDigitFactory.getDigitType(romanNumberString.charAt(charIndex+1));
            
            if( thisDigitType.getValue() >= nextDigitType.getValue() ) {
                value += thisDigitType.getValue();
            }
            else {
                value -= thisDigitType.getValue();
            }            
        }
        
        // The last character is always an add
        RomanDigitType lastDigitType = RomanDigitFactory.getDigitType(romanNumberString.charAt(charIndex));
        value += lastDigitType.getValue();
        
        return value;
    }


    /*
     * This was much harder to write than I expected.
     */
    public String fromInt( int number ) {
        String romanNumberString = new String();
        
        RomanDigitType thisCharType = RomanDigitFactory.getDigitType('M');

        RomanDigitType nextCharType = thisCharType.getNextSmallerDigit();

        SubtractionDetails subDetails = new SubtractionDetails();
        
        while( number > 0 ) {
            
            if( number >= thisCharType.getValue() ) {
                romanNumberString += thisCharType.getCharacter();
                number -= thisCharType.getValue();
            }
            else if( isSubtractionCase(number, thisCharType, subDetails) ) {
                romanNumberString += subDetails.getSubtractionCharacterType().getCharacter();
                romanNumberString += thisCharType.getCharacter();
                number -= ( subDetails.getSubtractionValue() );
            }
            else if ( (number < thisCharType.getValue()) && (number > nextCharType.getValue() ) ) { 
                romanNumberString += nextCharType.getCharacter();
                number -= nextCharType.getValue();
            }
            else {
                
                thisCharType = nextCharType;
                nextCharType = thisCharType.getNextSmallerDigit();
            }
        }
        
        return romanNumberString;
    }
    
    protected boolean isSubtractionCase( int number, RomanDigitType thisCharType, SubtractionDetails subDetails ) {
        RomanDigitType nextCharType = RomanDigitFactory.getDigitType('I');

        while( ! nextCharType.equals(thisCharType) ) {
            
            if( number == (thisCharType.getValue() - nextCharType.getValue() ) && nextCharType.getValue() != number ) {
                subDetails.setSubtractionValue(thisCharType.getValue() - nextCharType.getValue());
                subDetails.setSubtractionCharacterType(nextCharType);
                subDetails.setSubtraction(true);
                return true;
            }
            else if( (thisCharType.getValue() - number) < (nextCharType.getValue()) && (thisCharType.getValue() - nextCharType.getValue() != nextCharType.getValue())) {
                subDetails.setSubtractionValue(thisCharType.getValue() - nextCharType.getValue());
                subDetails.setSubtractionCharacterType(nextCharType);
                subDetails.setSubtraction(true);
                return true;
            }
            else {
                nextCharType = nextCharType.getNextBiggerDigit();
            }
        }
        
        subDetails.setSubtraction(false);        
        return false;
    }

    private void validate(String romanNumberString) {
        if( RomanNumber.validate(romanNumberString) == false ) {
            throw new IllegalArgumentException( romanNumberString + " is not a valid roman number.");
        }
    }
}
