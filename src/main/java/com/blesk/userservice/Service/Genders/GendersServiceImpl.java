package com.blesk.userservice.Service.Genders;

import com.blesk.userservice.DAO.Genders.GendersDAO;
import com.blesk.userservice.DAO.Genders.GendersDAOImpl;
import com.blesk.userservice.Exception.UserServiceException;
import com.blesk.userservice.Model.Genders;
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
public class GendersServiceImpl implements GendersService {

    private GendersDAOImpl gendersDAO;

    @Autowired
    public GendersServiceImpl(GendersDAOImpl gendersDAO) {
        this.gendersDAO = gendersDAO;
    }

    @Override
    @Transactional
    public Genders createGender(Genders genders) {
        Genders gender = this.gendersDAO.save(genders);
        if (gender == null)
            throw new UserServiceException(Messages.CREATE_GENDER, HttpStatus.NOT_FOUND);
        return gender;
    }

    @Override
    @Transactional
    public Boolean deleteGender(Long genderId) {
        Genders genders = this.gendersDAO.get(GendersDAO.class, genderId);
        if (genders == null)
            throw new UserServiceException(Messages.GET_GENDER, HttpStatus.NOT_FOUND);
        if (!this.gendersDAO.delete("genders", "gender_id", genderId))
            throw new UserServiceException(Messages.DELETE_GENDER, HttpStatus.NOT_FOUND);
        return true;
    }

    @Override
    @Transactional
    public Boolean updateGender(Genders genders) {
        if (!this.gendersDAO.update(genders))
            throw new UserServiceException(Messages.UPDATE_GENDER, HttpStatus.NOT_FOUND);
        return true;
    }

    @Override
    @Transactional
    public Genders findGenderByName(String name) {
        Genders genders = this.gendersDAO.getItemByColumn(Genders.class, "name", name);
        if (genders == null)
            throw new UserServiceException(Messages.GET_ALL_GENDERS, HttpStatus.NOT_FOUND);

        return genders;
    }

    @Override
    @Transactional
    public List<Genders> getAllGenders(int pageNumber, int pageSize) {
        List<Genders> genders = this.gendersDAO.getAll(Genders.class, pageNumber, pageSize);
        if (genders.isEmpty())
            throw new UserServiceException(Messages.GET_ALL_GENDERS, HttpStatus.NOT_FOUND);
        return genders;
    }

    @Override
    @Transactional
    public Map<String, Object> searchForGender(HashMap<String, HashMap<String, String>> criteria) {
        if (criteria.get(Keys.PAGINATION) == null)
            throw new UserServiceException(Messages.PAGINATION_ERROR, HttpStatus.NOT_FOUND);

        Map<String, Object> genders = this.gendersDAO.searchBy(Genders.class, criteria, Integer.parseInt(criteria.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)));

        if (genders == null || genders.isEmpty())
            throw new UserServiceException(Messages.GET_ALL_GENDERS, HttpStatus.NOT_FOUND);

        return genders;
    }

    @Override
    @Transactional
    public Genders getGender(Long genderId) {
        Genders gender = this.gendersDAO.get(Genders.class, genderId);
        if (gender == null)
            throw new UserServiceException(Messages.GET_ALL_GENDERS, HttpStatus.NOT_FOUND);
        return gender;
    }
}