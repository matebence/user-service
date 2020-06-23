package com.blesk.userservice.DAO.Users;

import com.blesk.userservice.DAO.DAOImpl;
import com.blesk.userservice.Model.Users;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

@Repository
public class UsersDAOImpl extends DAOImpl<Users> implements UsersDAO {

    @Override
    public Boolean update(Users users) {
        Session session = this.entityManager.unwrap(Session.class);
        try {
            session.merge(users);
        } catch (ConstraintViolationException e) {
            throw e;
        } catch (Exception e) {
            session.clear();
            session.close();
            return false;
        }
        return true;
    }
}