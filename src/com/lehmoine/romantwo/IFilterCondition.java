package com.lehmoine.romantwo;

public interface IFilterCondition<T> {
    boolean include(T element);
}
