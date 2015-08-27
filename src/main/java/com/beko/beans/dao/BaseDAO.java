package com.beko.beans.dao;

import java.util.List;

/**
 * Created by ankovalenko on 4/30/2015.
 */
public interface BaseDAO<T> {

    public void create(T entity);
    public T findById(String id);
    public T findById(String id, boolean lazy);
    public T update(T entity);
    public List<T> getAll();
}
