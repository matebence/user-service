package com.blesk.userservice.Service.Payouts;

import com.blesk.userservice.Model.Payouts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PayoutsService {

    Payouts createPayout(Payouts payouts);

    Boolean deletePayout(Payouts payouts);

    Boolean updatePayout(Payouts payout, Payouts payouts);

    Payouts getPayout(Long payoutId);

    Payouts findPayoutByIban(String iban);

    List<Payouts> getAllPayouts(int pageNumber, int pageSize);

    List<Payouts> getPayoutsForJoin(List<Long> ids, String columName);

    Map<String, Object> searchForPayout(HashMap<String, HashMap<String, String>> criterias);
}