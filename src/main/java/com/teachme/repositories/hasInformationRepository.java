package com.teachme.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.neo4j.annotation.Query;
import com.teachme.domain.*;
import java.util.List;

public interface hasInformationRepository extends PagingAndSortingRepository<hasInformation, Long> {
    
}