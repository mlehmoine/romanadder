package com.lehmoine.romantwo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ListGrouper<T> {
    public ListGrouper() {
        
    }
    
    public List<List<T>> groupBy( List<T> list, Comparator<T> comparator) {
        List<List<T>> result = new ArrayList<List<T>>();
        
        
        for( T element : list ) {
            boolean inserted = false;

            for( List<T> resultList : result ) {
                if( comparator.compare(element, resultList.get(0)) == 0) {
                    resultList.add(element);
                    inserted = true;
                    break;
                }
            }
            
            if( inserted == false ) {
                List<T> newList = new ArrayList<T>();
                newList.add(element);
                result.add(newList);
            }            
        }
        
        return result;
    }    
}
