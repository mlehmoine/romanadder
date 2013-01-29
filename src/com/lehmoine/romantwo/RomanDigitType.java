package com.lehmoine.romantwo;

import java.util.HashMap;

public class RomanDigitType implements IHasValue {
    
    public enum RUN_LENGTH { LONG, SHORT, VERYLONG };
    
    public static RomanDigitType I = new RomanDigitType(1,RUN_LENGTH.LONG,'I');
    public static RomanDigitType V = new RomanDigitType(5,RUN_LENGTH.SHORT,'V');
    public static RomanDigitType X = new RomanDigitType(10,RUN_LENGTH.LONG,'X');
    public static RomanDigitType L = new RomanDigitType(50,RUN_LENGTH.SHORT, 'L');
    public static RomanDigitType C = new RomanDigitType(100,RUN_LENGTH.LONG, 'C');
    public static RomanDigitType D = new RomanDigitType(500,RUN_LENGTH.SHORT, 'D');
    public static RomanDigitType M = new RomanDigitType(1000,RUN_LENGTH.VERYLONG, 'M');
    
    private static final HashMap<RomanDigitType, RomanDigitType> nextBiggerCharMap = new HashMap<RomanDigitType, RomanDigitType>();
    private static final HashMap<RomanDigitType, RomanDigitType> nextSmallerCharMap = new HashMap<RomanDigitType, RomanDigitType>();

    static {
        nextBiggerCharMap.put( I, V );
        nextBiggerCharMap.put( V, X );
        nextBiggerCharMap.put( X, L );
        nextBiggerCharMap.put( L, C );
        nextBiggerCharMap.put( C, D );
        nextBiggerCharMap.put( D, M );
        // M has no bigger type
        
        nextSmallerCharMap.put( M, D);
        nextSmallerCharMap.put( D, C);
        nextSmallerCharMap.put( C, L);
        nextSmallerCharMap.put( L, X);
        nextSmallerCharMap.put( X, V);
        nextSmallerCharMap.put( V, I);
        // I has no smaller value
       
    }
    
    private int value;
    private RUN_LENGTH runLength;
    private Character character; 
    
    
    private RomanDigitType(int value, RUN_LENGTH runLength, Character character) {
        this.value = value;
        this.runLength = runLength;
        this.character = character;
    }
    
    public Integer getValue() {
        return value;
    }
    
    public RUN_LENGTH getRunLength() {
        return runLength;
    }
   
    public Character getCharacter() {
        return character;
    }
    
    public RomanDigitType getNextSmallerDigit() {
        return nextSmallerCharMap.get(this);
    }
    
    public RomanDigitType getNextBiggerDigit() {
        return nextBiggerCharMap.get(this);
    }
    
    public RomanDigit newDigit(RomanDigit.OPERATION operation) {
        return new RomanDigit(this, operation);
    }
    
    public RomanDigit newAddDigit() {
        return new RomanDigit(this, RomanDigit.OPERATION.ADD );
    }
    
    public RomanDigit newSubtractDigit() {
        return new RomanDigit(this, RomanDigit.OPERATION.SUBTRACT );
    }

    public boolean equals(RomanDigitType other) {
        return this.value == other.value;
    }
    
    
}
