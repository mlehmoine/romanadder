package com.lehmoine.romantwo.junit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.lehmoine.romantwo.RomanUtil;

public class TestRomanUtil {

    @Before
    public void setUp() {
        
    }
    
    @After
    public void tearDown() {

    }

    @Test
    public void testOne() {
        assertEquals( 1, RomanUtil.getRomanValue('I') );
        assertEquals( 5, RomanUtil.getRomanValue('V') );
        assertEquals( 10, RomanUtil.getRomanValue('X') );
        assertEquals( 50, RomanUtil.getRomanValue('L') );
        assertEquals( 100, RomanUtil.getRomanValue('C') );
        assertEquals( 500, RomanUtil.getRomanValue('D') );
        assertEquals( 1000, RomanUtil.getRomanValue('M') );
    }
    
    @Test
    public void testTwo() {
        try {
            RomanUtil.getRomanValue('P');            
            fail( "missing exception for illegal argument");
        }
        catch( IllegalArgumentException e ) {
            // We should have gotten an exception
        }
    }
}
