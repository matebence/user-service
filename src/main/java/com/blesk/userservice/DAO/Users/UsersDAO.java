package com.blesk.userservice.DAO.Users;

import com.blesk.userservice.DAO.DAO;
import com.blesk.userservice.Model.Users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UsersDAO extends DAO<Users> {

    Boolean softDelete(Users users);

    Users get(Long id, boolean isDeleted);

    List<Users> getAll(int pageNumber, int pageSize, boolean isDeleted);

    Users getItemByColumn(String column, String value, boolean isDeleted);

    Map<String, Object> searchBy(HashMap<String, HashMap<String, String>> criterias, boolean isDeleted);
}