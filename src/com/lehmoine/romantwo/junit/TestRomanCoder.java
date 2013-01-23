package com.lehmoine.romantwo.junit;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.lehmoine.romantwo.RomanCoder;

public class TestRomanCoder {

    @Before
    public void setUp() {
        
    }
    
    @After
    public void tearDown() {

    }
    
    @Test
    public void testFromInt() {
        RomanCoder coder = new RomanCoder();

        Map<Integer,String> vectors = new HashMap<Integer,String>();
        vectors.put( 1, "I");
        vectors.put( 5, "V");
        vectors.put( 2, "II");
        vectors.put( 3, "III");
        vectors.put( 4, "IV");
        vectors.put( 99, "IC");
        vectors.put( 101, "CI");
        
        Set<Integer> keys = vectors.keySet();
        for( Integer key : keys ) {
            String expectedValue = vectors.get(key);
            assertEquals( expectedValue, coder.fromInt(key));
        }
    }
    
    @Test
    public void testToInt() {
        RomanCoder coder = new RomanCoder();

        Map<String,Integer> vectors = new HashMap<String,Integer>();
        vectors.put("I", 1);
        vectors.put("II", 2);
        vectors.put("VI", 6);
        vectors.put("IV", 4);
        vectors.put("XXVI", 26);

        Set<String> keys = vectors.keySet();
        for( String key : keys ) {
            int expectedValue = vectors.get(key);
            assertEquals( expectedValue, coder.toInt(key));
        }
    }

    @Test
    public void testToIntExceptions() {
        RomanCoder coder = new RomanCoder();

        List<String> numbers = new ArrayList<String>();
        numbers.add("P");
        numbers.add("PII");
        
        for( String number : numbers ) {
            try{
                coder.toInt(number);
                fail( number + "did not throw illegal argument exception" );
            }
            catch ( IllegalArgumentException e ) {
                // We should get this exception
            }
            catch ( Exception e ) {
                fail( number + " threw the wrong kind of exception" );
            }
        }
    }    
}
