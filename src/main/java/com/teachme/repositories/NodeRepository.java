package com.teachme.repositories;

import java.util.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.neo4j.annotation.Query;
import com.teachme.domain.*;


@CrossOrigin(origins = "http://localhost:4200")
public interface NodeRepository extends PagingAndSortingRepository<Node, Long> {

    Optional<Node> findBynodeId(@Param("nodeId") Long id);
    List<Node> findByquestion(@Param("question") String Question);
    List<Node> findByanswer(@Param("answer") String Answer);
    List<Node> findByinformation(@Param("information") String information);

    String cypherQuery = "MATCH p=(n:Node {nodeId:{id}})-[:hasChild *0..]->(child) WITH COLLECT(p) AS ps CALL apoc.convert.toTree(ps) yield value RETURN apoc.convert.toJson(value)";

    //@Query("MATCH (n:Node {nodeId: {id}})-[rel:hasChild *0..]->(child: Node) RETURN rel, child")
    @Query(cypherQuery)
    String tree(@Param("id") Long id);
}