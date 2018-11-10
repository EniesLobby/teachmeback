package com.teachme.repositories;
import java.util.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import com.teachme.domain.*;


@CrossOrigin(origins = "http://localhost:4200")
public interface informationRepository extends PagingAndSortingRepository<Information, Long> {
    Optional<Information> findBynodeId(@Param("nodeId") Long id);
}