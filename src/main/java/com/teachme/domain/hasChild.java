package com.teachme.domain;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "hasChild")
public class hasChild {
    @Id
    @GeneratedValue
    private Long id;
    private Long source_nodeId;
    private Long target_nodeId;
    private Long rootId;

	@StartNode
	private Node source;

	@EndNode
	private Node target;

	public hasChild() { }

	public hasChild(Node source, Node target) {
        this.source = source;
        this.source_nodeId = source.getnodeId();

        this.target = target;
        this.target_nodeId = target.getnodeId();

        this.rootId = source.getrootId();
	}

	public Long getId() {
	    return id;
	}

	public Node getSource() {
	    return source;
	}

	public Node getTarget() {
	    return target;
    }
    
    public Long getsourceId() {
        return source_nodeId;
    }

    public Long gettargetId() {
        return target_nodeId;
    }
}