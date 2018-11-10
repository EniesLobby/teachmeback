package com.teachme.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.neo4j.annotation.Query;
import com.teachme.domain.*;
import java.util.List;

public interface hasChildRepository extends PagingAndSortingRepository<hasChild, Long> {
    
    String relations = 
    "MATCH p = (a)-[rel:hasChild]-(b) WHERE rel.rootId={id} WITH toString(rel.source_nodeId) + '-' + toString(rel.target_nodeId) " +
    "as id,rel.source_nodeId as source, rel.target_nodeId as target RETURN id, source, target";
    
    String test = 
    "MATCH p = (a)-[rel:hasChild]-(b) WHERE rel.rootId={id} RETURN rel";

    @Query(relations)
    List<ctResult> tree(@Param("id") Long id);
}