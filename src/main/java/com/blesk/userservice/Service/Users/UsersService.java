package com.blesk.userservice.Service.Users;

import com.blesk.userservice.Model.Users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UsersService {

    Users createUser(Users users);

    Boolean deleteUser(Users users);

    Boolean updateUser(Users user, Users users);

    Users getUser(Long accountId);

    Users findUserByFirstName(String firstName);

    Users findUserByLastName(String lastName);

    List<Users> getAllUsers(int pageNumber, int pageSize);

    List<Users> getUsersForJoin(List<Long> ids, String columName);

    Map<String, Object> searchForUser(HashMap<String, HashMap<String, String>> criterias);
}