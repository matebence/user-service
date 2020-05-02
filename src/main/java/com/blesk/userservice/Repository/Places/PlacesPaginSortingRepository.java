package com.blesk.userservice.Repository.Places;

import com.blesk.userservice.Model.Places;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlacesPaginSortingRepository extends PagingAndSortingRepository<Places, Long> {
}