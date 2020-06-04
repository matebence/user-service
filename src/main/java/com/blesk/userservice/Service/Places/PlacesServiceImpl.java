package com.blesk.userservice.Service.Places;

import com.blesk.userservice.DAO.Places.PlacesDAOImpl;
import com.blesk.userservice.Model.Places;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlacesServiceImpl implements PlacesService {

    private PlacesDAOImpl placesDAO;

    @Autowired
    public PlacesServiceImpl(PlacesDAOImpl placesDAO) {
        this.placesDAO = placesDAO;
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Places createPlace(Places places) {
        return this.placesDAO.save(places);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean deletePlace(Places places) {
        return this.placesDAO.delete(places);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean updatePlace(Places places) {
        return this.placesDAO.update(places);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Places findPlaceByName(String name) {
        return this.placesDAO.getItemByColumn(Places.class, "name", name);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public List<Places> getAllPlaces(int pageNumber, int pageSize) {
        return this.placesDAO.getAll(Places.class, pageNumber, pageSize);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Map<String, Object> searchForPlace(HashMap<String, HashMap<String, String>> criterias) {
        return this.placesDAO.searchBy(Places.class, criterias);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Places getPlace(Long placeId) {
        return this.placesDAO.get(Places.class, "placeId", placeId);
    }
}