package com.teachme.repositories;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import com.teachme.domain.*;

public interface UserRepository extends PagingAndSortingRepository<User, String> {
    
    User findByEmail(@Param("email") String email);
}