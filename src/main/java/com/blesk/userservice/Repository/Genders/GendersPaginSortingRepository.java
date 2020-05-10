package com.blesk.userservice.Repository.Genders;

import com.blesk.userservice.Model.Genders;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GendersPaginSortingRepository extends PagingAndSortingRepository<Genders, Long> {
}