package com.teachme.repositories;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import com.teachme.domain.*;

public interface CounterRepository extends PagingAndSortingRepository<Counter, Long> {
    
    Counter findBylabel(@Param("label") String label);
}