package com.blesk.userservice.Repository.Payments;

import com.blesk.userservice.Model.Payments;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentsCrudRepository extends CrudRepository<Payments, Long> {
}