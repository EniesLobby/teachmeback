package com.teachme.domain;
import java.util.*;
import org.neo4j.ogm.annotation.*;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;


@RelationshipEntity(type = "hasInformation")
public class hasInformation {
    @Id @GeneratedValue
    private Long id;
    private Long nodeId;

    @StartNode
	private Node source;

	@EndNode
    private Information target;
    
    public hasInformation(Node source, Information target) {
        this.source = source;
        this.target = target;
    }
    
    public hasInformation() { }
}
