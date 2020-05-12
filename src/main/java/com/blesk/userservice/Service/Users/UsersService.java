package com.blesk.userservice.Service.Users;

import com.blesk.userservice.Model.Users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UsersService {

    Users createUser(Users users);

    Boolean deleteUser(Users users, boolean su);

    Boolean updateUser(Users users);

    Users getUser(Long accountId, boolean su);

    Users findUserByFirstName(String firstName, boolean isDeleted);

    Users findUserByLastName(String lastName, boolean isDeleted);

    List<Users> getAllUsers(int pageNumber, int pageSize, boolean su);

    Map<String, Object> searchForUser(HashMap<String, HashMap<String, String>> criteria, boolean su);
}