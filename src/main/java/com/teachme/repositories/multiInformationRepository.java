package com.teachme.repositories;
import java.util.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.neo4j.annotation.Query;
import com.teachme.domain.*;


@CrossOrigin(origins = "http://localhost:4200")
public interface multiInformationRepository extends PagingAndSortingRepository<multiInformation, Long> {
    
    // returns multiInformation for given nodeId
    String information = "MATCH (n1:multiInformation)<-[r1:MULTI_INFORMATION]-(:Information)<-[r2:hasInformation]-(n2:Node {nodeId: {nodeId}}) RETURN n1";

    // returns multiInformation for given nodeId and Answer Id
    String informationDetailed = "MATCH (n1:multiInformation {idOfNodes: {answer_id}})<-[r1:MULTI_INFORMATION]-(:Information)<-[r2:hasInformation]-(n2:Node {nodeId: {nodeId}}) RETURN n1";


    @Query(information)
    List<multiInformation> getAllInformation(@Param("nodeId") Long id);

    @Query(informationDetailed)
    Optional<multiInformation> getOneInformation(@Param("nodeId") Long id, @Param("answer_id") String answer_id);
}