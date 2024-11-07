package org.asuazo.java.repository;

import java.util.List;


public interface Repository<T> {

    List<T> list();

    T byId(String dni);

    void save(T t);


}
