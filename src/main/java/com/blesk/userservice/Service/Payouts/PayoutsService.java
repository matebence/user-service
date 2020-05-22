package com.blesk.userservice.Service.Payouts;

import com.blesk.userservice.Model.Payouts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PayoutsService {

    Payouts createPayout(Payouts payouts, boolean su);

    Boolean deletePayout(Payouts payouts, boolean su);

    Boolean updatePayout(Payouts payout, Payouts payouts);

    Payouts getPayout(Long payoutId, boolean su);

    Payouts findPayoutByIban(String iban, boolean isDeleted);

    List<Payouts> getAllPayouts(int pageNumber, int pageSize, boolean su);

    Map<String, Object> searchForPayout(HashMap<String, HashMap<String, String>> criteria, boolean su);
}