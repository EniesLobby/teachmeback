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
    
/** class ctResult {
        String id;
        Long sourceId;
        Long targetId 
    } 
    
    MATCH (a:USER)-[:owns]->(b:ALBUM)-[:CONTAINS]->(c:PHOTO)
    WITH a,b,{url: c.name} as c_photos
    WITH a,{album: b.name , photos: collect(c_photos)} as b_albums
    WITH {name: a.name, albums: collect(b_albums)} as a_users
    RETURN {users: collect(a_users)}

    MATCH (a:User)
    WITH {user: str(a)} as users
    RETURN {users: collect(users)}
    
    */

    @Query(relations)
    List<ctResult> tree(@Param("id") Long id);
}