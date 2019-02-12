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

    //deletes multiInformation for given nodeId and Answer id
    String delete = "MATCH (n1:Node {nodeId: {nodeId}})-[:hasInformation]->(n2:Information)-[:MULTI_INFORMATION]->(n3:multiInformation {idOfNodes: {answer_id}}) RETURN n3";

    // returns one specific information not equal to nodeId
    String specInformation = "MATCH (n1:multiInformation {idOfNodes: {nodeId}})<-[r1:MULTI_INFORMATION]-(:Information)<-[r2:hasInformation]-(n2:Node) WHERE n2.nodeId <> toInteger({nodeId}) RETURN n1";
    
    @Query(information)
    List<multiInformation> getAllInformation(@Param("nodeId") Long id);

    @Query(informationDetailed)
    Optional<multiInformation> getOneInformation(@Param("nodeId") Long id, @Param("answer_id") String answer_id);

    @Query(delete)
    void deleteAnswer(@Param("nodeId") Long nodeId, @Param("answer_id") String answer_id);

    @Query(specInformation)
    Optional<multiInformation> getSpecificInformation(@Param("nodeId") String idOfNodes);
}