package com.blesk.userservice.DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DAO<T> {

    T save(T t);

    Boolean update(T t);

    Boolean delete(String entity, String IdColumn, Long id);

    T get(Class c, Long id);

    List<T> getAll(Class c, int pageNumber, int pageSize);

    T getItemByColumn(Class c, String column, String value);

    Map<String, Object> searchBy(Class c, HashMap<String, HashMap<String, String>> criterias, int pageNumber);
}