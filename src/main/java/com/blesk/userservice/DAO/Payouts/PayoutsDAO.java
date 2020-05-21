package com.blesk.userservice.DAO.Payouts;

import com.blesk.userservice.DAO.DAO;
import com.blesk.userservice.Model.Payouts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PayoutsDAO extends DAO<Payouts> {

    Boolean softDelete(Payouts payouts);

    Payouts getItemByColumn(String column, String value, boolean isDeleted);

    List<Payouts> getAll(int pageNumber, int pageSize, boolean isDeleted);

    Map<String, Object> searchBy(HashMap<String, HashMap<String, String>> criterias, int pageNumber, boolean isDeleted);
}