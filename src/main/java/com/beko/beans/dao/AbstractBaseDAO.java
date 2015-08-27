package com.beko.beans.dao;

import com.beko.beans.entity.BaseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by ankovalenko on 4/30/2015.
 */
@Transactional
public abstract class AbstractBaseDAO<T> implements BaseDAO<T>{

    @PersistenceContext
    protected EntityManager em;

    private Class<T> persistentClass;

    public AbstractBaseDAO(){
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }


    @Override
    public void create(T entity) {
        em.persist(entity);
    }

    @Override
    public T findById(String id) {
        return em.find(persistentClass, id);
    }

    @Override
    public T findById(String id, boolean lazy) {
        T entity = em.find(persistentClass, id);
        if(entity!=null && entity instanceof BaseEntity){
            ((BaseEntity)entity).lazyInit();
        }
        return entity;
    }

    @Override
    public T update(T entity) {
        return em.merge(entity);
    }

    @Override
    public List<T> getAll() {
        return em.createQuery("from "+persistentClass.getCanonicalName()+" t").getResultList();
    }
}
