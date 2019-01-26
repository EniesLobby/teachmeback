package com.teachme.domain;
import java.util.*;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Information {

	@Id @GeneratedValue private Long Id;

    private Long nodeId;
    private Long rootId;
    private String note;

    private List<multiInformation> multiInformation = new ArrayList<>();

    public void setNewMultiInformation(String key, String value, String notes) {
        this.multiInformation.add(new multiInformation(key, value, notes));
    }

    public List<multiInformation> getMultiInformation() {
        return this.multiInformation;
    }

    public Information() { }

    public Information(String note, Long rootId, Long nodeId) {
        this.note = note;
        this.nodeId = nodeId;
        this.rootId = rootId;
    }

    public void setNote(String notes) {
        this.note = notes;
    }

    public String getNote() {
        return this.note;
    }

}