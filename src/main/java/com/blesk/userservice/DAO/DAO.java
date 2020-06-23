package com.blesk.userservice.DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DAO<T> {

    T save(T t);

    Boolean update(T t);

    Boolean delete(T t);

    T get(Class<T> c, String column, Long id);

    List<T> getAll(Class<T> c, int pageNumber, int pageSize);

    T getItemByColumn(Class<T> c, String column, String value);

    List<T> getJoinValuesByColumn(Class<T> c, List<Long> ids, String columName);

    Map<String, Object> searchBy(Class<T> c, HashMap<String, HashMap<String, String>> criterias);
}