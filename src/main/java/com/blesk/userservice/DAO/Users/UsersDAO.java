package com.blesk.userservice.DAO.Users;

import com.blesk.userservice.DAO.DAO;
import com.blesk.userservice.Model.Users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UsersDAO extends DAO<Users> {

    Boolean softDelete(Users users);

    Users get(Long id);

    List<Users> getAll(int pageNumber, int pageSize);

    Users getItemByColumn(String column, String value);

    Map<String, Object> searchBy(HashMap<String, HashMap<String, String>> criterias);
}