package com.lehmoine.romantwo.junit;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.lehmoine.romantwo.ListGrouper;

public class TestListGrouper {
    @Before
    public void setUp() {
        
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testOne() {
        
        ListGrouper<Character> g = new ListGrouper<Character>();
        
        List<Character> list = new ArrayList<Character>();
        list.add('A');
        list.add('A');
        list.add('B');
        list.add('C');
        list.add('A');
        list.add('D');
        list.add('B');
        
        List<List<Character>> groups = g.groupBy(list, new Comparator<Character>(){

            @Override
            public int compare(Character o1, Character o2) {
                return o1.compareTo(o2);
            }});
        
        assertEquals( 4, groups.size() );
        
        for( List<Character> group : groups ) {
            if( group.get(0).equals('A') ) {
                assertEquals( 3, group.size() );
            }
            else if( group.get(0).equals('B') ) {
                assertEquals( 2, group.size() );
            }
            else if( group.get(0).equals('C') ) {
                assertEquals( 1, group.size() );
            }
            else if( group.get(0).equals('D') ) {
                assertEquals( 1, group.size() );
            }
            
            int i;
            for( i = 1; i < group.size(); i ++) {
                assertEquals( group.get(0), group.get(i));
            }
            
        }
    }
}
