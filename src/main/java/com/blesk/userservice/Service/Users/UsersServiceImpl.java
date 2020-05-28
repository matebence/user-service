package com.blesk.userservice.Service.Users;

import com.blesk.userservice.DAO.Users.UsersDAOImpl;
import com.blesk.userservice.Model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersDAOImpl usersDAO;

    public UsersServiceImpl() {
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Users createUser(Users users) {
        return this.usersDAO.save(users);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean deleteUser(Users users, boolean su) {
        if (su) {
            return this.usersDAO.delete("users", "account_id", users.getAccountId());
        } else {
            return this.usersDAO.softDelete(users);
        }
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean updateUser(Users user, Users users) {
        user.setAccountId(users.getAccountId());
        user.setFirstName(users.getFirstName());
        user.setLastName(users.getLastName());
        user.setGender(users.getGender());
        user.setBalance(users.getBalance());
        user.setTel(users.getTel());
        user.setImg(users.getImg());
        user.getPlaces().setCountry(users.getPlaces().getCountry());
        user.getPlaces().setRegion(users.getPlaces().getRegion());
        user.getPlaces().setDistrict(users.getPlaces().getDistrict());
        user.getPlaces().setPlace(users.getPlaces().getPlace());
        user.getPlaces().setStreet(users.getPlaces().getStreet());
        user.getPlaces().setZip(users.getPlaces().getZip());
        user.getPlaces().setCode(users.getPlaces().getCode());

        return this.usersDAO.update(user);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Users getUser(Long accountId, boolean su) {
        if (su) {
            return this.usersDAO.getItemByColumn(Users.class, "accountId", accountId.toString());
        } else {
            return this.usersDAO.getItemByColumn("accountId", accountId.toString());
        }
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Users findUserByFirstName(String firstName, boolean su) {
        if (su) {
            return this.usersDAO.getItemByColumn(Users.class, "firstName", firstName);
        } else{
            return this.usersDAO.getItemByColumn("firstName", firstName);
        }
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Users findUserByLastName(String lastName, boolean su) {
        if (su) {
            return this.usersDAO.getItemByColumn(Users.class, "lastName", lastName);
        } else{
            return this.usersDAO.getItemByColumn("lastName", lastName);
        }
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public List<Users> getAllUsers(int pageNumber, int pageSize, boolean su) {
        if (su) {
            return this.usersDAO.getAll(Users.class, pageNumber, pageSize);
        } else {
            return this.usersDAO.getAll(pageNumber, pageSize);
        }
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Map<String, Object> searchForUser(HashMap<String, HashMap<String, String>> criterias, boolean su) {
        if (su) {
            return this.usersDAO.searchBy(Users.class, criterias);
        } else {
            return this.usersDAO.searchBy(criterias);
        }
    }
}