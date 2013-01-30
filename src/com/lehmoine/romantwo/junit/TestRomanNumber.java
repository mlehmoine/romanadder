package com.lehmoine.romantwo.junit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.lehmoine.romantwo.RomanNumber;
import static org.junit.Assert.*;

public class TestRomanNumber {

    @Before
    public void setUp() {
        
    }
    
    @After
    public void tearDown() {

    }
    
    @Test
    public void testOne() {
        RomanNumber n = new RomanNumber("I");
        assertEquals( "I", n.format() );
    }

    @Test
    public void testInvalid() {
        try {            
            @SuppressWarnings("unused")
            RomanNumber n = new RomanNumber("butt");
            fail( "never get here");
        } 
        catch( Exception e ) {
            // we pass if we got an exception
        }
    }
    
    @Test
    public void testValidate() {
        RomanNumber n = new RomanNumber("I");
        assertEquals(true, n.validate() );
    }

    @Test
    public void testStaticValidate() {
        assertEquals(true, RomanNumber.validate("XV"));
        assertEquals(false, RomanNumber.validate("butt"));
    }

}
