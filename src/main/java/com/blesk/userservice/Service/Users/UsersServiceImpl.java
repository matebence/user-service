package com.blesk.userservice.Service.Users;

import com.blesk.userservice.DAO.Users.UsersDAOImpl;
import com.blesk.userservice.Exception.UserServiceException;
import com.blesk.userservice.Model.Users;
import com.blesk.userservice.Value.Keys;
import com.blesk.userservice.Value.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UsersServiceImpl implements UsersService {

    private UsersDAOImpl usersDAO;

    @Autowired
    public UsersServiceImpl(UsersDAOImpl usersDAO) {
        this.usersDAO = usersDAO;
    }

    @Override
    @Transactional
    public Users createUser(Users users) {
        Users user = this.usersDAO.save(users);
        if (user == null)
            throw new UserServiceException(Messages.CREATE_USER, HttpStatus.NOT_FOUND);
        return user;
    }

    @Override
    @Transactional
    public Boolean deleteUser(Long userId, boolean su) {
        if (su) {
            Users users = this.usersDAO.get(Users.class, userId);
            if (users == null)
                throw new UserServiceException(Messages.GET_USER, HttpStatus.NOT_FOUND);
            if (!this.usersDAO.delete("users", "user_id", userId))
                throw new UserServiceException(Messages.DELETE_USER, HttpStatus.NOT_FOUND);
            return true;
        } else {
            Users users = this.usersDAO.get(userId, false);
            if (users == null)
                throw new UserServiceException(Messages.GET_USER, HttpStatus.NOT_FOUND);
            return this.usersDAO.softDelete(users);
        }
    }

    @Override
    @Transactional
    public Boolean updateUser(Users users) {
        if (!this.usersDAO.update(users))
            throw new UserServiceException(Messages.UPDATE_USER, HttpStatus.NOT_FOUND);
        return true;
    }

    @Override
    @Transactional
    public Users getUser(Long userId, boolean su) {
        if (su) {
            return this.usersDAO.get(Users.class, userId);
        } else {
            return this.usersDAO.get(userId, false);
        }
    }

    @Override
    @Transactional
    public Users findUserByFirstName(String firstName, boolean isDeleted) {
        Users users = this.usersDAO.getItemByColumn("firstName", firstName, isDeleted);
        if (users == null)
            throw new UserServiceException(Messages.GET_ALL_USERS, HttpStatus.NOT_FOUND);

        return users;
    }

    @Override
    @Transactional
    public Users findUserByLastName(String lastName, boolean isDeleted) {
        Users users = this.usersDAO.getItemByColumn("lastName", lastName, isDeleted);
        if (users == null)
            throw new UserServiceException(Messages.GET_ALL_USERS, HttpStatus.NOT_FOUND);

        return users;
    }

    @Override
    @Transactional
    public List<Users> getAllUsers(int pageNumber, int pageSize, boolean su) {
        if (su) {
            return this.usersDAO.getAll(Users.class, pageNumber, pageSize);
        } else {
            return this.usersDAO.getAll(pageNumber, pageSize, false);
        }
    }

    @Override
    @Transactional
    public Map<String, Object> searchForUser(HashMap<String, HashMap<String, String>> criteria, boolean su) {
        if (su) {
            return this.usersDAO.searchBy(Users.class, criteria, Integer.parseInt(criteria.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)));
        } else {
            return this.usersDAO.searchBy(criteria, Integer.parseInt(criteria.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)), false);
        }
    }
}