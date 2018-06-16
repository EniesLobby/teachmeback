package com.teachme.domain;

import java.util.*;

import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@RelationshipEntity(type = "hasChild")
public class hasChild {
    
    @Id @GeneratedValue private Long id;
    
    private Long rootId;
    private String hasChildId; //format source-target

    @StartNode
    private Node source;

    @EndNode
    private Node target;

    public hasChild(Node source, Node target) {
        this.source = source;
        this.target = target;
    }

    public void setSource(Node source) {
        this.source = source;
    }

    public Node getSource() {
        return this.source;
    }

    public void setTarget(Node target) {
        this.target = target;
    }

    public Node getTarget() {
        return this.target;
    }


}