package com.blesk.userservice.Service.Genders;

import com.blesk.userservice.DAO.Genders.GendersDAOImpl;
import com.blesk.userservice.Model.Genders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GendersServiceImpl implements GendersService {

    private GendersDAOImpl gendersDAO;

    @Autowired
    public GendersServiceImpl(GendersDAOImpl gendersDAO) {
        this.gendersDAO = gendersDAO;
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Genders createGender(Genders genders) {
        return this.gendersDAO.save(genders);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean deleteGender(Genders genders) {
        return this.gendersDAO.delete(genders);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean updateGender(Genders gender, Genders genders) {
        gender.setName(genders.getName());
        return this.gendersDAO.update(gender);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Genders findGenderByName(String name) {
        return this.gendersDAO.getItemByColumn(Genders.class, "name", name);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public List<Genders> getAllGenders(int pageNumber, int pageSize) {
        return this.gendersDAO.getAll(Genders.class, pageNumber, pageSize);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Map<String, Object> searchForGender(HashMap<String, HashMap<String, String>> criterias) {
        return this.gendersDAO.searchBy(Genders.class, criterias);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Genders getGender(Long genderId) {
        return this.gendersDAO.get(Genders.class, "genderId", genderId);
    }
}