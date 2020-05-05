package com.blesk.userservice.Service.Places;

import com.blesk.userservice.DAO.Places.PlacesDAOImpl;
import com.blesk.userservice.Exception.UserServiceException;
import com.blesk.userservice.Model.Places;
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
public class PlacesServiceImpl implements PlacesService {

    private PlacesDAOImpl placesDAO;

    @Autowired
    public PlacesServiceImpl(PlacesDAOImpl placesDAO) {
        this.placesDAO = placesDAO;
    }

    @Override
    @Transactional
    public Places createPlace(Places places) {
        Places place = this.placesDAO.save(places);
        if (place == null)
            throw new UserServiceException(Messages.CREATE_PLACE, HttpStatus.NOT_FOUND);
        return place;
    }

    @Override
    @Transactional
    public Boolean deletePlace(Long placeId) {
        Places places = this.placesDAO.get(Places.class, placeId);
        if (places == null)
            throw new UserServiceException(Messages.GET_PLACE, HttpStatus.NOT_FOUND);
        if (!this.placesDAO.delete("places", "place_id", placeId))
            throw new UserServiceException(Messages.DELETE_PLACE, HttpStatus.NOT_FOUND);
        return true;
    }

    @Override
    @Transactional
    public Boolean updatePlace(Places places) {
        if (!this.placesDAO.update(places))
            throw new UserServiceException(Messages.UPDATE_PLACE, HttpStatus.NOT_FOUND);
        return true;
    }

    @Override
    @Transactional
    public Places findPlaceByName(String name) {
        Places places = this.placesDAO.getItemByColumn(Places.class, "name", name);
        if (places == null)
            throw new UserServiceException(Messages.GET_ALL_PLACES, HttpStatus.NOT_FOUND);

        return places;
    }

    @Override
    @Transactional
    public List<Places> getAllPlaces(int pageNumber, int pageSize) {
        List<Places> places = this.placesDAO.getAll(Places.class, pageNumber, pageSize);
        if (places.isEmpty())
            throw new UserServiceException(Messages.GET_ALL_PLACES, HttpStatus.NOT_FOUND);
        return places;
    }

    @Override
    @Transactional
    public Map<String, Object> searchForPlace(HashMap<String, HashMap<String, String>> criteria) {
        if (criteria.get(Keys.PAGINATION) == null)
            throw new UserServiceException(Messages.PAGINATION_ERROR, HttpStatus.NOT_FOUND);

        Map<String, Object> places = this.placesDAO.searchBy(Places.class, criteria, Integer.parseInt(criteria.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)));

        if (places == null || places.isEmpty())
            throw new UserServiceException(Messages.GET_ALL_PLACES, HttpStatus.NOT_FOUND);

        return places;
    }

    @Override
    @Transactional
    public Places getPlace(Long placeId) {
        Places place = this.placesDAO.get(Places.class, placeId);
        if (place == null)
            throw new UserServiceException(Messages.GET_ALL_PLACES, HttpStatus.NOT_FOUND);
        return place;
    }
}