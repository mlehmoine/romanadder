package com.lehmoine.romantwo;

import java.util.ArrayList;
import java.util.AbstractList;

public class ListFilter<T> {

    public AbstractList<T> filter( AbstractList<T> list, IFilterCondition<T> fc ) {
        AbstractList<T> result = new ArrayList<T>();
        for( T element : list ) {
            if( fc.include(element) == true ) {
                result.add(element);
            }
        }
        
        return result;
    }
}
