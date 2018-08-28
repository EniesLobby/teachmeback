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

    String cypherQuery = "MATCH p = (n:Node {nodeId:{id}})-[:hasChild *0..]->(child) WITH COLLECT(p) AS ps CALL apoc.convert.toTree(ps) yield value RETURN apoc.convert.toJson(value)";
    String cypherQueryCT = "MATCH (n:Node { nodeId:{id} })-[rel:hasChild *0..]->(child: Node) RETURN child.nodeId, rel";

    String nodes = "MATCH p=(n:Node { rootId: {id} })-[rel:hasChild]-() RETURN n"; //returns only nodes List<Node>
    String relations = "MATCH p = (a)-[rel:hasChild]-(b) RETURN a.nodeId as nodeId, rel.source_nodeId as sourceId, rel.target_nodeId as targetId, rel.rootId as rootId";

    /**
 * class ctResult {
    Long nodeId;
    Long sourceId;
    Long targetId;
    Long rootId;
} */
    //@Query("MATCH (n:Node {nodeId: {id}})-[rel:hasChild *0..]->(child: Node) RETURN rel, child")
    @Query(relations)
    String tree(@Param("id") Long id);

    @Query(relations)
    List<ctResult> treemap(@Param("id") Long id); 

    @Query(nodes)
    List<Node> treeCT(@Param("id") Long id);
}



/** Example of tree
 * 
 * [
 * {"nodeId":51,"source":49,"target":51},
 * {"nodeId":48,"source":48,"target":50},
 * {"nodeId":48,"source":48,"target":49},
 * {"nodeId":50,"source":48,"target":50},
 * {"nodeId":52,"source":49,"target":52},
 * {"nodeId":49,"source":49,"target":52},
 * {"nodeId":49,"source":49,"target":51},
 * {"nodeId":49,"source":48,"target":49}
 * ]
 * 
 * 
 * var data = [ 
 // nodes
 { data: { id: '1', question: "ae", answer: "ae", information: "ae" } },
 { data: { id: '2', question: "ae", answer: "ae", information: "ae" } },
 { data: { id: '3', question: "ae", answer: "ae", information: "ae" } },
 
 // edges
 { data: { id: '1-2', source: '1', target: '2' } },
 { data: { id: '1-3', source: '1', target: '3' } }

];
 */