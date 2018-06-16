package com.teachme.repositories;

import java.util.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.neo4j.annotation.Query;
import com.teachme.domain.*;


@CrossOrigin(origins = "http://localhost:4200")
public interface hasChildRepository extends PagingAndSortingRepository<Node, Long> {

}



/** Example of tree
 * var data = [
    
 // nodes
 { data: { id: '1', question: "ae", answer: "ae", information: "ae" } },
 { data: { id: '2', question: "ae", answer: "ae", information: "ae" } },
 { data: { id: '3', question: "ae", answer: "ae", information: "ae" } },
 
 // edges
 {
   data: {
     id: '1-2',
     source: '1',
     target: '2'
   }
 },
 
 {
     data: {
         id: '1-3',
         source: '1',
         target: '3'
     }
}

];
 */