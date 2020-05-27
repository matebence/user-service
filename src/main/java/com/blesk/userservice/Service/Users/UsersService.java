package com.blesk.userservice.Service.Users;

import com.blesk.userservice.Model.Users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UsersService {

    Users createUser(Users users);

    Boolean deleteUser(Users users, boolean su);

    Boolean updateUser(Users user, Users users);

    Users getUser(Long accountId, boolean su);

    Users findUserByFirstName(String firstName, boolean su);

    Users findUserByLastName(String lastName, boolean su);

    List<Users> getAllUsers(int pageNumber, int pageSize, boolean su);

    Map<String, Object> searchForUser(HashMap<String, HashMap<String, String>> criterias, boolean su);
}