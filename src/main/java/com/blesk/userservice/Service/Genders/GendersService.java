package com.blesk.userservice.Service.Genders;

import com.blesk.userservice.Model.Genders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface GendersService {

    Genders createGender(Genders genders);

    Boolean deleteGender(Genders genders);

    Boolean updateGender(Genders gender, Genders genders);

    Genders getGender(Long genderId);

    Genders findGenderByName(String name);

    List<Genders> getAllGenders(int pageNumber, int pageSize);

    List<Genders> getGendersForJoin(List<Long> ids, String columName);

    Map<String, Object> searchForGender(HashMap<String, HashMap<String, String>> criterias);
}