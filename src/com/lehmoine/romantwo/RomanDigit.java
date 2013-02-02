package com.lehmoine.romantwo;

public class RomanDigit implements IHasValue {

    public enum OPERATION {
        ADD, SUBTRACT
    }
    
    private RomanDigitType type;
    private OPERATION operation;
    
    public RomanDigit(RomanDigitType type, OPERATION operation) {
        this.type = type;
        this.operation = operation;
    }
    
    public RomanDigit(RomanDigit other) {
        this.type = other.type;
        this.operation = other.operation;
    }
    
    public OPERATION getOperation() {
        return operation;
    }


    @Override
    public Integer getValue() {
        return type.getValue();
    }
    
    @Override
    public Character getCharacter() {
        return type.getCharacter();
    }
    
    public RomanDigitType getType() {
        return type;
    }
    
    public boolean isAdd() {
        return operation.equals(OPERATION.ADD);
    }
    
    public boolean isSubtract() {
        return operation.equals(OPERATION.SUBTRACT);
    }

    @Override
    public boolean equals(Object o) {
        RomanDigit other = (RomanDigit) o;
        
        if( this.type.equals(other.type) && this.operation.equals(other.operation) ) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public String toString() {
        if( this.isAdd() ) {
            return "+" + type.getCharacter().toString();            
        }
        else if( this.isSubtract() ) {
            return "-" + type.getCharacter().toString();            
        }
        else {
            return "unknown";
        }
    }
}
