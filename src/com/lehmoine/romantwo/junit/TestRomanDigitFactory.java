package com.lehmoine.romantwo.junit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.lehmoine.romantwo.RomanDigitFactory;
import com.lehmoine.romantwo.RomanDigitType;


public class TestRomanDigitFactory {

	@Before
	public void setUp() {
		
	}
	
	@After
	public void tearDown() {

	}


    @Test
    public void testOne() {
        RomanDigitType t = RomanDigitFactory.getDigitType('I');
        assertEquals(RomanDigitType.I, t);
        
    }

    @Test
    public void testTwo() {
        RomanDigitType t = RomanDigitFactory.getDigitType('V');
        assertEquals(RomanDigitType.V, t);
        
    }

    @Test
    public void testThree() {
        try {
            
            @SuppressWarnings("unused")
            RomanDigitType t = RomanDigitFactory.getDigitType('P');
            fail( "exception was not generated");
        }
        catch( Exception e ) {
            // we pass if we get an exception
        }
        
    }

}
