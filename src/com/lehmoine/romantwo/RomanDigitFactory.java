package com.lehmoine.romantwo;

import java.util.HashMap;
import java.util.Map;

public class RomanDigitFactory {

    static Map<Character, RomanDigitType> charToType; 
    
    static {
        charToType = new HashMap<Character, RomanDigitType>();
        charToType.put('I', RomanDigitType.I);
        charToType.put('V', RomanDigitType.V);
        charToType.put('X', RomanDigitType.X);
        charToType.put('L', RomanDigitType.L);
        charToType.put('C', RomanDigitType.C);
        charToType.put('D', RomanDigitType.D);
        charToType.put('M', RomanDigitType.M);
    }
    
    static RomanDigit newDigit( Character c, RomanDigit.OPERATION operation ) {
        RomanDigitType type = charToType.get(c);
        RomanDigit digit = new RomanDigit(type, operation);
        
        return digit;
    }
}
