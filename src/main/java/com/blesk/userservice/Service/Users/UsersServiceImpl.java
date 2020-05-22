package com.blesk.userservice.Service.Users;

import com.blesk.userservice.DAO.Users.UsersDAOImpl;
import com.blesk.userservice.Model.Users;
import com.blesk.userservice.Utilitie.Tools;
import com.blesk.userservice.Value.Keys;
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
            return this.usersDAO.delete("users", "user_id", users.getAccountId());
        } else {
            return this.usersDAO.softDelete(users);
        }
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean updateUser(Users user, Users users) {
        user.setAccountId(Tools.getNotNull(users.getAccountId(), user.getAccountId()));
        user.setFirstName(Tools.getNotNull(users.getFirstName(), user.getFirstName()));
        user.setLastName(Tools.getNotNull(users.getLastName(), user.getLastName()));
        user.setGender(Tools.getNotNull(users.getGender(), user.getGender()));
        user.setBalance(Tools.getNotNull(users.getBalance(), user.getBalance()));
        user.setTel(Tools.getNotNull(users.getTel(), user.getTel()));
        user.setImg(Tools.getNotNull(users.getImg(), user.getImg()));
        user.getPlaces().setCountry(Tools.getNotNull(users.getPlaces().getCountry(), user.getPlaces().getCountry()));
        user.getPlaces().setRegion(Tools.getNotNull(users.getPlaces().getRegion(), user.getPlaces().getRegion()));
        user.getPlaces().setDistrict(Tools.getNotNull(users.getPlaces().getDistrict(), user.getPlaces().getDistrict()));
        user.getPlaces().setPlace(Tools.getNotNull(users.getPlaces().getPlace(), user.getPlaces().getPlace()));
        user.getPlaces().setStreet(Tools.getNotNull(users.getPlaces().getStreet(), user.getPlaces().getStreet()));
        user.getPlaces().setZip(Tools.getNotNull(users.getPlaces().getZip(), user.getPlaces().getZip()));
        user.getPlaces().setCode(Tools.getNotNull(users.getPlaces().getCode(), user.getPlaces().getCode()));

        return this.usersDAO.update(user);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Users getUser(Long userId, boolean su) {
        if (su) {
            return this.usersDAO.getItemByColumn(Users.class, "userId", userId.toString());
        } else {
            return this.usersDAO.getItemByColumn("userId", userId.toString(), false);
        }
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Users findUserByFirstName(String firstName, boolean isDeleted) {
        return this.usersDAO.getItemByColumn("firstName", firstName, isDeleted);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Users findUserByLastName(String lastName, boolean isDeleted) {
        return this.usersDAO.getItemByColumn("lastName", lastName, isDeleted);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public List<Users> getAllUsers(int pageNumber, int pageSize, boolean su) {
        if (su) {
            return this.usersDAO.getAll(Users.class, pageNumber, pageSize);
        } else {
            return this.usersDAO.getAll(pageNumber, pageSize, false);
        }
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Map<String, Object> searchForUser(HashMap<String, HashMap<String, String>> criteria, boolean su) {
        if (su) {
            return this.usersDAO.searchBy(Users.class, criteria, Integer.parseInt(criteria.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)));
        } else {
            return this.usersDAO.searchBy(criteria, Integer.parseInt(criteria.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)), false);
        }
    }
}