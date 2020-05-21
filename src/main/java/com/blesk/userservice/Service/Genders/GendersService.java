package com.blesk.userservice.Service.Genders;

import com.blesk.userservice.Model.Genders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface GendersService {

    Genders createGender(Genders genders);

    Boolean deleteGender(Long genderId);

    Boolean updateGender(Genders gender, Genders genders);

    Genders getGender(Long genderId);

    Genders findGenderByName(String name);

    List<Genders> getAllGenders(int pageNumber, int pageSize);

    Map<String, Object> searchForGender(HashMap<String, HashMap<String, String>> criteria);
}