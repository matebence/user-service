package com.blesk.userservice.DAO.Payments;

import com.blesk.userservice.DAO.DAO;
import com.blesk.userservice.Model.Payments;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PaymentsDAO extends DAO<Payments> {

    Boolean softDelete(Payments payments);

    Payments getItemByColumn(String column, String value);

    List<Payments> getAll(int pageNumber, int pageSize);

    Map<String, Object> searchBy(HashMap<String, HashMap<String, String>> criterias);
}