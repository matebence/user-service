package com.blesk.userservice.Repository.Payouts;

import com.blesk.userservice.Model.Payouts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayoutsCrudRepository extends CrudRepository<Payouts, Long> {
}