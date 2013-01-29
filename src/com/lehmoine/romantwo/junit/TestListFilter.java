package com.lehmoine.romantwo.junit;

import static org.junit.Assert.*;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.lehmoine.romantwo.IFilterCondition;
import com.lehmoine.romantwo.ListFilter;

public class TestListFilter {
    @Before
    public void setUp() {
        
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testOne() {

        ListFilter<Character> f = new ListFilter<Character>();
        
        AbstractList<Character> list = new ArrayList<Character>();
        list.add('A');
        list.add('A');
        list.add('B');
        list.add('C');
        list.add('A');
        list.add('D');
        list.add('B');

        
        List<Character> sublist = f.filter(list, new IFilterCondition<Character>(){

            @Override
            public boolean include(Character element) {
                return element.equals('A');
            }});
        
        assertEquals( 3, sublist.size() );
    }
}
