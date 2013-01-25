package com.lehmoine.romantwo;

public class ExpOne {

    /**
     * @param args
     */
    public static void main(String[] args) {

        // What is the maximum # of subtracts I'm likely to see in a roman number;
        
        int maxSubtracts = 0;
        
        int i;
        
        for( i = 1; i < 2000; i++ ) {
            RomanCoder coder = new RomanCoder();
            String romanNumber = coder.fromInt(i);
            int subtracts = countSubtracts(romanNumber);
            if( subtracts > maxSubtracts) {
                maxSubtracts = subtracts;
            }
        }

        System.out.println( "max = " + maxSubtracts );
        
    }

    static int countSubtracts( String romanNumber ) {
        int i;
        
        int subtracts = 0;
        for( i = 0; i < romanNumber.length()-1 ; i++ ) {
            Character thisChar = romanNumber.charAt(i);
            Character nextChar = romanNumber.charAt(i+1);
            
            if( RomanUtil.getRomanValue(thisChar) < RomanUtil.getRomanValue(nextChar)) {
               subtracts++; 
            }
        }

        return subtracts;
    }
    
}
