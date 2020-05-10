package com.blesk.userservice.Service.Places;

import com.blesk.userservice.Model.Places;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PlacesService {

    Places createPlace(Places places);

    Boolean deletePlace(Long placeId);

    Boolean updatePlace(Places places);

    Places getPlace(Long placeId);

    Places findPlaceByName(String name);

    List<Places> getAllPlaces(int pageNumber, int pageSize);

    Map<String, Object> searchForPlace(HashMap<String, HashMap<String, String>> criteria);
}